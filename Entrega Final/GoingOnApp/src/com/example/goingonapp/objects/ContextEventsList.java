package com.example.goingonapp.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContextEventsList implements Serializable {
			
		private static final long serialVersionUID = 1L;
		//Attributes
		private List<Event> listEvents;
		
		// Constructor
		public ContextEventsList() {
			listEvents = new ArrayList<Event>();
		}

		//Getters and Setters
		
		/**
		 * @return the listEvents
		 */
		public List<Event> getListEvents() {
			return listEvents;
		}

		/**
		 * @param listEvents the listEvents to set
		 */
		public void setListEvents(List<Event> listEvents) {
			this.listEvents = listEvents;
		}
		
		//Methods
		
		public Event getEvent(int idEvent) {
			for(int i =0; i< this.listEvents.size(); i++){
				if (Long.parseLong(this.listEvents.get(i).getmId()) == idEvent) {
					return this.listEvents.get(i);
				}
			}
			return null;
		}
		
		public Event getEvent(String eventName) {
			for(int i =0; i< this.listEvents.size(); i++){
				if (this.listEvents.get(i).getName().equals(eventName)) {
					return this.listEvents.get(i);
				}
			}
			return null;
		}
		
		public void insertEvent(Event newEvent) {
			this.listEvents.add(newEvent);
		}
		
		public void deleteEvent(Event event){
			int idEvent = (int) Long.parseLong(event.getmId());
			for(int i =0; i< this.listEvents.size(); i++){
				if (Long.parseLong(this.listEvents.get(i).getmId()) == idEvent) {
					this.listEvents.remove(i);
					break;
				}
			}
		}

}
