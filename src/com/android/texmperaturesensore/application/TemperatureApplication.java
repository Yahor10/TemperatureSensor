package com.android.texmperaturesensore.application;

import com.android.temperaturesensor.preferences.PreferenceUtils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

public class TemperatureApplication extends Application {

	private boolean serviceEnable = false;

	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		Intent registerReceiver = registerReceiver(batteryInfoReceiver,
				intentFilter);

	}

	public static TemperatureApplication getApplication(Context context) {
		return (TemperatureApplication) context.getApplicationContext();
	}

	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
//			Log.v(null, "EXTRA_TEMPERATURE:" + level);

			boolean smsEnable = PreferenceUtils.isSmsEnable(context);
			if (smsEnable) {
				// cold ,hot message
				String phoneNumber = PreferenceUtils.getPhoneNumber(context);
				
				if (!TextUtils.isEmpty(phoneNumber)) { // TODO check phone pattern
					String message = "";
//					sendSMS(phoneNumber, message);
				}
			}
			
			boolean emailEnable = PreferenceUtils.isEmailEnable(context);
		}
	};

	private void sendSMS(String phoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
