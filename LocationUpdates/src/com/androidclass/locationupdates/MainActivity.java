package com.androidclass.locationupdates;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements 
					GooglePlayServicesClient.ConnectionCallbacks, 
					GooglePlayServicesClient.OnConnectionFailedListener,
					LocationListener {
	/*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
	LocationClient mLocationClient;
	GoogleMap mMap;
	LocationRequest mLocationRequest;
	boolean mUpdatesRequested = true;
	SharedPreferences mPrefs;
	Editor mEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mLocationClient = new LocationClient(this, this, this);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000); 
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10);                                               
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);     
        
        // Open the shared preferences
        mPrefs = getSharedPreferences("SharedPreferences", this.MODE_PRIVATE);
        // Get a SharedPreferences editor
        mEditor = mPrefs.edit();        
	}	

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
        mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// Stop location update
		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(this);
		}
		// Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
	}

	@Override
	protected void onPause() {
		// Save the current setting for updates
        mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        mEditor.commit();
        super.onPause();		
	}

	@Override
	protected void onResume() {
		super.onResume();
        if (mPrefs.contains("KEY_UPDATES_ON")) {
        	// Get any previous setting for location updates. Gets "false" if an error occurs
            mUpdatesRequested = mPrefs.getBoolean("KEY_UPDATES_ON", false);        
        } else {
        	// Otherwise, turn off location updates
            mEditor.putBoolean("KEY_UPDATES_ON", false);
            mEditor.commit();
        }		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//
	// OnConnectionFailedListener Interface
	//
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
        // Google Play services can resolve some errors it detects.
        // If the error has a resolution, try sending an Intent to
        // start a Google Play services activity that can resolve
        // error.
        //
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);             
                // Thrown if Google Play services canceled the original PendingIntent
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            // If no resolution is available, display a dialog to the user with the error.
            //showDialog(connectionResult.getErrorCode());
        }
	}
	// ------------

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	       // Decide what to do based on the original request code
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
             // If the result code is Activity.RESULT_OK, try to connect again
             switch (resultCode) {
                  case Activity.RESULT_OK :
                	  // Try the request again
                  break;
             }
        }
	}

	//
	// ConnectionCallbacks Interface
	//
	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();		
	}
	// ----

	@Override
	public void onLocationChanged(Location location) {
		LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLoc, 10);
		mMap.animateCamera(update);
		
		Toast.makeText(this, newLoc.toString(), Toast.LENGTH_LONG).show();
	}
}
