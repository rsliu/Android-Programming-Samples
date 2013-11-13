package com.androidclass.geofencedetection;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity implements 
					ConnectionCallbacks,
					OnConnectionFailedListener,
					OnAddGeofencesResultListener, 
					OnClickListener,
					LocationListener{
	
	LocationClient mLocationClient;
	
    // Persistent storage for geofences
    SimpleGeofenceStore mGeofenceStorage;
	// Internal List of Geofence objects
    ArrayList<Geofence> mGeofenceList;
    // PendingIntent
    PendingIntent mGeofencePendingIntent;
    // Google map
    GoogleMap mMap;
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
				
				enteringLocations = intent.getStringArrayExtra("EnteringLocations");
				displayLocationInfo(enteringLocations);
			}			
		};
		
		LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(LOCATION_UPDATE);
		bManager.registerReceiver(mReceiver, intentFilter);					
		
		// Move camera to Taipei 101
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);		
		
		// Set button click listener
		Button btn = (Button) findViewById(R.id.btnMarket);
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
    
    private void displayLocationInfo(String[] entringLocations) {
    	String[] locations = getResources().getStringArray(R.array.locations);
    	String[] images = getResources().getStringArray(R.array.images);
    	String[] descriptions = getResources().getStringArray(R.array.descriptions);
    	int index = -1;
    	    	
    	for (int i = 0; i < entringLocations.length; i++) {
    		if (locations[i].equals(entringLocations[0])) {
    			index = i;
    			break;
    		}
    	}

		mBuilder.setTitle(locations[index]); 
		mBuilder.setMessage("Your are now at " + locations[index] + ". " + descriptions[index]);
		
		// Get the image resource ID
		Resources resources = getResources();
		int imageResource = resources.getIdentifier(images[index], "drawable", getPackageName());
		if (imageResource > 0) {
			mBuilder.setIcon(resources.getDrawable(imageResource));
		}
		// Finally, create the dialog
		mBuilder.create().show();
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
        
        // Request location update
        LocationRequest request = LocationRequest.create();
        mLocationClient.requestLocationUpdates(request, (com.google.android.gms.location.LocationListener) this);
	}

	@Override
	public void onDisconnected() {
		
	}
	
	@Override
	public void onClick(View view) {
		Location location = new Location("MY_PROVIDER");  
		
		switch(view.getId()) {
		case R.id.btnNCKU:
	    	location.setLatitude(22.998881);
	    	location.setLongitude(120.216082);
	    	Toast.makeText(this, "NCKU!", Toast.LENGTH_SHORT).show();
	    	break;
		case R.id.btnMarket:
	    	location.setLatitude(25.033498);
	    	location.setLongitude(121.564096);
	    	Toast.makeText(this, "Taipei 101!", Toast.LENGTH_SHORT).show(); 
			break;
		}				
	}

	@Override
	public void onLocationChanged(Location location) {
		// Move camera
		LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
		mMap.animateCamera(update);	
	}
}