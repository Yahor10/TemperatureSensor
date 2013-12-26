package com.android.temperaturesensor.receiver;

import com.android.temperaturesensor.StartupService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, StartupService.class));
	}

}
