package com.androidclass.geofencedetection;

import java.util.List;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class GeofenceIntentService extends IntentService {
    /**
     * Sets an identifier for the service
     */
    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }
    
    /**
     * Handles incoming intents
     *@param intent The Intent sent by Location Services. This
     * Intent is provided
     * to Location Services (inside a PendingIntent) when you call
     * addGeofences()
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // First check for errors
        if (LocationClient.hasError(intent)) {
            // Get the error code with a static method
            int errorCode = LocationClient.getErrorCode(intent);
            // Log the error
            Log.e("GeofenceIntentService",
                    "Location Services error: " +
                    Integer.toString(errorCode));
            /*
             * You can also send the error code to an Activity or
             * Fragment with a broadcast Intent
             */
        /*
         * If there's no error, get the transition type and the IDs
         * of the geofence or geofences that triggered the transition
         */
        } else {
            // Get the type of transition (entry or exit)
            int transitionType = LocationClient.getGeofenceTransition(intent);
            // Test that a valid transition was reported
            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ||
            	transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                List <Geofence> triggerList = LocationClient.getTriggeringGeofences(intent);
                String[] triggerIds = new String[triggerList.size()];

                if (triggerList != null) {
	                for (int i = 0; i < triggerIds.length; i++) {
	                    // Store the Id of each geofence
	                    triggerIds[i] = triggerList.get(i).getRequestId();
	                }
                }
                /*
                 * At this point, you can store the IDs for further use
                 * display them, or display the details associated with
                 * them.
                 */
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getBaseContext(), "Arrived at NCKU!", Toast.LENGTH_LONG).show();
					}
                });                
            } else {
            	// An invalid transition was reported
	            Log.e("GeofenceIntentService",
	                    "Geofence transition error: " +
	                    Integer.toString(transitionType));
            }
        }
    }
}
