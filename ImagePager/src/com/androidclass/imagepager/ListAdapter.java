package com.androidclass.imagepager;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.androidclass.imagepager.R;

public class ListAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<String> mNames;
	
	public ListAdapter(Context context, ArrayList<String> names){
		mContext = context;
		mNames = names;
	}

	@Override
	public int getCount() {
		return mNames.size();
	}

	@Override
	public Object getItem(int position) {
		return mNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) 
				mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.list_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setImageDrawable(Drawable.createFromPath(mNames.get(position)));
		TextView textView = (TextView) view.findViewById(R.id.textView);
		textView.setText(mNames.get(position));
		return view;
	}

}
