package com.androidclass.imagepager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.androidclass.imagepager.R;

public class ViewActivity extends Activity {
	ArrayList<String> mNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_view);
		Intent intent = this.getIntent();
		mNames = intent.getStringArrayListExtra("names");
		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		pager.setAdapter(new ImageAdapter(this, mNames));
	}
}
