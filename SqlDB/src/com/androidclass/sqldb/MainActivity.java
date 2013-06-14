package com.androidclass.sqldb;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
	

	// Step 1: Create a helper class to manage database creation and version management
	//   You create a subclass implementing onCreate, onUpgrade and optionally onOpen,
	//   and this class takes care of opening the database if it exists, creating it if
	//   it does not, and upgrading it as necessary. Transactions are used to make sure
	//   the database is always in a sensible state.
	public static class DBHelper extends SQLiteOpenHelper {
		// Step 1a: Defines database name and version
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "Restaurant.db";
		
		// Step 1b: Defines column names for a single table ncku in a contract class.
		//   By implementing the BaseColumns interface, you'll inherit a primary key field
		//   called _ID which is required by some database classes in the Android framework.
		public static abstract class DBEntry implements BaseColumns {
			public static final String TABLE_NAME = "ncku";
			public static final String COLUMN_NAME_RESTAURANT_NAME = "name";
			public static final String COLUMN_NAME_FOOD_TYPE = "type";
		}
				
		// Step 1c: To prevents someone from accidentally instantiating the contract class,
		//   give it an empty constructor
		private DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		// Step 1d: Implements methods that create and maintain the database and tables
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String SQL_CREATE_ENTRIES =
				"CREATE TABLE " + DBEntry.TABLE_NAME + " (" +
				DBEntry._ID + " INTEGER PRIMARY KEY," +							// Primary key
				DBEntry.COLUMN_NAME_RESTAURANT_NAME + TEXT_TYPE + COMMA_SEP +	// Restaurant name column
				DBEntry.COLUMN_NAME_FOOD_TYPE + TEXT_TYPE + " )";				// Restaurant type column
		//private static final String SQL_DELETE_ENTRIES =
		//	    "DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES;

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_ENTRIES);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// If your database is only a cache for online data, you should discard
			// the old data here
		}
	}
	
	// Step 2: To access your database, instantiate your subclass of SQLiteOpenHelper
	DBHelper mDbHelper = new DBHelper(this);
	SQLiteDatabase mDb;
	
	Cursor mCursor;
	SimpleCursorAdapter mAdapter; // This is the Adapter being used to display the list's data.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		// Step 3: Create and/or open a database that will be used for reading and writing.
		mDb = mDbHelper.getWritableDatabase();
		
		// Step 4: Insert data into the database by passing a ContentValues object
		//   to the insert() method:
		ContentValues values = new ContentValues();
		values.put(DBHelper.DBEntry._ID, 1);
		values.put(DBHelper.DBEntry.COLUMN_NAME_RESTAURANT_NAME, "Tofu Chang");
		values.put(DBHelper.DBEntry.COLUMN_NAME_FOOD_TYPE, "Chinese");
		// The first argument for insert() is simply the table name. The second 
		// argument provides the name of a column in which the framework can insert 
		// NULL in the event that the ContentValues is empty (if you instead set 
		// this to "null", then the framework will not insert a row when there 
		// are no values)
		mDb.insert(DBHelper.DBEntry.TABLE_NAME, null, values);
		
		values.put(DBHelper.DBEntry._ID, 2);
		values.put(DBHelper.DBEntry.COLUMN_NAME_RESTAURANT_NAME, "Golden Sushi");
		values.put(DBHelper.DBEntry.COLUMN_NAME_FOOD_TYPE, "Japanese");
		mDb.insert(DBHelper.DBEntry.TABLE_NAME, null, values);
		
		values.put(DBHelper.DBEntry._ID, 3);
		values.put(DBHelper.DBEntry.COLUMN_NAME_RESTAURANT_NAME, "Spicy Kimchi");
		values.put(DBHelper.DBEntry.COLUMN_NAME_FOOD_TYPE, "Korean");
		mDb.insert(DBHelper.DBEntry.TABLE_NAME, null, values);
		
		// Step 5a: Read information from a database. To read from a database, create a cursor and use 
		//   the query() method, passing it your selection criteria and desired columns
		//   Define a projection that specifies which columns from the database you
		//   will actually use after this query
		String[] projection = {
				DBHelper.DBEntry._ID,
				DBHelper.DBEntry.COLUMN_NAME_RESTAURANT_NAME,
				DBHelper.DBEntry.COLUMN_NAME_FOOD_TYPE };
		
		// Step 5b: Create the cursor
		mCursor = mDb.query(
				DBHelper.DBEntry.TABLE_NAME,  // The table to query
			    projection,                     // The columns to return
			    null,                           // The columns for the WHERE clause
			    null,                           // The values for the WHERE clause
			    null,                           // don't group the rows
			    null,                           // don't filter by row groups
			    null                            // The sort order
			    );
		
		// Step 5c: Create the adapter require API level 11
		String[] columns = {
				DBHelper.DBEntry.COLUMN_NAME_RESTAURANT_NAME,
				DBHelper.DBEntry.COLUMN_NAME_FOOD_TYPE				
		};
		
		int[] view_ids = {
		    	android.R.id.text1,
		    	android.R.id.text2			
		};
		
		mAdapter = new SimpleCursorAdapter(
				this,											// Context
				android.R.layout.simple_expandable_list_item_2,	// Layout
				mCursor,											// Initially use a null cursor
				columns,										// Columns to display
				view_ids,										// Views used to display the columns
				0);
		
		setListAdapter(mAdapter); // Bind the adapter to the list view	
		
	}
}
