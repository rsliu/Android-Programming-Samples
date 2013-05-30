package com.androidclass.gridfragment;

import com.androidclass.gridviewex.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    ActionBar.Tab gridTab = bar.newTab().setText("Grid");
	    gridTab.setTabListener(new ActionBar.TabListener() {
	    	Fragment mFragment;
			
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				ft.detach(mFragment);
			}
			
			@Override
			public void onTabSelected(Tab arg0, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (mFragment == null) {
					mFragment = Fragment.instantiate(
							MainActivity.this, 
							GridFragment.class.getName());
					// fill parent's content area
					ft.add(android.R.id.content, mFragment); 
				} else {
					ft.attach(mFragment);
				}
			}
			
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    bar.addTab(gridTab);
	    
	    ActionBar.Tab listTab = bar.newTab().setText("List");
	    listTab.setTabListener(new ActionBar.TabListener() {
	    	Fragment mFragment;
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				ft.detach(mFragment);
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				if (mFragment == null) {
					mFragment = Fragment.instantiate(
							MainActivity.this, 
							com.androidclass.listfragment.ListFragment.class.getName());
					// fill parent's content area
					ft.add(android.R.id.content, mFragment); 
				} else {
					ft.attach(mFragment);
				}				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    bar.addTab(listTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
