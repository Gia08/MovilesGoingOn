package com.example.goingonapp.objects;

import java.util.ArrayList;

import com.example.goingonapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsAdapter extends BaseAdapter {

	protected Activity activity;
    protected ArrayList<CreateEvent> items;
    
    public EventsAdapter(Activity activity, ArrayList<CreateEvent> items) {
	    this.activity = activity;
	    this.items = items;
	  }
    
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(items.get(position).getmId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Generate a convertView for eficiency
				View v = convertView;

				//Associate the layout of the list created
				if(convertView == null){
					LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = inf.inflate(R.layout.event_list_item, null);
				}

				// Create an event object
				CreateEvent ev = items.get(position);
				//Load the Image
				//FIXME 
				//ImageView event_image = (ImageView) v.findViewById(R.id.event_image);
				//event_image.setImageDrawable(ev.getFoto());
				//Get the event name
				TextView event_name = (TextView) v.findViewById(R.id.event_name);
				event_name.setText(ev.getName());
				//Get the event description
				TextView event_description = (TextView) v.findViewById(R.id.event_description);
				event_description.setText(ev.getDescription());

				// Return the view
				return v;
	}

}
