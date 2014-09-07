package com.androidclass.calendarevents;

import java.util.Calendar;
import java.util.TimeZone;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    final String[] COLUMNS = new String[] {
    	CalendarContract.Events.TITLE,
    	CalendarContract.Events.DTSTART
    };
    
    final int[] VIEW_IDS = new int[] {
    	android.R.id.text1,
    	android.R.id.text2
    };
    
    final String[] PROJECTIONS = new String[] {
    		CalendarContract.Events._ID,
        	CalendarContract.Events.TITLE,
        	CalendarContract.Events.DTSTART
    };
    
    final String[] CALENDAR_PROJECTIONS = new String[] {
        	CalendarContract.Calendars._ID,
        	CalendarContract.Calendars.ACCOUNT_NAME,
        	CalendarContract.Calendars.ACCOUNT_TYPE
        };
	
	SimpleCursorAdapter mAdapter; // This is the Adapter being used to display the list's data.
	long mCalendarID;
	Animation animFadein;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		// Step 1a: Create am empty adapter to display the loaded data
		mAdapter = new SimpleCursorAdapter(
				this,											// Context
				android.R.layout.simple_expandable_list_item_2,	// Layout
				null,											// Cursor
				COLUMNS,										// Columns to display
				VIEW_IDS,										// Views used to display the columns
				0);
		
		mAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Cursor cursor, int col) {
				if (col == 2) {
					long dateTime = cursor.getLong(2);
					String date = DateFormat.getDateFormat(MainActivity.this).format(dateTime);
					String time = DateFormat.getTimeFormat(MainActivity.this).format(dateTime);
					TextView textView = (TextView) view;
					textView.setText(date + " at " + time);
					return true;
				}
				return false;
			}									
		});
		
		setListAdapter(mAdapter);
		
		// Step 1b: Initializes the CursorLoader.
		getLoaderManager().initLoader(
				0, 		// A unique ID for this loader
				null,	// Optional arguments (Bundle) to supply to the loader at construction
				this	// Interface the LoaderManager will call to report about changes in the state of the loader
				);
		
		// Setup action bar
		ActionBar actionBar = this.getActionBar();
		
		// Run query to find out the ID of Google Calendar Event Table
		// Better done using a AsyncTask
		Cursor cur = null;
		ContentResolver cr = getContentResolver();
		Uri uri = CalendarContract.Calendars.CONTENT_URI;   
		String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND (" 
		                        + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?))";
		String[] selectionArgs = new String[] {"you.google.account@gmail.com", "com.google"}; 	// Replace with your own email address
		// Submit the query and get a Cursor object back. 
		cur = cr.query(uri, CALENDAR_PROJECTIONS, selection, selectionArgs, null);
		
		mCalendarID = 1;
		while (cur.moveToNext()) {		      
		    // Get the field values
		    mCalendarID = cur.getLong(0);
		}
		
		getListView().setBackgroundColor(getResources().getColor(R.color.White));
		//getListView().setBackground(R.drawable.background);
		getListView().setBackgroundResource(R.drawable.background);
		
		// Load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animFadein.setDuration(5000);
        getListView().setAnimation(animFadein);        
	}

	@Override
	protected void onStart() {
		super.onStart();
		animFadein.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_add:
			// Add an event to the calendar. Better done in an AsyncTask or a thread
			Calendar beginTime = Calendar.getInstance();
			beginTime.set(2013, Calendar.NOVEMBER, 9, 9, 30);
			Calendar endTime = Calendar.getInstance();
			endTime.set(2013, Calendar.NOVEMBER, 9, 10, 30);
			
		    ContentResolver contentResolver = this.getContentResolver();
		    ContentValues values = new ContentValues();
		    values.put(Events.CALENDAR_ID, mCalendarID); 							
		    values.put(Events.TITLE, "Progress check");
		    values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
		    values.put(Events.EVENT_LOCATION, "Office");
		    values.put(Events.DESCRIPTION, "Report progress and debug");
		    values.put(Events.DTSTART, beginTime.getTimeInMillis());
		    values.put(Events.DTEND, endTime.getTimeInMillis());
		    values.put(Events.EVENT_TIMEZONE, "Asia/Taipei");			// Time zone must be set
		    Uri uri = contentResolver.insert(Events.CONTENT_URI, values);			
			
		    // Alternatively, you can also add the event through the Calendar App.
		    // But this would require user interaction
			/*Intent intent = new Intent(Intent.ACTION_INSERT)
			    .setData(Events.CONTENT_URI)
			    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
			    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
			    .putExtra(Events.TITLE, "Meeting")
			    .putExtra(Events.DESCRIPTION, "Project meeting")
			    .putExtra(Events.EVENT_LOCATION, "Professor's office")
			    .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
			startActivity(intent);*/
			
			break;
		}
		return true;
	}

	// Step 2: Start the query. As soon as the background framework is initialized, 
	//   it calls your implementation of onCreateLoader(). To start the query, return 
	//   a CursorLoader from this method.
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Long now = System.currentTimeMillis();
		return new CursorLoader(
				this,									// Activity context
				CalendarContract.Events.CONTENT_URI,	// Table to query
				PROJECTIONS,							// An array of columns that should be included for each row retrieved
				"dtstart>" + now.toString(),			// No selection clause
				null,									// No selection arguments
				CalendarContract.Events.DTSTART			// Sort by Starting time
				);
	}

	// Step 3: Process the retrieved events
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// Delete the reference to the existing Cursor
		mAdapter.swapCursor(null);		
	}

}
