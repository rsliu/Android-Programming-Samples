package com.androidclass.contactlist;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract; //  defines useful constants and methods for accessing the provider
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.androidclass.contactlist.R;

public class ContactsFragment extends Fragment implements LoaderCallbacks<Cursor>, OnItemClickListener {
	/*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    //@SuppressLint("InlinedApi")
    String[] FROM_COLUMNS = {
        	Phone.DISPLAY_NAME_PRIMARY,
        	Phone.NUMBER,
    		//People.DISPLAY_NAME,
    		//People.Phones.NUMBER
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    int[] TO_IDS = {
           R.id.text1,
           R.id.text2
    };
    
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
      
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;
        
    @SuppressLint("InlinedApi")
	String[] PROJECTION =
        {
            /*
             * The detail data row ID. To make a ListView work,
             * this column is required.
             */
            //People._ID,
            //Contacts.LOOKUP_KEY,
            // The primary display name
            //People.DISPLAY_NAME,
    		Phone._ID,
    		Phone.DISPLAY_NAME_PRIMARY,
            Phone.NUMBER
        };    
    
    // Empty public constructor, required by the system
    public ContactsFragment() {}

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.contacts_list_view, container, false);
    }    
    
    //Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) getActivity().findViewById(R.id.listView);
        
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                null, // null cursor
                FROM_COLUMNS, TO_IDS,
                0);
                
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);
        
        // Set the item click listener to be the current fragment
        mContactsList.setOnItemClickListener(this);
        
        // Initialize the background thread and other variables that control asynchronous retrieval
        // 1. id, 2. Bundle, 3. LoaderCallbacks
        getLoaderManager().initLoader(0, null, this);
    }    
    
	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */        
        // Starts the query		
        return new CursorLoader(
                getActivity(),
                Phone.CONTENT_URI,
                PROJECTION,
                null,
                null, //new String[] {String.valueOf(mContactId)}, //mSelectionArgs,
                null
        );
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(cursor);		
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        // Get the Cursor
        Cursor cursor = ((CursorAdapter) parent.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        
        Toast.makeText(getActivity(), cursor.getString(1), Toast.LENGTH_LONG).show();   		
	}	
}
