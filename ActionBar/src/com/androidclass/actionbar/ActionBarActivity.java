package com.androidclass.actionbar;

import com.androidclass.actionbar.ImageAdapter;
import com.androidclass.actionbar.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActionBarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar);
		
		// Get a reference to the action bar
		ActionBar actionbar = this.getActionBar();

		// Tell action bar that we wanted to use tabs
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		
		// Create the tab
		ActionBar.Tab gridTab = actionbar.newTab().setText("Tab 1");
		
		// Set Tab Listener to handle select, unselect, reselect events
		// The tab listener will create the fragment to be displayed inside the tab
		gridTab.setTabListener(new GridTabListener(this, "grid", GridFragment.class));
		
		// Now add the tab to the action bar
		actionbar.addTab(gridTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_action_bar, menu);		
		return true;
	}
	
	public static class GridFragment extends Fragment {
		private GridView gridView;
		private static final String[] MOBILE_OS = new String[] { 
			"Android", "iOS","Windows", "Blackberry" };
		
		// Creating a custom fragment, we need to implement onCreateView
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
			
			// Inflate the grid view and place it in the root ViewGroup
			View view = inflater.inflate(R.layout.image_grid, container, false);
			gridView = (GridView) view.findViewById(R.id.gridView);
			gridView.setAdapter(new ImageAdapter(getActivity(), MOBILE_OS));
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					view.setSelected(!view.isSelected());
					if (view.isSelected()) {
						view.setBackgroundColor(0xFF00FF00);
					} else {
						view.setBackgroundColor(0x00FF00FF);
					}
					Toast.makeText(
					   getActivity(),
					   ((TextView) view.findViewById(R.id.grid_item_label))
					   .getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
	        // If you have a layout file, just use inflate the layout for this fragment
	        //return inflater.inflate(R.layout.image_grid, container, false);
			return view;
	    }		
	}
	
	public static class GridTabListener implements ActionBar.TabListener {
		private final Activity mActivity;
		private final String mTag;
		private final Class<?> mClass;
		private Fragment mFragment;
		
		// Constructor
		public GridTabListener(Activity activity, String tag, Class<?> cls) {
			mActivity = activity;
			mTag = tag;
			mClass = cls;
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// Usually do nothing
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Replace the current fragment on the screen with our fragment
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

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.detach(mFragment);
		}
	}

}
