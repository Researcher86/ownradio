#include "ownradio.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    OwnRadio w;
    // ������ ����� ����������� ����, ����� ��������� ������ "��������/����������"
    w.setWindowFlags(Qt::Window);
    w.show();
    return a.exec();
}
