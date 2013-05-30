package com.androidclass.customdialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class CustomDialog extends DialogFragment implements DialogInterface.OnClickListener {
	public interface OnDataChanged {
		public void OnDataChanged(String username, String password);
	}
	
	OnDataChanged myListener;
	EditText txtUsername;
	EditText txtPassword;
	
	public void setOnDataChangedListener (OnDataChanged listener) {
		myListener = listener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.custom_dialog, null);
		
		txtUsername = (EditText) view.findViewById(R.id.editText1);
		txtPassword = (EditText) view.findViewById(R.id.editText2);
		
		Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Custom Dialog");
		builder.setView(view);
		builder.setPositiveButton("Ok", this);
		builder.setNegativeButton("Cancel", this);
		
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which) {
		case DialogInterface.BUTTON_POSITIVE:
			String username, password;
			username = txtUsername.getText().toString();
			password = txtPassword.getText().toString();
			myListener.OnDataChanged(username, password);
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			break;
		}
	}
}
