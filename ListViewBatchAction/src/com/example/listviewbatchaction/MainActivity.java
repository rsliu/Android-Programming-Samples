package com.example.listviewbatchaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView mListView;
	ArrayList<String> mFruits = new ArrayList<String>();
	ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Callback mCallBack = new Callback() {

			@Override
			public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		
		mFruits.add("Apple");
		mFruits.add("Banana");
		mFruits.add("Strawberry");
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mFruits);
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setAdapter(mAdapter);
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		/*mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
	            case R.id.menu_delete:
	            	SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
	            	for (int i = mAdapter.getCount() - 1; i >= 0; i--) {
	            		if (checkedItems.get(i)) {
	            			mFruits.remove(i);
	            		}
	            	}
	            	mAdapter.notifyDataSetChanged();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            default:
	                return false;
				}				
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// Inflate the menu for the CAB
				MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.actions, menu);
		        return true;				
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode arg0, int arg1,
					long arg2, boolean arg3) {
				// TODO Auto-generated method stub		
			}			
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
