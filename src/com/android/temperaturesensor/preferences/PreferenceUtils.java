package com.android.temperaturesensor.preferences;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtils {

	public static boolean isEmailEnable(Context context) {
		boolean result = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PreferenceKeys.EMAIL_ENABLE, false);
		return result;
	}

	public static void setEmailEnable(Context context, boolean enable) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putBoolean(PreferenceKeys.EMAIL_ENABLE, enable);
		pEditor.commit();
	}

	public static boolean isSmsEnable(Context context) {
		boolean result = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PreferenceKeys.SMS_ENABLE, false);
		return result;
	}

	public static void setSmsEnable(Context context, boolean enable) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putBoolean(PreferenceKeys.SMS_ENABLE, enable);
		pEditor.commit();
	}

	public static void setTwitterEnable(Context context, boolean enable) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putBoolean(PreferenceKeys.TWITTER_ENABLE, enable);
		pEditor.commit();
	}

	public static boolean isTwitterEnable(Context context) {
		boolean result = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PreferenceKeys.TWITTER_ENABLE, false);
		return result;
	}

	public static void setTestModeEnable(Context context, boolean enable) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putBoolean(PreferenceKeys.TEST_MODE, enable);
		pEditor.commit();
	}

	public static boolean isTestMode(Context context) {
		boolean result = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PreferenceKeys.TEST_MODE, false);
		return result;
	}
	
	public static void setServiceEnable(Context context, boolean enable) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putBoolean(PreferenceKeys.SERVICE_ENABLE, enable);
		pEditor.commit();
	}

	public static boolean isServiceEnable(Context context) {
		boolean result = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PreferenceKeys.SERVICE_ENABLE, true);
		return result;
	}

	public static void setPhoneNumber(Context context, String phoneNumber) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putString(PreferenceKeys.PHONE_NUMBER, phoneNumber);
		pEditor.commit();
	}

	public static String getPhoneNumber(Context context) {
		String result = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PreferenceKeys.PHONE_NUMBER, "");
		return result;
	}

	public static void setEmail(Context context, String email) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putString(PreferenceKeys.EMAIL, email);
		pEditor.commit();
	}

	public static String getEmail(Context context) {
		String result = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PreferenceKeys.EMAIL, "");
		return result;
	}

	public static void setMaxTemperature(Context context, int max) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putInt(PreferenceKeys.MAX_TEMPERATURE, max);
		pEditor.commit();
	}
	
	public static void setMaxTemperatureMessage(Context context, String  message) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putString(PreferenceKeys.WARM_MESSAGE, message);
		pEditor.commit();
	}

	public static String getWarmMessage(Context context) {
		String result = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PreferenceKeys.WARM_MESSAGE, "Warm battery sensor message");
		return result;
	}
	
	public static void setMinTemperatureMessage(Context context, String  message) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putString(PreferenceKeys.COLD_MESSAGE, message);
		pEditor.commit();
	}

	public static String getColdMessage(Context context) {
		String result = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PreferenceKeys.WARM_MESSAGE, "Cold battery sensor message");
		return result;
	}
	
	public static int getMaxTemperature(Context context) {
		int result = PreferenceManager.getDefaultSharedPreferences(context)
				.getInt(PreferenceKeys.MAX_TEMPERATURE, 40);
		return result;
	}

	public static void setMinTemperature(Context context, int min) {
		Editor pEditor = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		pEditor.putInt(PreferenceKeys.MIN_TEMPERATURE, min);
		pEditor.commit();
	}

	public static int getMinTemperature(Context context) {
		int result = PreferenceManager.getDefaultSharedPreferences(context)
				.getInt(PreferenceKeys.MIN_TEMPERATURE, 10);
		return result;
	}

}
