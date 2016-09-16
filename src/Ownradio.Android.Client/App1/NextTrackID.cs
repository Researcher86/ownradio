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

namespace ownradio
{
	class NextTrackID : IGetNextTrackID
	{
		public NextTrackID() { }
		/// <summary>
		/// �������� ID ���������� � ������������ �����
		/// </summary>
		/// <param name="DeviceID">ID ����������</param>
		/// <param name="GUID">ID �����, ������� ������������</param>
		/// <param name="Method">����� ��������� �����</param>
		/// <param name="ListedTillTheEnd">��� �� ���� ������� �� �����</param>
		/// <returns>ID ���������� �����</returns>
		public String GetNextTrackID(String DeviceID, String GUID, String Method, bool ListedTillTheEnd)
		{
			Uri URLRequest = new Uri("http://radio.redoc.ru/api/TrackSource/NextTrack?userId=" + DeviceID + "&lastTrackId=" + GUID + "&lastTrackMethod=" + System.Net.WebUtility.UrlEncode(Method) + "&listedTillTheEnd=" + ListedTillTheEnd);
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(URLRequest);
			request.Method = "GET";
			HttpWebResponse response = (HttpWebResponse)request.GetResponse();
			String str;
			using (StreamReader stream = new StreamReader(response.GetResponseStream(), System.Text.Encoding.UTF8))
			{
				str = stream.ReadToEnd();
				stream.Close();
			}
			response.Close();
			String searchString = "\"TrackId\":\"";
			int startIndex = str.IndexOf(searchString) + searchString.Length;
			searchString = "\",\"Method\"";
			int endIndex = str.IndexOf(searchString);
			GUID = str.Substring(startIndex, endIndex - startIndex);
			//status.Text += "\n" + substring;
			return GUID;
		}
	}
}