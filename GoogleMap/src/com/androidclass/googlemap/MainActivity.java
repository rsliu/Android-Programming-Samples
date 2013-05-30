package com.androidclass.googlemap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.androidclass.googlemap.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap mMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setUpMapIfNeeded("No. 1 University");
        getAddressTask.execute(new LatLng(22.996518,120.216574));
    }
    
    AsyncTask<LatLng, Void, String> getAddressTask = new AsyncTask<LatLng, Void, String> () {
		@Override
		protected String doInBackground(LatLng... params) {
			Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
			String addressText = "";
			LatLng loc = params[0];
			
			try {
				List<Address> addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1);
				Address address = addresses.get(0);
				
				addressText = String.format("%s, %s, %s", 
						address.getMaxAddressLineIndex() > 0 ?  
								address.getAddressLine(0) : "",		// Line 0
                                address.getLocality(),				// City
                                address.getCountryName());			// Country
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addressText;
		}

		@Override
		protected void onPostExecute(String result) {
			setUpMapIfNeeded(result);
			super.onPostExecute(result);
		}
    	
    };
    
    private void setUpMapIfNeeded(String info) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // The Map is verified. It is now safe to manipulate the map.
            	mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            	
            	mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this));
            	
            	LatLng nckuLatLng = new LatLng(22.996518,120.216574);
            	Marker marker = mMap.addMarker(new MarkerOptions()
            		.position(nckuLatLng)			// set position of the marker
            		.title("NCKU")					// set title
            		.snippet(info)	// set snippet
            		.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));	// set marker icon
            	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(nckuLatLng, 17);
            	mMap.animateCamera(update);
            	
            	// Provide user with their current position on the map
            	// When the My Location layer is enabled, the My Location button appears 
            	// in the top right corner of the map. When a user clicks the button, 
            	// the camera centers the map on the current location of the device, 
            	// if it is known. The location will be indicated on the map by a small 
            	// blue dot if the device is stationary, or as a chevron if the device is moving
            	mMap.setMyLocationEnabled(true);
            	mMap.setOnMarkerClickListener(this);
            	mMap.setOnInfoWindowClickListener(this);
            }
        }
    }

	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return true;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		marker.hideInfoWindow();
	}
}
