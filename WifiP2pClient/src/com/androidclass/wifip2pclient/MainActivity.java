package com.androidclass.wifip2pclient;

import java.util.Collection;

import com.androidclass.wifip2p.R;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionListener, PeerListListener {
	WifiP2pManager mManager;
	Channel mChannel;
	WifiP2pBroadcastReceiver mReceiver;
	IntentFilter mIntentFilter;
	ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Step 0: Setup objects needed for UI
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		// Step 3: In your activity's onCreate() method, obtain an instance of WifiP2pManager 
		//   and register your application with the Wi-Fi Direct framework by calling initialize(). 
		//   This method returns a WifiP2pManager.Channel, which is used to connect your application 
		//   to the Wi-Fi Direct framework. You should also create an instance of your broadcast 
		//   receiver with the WifiP2pManager and WifiP2pManager.Channel objects along with a 
		//   reference to your activity. This allows your broadcast receiver to notify your activity 
		//   of interesting events and update it accordingly
		mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(this, getMainLooper(), null);
		mReceiver = new WifiP2pBroadcastReceiver(mManager, mChannel, this);
		
		// Step 4: Create an intent filter and add the same intents that your broadcast 
		//   receiver checks for
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
	}

	// Step 5: Register the broadcast receiver in the onResume() method of 
	//   your activity and unregister it in the onPause() method of your activity
	@Override
	protected void onPause() {
		unregisterReceiver(mReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mReceiver, mIntentFilter);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Step 1: Create a Broadcast receiver for Wi-Fi direct intents. A broadcast receiver allows 
	//   you to receive intents broadcast by the Android system, so that your application can 
	//   respond to events that you are interested in.
	public class WifiP2pBroadcastReceiver extends BroadcastReceiver {
		private WifiP2pManager mManager;
		private Channel mChannel;
		private Activity mActivity;
		
		// Constructor. You most likely want to have parameters for the WifiP2pManager, WifiP2pManager.Channel, 
		// and the activity that this broadcast receiver will be registered in. This allows the broadcast receiver 
		// to send updates to the activity as well as have access to the Wi-Fi hardware and a communication 
		// channel if needed.
		public WifiP2pBroadcastReceiver(WifiP2pManager manager, Channel channel, Activity activity) {
			mManager = manager;
			mChannel = channel;
			mActivity = activity;
		}
		
		// Check for the intents that you are interested in onReceive(). Carry out any necessary 
		// actions depending on the intent that is received. For example, if the broadcast receiver 
		// receives a WIFI_P2P_PEERS_CHANGED_ACTION intent, you can call the requestPeers() method 
		// to get a list of the currently discovered peers.
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			// Peers list has changed
			if (action.equals(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)) {
				mManager.requestPeers(mChannel, MainActivity.this);
			}
			// Wifi P2p state changed
			else if (action.equals(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)) {
				// Check if Wifi P2p is on/supported
				int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
				if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
					// Wifi P2p is enabled
				} else {
					// Wifi P2p is disabled
				}
			} 
		}		
	}

	// Step 6a: Discovering peers.
	@Override
	public void onFailure(int arg0) {
		Toast.makeText(this, "Failed to detect peers.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSuccess() {
		// The onSuccess() method only notifies you that the discovery process succeeded and
		// does not provide any information about the actual peers that it discovered, if any.		
	}
	
	// Step 6b: Discovering peers. If the discovery process succeeds and detects peers, 
	//   the system broadcasts the WIFI_P2P_PEERS_CHANGED_ACTION intent, which you can 
	//   listen for in a broadcast receiver to obtain a list of peers. When your application 
	//   receives the WIFI_P2P_PEERS_CHANGED_ACTION intent, you can request a list of the 
	//   discovered peers with requestPeers().
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
		Collection<WifiP2pDevice> list =  peers.getDeviceList();
		
		// Update device list
		mAdapter.clear();
		for(WifiP2pDevice device : list) {
			mAdapter.add(device.deviceName);
		}
		mAdapter.notifyDataSetChanged();
	}	
}
