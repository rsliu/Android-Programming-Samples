package com.example.actionbartabs;

import java.util.Calendar;

import com.example.actionbartabsdemo.R;

import android.os.Bundle;
import android.provider.Contacts;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TabsDemoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs_demo);
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab datePickerTab = actionbar.newTab().setText("DatePicker");
		datePickerTab.setTabListener(new TabListener<DatePickerFragment>(
				this, "DatePicker", DatePickerFragment.class));
		actionbar.addTab(datePickerTab);
		
		ActionBar.Tab contactsTab = actionbar.newTab().setText("Contacts");
		contactsTab.setTabListener(new TabListener<ContactsFragment>(
				this, "Contacts", ContactsFragment.class));
		actionbar.addTab(contactsTab);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabs_demo, menu);
		return true;
	}

	public static class DatePickerFragment extends Fragment implements OnDateChangedListener {		
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        // Inflate the layout for this fragment	    	
	        View v = inflater.inflate(R.layout.date_picker, container, false);
	        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
	        datePicker.init(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, this);
	        return v;
	    }

		@Override
		public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Date set!", Toast.LENGTH_LONG).show();
		}
	}
	
	public static class ContactsFragment extends ListFragment {
		 @Override
		 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			Cursor names = getActivity().managedQuery(
					Contacts.Phones.CONTENT_URI, null, null, null, null);
			getActivity().startManagingCursor(names);
			ListAdapter adapter = new SimpleCursorAdapter( 
					getActivity(), R.layout.two_text,
					names, new String[] {
							Contacts.Phones.NAME,
							Contacts.Phones.NUMBER						
					}, new int[] {
							R.id.textName,
							R.id.textNumber
					});
			
			setListAdapter(adapter);
		 }		 
	}
	
	public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }
	    /* The following are each of the ActionBar.TabListener callbacks */
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	    }
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}	
}
