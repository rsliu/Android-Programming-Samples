package com.androidclass.alertdialog;

import com.androidclass.alertdialog.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlertDialogActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
	
		// Setup button onClickListener
		Button button = (Button) this.findViewById(R.id.button);
		// Instead implement the interface at the activity level,
		// here we define an anonymous class and instantiate the class
		// to handle the button click event
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyDialogFragment frag = MyDialogFragment.newInstance(R.string.dialog_title);
				frag.show(getFragmentManager(), "TEST");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dialog, menu);
		return true;
	}
	
	public static class MyDialogFragment extends DialogFragment {
		// The factory pattern
		public static MyDialogFragment newInstance(int title) {
			MyDialogFragment frag = new MyDialogFragment();
			
			// Pass dialog ID through Bundle
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			// Get parameter through the Bundle
			int title = getArguments().getInt("title");

			// Instantiate an AlertDialog.Builder with its constructor
			Builder builder = new AlertDialog.Builder(getActivity());
			
			// Setup title and message. You can choose to use either a string resource or 
			// a character string
			builder.setTitle(title); // String resource (passed in)
			builder.setMessage("This is an AlertDialog"); // Character string
			builder.setCancelable(true);
			
			// Setup buttons. Again, here we define new classes dedicated for
			// handling button click event for the dialog. If you want the activity
			// to receive Dialog events as well, then implement DialogFragment.NoticeDialogListener
			// interface in your activity, which should be straight forward.
			DialogOnClickListener onClickListener = new DialogOnClickListener();
			builder.setPositiveButton(R.string.agree, onClickListener);
			builder.setNegativeButton(R.string.cancel, onClickListener);

			// Finally, create the dialog
			return builder.create();
		}
		
		// Click listener class. Note, here we specifically call out DialogInterface.OnClickListener
		// to avoid confusion with the Button.OnClickListener interface. Also note the private
		// and final keywords.
		private final class DialogOnClickListener implements DialogInterface.OnClickListener {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Toast.makeText(getActivity(), "OK clicked", Toast.LENGTH_LONG).show();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					Toast.makeText(getActivity(), "OK clicked", Toast.LENGTH_LONG).show();
					break;
				}
			}
		}	
	}
}
