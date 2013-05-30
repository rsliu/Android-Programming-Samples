package com.androidclass.buttons;

import java.util.Date;

import com.androidclass.buttonsample.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup.*;
import android.widget.Toast;
import android.view.View.OnClickListener;

// Implements OnClickListener
public class ButtonsActivity extends Activity implements OnCheckedChangeListener, OnClickListener {
	
	// Various button controls
	Button button;
	ImageButton imageButton;
	CheckBox checkBox;
	ToggleButton toggleButton;
	RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buttons);
		
		// Set OnClickListener so we can get notified when the button is clicked
		button = (Button) this.findViewById(R.id.button);
		button.setOnClickListener(this);
		
		// Set OnClickListener of the ImageButton
		imageButton = (ImageButton) this.findViewById(R.id.imageButton);
		imageButton.setOnClickListener(this);
		
		// Set OnClickListener of the CheckBox
		checkBox = (CheckBox) this.findViewById(R.id.checkBox);
		checkBox.setOnClickListener(this);
		
		// Set OnClickListener of the ToggleButton
		toggleButton = (ToggleButton) this.findViewById(R.id.toggleButton);
		toggleButton.setOnClickListener(this);
		
		// Set OnCheckedChangeListener of the RadioGroup
		radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_buttons, menu);
		return true;
	}

	// Button click event handler
	@Override
	public void onClick(View v) {		
		// Must check which button is clicked before taking actions
		if (v.getId() == R.id.button) {
			Toast.makeText(this, "OK button clicked!", Toast.LENGTH_LONG).show();
			
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[] {"renshiou.liu@gmail.com"});
			email.putExtra(Intent.EXTRA_SUBJECT, "Test");
			email.putExtra(Intent.EXTRA_TEXT, "This is a test");
			email.setType("message/rfc822");
			startActivity(Intent.createChooser(email, "Choose an email client:"));
			
		} else if (v.getId() == R.id.imageButton){
			Toast.makeText(this, "Image button clicked!", Toast.LENGTH_LONG).show();
		} else if (v.getId() == R.id.checkBox) {
			TextView tv = (TextView) v;			
			tv.setText(checkBox.isChecked()? "Checked" : "Not checked");			
 		} else if (v.getId() == R.id.toggleButton) {
 			if (toggleButton.isChecked()) {
 				Toast.makeText(this, "Toggle Button Checked", Toast.LENGTH_LONG).show();
 			} else {
 				Toast.makeText(this, "Toggle Button Unchecked", Toast.LENGTH_LONG).show();
 			}
 		} 
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton radioButton = (RadioButton) this.findViewById(radioGroup.getCheckedRadioButtonId());
		Toast.makeText(this, radioButton.getText(), Toast.LENGTH_LONG).show();
	}
}
