package com.example.goingonapp.activities;



import com.example.goingonapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class GoingOnAppMap_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_map);
		
		userEmail = getIntent().getExtras().getString("userEmail");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_map_, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_refresh: {
				updateEventList();
				break;
			}
			
			case R.id.action_add: {
				createEvent();
				break;
			}
	
			case R.id.action_logout: {
				LogOut();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void updateEventList(){
		
	}
	
	public void createEvent(){
		Intent intent = new Intent(this, GoingOnAppAddEvent_Activity.class);
		intent.putExtra("userEmail", userEmail);
		startActivity(intent);
	}
	
	public void LogOut(){
		
	}

}
