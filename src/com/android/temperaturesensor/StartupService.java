package com.android.temperaturesensor;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class StartupService extends Service {

	public final static int START_SERVICE_ID = 100;

	@Override
	public void onCreate() {
		startForeground(START_SERVICE_ID, getNotificationIntent());
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private Notification getNotificationIntent() {
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

		PendingIntent intent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		int iconSmall = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this).setSmallIcon(iconSmall).setLargeIcon(bm)
				.setContentTitle("Tempreture service")
				.setContentText("Service").setAutoCancel(false)
				.setContentIntent(intent).setWhen(when)
				.setTicker("Start service");
		return builder.build();

	}
}
