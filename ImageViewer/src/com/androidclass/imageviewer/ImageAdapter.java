package com.androidclass.imageviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
	Context mContext;
	int[] mImageIDs;
	ArrayList<Drawable> mImages;
	
	public ImageAdapter(Context context, ArrayList<Drawable> images) {
		mContext = context;
		//mImageIDs = imageIDs;
		mImages = images;
	}

	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public Object getItem(int position) {
		return mImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = new ImageView(mContext);
		view.setBackgroundColor(0xff000000);
		view.setScaleType(ScaleType.FIT_CENTER);
		view.setLayoutParams(new Gallery.LayoutParams(100,120));
		//view.setImageResource(mImageIDs[position]);
		view.setImageDrawable(mImages.get(position));
		return view;
	}

}
