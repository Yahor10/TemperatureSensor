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
	
	public static void setPhoneNumber(Context context,String phoneNumber){
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
	
	public static void setEmail(Context context,String email){
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

}
