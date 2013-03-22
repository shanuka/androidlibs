package lk.zmessenger.consumerwatchconsummer.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.AlertDialogManager;
import lk.zmessenger.consumerwatchconsummer.ProductActivity;
import lk.zmessenger.consumerwatchconsummer.R;
import lk.zmessenger.consumerwatchconsummer.WholeSalefProductActivity;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.GridAdapter;
import lk.zmessenger.consumerwatchconsummer.domain.ProductDetailsDto;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ProductSummeryData;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shanuka.aynchttpclient.AsyncHttpClient;
import com.shanuka.aynchttpclient.AsyncHttpResponseHandler;
import com.shanuka.aynchttpclient.RequestParams;

public class ProductList {
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	// private ProgressDialog dialog;
	private static String categorytURL = Extra.URL + "pricelist/viewproducts/";
	private Activity _activity;
	private GridView _gridview;
	protected List<ProductDetailsDto> productlist = new ArrayList<ProductDetailsDto>();
	private String marketid;

	public ArrayList<HashMap<String, String>> getProductList(Activity a,
			GridView gridview) {
		_activity = a;
		_gridview = gridview;
		final ArrayList<HashMap<String, String>> categoryListOut = new ArrayList<HashMap<String, String>>();
		Intent in = _activity.getIntent();
		marketid = in.getStringExtra(Extra.KEY_CATEGORY_ID);
		AsyncHttpClient ahc = new AsyncHttpClient();
		ahc.setTimeout(20000);

		RequestParams params = new RequestParams();
		// System.out.println(marketid);

		params.put("gcmregid", GCMRegistrar.getRegistrationId(_activity));
		params.put("categoryid", marketid);
		params.put("markettype", Extra.MACKET_TYPE_RETAIL);

		ahc.post(categorytURL, params, new AsyncHttpResponseHandler() {
			private DisplayMetrics metrics;
			ProgressDialog dialog;

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				// dialog = ProgressDialog.show(_activity, "Loading",
				// "Please wait while loading...");
				dialog = ProgressDialog.show(_activity, "",
						"Loading.....");
			}

			@Override
			public void onSuccess(String responce) {
				// TODO Auto-generated method stub
				super.onSuccess(responce);
				System.out.println(responce);
				try {
					JSONObject jObj = new JSONObject(responce);
					ProductSummeryData pd = new ProductSummeryData();
					productlist = pd.getProducSummerytData(jObj.getString(
							"products_price_summary").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				// dialog.dismiss();
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				System.out.println("onFinish " + productlist.size());
				if (productlist.size() > 0) {
					System.out.println("onFinish if " + productlist.size());
					for (ProductDetailsDto product : productlist) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Extra.KEY_TITLE, product.getProduct()
								.getProductName());
						map.put(Extra.KEY_ID, String.valueOf(product.getProduct()
								.getProductId()));
						map.put(Extra.KEY_THUMB_URL, product.getProduct()
								.getImageUrl());

						// System.out.println(product.getSummaryDetails().get(0)
						// .getAverage());
						// System.out.println(product.getSummaryDetails().get(0)
						// .getMarket_name());
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

							Intent in = new Intent(_activity
									.getApplicationContext(),
									ProductActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

							Gson gson = new GsonBuilder()
									.excludeFieldsWithoutExposeAnnotation()
									.create();
							String jsonStringApprove = gson.toJson(productlist);
							in.putExtra(Extra.PARSEJSON, jsonStringApprove);
							in.putExtra(Extra.KEY_ID, String.valueOf(position));
							_activity.startActivity(in);

							_activity.overridePendingTransition(
									R.anim.pull_in_from_top, R.anim.hold);
						}
					});
				} else {
					boolean status = false;
					AlertDialog alertDialog = new AlertDialog.Builder(_activity)
							.create();

					// Setting Dialog Title
					alertDialog.setTitle("Error");

					// Setting Dialog Message
					alertDialog.setMessage("Data not found");

					// Setting alert dialog icon
					alertDialog.setIcon((status) ? R.drawable.success
							: R.drawable.fail);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									_activity.finish();
								}
							});

					// Showing Alert Message
					alertDialog.show();
				}
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				alert.showAlertDialog(_activity, "Error", error.getMessage(),
						false);
			}
		});

		return categoryListOut;
	}

	public ArrayList<HashMap<String, String>> getWholeSaleProductList(
			Activity a, GridView gridview) {
		_activity = a;
		_gridview = gridview;
		final ArrayList<HashMap<String, String>> categoryListOut = new ArrayList<HashMap<String, String>>();
		Intent in = _activity.getIntent();
		marketid = in.getStringExtra(Extra.KEY_CATEGORY_ID);
		AsyncHttpClient ahc = new AsyncHttpClient();
		ahc.setTimeout(20000);

		RequestParams params = new RequestParams();
		// System.out.println(marketid);

		params.put("gcmregid", GCMRegistrar.getRegistrationId(_activity));
		params.put("categoryid", marketid);
		params.put("markettype", Extra.MACKET_TYPE_WHOLESALE);

		ahc.post(categorytURL, params, new AsyncHttpResponseHandler() {
			private DisplayMetrics metrics;

			ProgressDialog dialog;

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog = ProgressDialog.show(_activity, "",
						"Loading.....");
			}

			@Override
			public void onSuccess(String responce) {
				// TODO Auto-generated method stub
				super.onSuccess(responce);
				System.out.println(responce);
				try {
					JSONObject jObj = new JSONObject(responce);
					ProductSummeryData pd = new ProductSummeryData();
					productlist = pd.getProducSummerytData(jObj.getString(
							"products_price_summary").toString());
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
				System.out.println("onFinish " + productlist.size());
				if (productlist.size() > 0) {
					System.out.println("onFinish if " + productlist.size());
					for (ProductDetailsDto product : productlist) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Extra.KEY_TITLE, product.getProduct()
								.getProductName());
						map.put(Extra.KEY_ID, String.valueOf(product.getProduct()
								.getProductId()));
						map.put(Extra.KEY_THUMB_URL, product.getProduct()
								.getImageUrl());

						// System.out.println(product.getSummaryDetails().get(0)
						// .getAverage());
						// System.out.println(product.getSummaryDetails().get(0)
						// .getMarket_name());
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

							Intent in = new Intent(_activity
									.getApplicationContext(),
									WholeSalefProductActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

							Gson gson = new GsonBuilder()
									.excludeFieldsWithoutExposeAnnotation()
									.create();
							String jsonStringApprove = gson.toJson(productlist);
							in.putExtra(Extra.PARSEJSON, jsonStringApprove);
							in.putExtra(Extra.KEY_ID, String.valueOf(position));
							_activity.startActivity(in);

							_activity.overridePendingTransition(
									R.anim.pull_in_from_top, R.anim.hold);
						}
					});
				} else {
					boolean status = false;
					AlertDialog alertDialog = new AlertDialog.Builder(_activity)
							.create();

					// Setting Dialog Title
					alertDialog.setTitle("Error");

					// Setting Dialog Message
					alertDialog.setMessage("Data not found");

					// Setting alert dialog icon
					alertDialog.setIcon((status) ? R.drawable.success
							: R.drawable.fail);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									_activity.finish();
								}
							});

					// Showing Alert Message
					alertDialog.show();
				}
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				alert.showAlertDialog(_activity, "Error", error.getMessage(),
						false);
			}
		});

		return categoryListOut;
	}

}
