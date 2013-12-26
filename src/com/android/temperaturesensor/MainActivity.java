package com.android.temperaturesensor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.texmperaturesensore.application.TemperatureApplication;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		SensorEventListener {

	private Spinner mMinTemperature;
	private Spinner mMaxTemperature;
	private CheckBox mServiceActivate;
	private CheckBox smsEnable;
	private CheckBox emailEnable;
	private CheckBox twitterEnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// layout

		mMinTemperature = (Spinner) findViewById(R.id.minTemperature);
		mMaxTemperature = (Spinner) findViewById(R.id.maxTemperature);

		smsEnable = (CheckBox) findViewById(R.id.smsEnable);
		emailEnable = (CheckBox) findViewById(R.id.emailEnable);
		twitterEnable = (CheckBox) findViewById(R.id.twitterEnable);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.min_temperature,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMinTemperature.setAdapter(adapter);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.max_temperature,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMaxTemperature.setAdapter(adapter2);

		mServiceActivate = (CheckBox) findViewById(R.id.service);
		mServiceActivate.setOnCheckedChangeListener(this);
		mServiceActivate.setChecked(true);

		TemperatureApplication app = TemperatureApplication
				.getApplication(this);

		boolean smsSenderEnable = app.isSmsSenderEnable();
		boolean emailSenderEnable = app.isEmailSenderEnable();
		boolean twitterSenderEnable = app.isTwitterSenderEnable();

		smsEnable.setChecked(smsSenderEnable);
		emailEnable.setChecked(emailSenderEnable);
		twitterEnable.setChecked(twitterSenderEnable);
		
	    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent registerReceiver = registerReceiver(batteryInfoReceiver, 
	    	    intentFilter);
		
		int temperature = registerReceiver.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
		Log.v(null, "temperature:" + temperature);
		 // TODO get current current
	}

	
	@Override
	protected void onDestroy() {
		unregisterReceiver(batteryInfoReceiver);
		super.onDestroy();
	}
	
	
	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			int level = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
			Log.v(null, "EXTRA_TEMPERATURE:" + level);
			
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.service:
			if (isChecked) {
				mServiceActivate.setText("ON");
				startService(new Intent(this, StartupService.class));
			} else {
				mServiceActivate.setText("OFF");
				stopService(new Intent(this, StartupService.class));
			}
			break;

		default:
			break;

		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
			Toast.makeText(this, "AMBIENT TEMPERATURE: " + event.values[0],
					Toast.LENGTH_SHORT);
		}

	}
}
