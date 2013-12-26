package com.android.temperaturesensor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.android.temperaturesensor.email.GMailSender;
import com.android.temperaturesensor.preferences.PreferenceUtils;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnItemSelectedListener, OnFocusChangeListener {

	private Spinner mMinTemperature;
	private Spinner mMaxTemperature;
	private CheckBox mServiceActivate;
	private CheckBox smsEnableCheckBox;
	private CheckBox emailEnableCheckBox;
	private CheckBox twitterEnable;
	private EditText mColdTemperature;
	private EditText mWarmTemperature;
	private EditText mEmail;
	private ArrayAdapter<CharSequence> coldAdapter;

	private EmailValidator emailValidator;
	private String emailString = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		emailValidator = new EmailValidator();
		// layout

		mMinTemperature = (Spinner) findViewById(R.id.minTemperature);
		mMaxTemperature = (Spinner) findViewById(R.id.maxTemperature);

		smsEnableCheckBox = (CheckBox) findViewById(R.id.smsEnable);
		emailEnableCheckBox = (CheckBox) findViewById(R.id.emailEnable);
		twitterEnable = (CheckBox) findViewById(R.id.twitterEnable);

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
				Log.v(null, "AFTER COLD TExt CHANGED" + s);
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
				Log.v(null, "AFTER WARM TExt CHANGED" + s);
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

		coldAdapter = ArrayAdapter.createFromResource(this,
				R.array.min_temperature, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		coldAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMinTemperature.setAdapter(coldAdapter);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.max_temperature,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		coldAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mMaxTemperature.setAdapter(adapter2);

		mServiceActivate = (CheckBox) findViewById(R.id.service);
		mServiceActivate.setOnCheckedChangeListener(this);
		mServiceActivate.setChecked(true);

		boolean smsEnable = false;
		boolean emailEnable = PreferenceUtils.isEmailEnable(this);

		smsEnableCheckBox.setChecked(smsEnable);
		emailEnableCheckBox.setChecked(emailEnable);

		emailEnableCheckBox.setOnCheckedChangeListener(this);

		mMinTemperature.setOnItemSelectedListener(this);
		// TODO get current current

	}

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
		case R.id.emailEnable:
			PreferenceUtils.setEmailEnable(this, isChecked);
			break;
		default:
			break;

		}
	}

	class SendEmail extends AsyncTask<Void, String, Boolean> {

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
				Toast.makeText(getApplicationContext(), "Email successfully",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Email failed",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				GMailSender sender = new GMailSender("sekt88@gmail.com", "1");
				sender.sendMail("Temperature sensor",
						"Temperature has been changed", "sekt88@gmail.com",
						"sekt88@gmail.com");
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

	@Override
	public void onItemSelected(AdapterView<?> adapter, View view, int pos,
			long val) {
		int id = adapter.getId();
		switch (id) {
		case R.id.minTemperature:
			CharSequence item = coldAdapter.getItem(pos);
			break;
		case R.id.maxTemperature:
			// TODO handle max T
			break;

		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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
			default:
				break;
			}

		}
	}

}
