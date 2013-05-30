package com.androidclass.gridview;

import com.androidclass.imagegridview.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.Toast;

public class ImageGridActivity extends Activity {
	
	GridView gridView;
	static final String[] MOBILE_OS = new String[] { 
		"Android", "Apple iOS","Windows", "Blackberry" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_grid);
		
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(this, MOBILE_OS));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(
				   getApplicationContext(),
				   ((TextView) view.findViewById(R.id.grid_item_label)).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_image_grid, menu);
		return true;
	}

}
