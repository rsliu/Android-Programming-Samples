package com.androidclass.autolink;

import com.androidclass.autolinksample.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AutoLinkSample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_link_sample);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_auto_link_sample, menu);
		return true;
	}

}
