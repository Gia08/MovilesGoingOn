package com.example.goingonapp.activities;

import java.util.ArrayList;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.ContextEventsList;
import com.example.goingonapp.objects.CreateEvent;
import com.example.goingonapp.objects.EventsAdapter;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class GoingOnAppMobileUser_Activity extends TabActivity {
	
	/**
	 * Mobile User instance
	 */	
	private RegisterUser user;	
	
	/**
	 * Variables related to Mobile User
	 */
	private String userMail;
	
	/**
	 * UI References
	 */
	private TabHost tabhost;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_mobile_user);
		
		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		
		TabSpec tab1 = tabhost.newTabSpec("EventsTab");
        TabSpec tab2 = tabhost.newTabSpec("TicketsTab");
		
        tab1.setIndicator("Events");
        Intent tabEventIntent = new Intent(this,GoingOnAppEventList_Activity.class);
        tab1.setContent(tabEventIntent);
        
        tab2.setIndicator("Tickets");
        tab2.setContent(new Intent(this,GoingOnAppTicketsList_Activity.class));
        
        tabhost.addTab(tab1);
        tabhost.addTab(tab2);
        
		image = (ImageView) findViewById(R.id.imageView_picture_user);
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_contact_picture);
		image.setImageBitmap(getRoundedShape(bm));
		
		userMail = getIntent().getExtras().getString("userEmail");
		
		//eventsUser = (ContextEventsList) getIntent().getSerializableExtra("eventsInfo");
		
		user = new RegisterUser(userMail);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_mobile_user_, menu);
		return true;
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
		finish();
	}
	
	private void updateUI() {
		this.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {

		    	 TextView t = (TextView)findViewById(R.id.textView_name_user);
		 		t.setText(user.getName());
		 		
		 		t = (TextView)findViewById(R.id.textView_description_user);
		 		t.setText(user.getDescription());
		 		
		 		//FIXME
		 		/*
		 		ImageView i = (ImageView)findViewById(R.id.imageView_picture_user);
		 		i.setImageBitmap(user.getImage());*/

		    }
		});
		
	}

}
