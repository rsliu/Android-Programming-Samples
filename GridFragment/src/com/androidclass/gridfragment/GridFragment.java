package com.androidclass.gridfragment;

import com.androidclass.gridviewex.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GridFragment extends Fragment {
	final String[] data = {"Banana", "Apple", "Strawberry", "Blueberry", "Pineapple" };

	View mView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GridAdapter adapter = new GridAdapter(getActivity(), data);
		
		// !!! Need to use the layout's view object here
		GridView gridView = (GridView) mView.findViewById(R.id.gridView);
		gridView.setAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.grid_view, null);
		return mView;
	}

}
