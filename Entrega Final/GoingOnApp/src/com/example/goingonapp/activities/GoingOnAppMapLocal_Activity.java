package com.example.goingonapp.activities;

import java.net.MalformedURLException;
import java.util.List;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.Event;
import com.facebook.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
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

public class GoingOnAppMapLocal_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private MobileServiceClient mClient;
	private ContextEventsList localMap;
	private String userEmail;
	private String userType;
	
	/**
	 * UI References
	 */
	private GoogleMap map;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_map_local);
		
		/**
		 * SetUp Map Activity
		 */
		pDialog = new ProgressDialog(GoingOnAppMapLocal_Activity.this);
        pDialog.setMessage("Loading Businesses....");
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
			userEmail = getIntent().getExtras().getString("userEmail");
		}
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapLocal))
				.getMap();
		map.setMyLocationEnabled(true);

		localMap = new ContextEventsList();
		
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

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GoingOnAppMapLocal_Activity.this);

				// set title
				alertDialogBuilder.setTitle("Local's information");

				// set dialog message
				alertDialogBuilder
				.setMessage(marker.getTitle()+ "\n" + marker.getSnippet()+ "\n\n" + "Go check local's info?")
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
		
		updateLocalInfoSystem();
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

	public void updateLocalInfoSystem() {
		try {
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this);
			processLocalList();
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL" , Toast.LENGTH_LONG).show(); 
		}
	}
	
	public void processLocalList(){
		
		mClient.invokeApi("getlocalinfomarket",null, "GET", null, new ApiJsonOperationCallback() {
	        @Override
	        public void onCompleted(JsonElement jsonElement, Exception error, ServiceFilterResponse serviceFilterResponse) {
	        	JsonArray json_event_list = jsonElement.getAsJsonArray();	        	
        		if (error != null){
        			pDialog.dismiss();
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show();
        		}
        		else{
        			pDialog.dismiss();	   
        			for(int i = 0; i < json_event_list.size(); i++)
    	        	{
	        			JsonObject item = json_event_list.get(i).getAsJsonObject();
		        		
		        		String name = item.get("name").toString();
						String descr = item.get("description").toString();
						int idTypeEvent = Integer.valueOf(item.get("idTypeUserLocal").toString());
						String id = item.get("id").toString();
						double latitude = Double.valueOf(item.get("latitude").toString());
						double longitude = Double.valueOf(item.get("longitude").toString());
						Event tempLocal = new Event(name, descr, id, latitude, longitude, idTypeEvent);
						localMap.insertEvent(tempLocal);
						updateUI();
    	        	} 	        		
        		}
	        	         
	        }
	    });	
		
	}
	
	private void updateUI() {
		for (int i = 0; i<localMap.getListEvents().size();i++){
			Event event = localMap.getListEvents().get(i);
			map.addMarker(new MarkerOptions()
			.position(new LatLng(event.getLatitude(), event.getLongitude()))
			.title(event.getName())
			.snippet(event.getDescription()));
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
	
	
}
