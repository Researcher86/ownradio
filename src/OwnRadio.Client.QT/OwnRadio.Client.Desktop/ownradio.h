#ifndef OWNRADIO_H
#define OWNRADIO_H

#include <QtWidgets>
#include <QMediaPlayer>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include "ui_ownradio.h"

class OwnRadio : public QDialog
{
    Q_OBJECT

public:
    OwnRadio(QWidget *parent = 0);
    ~OwnRadio();

private:
    Ui::OwnRadioClass ui;

    // Id ����������
    QString m_deviceId;
    // Id ����� ��� ������������
    QString m_nextTrackId;

    // ����� �������
    QString m_serviceUrl;

    // ������ �������������
    QMediaPlayer *m_player;

private slots:
    void on_playButton_clicked();
    void on_stopButton_clicked();
    void on_nextButton_clicked();
    void positionUpdate(qint64 position);
    void mediaStatusChange(QMediaPlayer::MediaStatus status);

private:
    void loadSettings();
    void saveSettings();
    void getNextTrackId();
    void playNextTrack();
};

#endif // OWNRADIO_H
