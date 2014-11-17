package com.example.goingonapp.activities;

import java.net.MalformedURLException;
import java.util.List;

import com.example.goingonapp.R;
import com.example.goingonapp.R.layout;
import com.example.goingonapp.R.menu;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.LoginUser;
import com.example.goingonapp.objects.MapObject;
import com.facebook.Session;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class GoingOnAppMain_Activity extends TabActivity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	private String userType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_main);
		
		/**
		 * Set up Main Activity
		 */
		if (userType == null){
			userType = "2";
		}
		else{
			userType = getIntent().getExtras().getString("userType");
		}
		if (userType == "1"){//Login with System
			userEmail = getIntent().getExtras().getString("userEmail");
		}
		else{//Login with Facebook
			userEmail = getIntent().getExtras().getString("fbUserId");
		}
		
		
		// create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabSpec tabEvents = tabHost.newTabSpec("eventTab");
        TabSpec tabLocals = tabHost.newTabSpec("localTab");
        TabSpec tabSearch = tabHost.newTabSpec("searchTab");

       // Set the Tab name and Activity
       // that will be opened when particular Tab will be selected
        tabEvents.setIndicator("Events");
        Intent mapEvent = new Intent(this,GoingOnAppMapEvent_Activity.class);
        mapEvent.putExtra("userEmail", userEmail);
        mapEvent.putExtra("userType", userType);
        tabEvents.setContent(mapEvent);
        
        tabLocals.setIndicator("Businesses");
        Intent mapLocal = new Intent(this,GoingOnAppMapLocal_Activity.class);
        mapLocal.putExtra("userEmail", userEmail);
        mapLocal.putExtra("userType", userType);
        tabLocals.setContent(mapLocal);

        tabSearch.setIndicator("Search");
        tabSearch.setContent(new Intent(this,GoingOnAppSearch_Activity.class));
        
        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tabEvents);
        tabHost.addTab(tabLocals);
        tabHost.addTab(tabSearch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_map_, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_user_profile: {
				userProfile();
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
	/**
	 * Menu Functions
	 */
	public void userProfile(){
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
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}

		Session.setActiveSession(null);

		Intent intent = new Intent(this, GoingOnAppLogin_Activity.class);

		// Verify it resolves
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;

		// Start an activity if it's safe
		if (isIntentSafe) {
			startActivity(intent);}
	}
	

}
