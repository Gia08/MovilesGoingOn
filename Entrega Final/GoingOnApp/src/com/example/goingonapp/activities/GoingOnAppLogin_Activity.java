package com.example.goingonapp.activities;

import java.net.MalformedURLException;
import java.util.List;

import com.example.goingonapp.R;
import com.example.goingonapp.fragments.FbLoginFragment;
import com.example.goingonapp.objects.LoginUser;
import com.example.goingonapp.objects.LoginUserResult;

import com.facebook.Session;
import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GoingOnAppLogin_Activity extends FragmentActivity {

	/**
	 * Variables related to Facebook Login
	 */
	private FbLoginFragment fbLoginFragment;
	private String fbUserId = null;
	private String userMail;
	private String userName;
	public String sessionFB;
	
	/**
	 * Variables related to Login 
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	private String mEmail;
	private String mPassword;
	private MobileServiceClient mClient;
	
	
	/**
	 * UI References
	 */
	private EditText mEmailView;
	private EditText mPasswordView;
	private TextView mLoginStatusMessageView;
	private View focusView;
	private ProgressDialog pDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
     	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_going_on_app_login);
				
		if (savedInstanceState == null) {
			fbLoginFragment = new FbLoginFragment();
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, fbLoginFragment)
			.commit(); 
		} else {
			
			fbLoginFragment = (FbLoginFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
		
		pDialog = new ProgressDialog(GoingOnAppLogin_Activity.this);
		
		if (Session.getActiveSession()!=null){
			
	        pDialog.setMessage("Logging with Facebook....");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
		}
		
		/**
		 * Set up the Login Form
		 */
     	
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
		.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});
		
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.log_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (isOnline()) {
							attemptLogin();
						}
						
						else {
							Toast.makeText(getApplicationContext(), R.string.error_no_internet, Toast.LENGTH_LONG).show();
						}
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoSignIn();
					}
				});

		if (!isOnline()) {
			Toast.makeText(getApplicationContext(), R.string.error_no_internet, Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.going_on_app_map_, 
               menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {

	}
	
	/**
	 *  Functions Related to Login
	 */
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 * @throws MalformedURLException 
	 */
	public void attemptLogin() {			
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
	 * Attempts to get all the data from the UI Form and set to the variables.
	 */
	private void processTableData() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

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
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			//pDialog = new ProgressDialog(GoingOnAppLogin_Activity.this);
	        pDialog.setMessage("Logging....");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
			addUser(1);
		}
	}
	
	/**
	 * Add the user to the table on the cloud
	 */
	private void addUser (int classUser) {
		LoginUser login = new LoginUser();
		login.Password = mPassword;
		login.Username = mEmail;
		login.idClassUser = classUser;
		
		mClient.invokeApi("loginuser",login, LoginUserResult.class, new ApiOperationCallback<LoginUserResult>() {
	        @Override
	        public void onCompleted(LoginUserResult loginUserResult, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	pDialog.dismiss();
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show();
	            } else {
	            	if (loginUserResult.result != 1){	
	            		pDialog.dismiss();
	            		Toast.makeText(getApplicationContext(), "Error: Email/Password Incorrect" , Toast.LENGTH_LONG).show(); 
	            		mPasswordView.setError(getString(R.string.error_incorrect_password));
	        			focusView = mPasswordView;
	        			focusView.requestFocus();
	            	}
	            	else{
	            		pDialog.dismiss();
	            		finishedAuthSystem();
	            	}
	            }
	        }
	    });
	}
	

	public void finishedAuthSystem() {
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
		intent.putExtra("userEmail", mEmail);	
		intent.putExtra("userType", 1);//System
		startActivity(intent);
		this.finish();
	}

	public void finishedAuthFacebook(String userMail, String userName) {
		pDialog.dismiss();
		this.userMail = userMail;
		this.userName = userName;
		Intent intent = new Intent(this, GoingOnAppMain_Activity.class);
		if (Session.getActiveSession()!=null){
			intent.putExtra("fbUserId", fbUserId);
			//intent.putExtra("userMail", this.userMail);
			//intent.putExtra("userName", this.userName);
			intent.putExtra("userType", 2);//Facebook
		}	
		startActivity(intent);		
		this.finish();
	}
	public void gotoSignIn(){
		Intent intent = new Intent(this, GoingOnAppSignUp_Activity.class );

		// Verify it resolves
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;

		// Start an activity if it's safe
		if (isIntentSafe) {
			startActivity(intent);}
	}

	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}


	public void setFbUserId(String id) {
		fbUserId = id;
	}
}
