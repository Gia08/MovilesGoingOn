package com.example.pruebagmaps;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pruebagmaps.Objects.Users;
import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class SignInActivity extends Activity  {
	
	private int signinstatus;
	private int signinresult;
	private MobileServiceClient mClient;
	private ProgressBar mProgressBar;
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUserName;
	private String mEmail;
	private String mPassword;
	private String mPasswordConf;
	private String mUserDescr;
	private double mLatitude;
	private double mLongitude;
	private int mUserType;
	
	

	// UI references.
	private EditText mUserNameView;
	private EditText mUserDescriptionView;
	private EditText mUserMailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmView;
	private TextView mLoginStatusMessageView;
	
	Button bsigin;
	
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_signup);
		
		// Set up the login form.
		
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
						attemptSigin();
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
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptSigin() {
		if (mAuthTask != null) {
			return;
		}
		
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
		View focusView = null;

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
			mPasswordConfirmView.setError(getString(R.string.error_invalid_password));
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
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mAuthTask = new UserLoginTask();
			mAuthTask.execute(mUserName, mEmail, mPassword, mUserDescr);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<String, String, Boolean> {
		
		String user,mail,pass,desc;
		
		@Override
		protected void onPreExecute() {
        	//para el progress dialog
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Guardando Usuario....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
		@Override
		protected Boolean doInBackground(String... params) {
			
			//obtnemos usr y pass
			user=params[0];
			mail=params[1];
			pass=params[2];
			desc=params[3];
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		if (siginstatus(user,mail,pass,desc)!=-1){    		    		
    			return true; //login valido
    		}else{    		
    			return false; //login invalido     	          	  
    		}
		}
	

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;

			if (success) {
				finishedAuth();
				
			} else {
				pDialog.dismiss();
				if (signinresult==0){
					mPasswordView
					.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				}
				else if (signinresult==2){
					Toast.makeText(getApplicationContext(), R.string.error_user_already_exists, Toast.LENGTH_LONG).show();
				}
			}
			
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
		
	}
	
	//Valida el estado del logueo solamente necesita como parametros el usuario y passw
    public int siginstatus(String userName, String mail ,String password, String description ) {
    	signinstatus=-1;
    	try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this).withFilter(new ProgressFilter());
			// Get the Mobile Service Table instance to use
			//mUsersTable = mClient.getTable(Users.class);
			// Load the items from the Mobile Service
			signinstatus = addUser();

		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL " , Toast.LENGTH_LONG).show();  
		}
    	return signinstatus;
    	
    }
	
	public void finishedAuth() {
		Intent intent = new Intent(this, MapActivity.class);
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
	            	if(gps.canGetLocation()){ // gps enabled} // return boolean true/false
	            		mLatitude = gps.getLatitude();
	                    mLongitude = gps.getLongitude();
	                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + mLatitude + "\nLong: " + mLongitude, Toast.LENGTH_LONG).show();    
	                }else{
	                    // can't get location
	                    // GPS or Network is not enabled
	                    // Ask user to enable GPS/network in settings
	                    gps.showSettingsAlert();
	                } 	
	            break;}
	    }
	}
			
	/**
	 * Add the user to the table on the cloud
	 */
	
	public class AddUserResult{
		public int id;
	}
	
	// Objeto usuario
		public class AddUser{
			public String Id;
			public String name;
			public String password;
			public String userName;
			public int idClassUser;
			public int idTypeUser;
			public int idTypeUserLocal;
			public String description;
			public double latitude;
			public double longitude;
		}
		
	private int addUser() {
		
		
		AddUser adduser = new AddUser();
		adduser.password = mPassword;
		adduser.name = mUserName;
		adduser.userName = mEmail;
		adduser.idClassUser = 1;
		adduser.idTypeUser = mUserType;
		adduser.idTypeUserLocal = mUserType;
		adduser.description = mUserDescr;
		adduser.latitude = mLatitude;
		adduser.longitude = mLongitude;
		
		// Insert the new item
		mClient.invokeApi("adduser",adduser, AddUserResult.class, new ApiOperationCallback<AddUserResult>() {
	        @Override
	        public void onCompleted(AddUserResult result, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show(); 
	            	signinstatus = -1;
	            	signinresult = 0;
	            } else {
	            	if (result.id != 0){
	            		Toast.makeText(getApplicationContext(), "Error: Email already exists" , Toast.LENGTH_LONG).show();  
	            		signinstatus = -1;
	            		signinresult = 2;
	            	}	
	            	else{
	            		signinstatus =0;
	            		signinresult = 1;
	            	}
	            }
	        }
	    });
		return signinstatus;
	}

	
private class ProgressFilter implements ServiceFilter {
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,
				final ServiceFilterResponseCallback responseCallback) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
				}
			});
			
			nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
				
				@Override
				public void onResponse(ServiceFilterResponse response, Exception exception) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
						}
					});
					
					if (responseCallback != null)  responseCallback.onResponse(response, exception);
				}
			});
		}
	}
}
