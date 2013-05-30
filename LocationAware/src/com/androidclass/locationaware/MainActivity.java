package com.androidclass.locationaware;

import java.util.ArrayList;

import com.androidclass.locationex.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements 
						GooglePlayServicesClient.ConnectionCallbacks,
						GooglePlayServicesClient.OnConnectionFailedListener,
						LocationListener,
						OnAddGeofencesResultListener{
	LocationClient mClient;
	LocationRequest mRequest;
	ArrayList<Geofence> mList;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(MainActivity.this, "Arrived at NCKU", Toast.LENGTH_LONG).show();
			super.handleMessage(msg);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mClient = new LocationClient(this, this, this);
		mRequest = LocationRequest.create();
		mRequest.setInterval(10000);
		mRequest.setFastestInterval(5000);
		mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mList = new ArrayList<Geofence>();
		mHandler = new Handler();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mClient.connect();
	}

	@Override
	protected void onStop() {
		if (mClient.isConnected()) {
			mClient.removeLocationUpdates(this);
		}
		mClient.disconnect();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
		mClient.requestLocationUpdates(mRequest, this);
		
		Geofence fence = new Geofence.Builder()
				.setRequestId("1")
				.setCircularRegion(22.998881, 120.216082, 2000)
				.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
				.setExpirationDuration(Geofence.NEVER_EXPIRE)
				.build();
		mList.add(fence);
		
		Intent intent = new Intent(this, GeofenceIntentSerivce.class);
		intent.setAction("GeofenceIntentSerivce");
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mClient.addGeofences(mList, pendingIntent, this);
	}

	@Override
	public void onDisconnected() {
		
	}

	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(this, location.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onAddGeofencesResult(int arg0, String[] arg1) {
		
	}

    public static class GeofenceIntentSerivce extends IntentService {

		public GeofenceIntentSerivce() {
			super("GeofenceIntentSerivce");
		}

		@Override
		protected void onHandleIntent(Intent intent) {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "Arrived at NCKU!", Toast.LENGTH_LONG).show();
				}
				
			});
		}
    	
    }
}
