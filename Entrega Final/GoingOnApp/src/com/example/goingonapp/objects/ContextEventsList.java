package com.example.goingonapp.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContextEventsList implements Serializable {
			
		private static final long serialVersionUID = 1L;
		//Attributes
		private List<MapObject> listEvents;
		
		// Constructor
		public ContextEventsList() {
			listEvents = new ArrayList<MapObject>();
		}

		//Getters and Setters
		
		/**
		 * @return the listEvents
		 */
		public List<MapObject> getListEvents() {
			return listEvents;
		}

		/**
		 * @param listEvents the listEvents to set
		 */
		public void setListEvents(List<MapObject> listEvents) {
			this.listEvents = listEvents;
		}
		
		//Methods
		
		public MapObject getEvent(int idEvent) {
			for(int i =0; i< this.listEvents.size(); i++){
				if (Integer.parseInt(this.listEvents.get(i).getmId()) == idEvent) {
					return this.listEvents.get(i);
				}
			}
			return null;
		}
		
		public MapObject getEvent(String eventName) {
			for(int i =0; i< this.listEvents.size(); i++){
				if (this.listEvents.get(i).getName().equals(eventName)) {
					return this.listEvents.get(i);
				}
			}
			return null;
		}
		
		public void insertEvent(MapObject newEvent) {
			this.listEvents.add(newEvent);
		}
		
		public void deleteEvent(MapObject event){
			int idEvent = (int) Long.parseLong(event.getmId());
			for(int i =0; i< this.listEvents.size(); i++){
				if (Long.parseLong(this.listEvents.get(i).getmId()) == idEvent) {
					this.listEvents.remove(i);
					break;
				}
			}
		}

}
