package com.androidclass.imagepager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.androidclass.imagepager.R;

public class MainActivity extends ListActivity {
	ArrayList<String> mNames;
	ListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNames = new ArrayList<String>();

		String sdcard = Environment.getExternalStorageDirectory().getPath();
		File sdFiles = new File(sdcard);
		File[] jpgFiles = sdFiles.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".jpg");
			}
		});
		
		for (File file : jpgFiles) {
			mNames.add(file.getPath());
		}
		
		mAdapter = new ListAdapter(this, mNames);
		this.setListAdapter(mAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Create an intent for passing file names
		Intent intent = new Intent(this, ViewActivity.class);
		intent.putStringArrayListExtra("names", mNames);
		// Start the image viewer activity
		// Make sure you have the other activity added in the manifest file
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
