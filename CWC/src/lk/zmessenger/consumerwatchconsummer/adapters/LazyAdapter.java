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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	private DisplayMetrics metrics_;
	private int lastposition = 0;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d,
			DisplayMetrics metrics) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
		View vi = null;
		ViewHolder holder = null;
		System.out.println("getCount" + getCount() + " position " + position);
		if (getCount() - position > 1) {
			if (convertView == null) {
				// imageView = (ImageView)
				// getLayoutInflater().inflate(R.layout.item_grid_image, parent,
				// false);
				vi = inflater.inflate(R.layout.list_row, null);
				holder = new ViewHolder();
				holder.title = (MTextView) vi.findViewById(R.id.marketName);
				holder.artist = (MTextView) vi.findViewById(R.id.textViewPrice);
				holder.date = (TextView) vi.findViewById(R.id.date);
				vi.setTag(holder);
			} else {
				holder = (ViewHolder) vi.getTag();
			}

			HashMap<String, String> song = new HashMap<String, String>();
			song = data.get(position);

			// Setting all values in listview
			holder.title.setText(song.get(Extra.KEY_TITLE));
			System.out.println(song.get(Extra.KEY_DATE));

			holder.date.setText(song.get(Extra.KEY_DATE));
			System.err.println("lazzy" + song.get(Extra.KEY_ARTIST));
			if (song.get(Extra.KEY_ARTIST) != null) {
				holder.artist.setText(String.format("%.2f",
						Double.valueOf(song.get(Extra.KEY_ARTIST))));

			}

			if (position >= lastposition) {
				Animation animation = new TranslateAnimation(
						metrics_.widthPixels / 2, 0, 0, 0);
				animation.setDuration(750);
				vi.startAnimation(animation);
				animation = null;
				lastposition = position;
			}

			return vi;
		} else {
			if (convertView == null) {
				// imageView = (ImageView)
				// getLayoutInflater().inflate(R.layout.item_grid_image, parent,
				// false);
				vi = inflater.inflate(R.layout.list_row_bottom, null);
				holder = new ViewHolder();

				vi.setTag(holder);
			} else {
				holder = (ViewHolder) vi.getTag();
			}

			return vi;

		}
	}

	static class ViewHolder {
		MTextView title;
		MTextView artist;
		TextView date;
	}
}