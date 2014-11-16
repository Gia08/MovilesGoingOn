package com.example.goingonapp.activities;



import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.LoginUserResult;
import com.example.goingonapp.objects.MapEventListResult;
import com.facebook.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GoingOnAppMap_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	private String userType;
	private MobileServiceClient mClient;
	
	/**
	 * UI References
	 */
	private GoogleMap map;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_map);
		
		/**
		 * SetUp Map Activity
		 */
		pDialog = new ProgressDialog(GoingOnAppMap_Activity.this);
        pDialog.setMessage("Loading Events....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
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
				
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();

		Location myLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		if (myLocation != null)
		{
			centerMapOnMyLocation(myLocation); 
		}
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(final Marker marker) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GoingOnAppMap_Activity.this);

				// set title
				alertDialogBuilder.setTitle("Event's information");

				// set dialog message
				alertDialogBuilder
				.setMessage("Go check event's info?")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						//goToEventProfile(marker.getTitle());
					}

				})
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}

		});

		updateEventsInfo();
	}
	

	/**
	 * Map function: Zooming camera to user position
	 * @param location
	 */
	private void centerMapOnMyLocation(Location location) {

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(location.getLatitude(), location.getLongitude()), 13));

		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
		.zoom(17)                   // Sets the zoom
		.bearing(90)                // Sets the orientation of the camera to east
		.tilt(40)                   // Sets the tilt of the camera to 30 degrees
		.build();                   // Creates a CameraPosition from the builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}
			
	public void updateEventsInfo() {
		try {
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this);
			processEventList();
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL" , Toast.LENGTH_LONG).show(); 
		}
	}
	
	public void processEventList(){
		
		mClient.invokeApi("geteventinfomarket",null, "GET", null, new ApiJsonOperationCallback() {
	        @Override
	        public void onCompleted(JsonElement jsonElement, Exception e, ServiceFilterResponse serviceFilterResponse) {
	        	JsonArray json_event_list = jsonElement.getAsJsonArray();
	        	for(int i = 0; i < json_event_list.size(); i++)
	        	{
	        		Log.d("GoingOn", "Item: "+json_event_list.get(i)+"\n");
	        		JsonObject item = json_event_list.get(i).getAsJsonObject();
	        		Log.d("GoingOn", item.get("name").toString());
	        	    // do some stuff....
	        	}
	        	//Log.d("GoingOn", "JsonElement: "+ jsonElement.getAsJsonObject());
	        	pDialog.dismiss();	            
	        }
	    });	
		
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
