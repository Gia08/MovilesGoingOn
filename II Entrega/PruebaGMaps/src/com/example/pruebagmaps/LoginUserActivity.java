package com.example.pruebagmaps;

import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


import com.facebook.Session;

import java.net.MalformedURLException;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
//public class LoginUserActivity extends FragmentActivity implements AccessTokenRequestListener  {
public class LoginUserActivity extends FragmentActivity  {
	
	private MobileServiceClient mClient;
	private FbLoginFragment fbLoginFragment;
	
	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<User> mUserTable;	
	private String fbUserId = null;

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	public static final String GO_LOGIN = "gologin";


	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private TextView mLoginStatusMessageView;
	
	private int loginresult;

	/**
	 * Progress spinner to use for table operations
	 */
	private ProgressBar mProgressBar;

	public class Loginuser {
	      public String Id;
	      public String Username;
	      public String Password;
	      public int idClassUser;
	}
	
	
	
	// Objeto que se reci
	public class LoginuserResult {
		public int login;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			fbLoginFragment = new FbLoginFragment();
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, fbLoginFragment)
			.commit(); 
		} else {
			// Or set the fragment from restored state info
			fbLoginFragment = (FbLoginFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}

		// Set up the login form.
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
	public void onResume(){
		super.onResume();
		Log.d("MiApp","Se volvio a abrir");
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
	public void attemptLogin() {
		
		try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this).withFilter(new ProgressFilter());
			// Load the items from the Mobile Service
			processTableData();

		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL" , Toast.LENGTH_LONG).show(); 
		}
		
	}
		
	private void processTableData() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

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
			addUser();
		}
	}
	
	/**
	 * Add the user to the table on the cloud
	 */
	private void addUser () {
		Loginuser login = new Loginuser();
		login.Password = mPassword;
		//login.Username = "sergio@hotmail.es";
		login.Username = mEmail;
		login.idClassUser = 1;
		
		mClient.invokeApi("loginuser",login, LoginuserResult.class, new ApiOperationCallback<LoginuserResult>() {
	        @Override
	        public void onCompleted(LoginuserResult result, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show();
	            } else {
	            	if (result.login != 1){	            		
	            		Toast.makeText(getApplicationContext(), "Error: Email/Password Incorrect" , Toast.LENGTH_LONG).show(); 
	            	}
	            	else{
	            		finishedAuth();
	            	}
	            }
	        }
	    });
		
	}
	
	/**
	 * Starts the authenticaton process
	 * @param mMail
	 * @param mPass
	 * @param typeLogin
	 */
	public void executeTaskFb(String mMail, String mPass, String typeLogin) {
		mEmail = mMail;
		addUser();
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

	public void finishedAuth() {
		Intent intent = new Intent(this, MapActivity.class);

		intent.putExtra("userEmail", mEmail);
		if (Session.getActiveSession()!=null){
			intent.putExtra("fbUserId", fbUserId);
		}
		startActivity(intent);
	}

	public void gotoSignIn(){
		Intent intent = new Intent(this, SignInActivity.class );

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

	@Override
	public void onBackPressed() {

	}

	public void setFbUserId(String id) {
		fbUserId = id;
	}
}
