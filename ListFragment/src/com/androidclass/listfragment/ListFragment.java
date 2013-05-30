package com.androidclass.listfragment;

import com.androidclass.listviewex.R;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ListFragment extends Fragment implements LoaderCallbacks<Cursor> {
	SimpleCursorAdapter mAdapter;
	String[] from = {
		Phone.DISPLAY_NAME,
		Phone.NUMBER
	};
	
	int[] to = {
		R.id.textView1,
		R.id.textView2
	};
	
	View mView;
	ListView mListView;
	
	public ListFragment() {}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mAdapter = new SimpleCursorAdapter(
				getActivity(), 
				R.layout.list_item, 
				null, 
				from, 
				to,
				0);
		
		mListView.setAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = (View) inflater.inflate(R.layout.list_fragment, container, false);
		mListView = (ListView) mView.findViewById(R.id.listView);
		return mView;
	}

	@Override
	public Loader onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = {
				Phone._ID,
				Phone.DISPLAY_NAME,
				Phone.NUMBER
		};
		
		return new CursorLoader(
				getActivity(), 
				Phone.CONTENT_URI, 
				projection, 
				null, 
				null, 
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
