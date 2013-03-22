package lk.zmessenger.consumerwatchconsummer;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.ImageLoader;
import lk.zmessenger.consumerwatchconsummer.domain.CompDescription;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ComplainDiscriptionData;
import lk.zmessenger.consumerwatchconsummer.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class SingleComplainActivity extends Activity {

	String url = "complain/viewallcategories";
	// private Spinner spinner1;
	private ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	TextView editText1, editText2, textViewresponce, textViewDes;
	private SessionManager sm;
	// private List<ComplainCategory> ccdList;
	private String complainid, title, thumb_url, description;
	private ImageView list_image2;
	private ImageLoader imageLoader;
	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent in = getIntent();
		complainid = in.getStringExtra(Extra.KEY_ID);
		title = in.getStringExtra(Extra.KEY_TITLE);
		thumb_url = in.getStringExtra(Extra.KEY_THUMB_URL);
		description = in.getStringExtra(Extra.KEY_DESCRIPTION);

		System.out.println(complainid);

		setContentView(R.layout.activity_single_complain);
		// Show the Up button in the action bar.
		setupActionBar();
		// spinner1 = (Spinner) findViewById(R.id.spinner1);
		editText1 = (TextView) findViewById(R.id.title2);
		editText2 = (TextView) findViewById(R.id.textView1);
		textViewresponce = (TextView) findViewById(R.id.textViewresponce);
		textViewDes = (TextView) findViewById(R.id.textViewDes);
		list_image2 = (ImageView) findViewById(R.id.list_image2);
		imageLoader = new ImageLoader(
				SingleComplainActivity.this.getApplicationContext());
		imageLoader.DisplayImage(Extra.IMAGE_URL + thumb_url, list_image2);

		editText1.setText(title);
		editText2.setText(description);
		sm = new SessionManager(getApplicationContext());
		// editText1.addValidator(new OrValidator(
		// "This is neither a creditcard or an email"));
		// editText2.addValidator(new OrValidator());
		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(SingleComplainActivity.this,
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
			params.put("complainid", complainid);
			ahc.get(Extra.URL + "complain/viewdescription", params,
					new AsyncHttpResponseHandler() {
						private List<CompDescription> desLit;

						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							dialog = ProgressDialog.show(
									SingleComplainActivity.this, "Loading",
									"Wait while loading.....");
						}

						@Override
						public void onSuccess(String content) {
							// TODO Auto-generated method stub
							super.onSuccess(content);
							System.out.println(content);
							try {
								JSONObject jObj = new JSONObject(content);
								ComplainDiscriptionData cdd = new ComplainDiscriptionData();
								desLit = cdd.getCompDescriptionData(jObj
										.getString("description_list"));
								for (CompDescription compDescription : desLit) {
									System.err.println(compDescription
											.getDescription());
									textViewDes.setText(compDescription
											.getDescription());
									if (compDescription.getCompResponses()
											.size() > 0) {
										System.err.println(compDescription
												.getCompResponses().get(0)
												.getResponse());
										textViewresponce.setText(compDescription
												.getCompResponses().get(0)
												.getResponse());
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(Throwable error) {
							// TODO Auto-generated method stub
							super.onFailure(error);
							System.out.println(error.getMessage());
						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							super.onFinish();
							if (dialog.isShowing()) {
								dialog.dismiss();
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

	}

}
