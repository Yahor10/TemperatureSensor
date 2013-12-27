package com.android.texmperaturesensore.application;

import com.android.temperaturesensor.email.GMailSender;
import com.android.temperaturesensor.notifications.NotificationHandler;
import com.android.temperaturesensor.preferences.PreferenceUtils;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class TemperatureApplication extends Application {

	private boolean serviceEnable = false;
	private Vibrator vibrator;
	private Ringtone ringtone;

	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryInfoReceiver, intentFilter);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		ringtone = RingtoneManager.getRingtone(getApplicationContext(),
				notification);
	}

	public static TemperatureApplication getApplication(Context context) {
		return (TemperatureApplication) context.getApplicationContext();
	}

	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {

		private float getTemperature(int temperature) {
			return (float) (temperature / 10);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			int temperature = intent.getIntExtra(
					BatteryManager.EXTRA_TEMPERATURE, 0);
			float currentTemperature = getTemperature(temperature);
			Log.v(null, "EXTRA_TEMPERATURE:" + currentTemperature);

			int maxTemperature = PreferenceUtils.getMaxTemperature(context);
			int minTemperature = PreferenceUtils.getMinTemperature(context);
			// TODO check warm,cold message;
			boolean testMode = PreferenceUtils.isTestMode(context);
			boolean serviceEnable = PreferenceUtils.isServiceEnable(context);

			if (testMode) {

				if (currentTemperature >= maxTemperature) {
					vibrator.vibrate(500);
					ringtone.play();
					NotificationHandler instance = NotificationHandler
							.getInstance(context);
					String message = PreferenceUtils.getWarmMessage(context);
					instance.createSimpleNotification(context,
							"Battery temperature sensor", message);
				}

				return;
			}

			if (serviceEnable) {

				boolean smsEnable = PreferenceUtils.isSmsEnable(context);
				if (smsEnable) {
					String message = ""; // cold,hot message
					String phoneNumber = PreferenceUtils
							.getPhoneNumber(context);
					if (!TextUtils.isEmpty(phoneNumber)) { // TODO check phone

						if (currentTemperature >= maxTemperature) {
							message = PreferenceUtils.getWarmMessage(context);
						} else if (currentTemperature <= minTemperature) {
							message = "Cold battery sensor message";
						}
						sendSMS(phoneNumber, message);
					}
				}

				boolean emailEnable = PreferenceUtils.isEmailEnable(context);
				if (emailEnable) {
					String message = ""; // cold,hot message
					String email = PreferenceUtils.getEmail(context);
					// TODO put offline validation

					if (currentTemperature >= maxTemperature) {
						message = PreferenceUtils.getWarmMessage(context);
					} else if (currentTemperature <= minTemperature) {
						message = "Cold battery sensor message";
					}
					
					if (!TextUtils.isEmpty(email)) {
						new SendEmail().execute(email, message);
					}
				}

				// TODO set twitter;

				if (currentTemperature >= maxTemperature) {
					vibrator.vibrate(500);
					ringtone.play();
				} else if (currentTemperature <= minTemperature) {
					vibrator.vibrate(500);
					ringtone.play();
				}

			}

		}
	};

	private void sendSMS(String phoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

	class SendEmail extends AsyncTask<String, String, Boolean> {

		ProgressDialog progressDialog;
		String email;

		public SendEmail() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {

			} else {
				Log.e(Constants.TAG, "EMAIL SEND FAILED");
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				GMailSender sender = new GMailSender("egosecure_minsk@tut.by",
						"egosecure2013");
				sender.sendMail("Temperature sensor message", params[1],
						params[0], params[0]);
				return true;

			} catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
			}

			return false; // To change body of implemented methods use File |
							// Settings | File Templates.
							// 12-26 15:36:39.434: V/(29755): adapter:2131296268
			// 12-26 15:36:39.434: V/(29755): view:16908308

		}
	}

}
