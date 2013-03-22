package lk.zmessenger.consumerwatchconsummer;

import java.util.ArrayList;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.domain.ComplainCategory;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ComplainCategoryData;
import lk.zmessenger.consumerwatchconsummer.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.andreabaccega.widget.FormEditText;
import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class NewComplainActivity extends Activity {

	String url = "complain/viewallcategories";
	private Spinner spinner1;
	private ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	FormEditText editText1, editText2;
	private SessionManager sm;
	private List<ComplainCategory> ccdList;
	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_complain);
		// Show the Up button in the action bar.
		setupActionBar();
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		editText1 = (FormEditText) findViewById(R.id.editText1);
		editText2 = (FormEditText) findViewById(R.id.editText2);
		sm = new SessionManager(getApplicationContext());
		// editText1.addValidator(new OrValidator(
		// "This is neither a creditcard or an email"));
		// editText2.addValidator(new OrValidator());
		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(NewComplainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		} else {
			System.out.println("xcwecew");
			AsyncHttpClient ahc = new AsyncHttpClient();
			ahc.setTimeout(20000);

			RequestParams params = new RequestParams();
			// params.put("gcmregid",
			// GCMRegistrar.getRegistrationId(MainActivity.this));
			params.put("compuserid", sm.getUser().getCompUserId().toString());
			ahc.get(Extra.URL + url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(String content) {
					// TODO Auto-generated method stub
					super.onSuccess(content);
					System.out.println(content);
					try {
						JSONObject jObj = new JSONObject(content);
						ComplainCategoryData ccd = new ComplainCategoryData();
						ccdList = ccd.getCategoryData(jObj.getString(
								"comp_categories").toString());
						List<String> list = new ArrayList<String>();
						for (ComplainCategory ccdItem : ccdList) {
							// System.out.println(ccdItem.getCategoryName());
							list.add(ccdItem.getCategoryName());

						}
						ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
								NewComplainActivity.this,
								android.R.layout.simple_spinner_item, list);
						dataAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner1.setAdapter(dataAdapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		}
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
	// getMenuInflater().inflate(R.menu.new_complain, menu);
	// return true;
	// }

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

	public void onclicknew(View view) {

		System.err.println(sm.getUser().getCompUserId());
		boolean cancel = false;
		View focusView = null;
		editText1.setError(null);
		editText2.setError(null);
		if (!editText1.testValidity()) {
			focusView = editText1;
			cancel = true;
		}
		if (!editText2.testValidity()) {
			focusView = editText2;
			cancel = true;
		}
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {

			String url = "complain/addcomplain";
			if (!cd.isConnectingToInternet()) {
				// Internet Connection is not present
				alert.showAlertDialog(NewComplainActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false);
				// stop executing code by return
				return;
			} else {
				String urls = "complain/addcomplain";
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(NewComplainActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				} else {
					RequestParams params = new RequestParams();
					// params.put("gcmregid",
					System.err.println(ccdList.get(
							spinner1.getSelectedItemPosition())
							.getCompCategoryId());
					// GCMRegistrar.getRegistrationId(MainActivity.this));
					params.put("compuserid", sm.getUser().getCompUserId()
							.toString());
					params.put("title", editText1.getText().toString());
					params.put("categoryid",
							ccdList.get(spinner1.getSelectedItemPosition())
									.getCompCategoryId().toString());
					params.put("description", editText2.getText().toString());
					AsyncHttpClient ahc = new AsyncHttpClient();
					ahc.setTimeout(20000);
					ahc.post(Extra.URL + urls, params,
							new AsyncHttpResponseHandler() {
								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									super.onStart();
									dialog = ProgressDialog.show(
											NewComplainActivity.this,
											"Wait.....",
											"Your Complain is being sending");
								}

								@Override
								public void onFinish() {
									// TODO Auto-generated method stub
									super.onFinish();
									if (dialog.isShowing()) {
										dialog.dismiss();
									}
								}

								@Override
								public void onSuccess(String content) {
									// TODO Auto-generated method stub
									super.onSuccess(content);
									System.out.println(content);
									try {
										JSONObject jObj = new JSONObject(
												content);
										if (jObj.getBoolean("status")) {
											AlertDialog alertDialog = new AlertDialog.Builder(
													NewComplainActivity.this)
													.create();

											// Setting Dialog Title
											alertDialog.setTitle("Success");

											// Setting Dialog Message
											alertDialog.setMessage(jObj
													.getString("statusmsg"));

											// Setting alert dialog icon
											alertDialog
													.setIcon((true) ? R.drawable.success
															: R.drawable.fail);

											// Setting OK Button
											alertDialog
													.setButton(
															"OK",
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	NewComplainActivity.this
																			.finish();
																}
															});

											// Showing Alert Message
											alertDialog.show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

				}
			}

		}
	}

}
