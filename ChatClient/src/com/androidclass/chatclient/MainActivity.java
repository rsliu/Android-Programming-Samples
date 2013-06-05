package com.androidclass.chatclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	ArrayList<String> mList;
	Socket mSocket;
	PrintStream mWriter;
	BufferedReader mReader;
	Thread mThread;
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
		
		// Step 2: Create objects needed by the ListView
		mList = new ArrayList<String>();
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		mListView.setAdapter(mAdapter);
		
		// Step 3: Create a asynchronous task to establish the internet connection
		AsyncTask<String, Void, Void> connTask = new AsyncTask<String, Void, Void>() {
			// Step 3a: Establish the connection
			@Override			
			protected Void doInBackground(String... urls) {
				try {
					InetAddress addr = InetAddress.getByName(urls[0]);
					mSocket = new Socket(addr, 8888);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			// Step 3b: Create reader and writer objects and start a thread in the background
			// listening for incoming messages
			@Override
			protected void onPostExecute(Void result) {
				try {
					// Create writer and reader
					mWriter = new PrintStream(mSocket.getOutputStream());
					mReader = new BufferedReader(
							new InputStreamReader(mSocket.getInputStream()));
					
					// Create a thread listening to incoming messages
					mThread = new Thread(new Runnable() {
						String in;			
						@Override
						public void run() {
							try {
								while((in = mReader.readLine()) != null) {
									// Send a runnable object to the UI thread 
									// for updating the list view
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
						
					});
					mThread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}
			
		}.execute("140.116.53.118");
		
		// Step 4a: Setup OnClickListener for the button
		mButton.setOnClickListener(this);
	}

	// Step 5: Close the internet connection onDestroy()
	@Override
	protected void onDestroy() {
		try {
			mSocket.close();	// Close the socket
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

	// Step 4b: Send out the message and clear the textbox
	@Override
	public void onClick(View arg0) {
		String text = mEditText.getText().toString() + "\r\n";
		mWriter.print(text);
		mEditText.setText("");
	}

}
