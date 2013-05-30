package com.androidclass.imageswitcher;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
	Context mContext;
	/*int[] mImageIDs;
	public ImageAdapter(Context context, int[] imageIDs) {
		mContext = context;
		mImageIDs = imageIDs;
	}*/
	
	ArrayList<Drawable> mImages;
	public ImageAdapter(Context context, ArrayList<Drawable> images) {
		mContext = context;
		mImages = images;
	}

	@Override
	public int getCount() {
		//return mImageIDs.length;
		return mImages.size();
	}

	@Override
	public Object getItem(int position) {
		//return mImageIDs[position];
		return mImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		ImageView imageView = new ImageView(mContext);
		//imageView.setImageResource(mImageIDs[position]);
		imageView.setImageDrawable(mImages.get(position));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new Gallery.LayoutParams(100, 120));
		return imageView;		
	}

}
