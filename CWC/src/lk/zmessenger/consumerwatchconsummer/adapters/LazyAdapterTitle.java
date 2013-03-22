package lk.zmessenger.consumerwatchconsummer.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import lk.zmessenger.consumerwatchconsummer.R;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.adapters.LazyAdapter.ViewHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapterTitle extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapterTitle(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
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
		View vi = convertView;

		
		ViewHolder holder = null;
		if (convertView == null) {
			
			vi = inflater.inflate(R.layout.list_row_title_only_noback, null);
			holder = new ViewHolder();
			
			holder.title = (TextView) vi.findViewById(R.id.title2); // title

			holder.textView1 = (TextView) vi.findViewById(R.id.textView1);

			holder.thumb_image = (ImageView) vi.findViewById(R.id.list_image2); // thumb
																					// image

			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);

		// Setting all values in listview
		holder.title.setText(song.get(Extra.KEY_TITLE));
		//System.err.println(Extra.IMAGE_URL + song.get(Extra.KEY_THUMB_URL));
		holder.textView1.setText(song.get(Extra.KEY_DESCRIPTION));
		imageLoader.DisplayImage(
				Extra.IMAGE_URL + song.get(Extra.KEY_THUMB_URL), holder.thumb_image);
		return vi;
		
		
		
	}
	static class ViewHolder {
		TextView title;
		TextView textView1;
		ImageView thumb_image;
	}
}