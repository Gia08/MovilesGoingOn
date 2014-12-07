package com.example.goingonapp.activities;

import java.util.Timer;
import java.util.TimerTask;
import com.example.goingonapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

public class GoingOnAppInit_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_on_app_init);

		new Timer().schedule(new TimerTask(){
			public void run() { 
				Intent intent = new Intent(GoingOnAppInit_Activity.this, GoingOnAppLogin_Activity.class);
				startActivity(intent);
				finish();				
			}
		}, 1000); 
    } 

    @Override
    public void onDestroy() {
    	super.onDestroy();  
        android.os.Debug.stopMethodTracing();
    }
    
    
}
