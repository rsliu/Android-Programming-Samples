package com.androidclass.contextmenu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class ContextMenuActivity extends Activity {

	private TextView text;
	private Button btn;
	private ActionMode mActionMode;
	private ActionMode.Callback mActionModeCallBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_menu);
	
		// Setup context menu for the hello world text view
		text = (TextView) this.findViewById(R.id.hello_world);
		this.registerForContextMenu(text);
		
		// Action mode
		mActionModeCallBack = new ActionMode.Callback() {
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {				
				getMenuInflater().inflate(R.menu.activity_colors_menu, menu);
				return true;
			}
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()) {
				case R.id.blue_id:
					text.setBackgroundColor(android.graphics.Color.BLUE);
					break;
				default:
					return false;
				}
				mode.finish();
				return true;
			}
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				mActionMode = null;
			}									
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}			
		};
		
		/*text.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				startActionMode(mActionModeCallBack);
				return true;
			}
		
		});*/
		
		btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActionMode(mActionModeCallBack);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.		
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		if (v.getId() == R.id.hello_world) {
			getMenuInflater().inflate(R.menu.activity_colors_menu, menu);
			menu.setHeaderIcon(R.drawable.ic_launcher);
			menu.setHeaderTitle("Colors");
		}
	}

	// Handle menu item click event
	@Override
	public boolean onContextItemSelected(MenuItem item) {				
		switch(item.getItemId()) {
		case R.id.blue_id:
			text.setBackgroundColor(android.graphics.Color.BLUE);
			break;
		case R.id.green_id:
			text.setBackgroundColor(android.graphics.Color.GREEN);
			break;
		default:
			return onContextItemSelected(item);
		}		
		return true;
	}
}
