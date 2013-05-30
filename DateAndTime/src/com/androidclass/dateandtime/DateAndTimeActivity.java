package com.androidclass.dateandtime;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class DateAndTimeActivity extends Activity implements OnDateChangedListener, OnTimeChangedListener {

	Calendar calendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_and_time);
		
		// Get an instance of the system calendar
		calendar = Calendar.getInstance();
		
		// Init the DatePicker using today's date and setup the onDateChanged listener
		DatePicker datePicker = (DatePicker) this.findViewById(R.id.datePicker);
		datePicker.init(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH, this);
		
		// Init the TimePicker
		TimePicker timePicker = (TimePicker) this.findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_date_and_time, menu);
		return true;
	}

	// Called when date is changed
	// Year, monthOfYear and dayOfMonth are passed as parameters
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// 1900 is subtracted from year to make it compatible with java.util.Date class
		Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
		Toast.makeText(this, date.toString(), Toast.LENGTH_LONG).show();
	}

	// Called when time is changed
	// Hour of day and minute are passed as parameters
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		Date date = new Date(calendar.YEAR, calendar.MONTH,
				calendar.DAY_OF_MONTH, hourOfDay, minute);
		Toast.makeText(this, date.toString(), Toast.LENGTH_LONG).show();
	}

}
