package com.example.goingonapp.activities;

import java.util.ArrayList;

import com.example.goingonapp.R;
import com.example.goingonapp.R.layout;
import com.example.goingonapp.R.menu;
import com.example.goingonapp.objects.CreateEvent;
import com.example.goingonapp.objects.EventsAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class GoingOnAppEventList_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_going_on_event_list);
		
		ListView lista = (ListView) findViewById(R.id.listaeventos);
	      
		//ArrayList<CreateEvent> arraydir = (ArrayList<CreateEvent>)eventsUser.getListEvents();
		ArrayList<CreateEvent> arraydir = new ArrayList<CreateEvent>();
		//for (int i = 0; i<arraydir.size();i++){
		for (int i = 0; i<7;i++){
			//FIXME
			//This should get the image
			//arraydir.get(i).event_Image = getResources().getDrawable(R.drawable.ic_concierto);			
			//Remove this, is for testing only:
			CreateEvent newEvent = new CreateEvent("" + (100 + 1), "Titulo " +i, "descripcion " + i,
					"1000", "1000", "1000", "1000", 
					"1000", 2, 
					2, 2, 2, 2, "1000");
			
			arraydir.add(newEvent);
		}
 
        // Create the custom adapter for events
        EventsAdapter adapter = new EventsAdapter(this, arraydir); 
        // Applies the adapter
        lista.setAdapter(adapter);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.going_on_app_event_list_, menu);
		return true;
	}

}
