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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        highTemp = (TextView) findViewById(R.id.highTemp);
        lowTemp = (TextView) findViewById(R.id.lowTemp);
        imageView = (ImageView) findViewById(R.id.imageView);
        
         
        
        AsyncTask<Void, Void, Void> parseTask = new AsyncTask<Void, Void, Void>() {
        	String high;
        	String low;
        	String current;
        	Bitmap bitmap;

			@Override
			protected void onPostExecute(Void result) {
				// UI must be updated in onPostExecute()
				currentTemp.setText(current);
				highTemp.setText(high);
				lowTemp.setText(low);
				super.onPostExecute(result);
			}

			@Override
			protected Void doInBackground(Void... arg0) {
		        try {
		        	// Get the HTML document
					String url = "http://tw.news.yahoo.com/weather/%E8%87%BA%E7%81%A3/%E5%8F%B0%E5%8D%97%E5%B8%82/%E5%8F%B0%E5%8D%97%E5%B8%82-2306182/";
					Document doc = Jsoup.connect(url).get();
					
					// Get the weather image
					Element element = doc.select("#obs-current-weather").first();
					String attr = element.attr("style");
					String[] parse = attr.split("'");
					String imageUrl = parse[1];
					// Start another task to download the image
					mDownloadTask.execute(imageUrl);
					
					// Get current/high/low temperatures
					element = doc.select("div.day-temp-current.temp-c").first();
					current = element.ownText();
					element = element.select("div.day-high-low").first();
					high = element.ownText();
					element = element.select("span.day-temp-low").first();
					low = element.ownText();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
