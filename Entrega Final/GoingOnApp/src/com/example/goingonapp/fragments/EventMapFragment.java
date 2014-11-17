package com.example.goingonapp.fragments;

import com.example.goingonapp.objects.MapObject;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventMapFragment extends MapFragment {

	 

	 public Marker placeMarker(MapObject eventInfo) {

	  Marker marker  = getMap().addMarker(new MarkerOptions()

		   .position(new LatLng(eventInfo.getLatitude(), eventInfo.getLongitude()))
	
		   .title(eventInfo.getName())
		   
		   .snippet(eventInfo.getDescription())
			  
		);	  

	  return marker;

	 }

}