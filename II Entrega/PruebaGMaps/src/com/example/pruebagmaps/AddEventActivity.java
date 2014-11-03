package com.example.pruebagmaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.windowsazure.mobileservices.ApiOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddEventActivity extends Activity {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private EventCreateTask mAuthTask = null;
	private GetEventLocation mEventLoc = null;
	
	private int createEventStatus;
	private MobileServiceClient mClient;
	private ProgressBar mProgressBar;

	// Values for email and password at the time of the login attempt.
	private String mEventName;
	private String mEventDescr;
	private String mCategory;
	private String mEventPrice;
	private double mLatitude;
	private double mLongitude;
	private String mUserMail;

	Context context;

	
	// UI references.
	private EditText mEventNameView;
	private EditText mEventDescriptionView;
	private Spinner mEventCategorySpinner;
	private DatePicker mInitialDatePicker;
	private DatePicker mFinalDatePicker;
	private TimePicker mInitialTimePicker;
	private TimePicker mFinalTimePicker;
	private EditText mEventPriceView;
	private TextView mEventStatusMessageView;
	MultiAutoCompleteTextView myMultiAutoCompleteTextView;
	Button buttonCreateEvent;

	String inputTextLocation;
	String prueba;

	private ProgressDialog pDialog;

	EditText mEventLocation;
	List<String> numbers;
	List<String> coordenadas;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = (Context) this;
		
		setContentView(R.layout.activity_add_event);


		mUserMail = getIntent().getExtras().getString("userEmail");

		mLatitude = 0.0;
		mLongitude = 0.0;

		mEventPriceView = (EditText) findViewById(R.id.editText_event_price);		
		mEventNameView = (EditText) findViewById(R.id.editText_event_name);
		mEventDescriptionView = (EditText) findViewById(R.id.editText_event_descr);

		mEventCategorySpinner = (Spinner) findViewById(R.id.spinner_categories);
		mInitialDatePicker = (DatePicker) findViewById(R.id.datePicker_initial_date);
		mFinalDatePicker = (DatePicker) findViewById(R.id.datePicker_final_date);

		mInitialTimePicker = (TimePicker) findViewById(R.id.timePicker_initial_time);
		mFinalTimePicker = (TimePicker) findViewById(R.id.timePicker_final_time);

		mEventStatusMessageView = (TextView) findViewById(R.id.add_event_status_message);

		mEventLocation = (EditText) findViewById(R.id.editText_event_location);

		inputTextLocation = "";
		
		numbers = new ArrayList<String>();
		coordenadas = new ArrayList<String>();
				
		mEventLocation.addTextChangedListener(new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				inputTextLocation = s.toString();
				mEventLoc = new GetEventLocation();
				mEventLoc.execute(inputTextLocation);
				myMultiAutoCompleteTextView = (MultiAutoCompleteTextView)findViewById(R.id.editText_event_location);
		        myMultiAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, numbers));
		        myMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		        
		        myMultiAutoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		            	mLatitude = Double.parseDouble(coordenadas.get(arg2*2));
		            	mLongitude = Double.parseDouble(coordenadas.get((arg2*2)+1));
		            }

		        });
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		findViewById(R.id.button_create_event).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addEvent();
					}
				});

		findViewById(R.id.button_cancel_create_event).setOnClickListener(
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
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void addEvent() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEventPriceView.setError(null);
		mEventNameView.setError(null);
		mEventDescriptionView.setError(null);

		// Store values at the time of the login attempt.
		mEventPrice = mEventPriceView.getText().toString();
		mEventName = mEventNameView.getText().toString();
		mEventDescr= mEventDescriptionView.getText().toString();	
		mCategory = mEventCategorySpinner.getSelectedItem().toString();


		boolean cancel = false;
		View focusView = null;

		// Check for a valid event name.
		if (TextUtils.isEmpty(mEventPrice)) {
			mEventPriceView.setError(getString(R.string.error_field_required));
			focusView = mEventPriceView;
			cancel = true;
		}

		// Check for a valid event name.
		if (TextUtils.isEmpty(mEventName)) {
			mEventNameView.setError(getString(R.string.error_field_required));
			focusView = mEventNameView;
			cancel = true;
		}

		// Check for a valid event description.
		if (TextUtils.isEmpty(mEventDescr)) {
			mEventDescriptionView.setError(getString(R.string.error_field_required));
			focusView = mEventDescriptionView;
			cancel = true;
		}
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mEventStatusMessageView.setText(R.string.create_event_process);

			mAuthTask = new EventCreateTask();
			mAuthTask.execute(mEventName, mEventDescr, mEventPrice,mCategory, 
					mInitialDatePicker.getDayOfMonth() + "", mInitialDatePicker.getMonth() + "", mInitialDatePicker.getYear() + "",
					mInitialTimePicker.getCurrentHour()+"", mInitialTimePicker.getCurrentMinute()+"",
					mFinalDatePicker.getDayOfMonth() + "", mFinalDatePicker.getMonth() + "", mFinalDatePicker.getYear() + "",
					mFinalTimePicker.getCurrentHour()+"", mFinalTimePicker.getCurrentMinute()+"");


		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class EventCreateTask extends AsyncTask<String, String, Boolean> {

		String eventName,eventDescr, eventPrice, eventCategory,
		initDateDay, initDateMonth, initDateYear,
		initTimeHour, initTimeMinute,
		finalDateDay, finalDateMonth, finalDateYear,
		finalTimeHour, finalTimeMinute;

		@Override
		protected void onPreExecute() {
			//para el progress dialog
			pDialog = new ProgressDialog(AddEventActivity.this);
			pDialog.setMessage("Creating Event....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			//mInitialDate, mInitialTime, mFinalDate, mFinalTime
			//obtnemos usr y pass
			eventName=params[0];
			eventDescr=params[1];
			eventPrice=params[2];
			eventCategory=params[3];
			initDateDay=params[4];
			initDateMonth=params[5];
			initDateYear=params[6];
			initTimeHour=params[7];
			initTimeMinute=params[8];
			finalDateDay=params[9];
			finalDateMonth=params[10];
			finalDateYear=params[11];
			finalTimeHour=params[12];
			finalTimeMinute=params[13];

			
				if (createEventStatus(eventName,eventDescr, eventPrice, eventCategory,
						initDateDay, initDateMonth, initDateYear,
						initTimeHour, initTimeMinute,
						finalDateDay, finalDateMonth, finalDateYear,
						finalTimeHour, finalTimeMinute)== 0){    		    		
					return true; //login valido
				}else{    		
					return false; //login invalido     	          	  
				}
		}


		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			pDialog.dismiss();
			if (success) {
				finishedAuth();

			} else {

				mEventNameView
				.setError(getString(R.string.error_event_not_valid));
				mEventNameView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}



	public class GetEventLocation extends AsyncTask<String, String, Boolean> {

		private String client_id = "JO4TEYVPHJ1OGZ4HM1J3D354WYXN0XJWBBZRNKW3VZUTHONL";
		private String client_secret = "R0J2QS2K4EBEHUKZJWHBZYY51GN3XS1H4AFVLBET5Q2JLECP";
		private String currentDateandTime = "20140507";  //yyyymmdd
		private String jsonResult;
		private String currentLocation = "Costa%20Rica";
		private String place;

		@Override
		protected Boolean doInBackground(String... params) {
			place = params[0];
			String formatplace = formatNamePlace(place);
			String URL = "https://api.foursquare.com/v2/venues/suggestCompletion?near="+currentLocation+"&query="+formatplace+"&client_id="+client_id+"&client_secret="+client_secret+"&v="+currentDateandTime;
			try {
				HttpGet httppost = new HttpGet(URL);
				HttpClient hc = new DefaultHttpClient();
				HttpResponse response = hc.execute(httppost);  //response class to handle responses
				jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
				JSONObject object = new JSONObject(jsonResult); 
				if (object.getString("response").equals("{}")){
					//System.out.println("No hay resultado");
				}
				else{
					JSONObject variable_response = object.getJSONObject("response");
					JSONArray variable_minivenues = variable_response.getJSONArray("minivenues");
					JSONObject data;
					JSONObject location;
					String result = "";
					String coordLat = "";
					String coordLng = "";
					for (int i = 0; i < variable_minivenues.length(); i++){
						data = variable_minivenues.getJSONObject(i);
						location = data.getJSONObject("location");
						result = data.get("name")+ ","+ location.get("address") +", "+ 
								location.get("city") + ", "+ location.get("state")+", " + location.get("country");
						//System.out.println("Nombre = "+ data.get("name")+ ", Direccion = " + location.get("address") +", "+ 
						//		   location.get("city") + ", "+ location.get("state")+", " + location.get("country"));
						coordLat = location.getString("lat");
						coordLng = location.getString("lng");
						numbers.add(result);
						coordenadas.add(coordLat);
						coordenadas.add(coordLng);
					}					
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}


		protected void onPostExecute(String Result){
			try{

				Toast.makeText(getApplicationContext(), "R E S U L T :"+jsonResult, Toast.LENGTH_LONG).show();
				System.out.println(jsonResult);
				//showing result

			}catch(Exception E){
				Toast.makeText(getApplicationContext(), "Error:"+E.getMessage(), Toast.LENGTH_LONG).show();
			}

		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}
			return answer;
		}

		private String formatNamePlace(String place){
			String result = "";
			for (int i = 0; i < place.length(); i++){
				if (place.charAt(i) == ' '){
					result += "%20";
				}
				else{
					result += place.charAt(i);
				}
			}
			return result;
		}


	}
	
	public int createEventStatus(String eventName,String eventDescr, String eventPrice, String eventCategory,
			String initDateDay, String initDateMonth, String initDateYear,
			String initTimeHour, String initTimeMinute,
			String finalDateDay, String finalDateMonth, String finalDateYear,
			String finalTimeHour, String finalTimeMinute){
		createEventStatus=-1;
    	try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			
			mClient = new MobileServiceClient(
					"https://goingon.azure-mobile.net/",
					"NFAMhPBZapIrxYSYOgMIYSTZpTSaAJ18",
					this).withFilter(new ProgressFilter());
			
			java.util.Date initdt = new java.util.Date(Integer.parseInt(initDateYear)-1900,Integer.parseInt(initDateMonth)-1,Integer.parseInt(initDateDay));
			java.text.SimpleDateFormat initsdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String initDate = initsdf.format(initdt);
			
			java.util.Date enddt = new java.util.Date(Integer.parseInt(finalDateYear)-1900,Integer.parseInt(finalDateMonth)-1,Integer.parseInt(finalDateDay));
			java.text.SimpleDateFormat endsdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String endDate = endsdf.format(enddt);
			
			java.util.Date inittime = new java.util.Date(0,0,0,Integer.parseInt(initTimeHour),Integer.parseInt(initTimeMinute), 0);
			java.text.SimpleDateFormat inittimesdf = new java.text.SimpleDateFormat("HH:mm:ss");
			String initTime = inittimesdf.format(inittime);
			
			java.util.Date finaltime = new java.util.Date(0,0,0,Integer.parseInt(finalTimeHour),Integer.parseInt(finalTimeMinute), 0);
			java.text.SimpleDateFormat finaltimesdf = new java.text.SimpleDateFormat("HH:mm:ss");
			String finalTime = endsdf.format(enddt);
	    
	    
			
			createEventStatus = createEvent(eventName, eventDescr,  eventPrice,  eventCategory,
					initDate, endDate, initTime, finalTime);

		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "There was an error creating the Mobile Service. Verify the URL " , Toast.LENGTH_LONG).show();  
		}
    	
    	
    	return createEventStatus;
	}
	
	public class event {
		private int id;		
		private String name;		
		private String description;
		private String startDate;
		private String endDate;
		private String startTime;
		private String endTime;
		private String eventPrice;
		
		private int idTypeEvent;
		private int idTypePrivacyEvent;
		private int idTypeStateEvent;
		
		private double latitude;
		private double longitude;
		private String Username;
	
	}
	
	public class AddEventResult{
		public int id;
	}
	
	//Valida el estado del logueo solamente necesita como parametros el usuario y passw
	public int createEvent(String eventName,String eventDescr, String eventPrice, String eventCategory,
			String initDate, String endDate, String initTime, String endTime ){
		
		event newEvent = new event();
		
		newEvent.name = eventName;
		newEvent.description = eventDescr;
		newEvent.eventPrice = eventPrice;
		newEvent.latitude = mLatitude;
		newEvent.longitude = mLongitude;
		newEvent.Username = mUserMail;
	    
		newEvent.startDate = initDate;
		newEvent.endDate = endDate;
		newEvent.startTime =initTime;
		newEvent.endTime = endTime;
		newEvent.idTypeEvent = 1;
		newEvent.idTypePrivacyEvent = 1;
		newEvent.idTypeStateEvent = 1;
		

		mClient.invokeApi("addEvent",newEvent, AddEventResult.class, new ApiOperationCallback<AddEventResult>() {
	        @Override
	        public void onCompleted(AddEventResult result, Exception error, ServiceFilterResponse response) {
	            if (error != null) {
	            	Toast.makeText(getApplicationContext(), "Error: " + error , Toast.LENGTH_LONG).show(); 
	            	createEventStatus = -1;
	            } else {
	            	if (result.id != 0){
	            		Toast.makeText(getApplicationContext(), "Ocurrio algun error" , Toast.LENGTH_LONG).show();  
	            		createEventStatus = -1;
	            	}	
	            	else{
	            		Toast.makeText(getApplicationContext(), "Se agrego con exito" , Toast.LENGTH_LONG).show();  
	            		createEventStatus = 0;
	            	}
	            }
	        }
	    });
		return createEventStatus;
	}

	public void finishedAuth() {
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("userEmail", mUserMail);
		intent.putExtra("fbEventsInfo", getIntent().getExtras().getString("fbEventsInfo"));
		// Verify it resolves
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;

		// Start an activity if it's safe
		if (isIntentSafe) {
			startActivity(intent);}
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
