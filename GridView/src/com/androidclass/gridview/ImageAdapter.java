package com.androidclass.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.androidclass.imagegridview.R;
 
public class ImageAdapter extends BaseAdapter {
	Context context;
	String[] values;
 
	// context: needed for creating a View object
	// values: data strings
	public ImageAdapter(Context context, String[] values) {
		this.context = context;
		this.values = values;
	}
 
	// This method creates a view that displays the data at the specified
	// position in the dataset
	// position: The position of the item within the adapter's data set of the item 
	//           whose view we want.
	// convertView: The old view to reuse, if possible. Note: You should check that 
	//           this view is non-null and of an appropriate type before using.
	// parent: The parent that this view will eventually be attached to
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View view;
 
		if (convertView == null) {
			// Inflate the layout from mobile.xml
			view = inflater.inflate(R.layout.mobile, null);
 
			// Set value into textview
			TextView textView = (TextView) view.findViewById(R.id.grid_item_label);
			textView.setText(values[position]);
 
			// Set image based on selected text
			ImageView imageView = (ImageView) view.findViewById(R.id.grid_item_image);
 
			String os = values[position];
 
			if (os.equals("Windows")) {
				imageView.setImageResource(R.drawable.windowsmobile_logo);
			} else if (os.equals("iOS")) {
				imageView.setImageResource(R.drawable.ios_logo);
			} else if (os.equals("Blackberry")) {
				imageView.setImageResource(R.drawable.blackberry_logo);
			} else {
				imageView.setImageResource(R.drawable.android_logo);
			}
 
		} else {
			view = (View) convertView;
		}
 
		return view;
	}
 
	// How many items are in the data set represented by this Adapter.
	@Override
	public int getCount() {
		return values.length;
	}
 
	// Get the data item associated with the specified position in the data set.
	@Override
	public Object getItem(int position) {
		return null;
	}
 
    // Get the row id associated with the specified position in the list.
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}