package com.android.temperaturesensor.notifications;

import java.util.Locale;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.temperaturesensor.MainActivity;
import com.android.temperaturesensor.R;
import com.android.temperaturesensor.StartupService;
import com.android.temperaturesensor.preferences.PreferenceUtils;

public class NotificationHandler {
	private static final int NOTIFICATION_ID = 10;
	// Notification handler singleton
	private static NotificationHandler nHandler;
	private static NotificationManager mNotificationManager;

	private NotificationHandler() {
	}

	/**
	 * Singleton pattern implementation
	 * 
	 * @return
	 */
	public static NotificationHandler getInstance(Context context) {
		if (nHandler == null) {
			nHandler = new NotificationHandler();
			mNotificationManager = (NotificationManager) context
					.getApplicationContext().getSystemService(
							Context.NOTIFICATION_SERVICE);
		}

		return nHandler;
	}

	/**
	 * Shows a simple notification
	 * 
	 * @param context
	 *            aplication context
	 */
	public void createSimpleNotification(Context context, String title,
			String text) {
		// Creates an explicit intent for an Activity
		Intent resultIntent = new Intent(context, MainActivity.class);

		// Creating a artifical activity stack for the notification activity
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);

		// Pending intent to the notification manager
		PendingIntent resultPending = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Building the notification
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher) // notification
																// icon
				.setContentTitle(title) // main title of the notification
				.setContentText(text) // notification text
				.setContentIntent(resultPending); // notification intent

		// mId allows you to update the notification later on.
		mNotificationManager.notify(123, mBuilder.build());
	}

	public void dismissNotification() {
		mNotificationManager.cancel(NOTIFICATION_ID);
	}
}
