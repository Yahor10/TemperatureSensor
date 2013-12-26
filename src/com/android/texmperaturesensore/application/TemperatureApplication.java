package com.android.texmperaturesensore.application;

import android.app.Application;
import android.content.Context;

public class TemperatureApplication extends Application {

	private boolean smsSenderEnable = false;
	private boolean emailSenderEnable = false;
	private boolean twitterSenderEnable = false;
	
	private String coldMessage = "";
	private String hotMessage = "";
	

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public static TemperatureApplication getApplication(Context context) {
		return (TemperatureApplication) context.getApplicationContext();
	}

	public boolean isSmsSenderEnable() {
		return smsSenderEnable;
	}

	public void setSmsSenderEnable(boolean smsSenderEnable) {
		this.smsSenderEnable = smsSenderEnable;
	}

	public boolean isEmailSenderEnable() {
		return emailSenderEnable;
	}

	public void setEmailSenderEnable(boolean emailSenderEnable) {
		this.emailSenderEnable = emailSenderEnable;
	}

	public boolean isTwitterSenderEnable() {
		return twitterSenderEnable;
	}

	public void setTwitterSenderEnable(boolean twitterSenderEnable) {
		this.twitterSenderEnable = twitterSenderEnable;
	}

}
