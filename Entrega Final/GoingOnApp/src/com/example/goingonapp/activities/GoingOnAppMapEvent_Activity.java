package com.example.goingonapp.activities;



import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.Event;
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
import com.google.android.gms.maps.model.MarkerOptions;
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
import android.os.AsyncTask;
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

public class GoingOnAppMapEvent_Activity extends Activity {

	/**
	 * Variables related to Main Activity
	 */
	private String userEmail;
	private String userType;
	private MobileServiceClient mClient;
	private ContextEventsList eventsMap;
	private getEventsID asyncEvents = null;
	
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
		pDialog = new ProgressDialog(GoingOnAppMapEvent_Activity.this);
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

		eventsMap = new ContextEventsList();
		
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

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GoingOnAppMapEvent_Activity.this);

				// set title
				alertDialogBuilder.setTitle("Event's information");

				// set dialog message
				alertDialogBuilder
				.setMessage(marker.getTitle()+ "\n" + marker.getSnippet()+ "\n\n" + "Go check event's info?")
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
		if (userType == "2"){
			updateEventsInfoFacebook();
		}	
		updateEventsInfoSystem();
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
		
	public void updateEventsInfoFacebook(){
		asyncEvents = new getEventsID();
		asyncEvents.execute();
	}
	
	public void updateEventsInfoSystem() {
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
	
	/**
	 * Function in charge of get all events from Facebook User
	 */
	public class getEventsID extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Session session = Session.getActiveSession();
			String URL = "https://graph.facebook.com/v2.2/"+userEmail+
					"/events?access_token="+session.getAccessToken()+"&fields=id,name,location,description,venue";
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet getURL = new HttpGet(URL);
				HttpResponse rp = hc.execute(getURL);
				if (rp.getStatusLine() != null) {
					String queryAlbums = EntityUtils.toString(rp.getEntity());
					JSONObject JOTemp = new JSONObject(queryAlbums);
					JSONArray JAEvents = JOTemp.getJSONArray("data"); 
					String description = "";
					String name = "";
					double latitude = 0.00;
					double longitude = 0.00;
					String id = "";
					for ( int i = 0; i < ( JAEvents.length() ); i++ )
					{
						JSONObject json_obj = JAEvents.getJSONObject(i);
						if(json_obj.has("venue")){
							JSONObject venue = json_obj.getJSONObject("venue");
							if(venue.has("latitude") && venue.has("longitude")){
								latitude = Double.valueOf(venue.getString("latitude"));
								longitude = Double.valueOf(venue.getString("longitude"));
								if(json_obj.has("description")){
									description = json_obj.getString("description");}
								if(json_obj.has("name")){
									name = json_obj.getString("name");}
								if(json_obj.has("id")){
									id = json_obj.getString("id");}
								Event tempEvent = new Event(name, description, id, latitude, longitude, 13);//idTypeEvent = 13, Facebook Event
								eventsMap.insertEvent(tempEvent);															
							}
							else {
								
							}
						}	
					}			
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("GoingOn", "Error: "+ e);
			}

			return null;
		}
		

		@Override
		protected void onPostExecute(Void result) {
			asyncEvents = null;
		}

		@Override
		protected void onCancelled() {
			asyncEvents = null;
		}
	}
	
	public void processEventList(){
		
		mClient.invokeApi("geteventinfomarket",null, "GET", null, new ApiJsonOperationCallback() {
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
						int idTypeEvent = Integer.valueOf(item.get("idTypeEvent").toString());
						String id = item.get("id").toString();
						double latitude = Double.valueOf(item.get("latitude").toString());
						double longitude = Double.valueOf(item.get("longitude").toString());
						Event tempEvent = new Event(name, descr, id, latitude, longitude, idTypeEvent);
						eventsMap.insertEvent(tempEvent);
						updateUI();
    	        	} 	        		
        		}
	        	         
	        }
	    });	
		
	}
	
	private void updateUI() {
		for (int i = 0; i<eventsMap.getListEvents().size();i++){
			Event event = eventsMap.getListEvents().get(i);
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
