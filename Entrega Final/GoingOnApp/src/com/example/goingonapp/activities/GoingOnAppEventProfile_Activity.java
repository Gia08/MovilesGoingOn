package com.example.goingonapp.activities;

import com.example.goingonapp.R;
import com.example.goingonapp.R.layout;
import com.example.goingonapp.R.menu;
import com.example.goingonapp.objects.MapObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GoingOnAppEventProfile_Activity extends Activity {

	/**
	 * Variables related to Event Profile Activity
	 */
	private MapObject eventObj;
	private String mEventName;
	private String mEventDescr;
	//private int mIdTypeEvent;
	private String mEventPrice;
	private double mLatitude;
	private double mLongitude;
	private String mInitialDate;
	private String mInitialTime;
	private String userType;
	private String userEmail;
	
	/**
	 * UI References
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_event_profile);
		
		eventObj = (MapObject) getIntent().getSerializableExtra("eventInfo");
		
		userType = getIntent().getExtras().getString("userType");
		userEmail = getIntent().getExtras().getString("userEmail");
		
		findViewById(R.id.profile_event_getDirection).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						gotoWaze();
					}
				});
		if (eventObj != null){
			Log.d("GoingOn", "Si existe eventObj: " + eventObj.getName());
		}
		getInfo();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_event_profile_, menu);
		return true;
	}
	
	
	private void updateUI() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				TextView t = (TextView)findViewById(R.id.profile_event_name);
				t.setText(mEventName);

				t = (TextView)findViewById(R.id.profile_event_description);
				t.setText(mEventDescr);

				t =(TextView)findViewById(R.id.profile_event_price);
				t.setText(mEventPrice);

				//t =(TextView)findViewById(R.id.profile_event_category);
				//t.setText(mCategory);

				t =(TextView)findViewById(R.id.profile_event_date);
				t.setText(mInitialDate);

				t =(TextView)findViewById(R.id.profile_event_time);
				t.setText(mInitialTime);

				/*
		 		ImageView i = (ImageView)findViewById(R.id.imageView_picture_user);
		 		i.setImageBitmap(user.getImage());
		 		*/
			}
		});
	}
	
	/*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
	public boolean getInfo() {
		
		mEventName = eventObj.getName().toString();
		mEventDescr = eventObj.getDescription().toString();
		//mCategory = eventObj.getCategory();
		mEventPrice = eventObj.getEventPrice().toString();
		mLatitude = eventObj.getLatitude();
		mLongitude = eventObj.getLongitude();
		mInitialDate = eventObj.getStartDate().toString();
		mInitialTime = eventObj.getEndDate().toString();
		updateUI();
		return true;
	}
	
	public void gotoWaze(){
		try
		{
			String url = "waze://?q=" + mLatitude + "," + mLongitude;
			Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
			startActivity( intent );
		}
		catch ( ActivityNotFoundException ex  )
		{
			Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
			startActivity(intent);
		}		
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
		intent.putExtra("userType", userType);
		intent.putExtra("userMail", userEmail);
    	startActivity(intent);
		finish();
	}
	
	public void gotomap(MenuItem menu) {
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
		intent.putExtra("userType", userType);
		intent.putExtra("userMail", userEmail);
    	startActivity(intent);
		finish();
	}

}
