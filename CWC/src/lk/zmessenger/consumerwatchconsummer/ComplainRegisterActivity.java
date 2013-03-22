package lk.zmessenger.consumerwatchconsummer;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.google.android.gcm.GCMRegistrar;
import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class ComplainRegisterActivity extends Activity {

	EditText editTextFullName, editTextNIC, editTextAddress;
	FormEditText editTextEmail;
	String meditTextFullName, meditTextNIC, meditTextAddress, meditTextEmail;
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complain_register);
		// Show the Up button in the action bar.
		setupActionBar();
		editTextFullName = (EditText) findViewById(R.id.editTextFullName);
		editTextNIC = (EditText) findViewById(R.id.editTextNIC);
		editTextAddress = (EditText) findViewById(R.id.editTextAddress);
		editTextEmail = (FormEditText) findViewById(R.id.editTextEmail);
		editTextEmail.addValidator(new OrValidator(
				"This is neither a creditcard or an email",
				// new CreditCardValidator(null), // we specify null as the
				// message string cause the Or validator will use his own
				// message
				new EmailValidator(null) // same here for null
				));
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.complain_register, menu);
	// return true;
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// Whenever this activity is paused (i.e. looses focus because another
		// activity is started etc)
		// Override how this activity is animated out of view
		// The new activity is kept still and this activity is pushed out to the
		// left
		overridePendingTransition(R.anim.hold, R.anim.push_out_to_top);
		super.onPause();
	}

	public void onClickSubmit(View view) {
		System.out.println("onClickSubmit");
		attemptSubmit();
	}

	public void attemptSubmit() {
		meditTextFullName = editTextFullName.getText().toString();
		meditTextNIC = editTextNIC.getText().toString();
		meditTextAddress = editTextAddress.getText().toString();
		meditTextEmail = editTextEmail.getText().toString();

		editTextFullName.setError(null);
		editTextNIC.setError(null);
		editTextAddress.setError(null);
		editTextEmail.setError(null);

		boolean cancel = false;
		View focusView = null;
		if (TextUtils.isEmpty(meditTextFullName)) {
			editTextFullName.setError(getString(R.string.error_field_required));
			focusView = editTextFullName;
			cancel = true;
		}
		if (TextUtils.isEmpty(meditTextNIC)) {
			editTextNIC.setError(getString(R.string.error_field_required));
			focusView = editTextNIC;
			cancel = true;
		} else if (meditTextNIC.length() > 10) {
			editTextNIC.setError(getString(R.string.error_invalid_password));
			focusView = editTextNIC;
			cancel = true;
		}
		if (TextUtils.isEmpty(meditTextAddress)) {
			editTextAddress.setError(getString(R.string.error_field_required));
			focusView = editTextAddress;
			cancel = true;
		}

		if (TextUtils.isEmpty(meditTextEmail)) {
			editTextEmail.setError(getString(R.string.error_field_required));
			focusView = editTextEmail;
			cancel = true;
		}

		if (!editTextEmail.testValidity()) {
			focusView =editTextEmail;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			// telephonyManager.getDeviceId();
			int simState = telephonyManager.getSimState();
			if (simState != TelephonyManager.SIM_STATE_ABSENT) {
				final SmsManager smsManager = SmsManager.getDefault();

				// System.out.println(telephonyManager.getDeviceId().toString());
				AsyncHttpClient ahc = new AsyncHttpClient();
				ahc.setTimeout(20000);

				RequestParams params = new RequestParams();
				params.put("gcmregid", GCMRegistrar
						.getRegistrationId(ComplainRegisterActivity.this));
				params.put("imei", telephonyManager.getDeviceId().toString());
				params.put("name", meditTextFullName);
				params.put("nic", meditTextNIC);
				params.put("address", meditTextAddress);
				params.put("email", meditTextEmail);
				String url = Extra.URL + "complainuser/add";

				ahc.post(url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						System.out.println(content);
						smsManager.sendTextMessage(
								Extra.PHONE_NUMBER,
								null,
								Extra.MESSAGE_BODY
										+ telephonyManager.getDeviceId(), null,
								null);
						AlertDialog alertDialog = new AlertDialog.Builder(
								ComplainRegisterActivity.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("Success");

						// Setting Dialog Message
						alertDialog.setMessage("User registerd");

						// Setting alert dialog icon
						alertDialog.setIcon((true) ? R.drawable.success
								: R.drawable.fail);

						// Setting OK Button
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										ComplainRegisterActivity.this.finish();
									}
								});

						// Showing Alert Message
						alertDialog.show();
					}
				});
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(
						ComplainRegisterActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("SIM NOT Found");

				// Setting Dialog Message
				alertDialog.setMessage("Please Insert Sim ");

				// Setting alert dialog icon
				alertDialog.setIcon((false) ? R.drawable.success
						: R.drawable.fail);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// ComplainRegisterActivity.this.finish();
							}
						});

				// Showing Alert Message
				alertDialog.show();
			}

		}
	}
}
