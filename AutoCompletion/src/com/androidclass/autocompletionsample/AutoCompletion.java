package com.androidclass.autocompletionsample;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.view.*;
import android.view.View.OnFocusChangeListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class AutoCompletion extends Activity implements OnFocusChangeListener {
	// Final variables cannot be changed once initialized
	final String[] COLORS = { "red", "blue", "green", "cyan", "yellow", "orange"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_completion);
		final Activity me = this;
				
		// Create Adaptor
		//ArrayAdapter<String> adapter = 
			//	new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COLORS);
		ColorAdapter adapter = new ColorAdapter(this, R.layout.color_item, COLORS);
		
		// Set adapter for AutoCompleteTextView
		AutoCompleteTextView text = 
				(AutoCompleteTextView) this.findViewById(R.id.autoCompleteTextView);		
		text.setAdapter(adapter);		
		
		// Set adapter and tokenizer for MultiAutoCompleteTextView
		MultiAutoCompleteTextView mtext = 
				(MultiAutoCompleteTextView) this.findViewById(R.id.multiAutoCompleteTextView);
		mtext.setAdapter(adapter);
		mtext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		
		final EditText text_filtered = (EditText) findViewById(R.id.textFiltered);
		text_filtered.setFilters(new InputFilter[] {
				new InputFilter.AllCaps(),
				new InputFilter.LengthFilter(2)
		});
		
		final EditText text_test = (EditText) findViewById(R.id.textInputType);
		text_test.setText("Test");
		text_test.setSelection(1,2);
		text_test.setTextColor(getResources().getColor(R.color.green));
		text_test.requestFocus();
		text_test.setOnFocusChangeListener(this);
		text_test.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {				
				Toast.makeText(me, text_test.getText(), Toast.LENGTH_LONG).show();
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_auto_completion, menu);
		
		return true;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Focus Changed", Toast.LENGTH_LONG).show();
	}
}
