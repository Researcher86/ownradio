#include "ownradio.h"
#include "ui_ownradio.h"

OwnRadio::OwnRadio(QWidget *parent) : QDialog(parent)
{
    ui.setupUi(this);
    // ������� ����������� �������� ������ ���� ����������
    setFixedSize(size());

    loadSettings();

    // �������������� ������ �������������
    m_player = new QMediaPlayer;
    m_player->setNotifyInterval(1000);
    //m_player->setVolume(50);
    QObject::connect(m_player, SIGNAL(positionChanged(qint64)), this, SLOT(positionUpdate(qint64)));
    QObject::connect(m_player, SIGNAL(mediaStatusChanged(QMediaPlayer::MediaStatus)), this, SLOT(mediaStatusChange(QMediaPlayer::MediaStatus)));
}

OwnRadio::~OwnRadio()
{
    delete m_player;

    saveSettings();
}

void OwnRadio::loadSettings()
{
    QSettings settings("MySoft", "OwnRadio");

    // ������ Id ����������. ��� ������ ������� ���������� ����� Id
    m_deviceId = settings.value("DeviceId").toString();
    if (m_deviceId.isEmpty())
    {
        m_deviceId = QUuid::createUuid().toString().mid(1, 36);
        settings.setValue("DeviceId", m_deviceId);
    }

    // ������ ����� �������. ��� ������ ������� ������������� �������� ��-���������
    m_serviceUrl = settings.value("ServiceUrl").toString();
    if (m_serviceUrl.isEmpty())
    {
        m_serviceUrl = "http://ownradio.ru/api";
        settings.setValue("ServiceUrl", m_serviceUrl);
    }

    // ������ Id �����
    m_nextTrackId = settings.value("NextTrackId").toString();
}

void OwnRadio::saveSettings()
{
    QSettings settings("MySoft", "OwnRadio");

    // ��������� Id ����� ��� ������ ��� ����������� ����������
    settings.setValue("NextTrackId", m_nextTrackId);
}

// ��������� ������� ������ "Play/Pause"
void OwnRadio::on_playButton_clicked()
{
    // ���� Id ����� ��� ������������ ������, �������� �������� ���
    if (m_nextTrackId == "")
        getNextTrackId();

    // ������ "Play"
    if (m_player->state() == QMediaPlayer::StoppedState)
    {
        // ���� Id ����� ������ - ����� ��������� �� ������ � ������ ������ �� ������
        if (m_nextTrackId == "")
        {
            ui.stateLabel->setText("stopped, error");
            return;
        }

        // ��������� ��������������� ����� � �������� Id
        m_player->setMedia(QUrl(m_serviceUrl + "/track/GetTrackByID/" + m_nextTrackId));
        m_player->play();
        ui.playButton->setText("Pause");
        ui.stateLabel->setText("playing");
        return;
    }

    // ������ "Pause"
    if (m_player->state() == QMediaPlayer::PlayingState)
    {
        m_player->pause();
        ui.playButton->setText("Play");
        ui.stateLabel->setText("paused");
        return;
    }

    // ������ "Play", � ������ ����� ����� �� �����
    if (m_player->state() == QMediaPlayer::PausedState)
    {
        m_player->play();
        ui.playButton->setText("Pause");
        ui.stateLabel->setText("playing");
        return;
    }
}

// ��������� ������� ������ "Stop"
void OwnRadio::on_stopButton_clicked()
{
    m_player->stop();
    ui.playButton->setText("Play");
    ui.stateLabel->setText("stopped");
}

// ��������� ������� ������ "Next"
void OwnRadio::on_nextButton_clicked()
{
    if (m_player->state() == QMediaPlayer::PlayingState)
        playNextTrack();
}

// ���������� ���������� �������
void OwnRadio::positionUpdate(qint64 position)
{
    qint64 minutesPosition = position / 1000 / 60;
    qint64 secondsPosition = position / 1000 % 60;
    qint64 minutesDuration = m_player->duration() / 1000 / 60;
    qint64 secondsDuration = m_player->duration() / 1000 % 60;

    QString sTime = QString("%1:%2 / %3:%4")
        .arg(minutesPosition, 2, 10, QLatin1Char('0'))
        .arg(secondsPosition, 2, 10, QLatin1Char('0'))
        .arg(minutesDuration, 2, 10, QLatin1Char('0'))
        .arg(secondsDuration, 2, 10, QLatin1Char('0'));

    ui.timeLabel->setText(sTime);
}

// ��������� ������� ����� ����� � ��������� ���������
void OwnRadio::mediaStatusChange(QMediaPlayer::MediaStatus status)
{
    // ��������������� �������� ����� �����, ��������� ��������� ����
    if (status == QMediaPlayer::EndOfMedia)
    {
        playNextTrack();
        return;
    }

    // ������
    if (status == QMediaPlayer::InvalidMedia)
    {
        ui.playButton->setText("Play");
        ui.stateLabel->setText("stopped, error");
        return;
    }
}

// ��������� Id ���������� �����
void OwnRadio::getNextTrackId()
{
    // ������ �������
    QString sRequestString = m_serviceUrl + "/track/GetNextTrackID/" + m_deviceId;

    // ������
    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkReply *reply = manager->get(QNetworkRequest(QUrl(sRequestString)));

    // ������� ���������� ���������� ����������� ��������
    QEventLoop wait;
    connect(reply, SIGNAL(finished()), &wait, SLOT(quit()));
    wait.exec();

    // ������� ������� � ����� � � ������ ���������� ������
    m_nextTrackId = reply->readAll().mid(1, 36);

    // ���������, ��� �������� ���������� Guid. ���� ��� - ������� ������
    QUuid trackId(m_nextTrackId);
    if (trackId.isNull())
        m_nextTrackId = "";
}

// ��������������� ���������� �����
void OwnRadio::playNextTrack()
{
    getNextTrackId();
    m_player->setMedia(QUrl(m_serviceUrl + "/track/GetTrackByID/" + m_nextTrackId));
    m_player->play();
}