package com.androidclass.calendarevents;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
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
	
	SimpleCursorAdapter mAdapter; // This is the Adapter being used to display the list's data.

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
