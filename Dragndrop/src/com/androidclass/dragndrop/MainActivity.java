package com.androidclass.dragndrop;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView option1, option2, choice1, choice2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//views to drag
		option1 = (TextView)findViewById(R.id.option_1);
		option2 = (TextView)findViewById(R.id.option_2);

		//views to drop onto
		choice1 = (TextView)findViewById(R.id.choice_1);
		choice2 = (TextView)findViewById(R.id.choice_2);
		
		//set touch listeners
		option1.setOnTouchListener(new ChoiceTouchListener());
		option2.setOnTouchListener(new ChoiceTouchListener());
		
		choice1.setOnDragListener(new ChoiceDragListener());
		choice2.setOnDragListener(new ChoiceDragListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class ChoiceTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// Handle touch down event
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    //Setup data being drag
				//ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);	
				
				//Start dragging the item touched
				view.startDrag(null, shadowBuilder, view, 0);
			    return true;
			}
			return false;
		}
	}	

	class ChoiceDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View target, DragEvent event) {
			switch (event.getAction()) {
			    case DragEvent.ACTION_DRAG_STARTED:
			        //no action necessary
			        break;
			    case DragEvent.ACTION_DRAG_ENTERED:
			    	target.setBackgroundColor(0xff00ff00);
			    	target.invalidate();
			        break;
			    case DragEvent.ACTION_DRAG_EXITED:
			        //no action necessary
			    	target.setBackgroundColor(0xffffff99);
			    	target.invalidate();
			        break;
			    case DragEvent.ACTION_DROP:
			        //handle the dragged view being dropped over a drop view
			    	// 1. get a reference to the View being dropped
			    	TextView dropped = (TextView) event.getLocalState();
			    	// 2. stop displaying the view where it was before it was dragged
			    	dropped.setVisibility(View.INVISIBLE);
			    	// 3. get a reference to the view being dropped on
			    	TextView dropTarget = (TextView) target;
			    	// 4. update the text in the target view to reflect the data being dropped
			    	dropTarget.setText(dropped.getText());			    	
			        break;
			    case DragEvent.ACTION_DRAG_ENDED:
			        //no action necessary
			        break;
			    default:
			        break;
			}			
			return true;
		}
	}
}
