package com.androidclass.linearlayout;

import com.androidclass.linearlayout.R;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LinearLayoutActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Linear layout examples
		setContentView(R.layout.linear_layout); // Load linear layout
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.linearLayout); // Must be after setContentView(). Layout View does not exist before setContentView() 
		
		// If you have a lot of child views of the same type and you wanted
		// to use the same event handler, use a loop for brevity
		for (int index = 1; index < layout.getChildCount(); index++) {
			Button button = (Button) layout.getChildAt(index);
			button.setOnClickListener(this);
		}
		
		// Below is what we would normally do, which takes a lot of code
//		Button button = (Button) this.findViewById(R.id.buttonBottom);
//		button.setOnClickListener(this);
//		button = (Button) this.findViewById(R.id.buttonTop);
//		button.setOnClickListener(this);
//		button = (Button) this.findViewById(R.id.buttonLeft);
//		button.setOnClickListener(this);
//		button = (Button) this.findViewById(R.id.buttonRight);
//		button.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    
	    LocationManager locationManager =
	            (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    final boolean gpsEnabled = 
	    		locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    if (!gpsEnabled) {
	    	Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		    startActivity(settingsIntent);	    	
	    }
	}

	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_layout_examples, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.linearLayout);

		switch (v.getId()) {
		case R.id.buttonLeft:
			layout.setGravity(Gravity.LEFT);
			break;
		case R.id.buttonTop:
			layout.setGravity(Gravity.TOP);
			break;
		case R.id.buttonRight:
			layout.setGravity(Gravity.RIGHT);
			break;
		case R.id.buttonBottom:
			layout.setGravity(Gravity.BOTTOM);
			break;
		case R.id.buttonHorizontal:
			layout.setOrientation(LinearLayout.HORIZONTAL);
			break;
		case R.id.buttonVertical:
			layout.setOrientation(LinearLayout.VERTICAL);
			break;
		}
	}
	
}
