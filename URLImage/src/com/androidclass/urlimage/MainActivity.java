package com.androidclass.urlimage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	String mURL = "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png";
	ImageView mImageView;
	
	// Method 2: Using thread
	Handler mHandler;
	Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mImageView = (ImageView) findViewById(R.id.imageView);
        
        
        // Method 1: Using a AsyncTask: AsyncTask is designed to be a helper class around 
        // Thread and Handler and does not constitute a generic threading framework.
        // AsyncTasks should ideally be used for short operations (a few seconds at the most.) 
        // If you need to keep threads running for long periods of time, it is highly recommended 
        // you use the various APIs provided by the java.util.concurrent pacakge
        /*AsyncTask<String, Void, Bitmap> downloadTask = new AsyncTask<String, Void, Bitmap>() {

			@Override
			protected void onPostExecute(Bitmap result) {
				mImageView.setImageBitmap(result);
			}

			@Override
			protected Bitmap doInBackground(String... urls) {
				Bitmap bmp = null;
				// Load image from the network
				try {
					URL url = new URL(urls[0]);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					bmp = BitmapFactory.decodeStream(is);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return bmp;
			}
        	
        }.execute(mURL);*/
        
        // Method 2: Using a thread
        mHandler = new Handler();
        Thread downloadThread = new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					// Load image from the network
					URL url = new URL(mURL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					mBitmap = BitmapFactory.decodeStream(is);
					
					// Thread cannot update the UI directly, so
					// notify the UI thread to perform the update via
					// the handler
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mImageView.setImageBitmap(mBitmap);
						}
					});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        	
        });
        
        downloadThread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
