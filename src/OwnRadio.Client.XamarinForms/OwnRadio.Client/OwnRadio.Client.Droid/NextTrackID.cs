using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using System.Net;
using System.IO;

[assembly: Xamarin.Forms.Dependency(typeof(OwnRadio.Client.Droid.NextTrackID))]
namespace OwnRadio.Client.Droid
{
    class NextTrackID : IGetNextTrackID
    {
        [Register("NextTrackID")]

        public NextTrackID() { }
        /// <summary>
        /// �������� ID ���������� � ������������ �����
        /// </summary>
        /// <param name="DeviceID">ID ����������</param>
        /// <returns>ID ���������� �����</returns>
        public String GetNextTrackID(String DeviceID, out String Method)
        {
            try
            {
                Uri URLRequest = new Uri("http://ownradio.ru/api/track/GetNextTrackID/" + DeviceID);
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(URLRequest);
                request.UserAgent = "OwnRadioMobileClient";
                request.Method = "GET";
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                String str;
                using (StreamReader stream = new StreamReader(response.GetResponseStream(), System.Text.Encoding.UTF8))
                {
                    str = stream.ReadToEnd();
                    stream.Close();
                }
                response.Close();

                String trackID = str.Substring(1, 36); //������� ���������� �������
                Method = "Method";//������ ���� �� ����������
                return trackID;
            }
            catch (Exception ex)
            {
                throw;
            }
        }
    }
 }