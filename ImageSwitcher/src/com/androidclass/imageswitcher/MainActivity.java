package com.androidclass.imageswitcher;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnItemClickListener {
	ImageSwitcher mImageSwitcher;
	Gallery mGallery;
	ImageAdapter mAdapter;
	String mSDPath;
	int[] mImageIDs = {
			R.drawable.image1,
			R.drawable.image2,
			R.drawable.image3,
			R.drawable.image4,
			R.drawable.image5
	};
	
	ArrayList<Drawable> mImages = new ArrayList<Drawable>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		mImageSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setBackgroundColor(0xFF000000); // Transparent background
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams( // Set width and height of the view
						new ImageSwitcher.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
				return imageView;
			}
			
		});
		mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
		mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
		
		// Load images
		mSDPath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(mSDPath);
		String[] imageFiles = file.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".jpg");
			}}
		);
		
		mGallery = (Gallery) findViewById(R.id.gallery);
		mAdapter = new ImageAdapter(this, mImages);
		mGallery.setAdapter(mAdapter);
		mGallery.setOnItemClickListener(this);
		
		AsyncTask<String[], Void, Void> imageLoadingTask = new AsyncTask<String[], Void, Void> () {

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				mAdapter.notifyDataSetChanged();
				mGallery.setSelection(0); // set default selection
				mImageSwitcher.setImageDrawable(mImages.get(0));				
			}

			@Override
			protected Void doInBackground(String[]... params) {
				for (int i = 0; i < params[0].length; i++) {
					if (params[0][i].endsWith(".jpg")) {
						File jpgFile = new File (params[0][i]);
						mImages.add(Drawable.createFromPath(mSDPath + "/" + params[0][i]));
					}
				}							
				return null;
			}
			
		}.execute(imageFiles);
		

		
		//gallery.setAdapter(new ImageAdapter(this, mImageIDs));
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		//mImageSwitcher.setImageResource(mImageIDs[position]);
		mImageSwitcher.setImageDrawable(mImages.get(position));
	}

}
