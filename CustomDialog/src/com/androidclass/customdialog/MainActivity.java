package com.androidclass.customdialog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements CustomDialog.OnDataChanged {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialog dialog = new CustomDialog();
				dialog.show(getFragmentManager(), "tag");
				dialog.setOnDataChangedListener(MainActivity.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void OnDataChanged(String username, String password) {
		Toast.makeText(this, username + ":" + password, Toast.LENGTH_LONG).show();
	}

}
