package com.androidclass.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	ArrayAdapter<String> mAdapter;
	ListView mListView;
	EditText mEditText;
	Button mButton;
	Socket mSocket;
	PrintStream mWriter;
	BufferedReader mReader;
	Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Step 1: Get references to UI controls
		mHandler = new Handler();
		mListView = (ListView) findViewById(R.id.listView);
		mEditText = (EditText) findViewById(R.id.editText);
		mButton = (Button) findViewById(R.id.btnSend);
		
		// Step 2: Setup adapter
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		mListView.setAdapter(mAdapter);
		
		// Step 3: Create a asynchronous task to establish the internet connection
		new AsyncTask<String, Void, Void>() {
			// Step 3a: Establish the connection
			@Override			
			protected Void doInBackground(String... urls) {
				try {
					InetAddress addr = InetAddress.getByName(urls[0]);
					mSocket = new Socket(addr, 8888);
					
					// Create writer and reader
					mWriter = new PrintStream(mSocket.getOutputStream());
					mReader = new BufferedReader(
							new InputStreamReader(mSocket.getInputStream()));
					
					// Create a thread running in the background to receive incoming messages
					new Thread(new Runnable() {
						String in;			
						@Override
						public void run() {
							try {
								// Keeps listening incoming messages
								while((in = mReader.readLine()) != null) {
									// Send a runnable object to the UI thread for updating the list view
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											mAdapter.add(in);
											mAdapter.notifyDataSetChanged();
											mListView.setSelection(mListView.getCount() - 1);
										}
									});
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}			
					}).start();					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	
				return null;
			}
		}.execute("140.116.53.118");
		
		// Step 4a: Setup OnClickListener for the button
		mButton.setOnClickListener(this);
	}

	// Step 5: Close the internet connection onDestroy()
	@Override
	protected void onDestroy() {
		try {
			// Close the socket
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Step 4b: Send out the message and clear the EditBox
	@Override
	public void onClick(View arg0) {
		String text = mEditText.getText().toString() + "\r\n";
		mWriter.print(text);
		mEditText.setText("");
	}

}
