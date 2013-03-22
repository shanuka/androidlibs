package lk.zmessenger.consumerwatchconsummer.adapters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lk.zmessenger.consumerwatchconsummer.R;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.domain.PriceSummery;
import lk.zmessenger.consumerwatchconsummer.domain.Products;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.MarketSummeryData;
import lk.zmessenger.consumerwatchconsummer.ui.MTextView;

import org.ocpsoft.prettytime.PrettyTime;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

public class WholeSaleImagePagerAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private DisplayMetrics metrics;
	private Timestamp datest;

	public WholeSaleImagePagerAdapter(Activity a, ArrayList<HashMap<String, String>> d) {

		_activity = a;
		data = d;
		inflater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(_activity.getApplicationContext());
		metrics = new DisplayMetrics();
		_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object instantiateItem(View view, final int position) {
		final View imageLayout = inflater.inflate(
				R.layout.item_news_pager_image, null);
		
		final ViewHolder hoder = new ViewHolder();
		hoder.imageView = (ImageView) imageLayout
				.findViewById(R.id.imageView1);
		
		

		hoder.imageView2 = (ImageButton) imageLayout
				.findViewById(R.id.imageViewadded);
		hoder.imageView2.setTag(R.drawable.category);

		hoder.newspagertitle = (TextView) imageLayout
				.findViewById(R.id.textView1);
		hoder.listView = (ListView) imageLayout
				.findViewById(R.id.listView);
		hoder.textViewDate = (MTextView) imageLayout
				.findViewById(R.id.textViewDate);
		// final TextView newspagercontent = (TextView) imageLayout
		// .findViewById(R.id.newspagercontent);
		// final ProgressBar spinner = (ProgressBar) imageLayout
		// .findViewById(R.id.loading);
		//
		// newspagercontent.setText(imageContents[position]);
		// newspagertitle.setText(imageTitles[position]);

		final HashMap<String, String> page = data.get(position);
		hoder.newspagertitle.setText(page.get(Extra.KEY_TITLE));
		String productId = page.get(Extra.KEY_ID);

		Products product = new Select().from(Products.class)
				.where("product_id =?", productId).executeSingle();
		if (product != null) {
			System.out.println("product_name " + product.getProductName());
			hoder.imageView2.setImageResource(R.drawable.categoryadded);
			hoder.imageView2.setTag(R.drawable.categoryadded);
		}

		hoder.imageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String productId = page.get(Extra.KEY_ID);

				Products product = new Products();
				product.setProductId(Integer.valueOf(productId));
				product.setProductName(page.get(Extra.KEY_TITLE));
				product.setImageUrl(page.get(Extra.KEY_THUMB_URL));

				System.out.println(product.getId());
				Object tag = hoder.imageView2.getTag();
				// System.out.println(imageView2.getTag().to);
				// System.out.println(Integer.valueOf(imageView2.getTag().toString()));
				int id = Integer.parseInt(tag.toString());
				System.out.println("id " + id + " R.drawable.category "
						+ R.drawable.category + " R.drawable.categoryadded "
						+ R.drawable.categoryadded);
				switch (id) {
				case R.drawable.category:
					// do someoperation of ur choice

					System.out.println("category");
					product.save();
					hoder.imageView2.setTag(R.drawable.categoryadded);
					hoder.imageView2.setImageResource(R.drawable.categoryadded);
					break;
				case R.drawable.categoryadded:
					// do someoperation of ur choice
					Products productd = new Select().from(Products.class)
							.where("product_id =?", productId).executeSingle();
					productd.delete();
					System.out.println("categoryadded");
					hoder.imageView2.setImageResource(R.drawable.category);
					hoder.imageView2.setTag(R.drawable.category);
					break;
				}// end of switch
			}
		});

		// imageView.set
		imageLoader.DisplayImage(
				Extra.IMAGE_URL + page.get(Extra.KEY_THUMB_URL), hoder.imageView);
		RelativeLayout ll = (RelativeLayout) imageLayout
				.findViewById(R.id.linearLayout);
		if (page.get(Extra.KEY_MARKET_LIST) != null) {
			System.out.println(page.get(Extra.KEY_MARKET_LIST));
			MarketSummeryData msd = new MarketSummeryData();
			List<PriceSummery> maketList = msd.getMaketSummerytData(page
					.get(Extra.KEY_MARKET_LIST));
			// Timestamp datest = new Timestamp(Long.valueOf(song
			// .get(Extra.KEY_DURATION)));

			// Date datetest = new Date(datest.getTime());
			// textViewDate.setText(maketList.);
			ArrayList<HashMap<String, String>> d = new ArrayList<HashMap<String, String>>();
			for (PriceSummery priceSummery : maketList) {
				datest = new Timestamp(Long.valueOf(priceSummery
						.getLastUpdated()));

				System.out.println(priceSummery.getAverage());

				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Extra.KEY_TITLE, priceSummery.getMarket_name());
				map.put(Extra.KEY_ARTIST, priceSummery.getAverage());
				Timestamp datest = new Timestamp(Long.valueOf(priceSummery
						.getLastUpdated()));

				Date datetest = new Date(datest.getTime());

				PrettyTime p = new PrettyTime();

				map.put(Extra.KEY_DATE, p.format(datetest));
				d.add(map);

			}
			HashMap<String, String> mapa = new HashMap<String, String>();
			d.add(mapa);
			hoder.listView.setAdapter(new LazyAdapter(_activity, d, metrics));
			ImageView im = (ImageView) imageLayout.findViewById(R.id.image);
			im.setVisibility(View.GONE);
			Date datetest = new Date(datest.getTime());
			hoder.textViewDate.setText(datetest.toLocaleString());
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
			hoder.listView.setLayoutAnimation(controller);
			// shopListView.setAdapter(adapter)

		} else {

			ll.setVisibility(View.GONE);
			hoder.textViewDate.setVisibility(View.GONE);
			// im.setVisibility(View.VISIBLE);
			// listView.setEmptyView(im);
		}

		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}
	static class ViewHolder{
		ImageView imageView;
		ImageButton imageView2;
		TextView newspagertitle;
		ListView listView;
		MTextView textViewDate;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}

}
