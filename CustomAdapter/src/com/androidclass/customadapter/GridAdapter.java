package com.androidclass.customadapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	Activity mActivity;
	String[] mData;
	
	public GridAdapter(Activity activity, String[] data) {
		mActivity = activity;
		mData = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View counterView, ViewGroup arg2) {
		View view;
		
		// TODO Auto-generated method stub
		if (counterView == null) {
			view = mActivity.getLayoutInflater().inflate(R.layout.grid_item, null);
			ImageView img = (ImageView) view.findViewById(R.id.imageView1);
			img.setImageResource(R.drawable.ic_launcher);
			TextView txt = (TextView) view.findViewById(R.id.textView1);
			txt.setText(mData[position]);
		} else {
			view = counterView;
		}
		return view;
	}

}
