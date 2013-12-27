package com.android.temperaturesensor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.temperaturesensor.preferences.PreferenceUtils;
import com.ppierson.t4jtwitterlogin.T4JTwitterLoginActivity;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnItemSelectedListener, OnFocusChangeListener {

	private Spinner mMinTemperature;
	private Spinner mMaxTemperature;

	private Switch mServiceActivate;
	private Switch mTestMode;

	private CheckBox smsEnableCheckBox;
	private CheckBox emailEnableCheckBox;
	private CheckBox twitterEnableCheckBox;

	private EditText mColdTemperature;
	private EditText mWarmTemperature;
	private EditText mEmail;

	private ArrayAdapter<CharSequence> coldAdapter;
	private ArrayAdapter<CharSequence> warmAdapter;

	private EmailValidator emailValidator;
	private String emailString = "";
	private EditText mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		emailValidator = new EmailValidator();

		mMinTemperature = (Spinner) findViewById(R.id.minTemperature);
		mMaxTemperature = (Spinner) findViewById(R.id.maxTemperature);

		smsEnableCheckBox = (CheckBox) findViewById(R.id.smsEnable);
		emailEnableCheckBox = (CheckBox) findViewById(R.id.emailEnable);
		twitterEnableCheckBox = (CheckBox) findViewById(R.id.twitterEnable);

		mColdTemperature = (EditText) findViewById(R.id.coldTemprature);
		mColdTemperature.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String string = s.toString();
				if (!TextUtils.isEmpty(string)) {
					PreferenceUtils.setMinTemperatureMessage(
							getApplicationContext(), string);
				}
			}
		});

		mWarmTemperature = (EditText) findViewById(R.id.warmTemprature);
		mWarmTemperature.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String string = s.toString();
				if (!TextUtils.isEmpty(string)) {
					PreferenceUtils.setMaxTemperatureMessage(
							getApplicationContext(), string);
				}
			}
		});

		mEmail = (EditText) findViewById(R.id.email);
		mEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				emailString = s.toString();
				PreferenceUtils.setEmail(getApplicationContext(), emailString);
			}
		});

		mEmail.setOnFocusChangeListener(this);

		mPhone = (EditText) findViewById(R.id.phone);
		mPhone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String phoneString = s.toString();
				PreferenceUtils.setPhoneNumber(getApplicationContext(),
						phoneString);
			}
		});

		coldAdapter = ArrayAdapter.createFromResource(this,
				R.array.min_temperature, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		coldAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMinTemperature.setAdapter(coldAdapter);

		warmAdapter = ArrayAdapter.createFromResource(this,
				R.array.max_temperature, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		warmAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMaxTemperature.setAdapter(warmAdapter);
		mMaxTemperature.setSelection(1);

		mServiceActivate = (Switch) findViewById(R.id.service);
		mServiceActivate.setOnCheckedChangeListener(this);
		mServiceActivate.setChecked(true);

		mTestMode = (Switch) findViewById(R.id.testMode);
		mTestMode.setOnCheckedChangeListener(this);

		boolean smsEnable = PreferenceUtils.isSmsEnable(this);
		boolean emailEnable = PreferenceUtils.isEmailEnable(this);
		boolean twitterEnable = PreferenceUtils.isTwitterEnable(this);

		smsEnableCheckBox.setChecked(smsEnable);
		emailEnableCheckBox.setChecked(emailEnable);
		twitterEnableCheckBox.setChecked(twitterEnable);

		emailEnableCheckBox.setOnCheckedChangeListener(this);
		smsEnableCheckBox.setOnCheckedChangeListener(this);
		twitterEnableCheckBox.setOnCheckedChangeListener(this);

		mMinTemperature.setOnItemSelectedListener(this);
		mMaxTemperature.setOnItemSelectedListener(this);
		// TODO get current current

		mEmail.setText(PreferenceUtils.getEmail(this));
		mPhone.setText(PreferenceUtils.getPhoneNumber(this));

		// TODO check tweeter aouth

	}

	private static final int TWITTER_LOGIN_REQUEST_CODE = 1112;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG", "ON ACTIVITY RESULT!");
		if (requestCode == TWITTER_LOGIN_REQUEST_CODE) {
			Log.d("TAG", "TWITTER LOGIN REQUEST CODE");
			if (resultCode == T4JTwitterLoginActivity.TWITTER_LOGIN_RESULT_CODE_SUCCESS) {
				Toast.makeText(this, "Twitter login success!",
						Toast.LENGTH_SHORT).show();
				Log.d("TAG", "TWITTER LOGIN SUCCESS");
			} else if (resultCode == T4JTwitterLoginActivity.TWITTER_LOGIN_RESULT_CODE_FAILURE) {
				Log.d("TAG", "TWITTER LOGIN FAIL");
				Toast.makeText(this, "Twitter login fail!", Toast.LENGTH_SHORT)
						.show();
			} else {
				//
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.service:

			if (isChecked) {
				// mServiceActivate.setText("Temperature Service is ON");
				startService(new Intent(this, StartupService.class));
				PreferenceUtils.setServiceEnable(this, true);
			} else {
				// mServiceActivate.setText("Temperature Service is OFF");
				stopService(new Intent(this, StartupService.class));
				PreferenceUtils.setServiceEnable(this, false);
			}

			break;
		case R.id.emailEnable:
			PreferenceUtils.setEmailEnable(this, isChecked);
			break;
		case R.id.smsEnable:
			PreferenceUtils.setSmsEnable(this, isChecked);
			break;
		case R.id.twitterEnable:
			PreferenceUtils.setTwitterEnable(this, isChecked);
			break;
		case R.id.testMode:
			PreferenceUtils.setTestModeEnable(this, isChecked);
			break;

		default:
			break;

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View view, int pos,
			long val) {
		int id = adapter.getId();
		CharSequence item = "";
		int parseInt = 0;

		switch (id) {
		case R.id.minTemperature:
			item = coldAdapter.getItem(pos);
			parseInt = Integer.parseInt(item.toString());
			PreferenceUtils.setMinTemperature(this, parseInt);
			break;
		case R.id.maxTemperature:
			item = warmAdapter.getItem(pos);
			parseInt = Integer.parseInt(item.toString());
			PreferenceUtils.setMaxTemperature(this, parseInt);
			break;
		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			switch (v.getId()) {
			case R.id.email:
				if (TextUtils.isEmpty(emailString)) {
					mEmail.setError(null);
					return;
				}
				boolean validate = emailValidator.validate(emailString);
				if (!validate) {
					String errMessage = getResources().getString(
							R.string.error_invalid_email);
					mEmail.setError(errMessage);
				} else {
					mEmail.setError(null);
				}
				break;

			case R.id.phone:

				break;
			default:
				break;
			}
		}
	}

	public void onLoginTweet(View w) {
		if (!T4JTwitterLoginActivity.isConnected(this)) {
			Intent twitterLoginIntent = new Intent(this,
					T4JTwitterLoginActivity.class);
			twitterLoginIntent.putExtra(
					T4JTwitterLoginActivity.TWITTER_CONSUMER_KEY,
					"qi8IiAjtmuj6dEbzDdV6g");// Temperature Sensor Android APP
			twitterLoginIntent.putExtra(
					T4JTwitterLoginActivity.TWITTER_CONSUMER_SECRET,
					"BPnpsDPHD4fWKTNMNpYc6mhLmGy12FRZI43sxxs5kSs");
			startActivityForResult(twitterLoginIntent,
					TWITTER_LOGIN_REQUEST_CODE);
		}
	}

	public class EmailValidator {

		private Pattern pattern;
		private Matcher matcher;
		private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		public EmailValidator() {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}

		/**
		 * Validate hex with regular expression
		 * 
		 * @param hex
		 *            hex for validation
		 * @return true valid hex, false invalid hex
		 */
		public boolean validate(final String hex) {
			matcher = pattern.matcher(hex);
			return matcher.matches();
		}
	}

}
