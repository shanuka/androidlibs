package lk.zmessenger.consumerwatchconsummer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.LazyAdapterTitle;
import lk.zmessenger.consumerwatchconsummer.domain.Products;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.activeandroid.query.Select;

public class ShoppingListActivity extends Activity {

	private ArrayList<HashMap<String, String>> menutList;
	private LazyAdapterTitle adapter;
	private Thread shopViewList;
	private ListView listView;
	List<Products> productLisst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();

		handler = new Handler();
		dialog = ProgressDialog.show(this, "Loading",
				"Please wait while loading...");
		shopViewList = new MarketViewList();
		shopViewList.start();
		productLisst = new ArrayList<Products>();

		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ShoppingListActivity.this);

				// set title
				alertDialogBuilder.setTitle("Delete Item ");
				alertDialogBuilder
						.setMessage("Do you want to delete this item?")
						.setCancelable(false)
						.setNegativeButton("Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int id) {

										productLisst.get(position).delete();
										menutList.remove(position);
										listView.setAdapter(new LazyAdapterTitle(
												ShoppingListActivity.this,
												menutList));

										dialog.cancel();

									}
								})
						.setPositiveButton("No",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int id) {

										dialog.cancel();

									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

				return false;

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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.shopping_list, menu);
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
		overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
		super.onPause();
	}

	private static ProgressDialog dialog;
	private static Handler handler;

	class MarketViewList extends Thread {
		@Override
		public void run() {
			try {
				// Simulate a slow network
				try {
					new Thread();
					Thread.sleep(200);
					List<Products> productList = new Select()
							.from(Products.class).orderBy("product_name ASC")
							.execute();
					productLisst = productList;
					menutList = new ArrayList<HashMap<String, String>>();
					for (Products products : productList) {
						System.out.println(products.getProductName());
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Extra.KEY_ID,
								String.valueOf(products.getProductId()));
						map.put(Extra.KEY_TITLE, products.getProductName());
						map.put(Extra.KEY_ARTIST, "");
						map.put(Extra.KEY_DURATION, "");
						map.put(Extra.KEY_THUMB_URL, products.getImageUrl());
						map.put(Extra.KEY_DESCRIPTION, "");

						// adding HashList to ArrayList
						menutList.add(map);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} finally {
				handler.post(new MyRunnable());
			}

		}
	}

	class MyRunnable implements Runnable {
		public void run() {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			Animation inFromLeft = new TranslateAnimation(

			Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT,
					0.0f,

					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f

			);

			inFromLeft.setDuration(500);

			inFromLeft.setInterpolator(new AccelerateInterpolator());
			AnimationSet set = new AnimationSet(true);
			set.addAnimation(inFromLeft);
			LayoutAnimationController controller = new LayoutAnimationController(
					set, 0.5f);
			adapter = new LazyAdapterTitle(ShoppingListActivity.this, menutList);

			listView.setAdapter(adapter);
			listView.setLayoutAnimation(controller);

		}
	}

}
