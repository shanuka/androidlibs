package lk.zmessenger.consumerwatchconsummer;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.domain.News;
import lk.zmessenger.consumerwatchconsummer.session.SessionManager;
import lk.zmessenger.consumerwatchconsummer.ui.MTextView;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.android.gcm.GCMRegistrar;
import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class MainActivity extends Activity {

	AlertDialogManager alert = new AlertDialogManager();
	// Connection detector
	ConnectionDetector cd;
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		public void run() {
			afficher();
		}
	};

	MTextView mtv;
	public void afficher() {
		//Toast.makeText(getBaseContext(), "test", Toast.LENGTH_SHORT).show();
		List<News> news = new Select().from(News.class).orderBy("Id DESC")
				.execute();
		
		if (news.size() > 0) {
			System.out.println("news " + news.get(0).getNews());
			mtv.setText(news.get(0).getNews());
			System.out.println(news.get(0).getNews());
		}
		
		handler.postDelayed(runnable, 100000);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);

		cd = new ConnectionDetector(getApplicationContext());
		
		mtv = (MTextView) findViewById(R.id.textView1);

		runnable.run();

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Extra.DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		System.out.println(getOperator().getNetworkOperatorName());

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, Extra.SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				// Toast.makeText(getApplicationContext(),
				// "Already registered with GCM", Toast.LENGTH_LONG)
				// .show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities
								.register(context, "test", "1233", regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}

	}

	public TelephonyManager getOperator() {
		TelephonyManager manager = (TelephonyManager) getApplication()
				.getSystemService(Context.TELEPHONY_SERVICE);
		// String opeartorName = manager.getSimOperator();
		// manager.getLine1Number()
		return manager;
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(
					Extra.EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			// lblMessage.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	public void onClickConsumerPrice(View view) {
		if (GCMRegistrar.getRegistrationId(this).length() > 0) {
			System.err.println("onClickConsumerPrice");
			Intent in = new Intent(getApplicationContext(),
					CategoryGridActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(in);
			overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
		}
	}

	public void onClickWholeSalePrice(View view) {
		if (GCMRegistrar.getRegistrationId(this).length() > 0) {
			System.err.println("onClickWholeSalePrice");
			Intent in = new Intent(getApplicationContext(),
					WholeSaleCategoryGridActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(in);
			overridePendingTransition(R.anim.pull_in_from_top, R.anim.hold);
		}
	}

	public void onClickShopingList(View view) {
		if (GCMRegistrar.getRegistrationId(this).length() > 0) {
			System.err.println("onClickShopingList");
			Intent in = new Intent(getApplicationContext(),
					ShoppingListActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(in);
			overridePendingTransition(R.anim.slide_in_left, R.anim.hold);
		}
	}

	public void onClickComplains(View view) {
		if (GCMRegistrar.getRegistrationId(this).length() > 0) {
			final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			AsyncHttpClient ahc = new AsyncHttpClient();
			ahc.setTimeout(20000);

			RequestParams params = new RequestParams();
			// params.put("gcmregid",
			// GCMRegistrar.getRegistrationId(MainActivity.this));
			params.put("imei", telephonyManager.getDeviceId().toString());

			ahc.post(Extra.URL + "complainuser/authenticate", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String content) {
							// TODO Auto-generated method stub
							super.onSuccess(content);
							System.err.println(content);
							SessionManager sm = new SessionManager(
									getApplicationContext());

							try {
								JSONObject jObj = new JSONObject(content);
								if (jObj.getBoolean("status")) {
									sm.createLoginSession(jObj
											.getString("user"));
									System.err.println("onClickComplains");
									Intent in = new Intent(
											getApplicationContext(),
											ComplainActivity.class);
									in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

									startActivity(in);
									overridePendingTransition(
											R.anim.pull_in_from_bottom,
											R.anim.hold);
								} else {
									System.err.println("onClickComplains");
									Intent in = new Intent(
											getApplicationContext(),
											ComplainRegisterActivity.class);
									in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

									startActivity(in);
									overridePendingTransition(
											R.anim.pull_in_from_bottom,
											R.anim.hold);
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
