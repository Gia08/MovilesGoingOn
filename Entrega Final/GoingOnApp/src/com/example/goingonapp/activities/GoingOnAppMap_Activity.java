package com.example.goingonapp.activities;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GoingOnAppMap_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_map_, menu);
		return true;
	}

}
