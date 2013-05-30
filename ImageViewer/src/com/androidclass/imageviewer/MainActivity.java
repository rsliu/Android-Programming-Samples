package com.androidclass.imageviewer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnItemClickListener {
	ImageSwitcher mSwitcher;
	Gallery mGallery;
	ImageAdapter mAdapter;
	ArrayList<Drawable> mImages;
	
	int[] mImageIDs = {
		R.drawable.image1,
		R.drawable.image2,
		R.drawable.image3,
		R.drawable.image4,
		R.drawable.image5
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		mSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView view = new ImageView(MainActivity.this);
				view.setBackgroundColor(0xff000000);
				view.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT
						));
				view.setScaleType(ScaleType.FIT_CENTER);
				return view;
			}
		});
				
		AsyncTask<Void, Void, Void> loadTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				mGallery = (Gallery) findViewById(R.id.gallery);
				mAdapter = new ImageAdapter(MainActivity.this, mImages);
				mGallery.setAdapter(mAdapter);
				mGallery.setOnItemClickListener(MainActivity.this);
				mSwitcher.setImageResource(R.drawable.image1);
			}

			@Override
			protected Void doInBackground(Void... params) {
				mImages = new ArrayList<Drawable>();
				String sdcard = Environment.getExternalStorageDirectory().getPath();
				File sdFiles = new File(sdcard);
				String[] jpgFiles = sdFiles.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String filename) {
						return filename.endsWith(".jpg");
					}
				});
				for (int i = 0; i < jpgFiles.length; i++) {
					mImages.add(Drawable.createFromPath(
							sdcard + "/" + jpgFiles[i]));
				}

				return null;
			}
			
		}.execute();
		
		Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//mSwitcher.setImageResource(mImageIDs[position]);
		mSwitcher.setImageDrawable(mImages.get(position));
	}

}
