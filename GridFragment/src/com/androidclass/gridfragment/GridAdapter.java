package com.androidclass.gridfragment;

import com.androidclass.gridviewex.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
			view = mActivity.getLayoutInflater().inflate(R.layout.grid_item, null);
			TextView txtView = (TextView) view.findViewById(R.id.textView1);
			txtView.setText(mData[position]);
		}
		return view;
	}	
}
