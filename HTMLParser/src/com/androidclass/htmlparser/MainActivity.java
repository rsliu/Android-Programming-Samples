package com.androidclass.htmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends Activity {
	TextView currentTemp;
	TextView highTemp;
	TextView lowTemp;
	ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Step 1: Get references to all the UI controls
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        highTemp = (TextView) findViewById(R.id.highTemp);
        lowTemp = (TextView) findViewById(R.id.lowTemp);
        imageView = (ImageView) findViewById(R.id.imageView);
        
        // Step 2: Create a AsyncTask to load the HTML page and parse the contents
        new AsyncTask<Void, Void, Void>() {
        	String high;
        	String low;
        	String current;

			@Override
			protected void onPostExecute(Void result) {
				// Step 2d: UI must be updated in onPostExecute()
				currentTemp.setText(current);
				highTemp.setText(high);
				lowTemp.setText(low);
				super.onPostExecute(result);
			}

			@Override
			protected Void doInBackground(Void... arg0) {
		        try {
		        	// Step 2a: Get the HTML document
					String url = "http://tw.news.yahoo.com/weather/%E8%87%BA%E7%81%A3/%E5%8F%B0%E5%8D%97%E5%B8%82/%E5%8F%B0%E5%8D%97%E5%B8%82-2306182/";
					Document doc = Jsoup.connect(url).get();
					
					// Step 2b: Get the image URL
					Element element = doc.select("#obs-current-weather").first();
					String attr = element.attr("style");
					String[] parse = attr.split("'");
					String imageUrl = parse[1];
					
					// Step 2c: Get current/high/low temperatures
					element = element.select("div.day-temp-current.temp-c").first();
					current = element.ownText();
					element = element.select("div.day-high-low").first();
					high = element.ownText();
					element = element.select("span.day-temp-low").first();
					low = element.ownText();
					
					// Step 3b: Start another task to download the image
					mDownloadTask.execute(imageUrl);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
        }.execute();
    }
    
    // Step 3a: Create another AsyncTask for downloading the image
	AsyncTask<String, Void, Bitmap> mDownloadTask = new AsyncTask<String, Void, Bitmap>() {

		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
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
    	
    };       

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
