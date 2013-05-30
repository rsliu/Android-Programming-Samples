package com.androidclass.customadapter;

import android.os.Bundle;
import com.androidclass.listfragment.*;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab gridTab = bar.newTab().setText("Grid");
		gridTab.setTabListener(new TabListener() {
			Fragment mFragment;

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (mFragment == null) {
					mFragment = Fragment.instantiate(MainActivity.this, GridFragment.class.getName());
					ft.add(android.R.id.content, mFragment);
				}
				ft.attach(mFragment);				
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				ft.detach(mFragment);
			}

		});
		
		bar.addTab(gridTab);
		
		ActionBar.Tab listTab = bar.newTab().setText("List");
		listTab.setTabListener(new TabListener() {
			Fragment mFragment;

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (mFragment == null) {
					mFragment = Fragment.instantiate(MainActivity.this, ListFragment.class.getName());
					ft.add(android.R.id.content, mFragment);
				}
				ft.attach(mFragment);				
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				ft.detach(mFragment);
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
