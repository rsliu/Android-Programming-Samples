package com.androidclass.autocompletionsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.androidclass.autocompletionsample.R;
 
public class ColorAdapter extends ArrayAdapter<String> {
	private Context context;
 
	// context: needed for creating a View object
	// values: data strings
	public ColorAdapter(Context context, int layoutResourceId, String[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
	}
 
	// This method creates a view that displays the data at the specified
	// position in the dataset
	// position: The position of the item within the adapter's data set of the item 
	//           whose view we want.
	// convertView: The old view to reuse, if possible. Note: You should check that 
	//           this view is non-null and of an appropriate type before using.
	// parent: The parent that this view will eventually be attached to
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view;
 
		if (convertView == null) {
			// Create a new View
			view = new View(context);
 
			// Inflate the layout from mobile.xml
			view = inflater.inflate(R.layout.color_item, null);
 
			// Get color name from the parent
			String color = super.getItem(position);
			// Set TextView content
			TextView textView = (TextView) view.findViewById(R.id.color_name);
			textView.setText(color);
			// Set the icon accordingly
			ImageView imageView = (ImageView) view.findViewById(R.id.color_icon);
			if (color.equals("red")) {
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.red_icon));
			} else if (color.equals("blue")) {
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_icon));
			} else {
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.green_icon));
			}
		} else {
			view = (View) convertView;
		}
 
		return view;
	}
} 