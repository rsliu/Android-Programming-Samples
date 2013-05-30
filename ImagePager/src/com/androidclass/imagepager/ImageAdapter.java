package com.androidclass.imagepager;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

// Base class providing the adapter to populate pages inside of a ViewPager
public class ImageAdapter extends PagerAdapter {
	Context mContext;
	ArrayList<String> mNames;
	
	public ImageAdapter(Context context, ArrayList<String> names){
		mContext = context;
		mNames = names;
	}

	@Override
	public int getCount() {
		return mNames.size();
	}

	// Determines whether a page View is associated with a specific key object as 
	// returned by instantiateItem(ViewGroup, int). This method is required for a 
	// PagerAdapter to function properly
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	// Remove a page for the given position. The adapter is responsible for 
	// removing the view from its container
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		ImageView imageView = new ImageView(mContext);
		imageView.setBackgroundColor(0xff000000);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageView.setImageDrawable(Drawable.createFromPath(mNames.get(position)));
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}
	

}
