package com.androidclass.datepickerdialog;

import java.util.Calendar;
import com.androidclass.datepickerdialog.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker_dialog);
		
		Button button = (Button) this.findViewById(R.id.button);
		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerFragment frag = new DatePickerFragment();
				frag.show(getFragmentManager(), "TEST");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_date_picker_dialog, menu);
		return true;
	}

	// Only nested classes can be static. By doing so you can use the 
	// nested class without having an instance of the outer class
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		// The factory pattern		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			// Create the DatePickerDialog and initialize the date to be displayed
			DatePickerDialog datePicker = new DatePickerDialog(
					getActivity(),  // context
					this, // DateSet event listener. You can also choose to implement the listener at activity level
					Calendar.YEAR,
					Calendar.MONTH, 
					Calendar.DAY_OF_MONTH);

			return datePicker;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// Do stuff here when date is set
			Toast.makeText(getActivity(), "Date set!", Toast.LENGTH_LONG).show();
		}	
	}
}
