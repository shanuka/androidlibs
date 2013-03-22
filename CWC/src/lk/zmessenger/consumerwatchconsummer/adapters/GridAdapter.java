package lk.zmessenger.consumerwatchconsummer.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import lk.zmessenger.consumerwatchconsummer.R;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.ui.MTextView;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private DisplayMetrics metrics_;
	private int lastposition = 0;

	public GridAdapter(Activity a, ArrayList<HashMap<String, String>> d,
			DisplayMetrics metrics) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		this.metrics_ = metrics;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// View vi = null;
		ViewHolder holder;

		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.grid_item_maket, null);
			holder = new ViewHolder();
			holder.name = (MTextView) convertView.findViewById(R.id.textView);
			holder.icon = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);

		// Setting all values in listview
		holder.name.setText(song.get(Extra.KEY_TITLE));
		// artist.setText(song.get(Extra.KEY_ARTIST));

		if (position > lastposition) {

			AnimationSet set = new AnimationSet(true);
			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(50);
			set.addAnimation(animation);
			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
					0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 1.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			//animation.setDuration(200);
			set.addAnimation(animation);
//			Animation animation = new TranslateAnimation(
//					metrics_.widthPixels / 2, 0, 0, 0);
			animation.setDuration(1000);
			convertView.startAnimation(set);
			animation = null;
			lastposition = position;
		}

		imageLoader.DisplayImage(
				Extra.IMAGE_URL + song.get(Extra.KEY_THUMB_URL), holder.icon);
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		ImageView icon;
	}
}