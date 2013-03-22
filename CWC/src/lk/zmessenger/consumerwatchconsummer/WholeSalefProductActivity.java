package lk.zmessenger.consumerwatchconsummer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.WholeSaleImagePagerAdapter;
import lk.zmessenger.consumerwatchconsummer.domain.ProductDetailsDto;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ProductSummeryData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WholeSalefProductActivity extends Activity {

	private List<ProductDetailsDto> productlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		final ViewPager pager = (ViewPager) findViewById(R.id.pager);
		Intent in = getIntent();
		String marketid = in.getStringExtra(Extra.PARSEJSON);
		String position = in.getStringExtra(Extra.KEY_ID);
		System.out.println("ProductActivity " + marketid);
		ProductSummeryData pd = new ProductSummeryData();
		// ProductData pd = new ProductData();
		productlist = pd.getProducSummerytData(marketid);

		ArrayList<HashMap<String, String>> categoryListOut = new ArrayList<HashMap<String, String>>();

		for (ProductDetailsDto product : productlist) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(Extra.KEY_TITLE, product.getProduct().getProductName());
			map.put(Extra.KEY_THUMB_URL, product.getProduct().getImageUrl());
			String jsonStringApprove = null;
			if (product.getSummaryDetails().size() > 0) {
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				jsonStringApprove = gson.toJson(product
						.getSummaryDetails());

				//System.err.println(jsonStringApprove);
			}
			map.put(Extra.KEY_MARKET_LIST, jsonStringApprove);
			categoryListOut.add(map);
		}
		pager.setAdapter(new WholeSaleImagePagerAdapter(WholeSalefProductActivity.this,
				categoryListOut));
		pager.setCurrentItem(Integer.parseInt(position));
	}

	

}
