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
using System.IO;
using System.Net;

namespace OwnRadio
{
	class Track : IGetTrack
	{
		public Track()
		{ }

		public String GetTrack(String GUID)
		{
			Uri downloadURL = new Uri("http://radio.redoc.ru/api/TrackSource/Play?trackId=" + GUID);
			WebClient webClient = new WebClient();
			String fileName = Path.GetTempFileName();
			fileName = fileName.Replace(".tmp", ".mp3");
			webClient.DownloadFile(downloadURL, fileName);
			webClient.Dispose();
			if (File.Exists(fileName) == false)
			{
				throw new Exception("�� ������ ������� ���� � id = " + GUID);
			}
			return fileName;
		}
	}
}