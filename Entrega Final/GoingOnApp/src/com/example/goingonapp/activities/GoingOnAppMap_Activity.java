package com.example.goingonapp.activities;



import com.example.goingonapp.R;
import com.facebook.Session;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GoingOnAppMap_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	private String userType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_map);
		
		if (userType == null){
			userType = "2";
		}
		else{
			userType = getIntent().getExtras().getString("userType");
		}
		Log.d("GoingOn", "El userType(onCreate) es: " + userType);
		if (userType == "1"){//Login with System
			userEmail = getIntent().getExtras().getString("userEmail");
		}
		else if (userType == "2"){//Login with Facebook
			userEmail = getIntent().getExtras().getString("fbUserId");
		}
		else{
			Log.d("GoingOn", "No funciono");
		}
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
		Intent intent = new Intent(this, GoingOnAppMobileUser_Activity.class);
		intent.putExtra("userEmail", userEmail);
		startActivity(intent);
	}
	
	public void createEvent(){
		Intent intent = new Intent(this, GoingOnAppAddEvent_Activity.class);
		intent.putExtra("userEmail", userEmail);
		startActivity(intent);
	}
	
	public void LogOut(){
		Intent intent = new Intent(this, GoingOnAppLogin_Activity.class);
		Log.d("GoingOn", "El userType es: " + userType);
		if (userType == "2"){//Login with Facebook
			Log.d("GoingOn", "Enviando SessionFb");
			intent.putExtra("sessionFb", "close");			
		}
		else{
			intent.putExtra("sessionFb", "open");
		}
		startActivity(intent);
		finish();
	}

}
