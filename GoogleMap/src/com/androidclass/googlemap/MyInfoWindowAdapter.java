package com.androidclass.googlemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidclass.googlemap.R;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter {
	Context mContext;
	
	public MyInfoWindowAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		LayoutInflater inflater = (LayoutInflater) 
				mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView view = (TextView) inflater.inflate(R.layout.info_window, null);
		view.setText(marker.getSnippet());
		return view;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

}
