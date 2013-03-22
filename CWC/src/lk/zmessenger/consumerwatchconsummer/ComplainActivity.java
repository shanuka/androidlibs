package lk.zmessenger.consumerwatchconsummer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.LazyAdapterTitle;
import lk.zmessenger.consumerwatchconsummer.domain.CompComplain;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ComplainData;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class ComplainActivity extends Activity {

	private ArrayList<HashMap<String, String>> menutList;
	private LazyAdapterTitle adapter;
	private SessionManager sm;
	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complain);
		// Show the Up button in the action bar.
		setupActionBar();
		menutList = new ArrayList<HashMap<String, String>>();
		final ListView listView = (ListView) findViewById(R.id.listView);
		// HashMap<String, String> map = new HashMap<String, String>();

		// menuListView = (ListView) findViewById(R.id.mainmenulist);

		sm = new SessionManager(getApplicationContext());

		AsyncHttpClient ahc = new AsyncHttpClient();
		ahc.setTimeout(20000);

		RequestParams params = new RequestParams();
		// params.put("gcmregid",
		// GCMRegistrar.getRegistrationId(MainActivity.this));
		params.put("compuserid", sm.getUser().getCompUserId().toString());

		ahc.get(Extra.URL + "complain/viewcomplains", params,
				new AsyncHttpResponseHandler() {
					private List<CompComplain> complain;

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						dialog = ProgressDialog.show(ComplainActivity.this,
								"Loading", "Wait while loading.....");
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						System.out.println(content);
						try {
							JSONObject jObj = new JSONObject(content);
							ComplainData cd = new ComplainData();
							complain = cd.getCompComplainData(jObj
									.getString("complains"));
							for (CompComplain complainO : complain) {
								System.err.println(complainO.getComplainTitle());
								System.err.println(complainO.getCompCategory()
										.getCategoryName());

								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Extra.KEY_ID, complainO
										.getCompComplainId().toString());
								map.put(Extra.KEY_TITLE, complainO
										.getCompCategory().getCategoryName());
								map.put(Extra.KEY_ARTIST, "");
								map.put(Extra.KEY_DURATION, "");
								map.put(Extra.KEY_THUMB_URL, "complain.png");
								map.put(Extra.KEY_DESCRIPTION,
										complainO.getComplainTitle());

								// adding HashList to ArrayList
								menutList.add(map);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					};

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						Animation inFromLeft = new TranslateAnimation(

						Animation.RELATIVE_TO_PARENT, 1.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,

								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f

						);

						inFromLeft.setDuration(500);

						inFromLeft
								.setInterpolator(new AccelerateInterpolator());
						AnimationSet set = new AnimationSet(true);
						set.addAnimation(inFromLeft);
						LayoutAnimationController controller = new LayoutAnimationController(
								set, 0.5f);
						adapter = new LazyAdapterTitle(ComplainActivity.this,
								menutList);
						listView.setAdapter(adapter);
						listView.setLayoutAnimation(controller);
					}

				});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(menutList.get(arg2).get(Extra.KEY_ID));
				System.out.println(menutList.get(arg2).get(
						Extra.KEY_DESCRIPTION));

				System.err.println("onClickComplains");
				Intent in = new Intent(getApplicationContext(),
						SingleComplainActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				in.putExtra(Extra.KEY_ID, menutList.get(arg2).get(Extra.KEY_ID));
				in.putExtra(Extra.KEY_TITLE,
						menutList.get(arg2).get(Extra.KEY_TITLE));
				in.putExtra(Extra.KEY_THUMB_URL,
						menutList.get(arg2).get(Extra.KEY_THUMB_URL));
				in.putExtra(Extra.KEY_DESCRIPTION,
						menutList.get(arg2).get(Extra.KEY_DESCRIPTION));

				startActivity(in);
				overridePendingTransition(R.anim.pull_in_from_bottom,
						R.anim.hold);

			}
		});

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

	//

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

	public void onlickNew(View view) {
		Intent in = new Intent(getApplicationContext(),
				NewComplainActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(in);
		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
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

}
