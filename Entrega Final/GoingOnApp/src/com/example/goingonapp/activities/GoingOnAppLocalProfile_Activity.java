package com.example.goingonapp.activities;

import com.example.goingonapp.R;
import com.example.goingonapp.R.layout;
import com.example.goingonapp.R.menu;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.RegisterUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
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

}
