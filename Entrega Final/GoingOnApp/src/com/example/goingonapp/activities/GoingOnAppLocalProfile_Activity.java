package com.example.goingonapp.activities;

import java.net.MalformedURLException;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.LoginUser;
import com.example.goingonapp.objects.RegisterUser;
import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class GoingOnAppLocalProfile_Activity extends TabActivity {

	/**
	 * Mobile User instance
	 */	
	private RegisterUser user;	
	
	/**
	 * Variables related to Mobile User
	 */
	private String userEmail;
	private String userType;
	private MobileServiceClient mClient;
	private String idUser;
	
	/**
	 * UI References
	 */
	private TabHost tabhost;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_local_profile);

		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		
		TabSpec tab1 = tabhost.newTabSpec("EventsTab");
        TabSpec tab2 = tabhost.newTabSpec("TicketsTab");
		
        tab1.setIndicator("Events");
        tab1.setContent(new Intent(this,GoingOnAppEventList_Activity.class));
        
        tab2.setIndicator("Tickets");
        tab2.setContent(new Intent(this,GoingOnAppTicketsList_Activity.class));
        
        tabhost.addTab(tab1);
        tabhost.addTab(tab2);
        
		image = (ImageView) findViewById(R.id.imageView_picture_local_user);
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_contact_picture);
		image.setImageBitmap(getRoundedShape(bm));
		
		userType = getIntent().getExtras().getString("userType");
		userEmail = getIntent().getExtras().getString("userEmail");
		idUser = getIntent().getExtras().getString("idUser");
		//eventsUser = (ContextEventsList) getIntent().getSerializableExtra("eventsInfo");
		
		user = new RegisterUser(userEmail);
		
		
        setTabColor(tabhost);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_local_profile_, menu);
		return true;
	}
	private void setTabColor(TabHost tabhost) {
	    for(int i=0;i < tabhost.getTabWidget().getChildCount();i++) {
	    	
	    	tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); //unselected
	        
	        TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	        tv.setTextColor(getResources().getColor(R.color.GO_dark_gray));
	    }
	    tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
	}
	
	private Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	    int targetWidth = 50;
	    int targetHeight = 50;
	    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
	                        targetHeight,Bitmap.Config.ARGB_8888);

	    Canvas canvas = new Canvas(targetBitmap);
	    Path path = new Path();
	    path.addCircle(((float) targetWidth - 1) / 2,
	        ((float) targetHeight - 1) / 2,
	        (Math.min(((float) targetWidth), 
	        ((float) targetHeight)) / 2),
	        Path.Direction.CCW);

	    canvas.clipPath(path);
	    Bitmap sourceBitmap = scaleBitmapImage;
	    canvas.drawBitmap(sourceBitmap, 
	        new Rect(0, 0, sourceBitmap.getWidth(),
	        sourceBitmap.getHeight()), 
	        new Rect(0, 0, targetWidth, targetHeight), null);
	    return targetBitmap;
	}
	
	public void gotomap(MenuItem menu) {
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("userType", userType);
    	startActivity(intent);
		this.finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
		intent.putExtra("userType", userType);
		intent.putExtra("userMail", userEmail);
    	startActivity(intent);
		this.finish();
	}
	
	private void updateUI() {
		this.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {

		    	 TextView t = (TextView)findViewById(R.id.textView_name_local_user);
		 		t.setText(user.getName());
		 		
		 		t = (TextView)findViewById(R.id.textView_description_local_user);
		 		t.setText(user.getDescription());
		 		
		 		//FIXME
		 		/*
		 		ImageView i = (ImageView)findViewById(R.id.imageView_picture_user);
		 		i.setImageBitmap(user.getImage());*/

		    }
		});		
	}
	
	public void getEventList(){
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
				
		LoginUser login = new LoginUser();
		login.Password = "";
		login.Username = "33";
		login.idClassUser = 0;
		
		mClient.invokeApi("getalluserevents",login, JsonElement.class, new ApiOperationCallback<JsonElement>() {
	        @Override
	        public void onCompleted(JsonElement getAllUserEventsResult, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	//pDialog.dismiss();
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show();
	            } else {
	            	//pDialog.dismiss();
	            	Log.d("GoingOn", "Se recibio: "+ getAllUserEventsResult.toString());
	            }
	        }
	    });
	
	}

}
