package com.example.goingonapp.activities;



import java.util.ArrayList;
import java.util.List;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.ContextEventsList;
import com.facebook.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

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

public class GoingOnAppMap_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	private String userType;
	private int logstatus;
	private int typeUser;
	private String fbUserId = null;
	
	/**
	 * UI References
	 */
	private GoogleMap map;
	private ContextEventsList eventsMap;
	private ArrayList<String> eventsID;
	private ProgressDialog pDialog;
	
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

	}
	
	///		-------------------------Zooming camera to position user-----------------
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
		/////----------------------------------Zooming camera to position user-----------------
		

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
		/**Intent intent = new Intent(this, GoingOnAppLogin_Activity.class);
		Log.d("GoingOn", "El userType es: " + userType);
		if (userType == "2"){//Login with Facebook
			Log.d("GoingOn", "Enviando SessionFb");
			intent.putExtra("sessionFb", "close");			
		}
		else{
			intent.putExtra("sessionFb", "open");
		}
		startActivity(intent);
		finish();**/
	}

}
