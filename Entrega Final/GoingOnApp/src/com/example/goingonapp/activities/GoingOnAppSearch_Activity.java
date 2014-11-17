package com.example.goingonapp.activities;

import com.example.goingonapp.R;
import com.example.goingonapp.R.layout;
import com.example.goingonapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GoingOnAppSearch_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_search_, menu);
		return true;
	}

}
