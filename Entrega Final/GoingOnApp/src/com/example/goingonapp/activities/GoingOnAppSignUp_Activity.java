package com.example.goingonapp.activities;



import java.net.MalformedURLException;
import java.util.List;

import com.example.goingonapp.R;
import com.example.goingonapp.objects.GPSTracker;
import com.example.goingonapp.objects.RegisterUser;
import com.example.goingonapp.objects.RegisterUserResult;
import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class GoingOnAppSignUp_Activity extends Activity {

	/**
	 * Variables related to Sign Up
	 */
	private int signUpStatus;
	private MobileServiceClient mClient;
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	private String mUserName;
	private String mEmail;
	private String mPassword;
	private String mPasswordConf;
	private String mUserDescr;
	private double mLatitude;
	private double mLongitude;
	private int mUserType;
	
	/**
	 * UI References
	 */	
	private EditText mUserNameView;
	private EditText mUserDescriptionView;
	private EditText mUserMailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmView;	
	private ProgressDialog pDialog;
	private View focusView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_app_sign_up);
		
		/**
		 * Set up the Login Form
		 */
		mLatitude = 0.0;
		mLongitude = 0.0;
		mUserType = 2;
		
		mUserNameView = (EditText) findViewById(R.id.userName);
		mUserDescriptionView = (EditText) findViewById(R.id.userDescription);
		
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mUserMailView = (EditText) findViewById(R.id.email);
		mUserMailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordConfirmView = (EditText) findViewById(R.id.passwordConf);


		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegister();
					}
				});
		
		findViewById(R.id.button_cancel_sign_up).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_sign_up_, menu);
		return true;
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 * @throws MalformedURLException 
	 */
	public void attemptRegister() {			
		try {
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this);
			processTableData();
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL" , Toast.LENGTH_LONG).show(); 
		}
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void processTableData() {
		
		// Reset errors.
		mUserMailView.setError(null);
		mPasswordView.setError(null);
		mPasswordConfirmView.setError(null);
		mUserDescriptionView.setError(null);
		mUserNameView.setError(null);
		
		// Store values at the time of the login attempt.
		mEmail = mUserMailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPasswordConf = mPasswordConfirmView.getText().toString();
		mUserDescr = mUserDescriptionView.getText().toString();
		mUserName = mUserNameView.getText().toString();
		boolean cancel = false;
		focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		} else if (TextUtils.isEmpty(mPasswordConf)) {
			mPasswordConfirmView.setError(getString(R.string.error_field_required));
			focusView = mPasswordConfirmView;
			cancel = true;
		} else if (!mPasswordConf.equals(mPassword)) {
			mPasswordConfirmView.setError(getString(R.string.error_confirmed_password));
			focusView = mPasswordConfirmView;
			cancel = true;
		} 

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mUserMailView.setError(getString(R.string.error_field_required));
			focusView = mUserMailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mUserMailView.setError(getString(R.string.error_invalid_email));
			focusView = mUserMailView;
			cancel = true;
		}
		// Check for a valid user name.
		if (TextUtils.isEmpty(mUserName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		}
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			pDialog = new ProgressDialog(GoingOnAppSignUp_Activity.this);
	        pDialog.setMessage("Registering...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	        RegisterUser();
		}
	}

private int RegisterUser() {
		
		
		RegisterUser registerUser = new RegisterUser();
		registerUser.password = mPassword;
		registerUser.name = mUserName;
		registerUser.userName = mEmail;
		registerUser.idClassUser = 1;
		registerUser.idTypeUser = mUserType;
		registerUser.idTypeUserLocal = mUserType;
		registerUser.description = mUserDescr;
		registerUser.latitude = mLatitude;
		registerUser.longitude = mLongitude;
		
		// Insert the new item
		mClient.invokeApi("adduser",registerUser, RegisterUserResult.class, new ApiOperationCallback<RegisterUserResult>() {
	        @Override
	        public void onCompleted(RegisterUserResult registerUserResult, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	Toast.makeText(getApplicationContext(), "Fatal Error: " + error , Toast.LENGTH_LONG).show(); 
	            	
	            } else {
	            	if (registerUserResult.result == 0){
	            		pDialog.dismiss();
	            		mUserMailView.setError(getString(R.string.error_user_already_exists));
	        			focusView = mUserMailView;
	            		Toast.makeText(getApplicationContext(), "Error: Email already exists" , Toast.LENGTH_LONG).show();  
	            	}	
	            	else{
	            		pDialog.dismiss();
	            		finishedAuth();
	            	}
	            }
	        }
	    });
		return signUpStatus;
	}
	
	public void finishedAuth() {
		Intent intent = new Intent(this, GoingOnAppMap_Activity.class);
		System.out.println("userMail es " + mEmail);
		intent.putExtra("userEmail", mEmail);
		intent.putExtra("fbEventsInfo", "null");
		
		// Verify it resolves
		  PackageManager packageManager = getPackageManager();
		  List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		  boolean isIntentSafe = activities.size() > 0;

		  // Start an activity if it's safe
		  if (isIntentSafe) {
		      startActivity(intent);}
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radioButton_mobile_user:
	            if (checked) {
	            	mLatitude = 0.0;
                    mLongitude = 0.0;
                    mUserType = 2;
	            break;}
	        case R.id.radioButton_local_user:
	            if (checked){
	            	mUserType = 1;
	            	GPSTracker gps = new GPSTracker(this);
	            	if(gps.canGetLocation()){ 
	            		mLatitude = gps.getLatitude();
	                    mLongitude = gps.getLongitude();
	                    Toast.makeText(getApplicationContext(), "Your Location is \nLat: " + mLatitude + "\nLong: " + mLongitude, Toast.LENGTH_LONG).show();    
	                }else{
	                    gps.showSettingsAlert();
	                } 	
	            break;}
	    }
	}
			
}


