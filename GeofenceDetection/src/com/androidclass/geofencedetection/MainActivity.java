package com.androidclass.geofencedetection;

import java.util.ArrayList;
import java.util.List;

import com.androidclass.geofencedetection.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements 
					ConnectionCallbacks,
					OnConnectionFailedListener,
					OnAddGeofencesResultListener, 
					OnClickListener {
	
	LocationClient mLocationClient;
	
    // Persistent storage for geofences
    SimpleGeofenceStore mGeofenceStorage;
	// Internal List of Geofence objects
    ArrayList<Geofence> mGeofenceList;
    // PendingIntent
    PendingIntent mGeofencePendingIntent;
    // Google map
    GoogleMap mMap;
    // LocationManager
    LocationManager mManager;
    // Intent receiver
    BroadcastReceiver mReceiver;
    public static final String LOCATION_UPDATE = "com.androidclass.geofencedetection";
    // Dialog builder
    Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();				
		mBuilder = new AlertDialog.Builder(this);
		createGeofences();
		
		
		// Setup broadcast receiver so that we can get notified when user's location is changed
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String[] enteringLocations;
				String locationInfo = "You've arriver: ";
				
				enteringLocations = intent.getStringArrayExtra("EnteringLocations");
				for(String location : enteringLocations) {
					locationInfo = locationInfo + " " + location + ",";
				}
								
				// Removing the last ","
				locationInfo = locationInfo.substring(0, locationInfo.length()-1);				
				
				// Setup title and message. You can choose to use either a string resource or 
				// a character string
				mBuilder.setTitle("Location Update"); // String resource (passed in)
				mBuilder.setMessage(locationInfo.substring(0, locationInfo.length()-1)); // Character string
				
				// Finally, create the dialog
				mBuilder.create().show();				
			}			
		};
		
		LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(LOCATION_UPDATE);
		bManager.registerReceiver(mReceiver, intentFilter);					
		
		// Move camera to Taipei 101
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LatLng taipei101 = new LatLng(25.033498,121.564096);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(taipei101, 15);
		mMap.animateCamera(update);		
		
		// Set button click listener
		Button btn = (Button) findViewById(R.id.btnTaipei);
		btn.setOnClickListener(this);
		btn = (Button) findViewById(R.id.btnNCKU);
		btn.setOnClickListener(this);
	}
	
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
	}	
	
    /**
     * Get the geofence parameters for each geofence from the UI
     * and add them to a List.
     */
    public void createGeofences() {
    	// Get locations info
        String[] locations = getResources().getStringArray(R.array.locations);
        String[] latitudes = getResources().getStringArray(R.array.latitudes);
        String[] longitudes = getResources().getStringArray(R.array.longitudes);

		// Instantiate a new geofence storage area
        mGeofenceStorage = new SimpleGeofenceStore(this);
        // Instantiate the current List of geofences
        mGeofenceList = new ArrayList<Geofence>();
        
        // Create SimpleGeofence objects
        for (int i = 0; i < locations.length; i++) {
        	SimpleGeofence geofence = new SimpleGeofence(
	                locations[i],						// Geofence ID
	                Float.valueOf(latitudes[i]),		// Latitude
	                Float.valueOf(longitudes[i]),		// Longitude
	                1000,								// Radius
	                Geofence.NEVER_EXPIRE,	    		// Expiration time in milliseconds
	                Geofence.GEOFENCE_TRANSITION_ENTER); // Records only entry transitions
	        // Store this flat version
	        mGeofenceStorage.setGeofence(locations[i], geofence);        
	        mGeofenceList.add(geofence.toGeofence());
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
        if (LocationStatusCodes.SUCCESS == statusCode) {
            /*
             * Handle successful addition of geofences here.
             * You can send out a broadcast intent or update the UI.
             * geofences into the Intent's extended data.
             */
        	Toast.makeText(this, "Geofence added!", Toast.LENGTH_LONG).show();   
        } else {
            /*
             * Report errors here. 
             * You can log the error using Log.e() or update
             * the UI.
             */
        	Toast.makeText(this, "Adding Geofence failed!", Toast.LENGTH_LONG).show();
        }
        // Turn off the in progress flag and disconnect the client
        //mLocationClient.disconnect();		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// Create an explicit Intent
        Intent intent = new Intent(this, GeofenceIntentService.class);
        intent.setAction("GeofenceIntentService");
        
        // Create a PendingIntent
        mGeofencePendingIntent = PendingIntent.getService(
                this,
                0,		// Request code, not used
                intent,	// An intent describing the service to be started
                PendingIntent.FLAG_UPDATE_CURRENT); // Update existing PendingIntent if already exists 
        
		// Send a request to add the current geofences
        mLocationClient.addGeofences(
                mGeofenceList, mGeofencePendingIntent, this);
	}

	@Override
	public void onDisconnected() {
		mManager.removeTestProvider("MY_PROVIDER");
	}
	
	@Override
	public void onClick(View view) {
		Location location = new Location("MY_PROVIDER");  
		
		switch(view.getId()) {
		case R.id.btnTaipei:
	    	location.setLatitude(25.033498);
	    	location.setLongitude(121.564096);
	    	Toast.makeText(this, "Taipei 101!", Toast.LENGTH_SHORT).show(); 
			break;
		case R.id.btnNCKU:
	    	location.setLatitude(22.998881);
	    	location.setLongitude(120.216082);
	    	Toast.makeText(this, "NCKU!", Toast.LENGTH_SHORT).show(); 
	    	break;
		}
		
		// Set the mock location
    	location.setTime(System.currentTimeMillis());
    	mManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    	if (mManager.getProvider("MY_PROVIDER") == null) {
        	mManager.addTestProvider("MY_PROVIDER",
        			false, false, false, false, false, false, false, 
        			android.location.Criteria.POWER_HIGH, 
        			android.location.Criteria.ACCURACY_HIGH);
    	}

    	mManager.setTestProviderEnabled("MY_PROVIDER", true);
    	mManager.setTestProviderLocation("MY_PROVIDER", location);  	
	}
}
