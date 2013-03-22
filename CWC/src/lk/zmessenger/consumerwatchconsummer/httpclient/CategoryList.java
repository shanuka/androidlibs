package lk.zmessenger.consumerwatchconsummer.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.AlertDialogManager;
import lk.zmessenger.consumerwatchconsummer.ProductGridActivity;
import lk.zmessenger.consumerwatchconsummer.R;
import lk.zmessenger.consumerwatchconsummer.WholeSaleProductGridActivity;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.GridAdapter;
import lk.zmessenger.consumerwatchconsummer.domain.Categories;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.CategoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.google.android.gcm.GCMRegistrar;
import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.JsonHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class CategoryList {
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	// private ProgressDialog dialog;
	private static String categorytURL = Extra.URL + "categories/gcmviewall";
	private Activity _activity;
	private GridView _gridview;
	protected List<Categories> categorylist = new ArrayList<Categories>();

	public ArrayList<HashMap<String, String>> getCategoryList(Activity a,
			GridView gridview) {
		_activity = a;
		_gridview = gridview;
		final ArrayList<HashMap<String, String>> categoryListOut = new ArrayList<HashMap<String, String>>();
		final List<Categories> categoryList = new ArrayList<Categories>();
		 

		AsyncHttpClient ahc = new AsyncHttpClient();
		ahc.setTimeout(20000);

		RequestParams params = new RequestParams();
		System.out.println(GCMRegistrar.getRegistrationId(_activity));
		params.put("gcmregid", GCMRegistrar.getRegistrationId(_activity));
		params.put("markettype", Extra.MACKET_TYPE_RETAIL);
		
	
		ahc.post(categorytURL, params, new AsyncHttpResponseHandler() {
			private DisplayMetrics metrics;
			ProgressDialog dialog;
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog = ProgressDialog.show(
						_activity,
						"",
						"Loading.....");
			}

			@Override
			public void onSuccess(String responce) {
				// TODO Auto-generated method stub
				super.onSuccess(responce);
				System.out.println(responce);
				try {
					JSONObject jObj = new JSONObject(responce);
					CategoryData cd = new CategoryData();
					categorylist = cd.getCategoryData(jObj.getString(
							"categories").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				System.out.println("onFinish " + categorylist.size());
				if (categorylist.size() > 0) {
					System.out.println("onFinish if " + categorylist.size());
					for (Categories categories : categorylist) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Extra.KEY_TITLE, categories.getCategoryName());
						map.put(Extra.KEY_THUMB_URL, categories.getImageUrl());
						System.out.println("onFinish "
								+ categories.getCategoryName());
						categoryListOut.add(map);
					}

					metrics = new DisplayMetrics();
					_activity.getWindowManager().getDefaultDisplay()
							.getMetrics(metrics);
					AnimationSet set = new AnimationSet(true);
					Animation animation = new AlphaAnimation(0.0f, 1.0f);
					animation.setDuration(50);
					set.addAnimation(animation);
					animation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 1.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation.setDuration(200);
					set.addAnimation(animation);
					LayoutAnimationController controller = new LayoutAnimationController(
							set, 0.5f);
					_gridview.setLayoutAnimation(controller);
					_gridview.setAdapter(new GridAdapter(_activity,
							categoryListOut, metrics));
					_gridview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub

							System.out.println(categorylist.get(position)
									.getCategoryId());
							Intent in = new Intent(_activity
									.getApplicationContext(),
									ProductGridActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							in.putExtra(Extra.KEY_CATEGORY_ID, categorylist
									.get(position).getCategoryId().toString());
							_activity.startActivity(in);
							_activity.overridePendingTransition(
									R.anim.pull_in_from_top, R.anim.hold);
						}
					});
				} else {
					alert.showAlertDialog(_activity, "Error", "Data not found",
							false);
				}
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);

				alert.showAlertDialog(_activity, "Error", error.getMessage(),
						false);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
			}
		});

		return categoryListOut;
	}

	public ArrayList<HashMap<String, String>> getWholeSaleCategoryList(
			Activity a, GridView gridview) {
		_activity = a;
		_gridview = gridview;
		final ArrayList<HashMap<String, String>> categoryListOut = new ArrayList<HashMap<String, String>>();
		final List<Categories> categoryList = new ArrayList<Categories>();

		AsyncHttpClient ahc = new AsyncHttpClient();
		ahc.setTimeout(20000);

		RequestParams params = new RequestParams();
		System.out.println(GCMRegistrar.getRegistrationId(_activity));
		params.put("gcmregid", GCMRegistrar.getRegistrationId(_activity));
		params.put("markettype", Extra.MACKET_TYPE_WHOLESALE);

		ahc.post(categorytURL, params, new AsyncHttpResponseHandler() {
			private DisplayMetrics metrics;

			ProgressDialog dialog;

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog = ProgressDialog.show(_activity, "", "Loading.....");
			}

			@Override
			public void onSuccess(String responce) {
				// TODO Auto-generated method stub
				super.onSuccess(responce);
				System.out.println(responce);
				try {
					JSONObject jObj = new JSONObject(responce);
					CategoryData cd = new CategoryData();
					categorylist = cd.getCategoryData(jObj.getString(
							"categories").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				System.out.println("onFinish " + categorylist.size());
				if (categorylist.size() > 0) {
					System.out.println("onFinish if " + categorylist.size());
					for (Categories categories : categorylist) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Extra.KEY_TITLE, categories.getCategoryName());
						map.put(Extra.KEY_THUMB_URL, categories.getImageUrl());
						System.out.println("onFinish "
								+ categories.getCategoryName());
						categoryListOut.add(map);
					}

					metrics = new DisplayMetrics();
					_activity.getWindowManager().getDefaultDisplay()
							.getMetrics(metrics);
					AnimationSet set = new AnimationSet(true);
					Animation animation = new AlphaAnimation(0.0f, 1.0f);
					animation.setDuration(50);
					set.addAnimation(animation);
					animation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 1.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation.setDuration(200);
					set.addAnimation(animation);
					LayoutAnimationController controller = new LayoutAnimationController(
							set, 0.5f);
					_gridview.setLayoutAnimation(controller);
					_gridview.setAdapter(new GridAdapter(_activity,
							categoryListOut, metrics));
					_gridview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub

							System.out.println(categorylist.get(position)
									.getCategoryId());
							Intent in = new Intent(_activity
									.getApplicationContext(),
									WholeSaleProductGridActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							in.putExtra(Extra.KEY_CATEGORY_ID, categorylist
									.get(position).getCategoryId().toString());
							_activity.startActivity(in);
							_activity.overridePendingTransition(
									R.anim.pull_in_from_top, R.anim.hold);
						}
					});
				} else {
					alert.showAlertDialog(_activity, "Error", "Data not found",
							false);
				}
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);

				alert.showAlertDialog(_activity, "Error", error.getMessage(),
						false);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
			}
		});

		return categoryListOut;
	}
}
