package lk.zmessenger.consumerwatchconsummer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.ImagePagerAdapter;
import lk.zmessenger.consumerwatchconsummer.domain.ProductDetailsDto;
import lk.zmessenger.consumerwatchconsummer.domain.Products;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.ProductSummeryData;
import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductActivity extends Activity {

	private List<ProductDetailsDto> productlist;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		pager = (ViewPager) findViewById(R.id.pager);
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
			map.put(Extra.KEY_ID,
					String.valueOf(product.getProduct().getProductId()));
			map.put(Extra.KEY_THUMB_URL, product.getProduct().getImageUrl());
			String jsonStringApprove = null;
			if (product.getSummaryDetails().size() > 0) {
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				jsonStringApprove = gson.toJson(product.getSummaryDetails());

				// System.err.println(jsonStringApprove);
			}
			map.put(Extra.KEY_MARKET_LIST, jsonStringApprove);
			categoryListOut.add(map);
		}
		pager.setAdapter(new ImagePagerAdapter(ProductActivity.this,
				categoryListOut));
		pager.setCurrentItem(Integer.parseInt(position));
	}

	public void imageViewAdd(View view) {
		System.err.println("imageViewAdd");
		System.out.println(productlist.get(pager.getCurrentItem()).getProduct()
				.getProductName());
		LayoutInflater inflater = (LayoutInflater) ProductActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View imageLayout = inflater.inflate(
				R.layout.item_news_pager_image, null);
		
		ImageButton imageView2 = (ImageButton) findViewById(R.id.imageViewadded);
		System.out.println(imageView2.getTag());
		
		Object tag = imageView2.getTag();
//		System.out.println(imageView2.getTag().to);
//		System.out.println(Integer.valueOf(imageView2.getTag().toString()));
		int id = Integer.parseInt(tag.toString());
		System.out.println("id " + id + " R.drawable.category "
				+ R.drawable.category +" R.drawable.categoryadded "+R.drawable.categoryadded);
		switch (id) {
		case R.drawable.category:
			// do someoperation of ur choice

			System.out.println("category");
			Products product = productlist.get(pager.getCurrentItem())
					.getProduct();
			product.save();
			System.out.println(product.getId());
			imageView2.setTag(R.drawable.categoryadded);
			imageView2.setImageResource(R.drawable.categoryadded);
			break;
		case R.drawable.categoryadded:
			// do someoperation of ur choice
			System.out.println("categoryadded");
			imageView2.setBackgroundColor(Color.BLACK);
			imageView2.setTag(R.drawable.category);
			break;
		}// end of switch
//			// if(imageView2.getDrawableState())
//		// imageView2.setImageResource(R.drawable.categoryadded);
	}
}
