package com.example.goingonapp.objects;

import java.io.Serializable;

public class MapObject implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Item Id
	 */
	public String mId;
	
	/**
	 * Item Name
	 */
	public String name;

	/**
	 * Item Description
	 */
	public String description;
	
	/**
	 * Item start Date
	 */
	public String startDate;
	
	/**
	 * Item end Date
	 */
	public String endDate;
	
	
	/**
	 * Item start Time
	 */
	public String startTime;
	
	
	/**
	 * Item end Time
	 */
	public String endTime;
	
	/**
	 *  Item event Price
	 */
	public String eventPrice;
	
	/**
	 *  Item id Type Event
	 */
	public int idTypeEvent;
	
	/**
	 *  Item id Type Privacy Event
	 */
	public int idTypePrivacyEvent;
	
	/**
	 *  Item id Type State Event
	 */
	public int idTypeStateEvent;
	
	/**
	 *  Item latitude
	 */
	public double latitude;
	
	/**
	 *  Item longitude
	 */
	public double longitude;
	
	/**
	 *  Item Email
	 */
	public String Username;

	/**
	 * CreateEvent constructor
	 */
	public MapObject() {

	}

	/**
	 * Initializes a new Event Item
	 *   
	 */
	public MapObject(String mId, String name,  String description, String startDate, String endDate, String startTime, String endTime, String eventPrice, int idTypeEvent, int idTypePrivacyEvent, int idTypeStateEvent, double latitude, double longitude, String Username) {
		this.mId = mId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventPrice = eventPrice;
		this.idTypeEvent= idTypeEvent;
		this.idTypePrivacyEvent = idTypePrivacyEvent;
		this.idTypeStateEvent = idTypeStateEvent;
		this.latitude = latitude;
		this.longitude = longitude;
		this.Username = Username;
	}
	
	public MapObject(String nombreEvento, String descripcionEvento, String id, double Lat, double Long, int idTypeEvent) {
        this.name = nombreEvento;
        this.description = descripcionEvento;
        this.latitude = Lat;
		this.longitude = Long;
        this.mId = id;
        this.idTypeEvent = idTypeEvent;
        this.eventPrice = "";
        this.startDate = "";
		this.endDate = "";
		this.startTime = "";
		this.endTime = "";
		this.idTypePrivacyEvent = 1;
		this.idTypeStateEvent = 1;
		this.Username = "";
    }
	
	public MapObject(String nombreEvento, String descripcionEvento, String id, double Lat, double Long, int idTypeEvent, String startTime) {
        this.name = nombreEvento;
        this.description = descripcionEvento;
        this.latitude = Lat;
		this.longitude = Long;
        this.mId = id;
        this.idTypeEvent = idTypeEvent;
        this.eventPrice = "";
        this.startDate = startTime;
		this.endDate = "";
		this.startTime = "";
		this.endTime = "";
		this.idTypePrivacyEvent = 1;
		this.idTypeStateEvent = 1;
		this.Username = "";
    }
	
	public MapObject(String nombreEvento, String descripcionEvento, String id, double Lat, double Long, int idTypeEvent, String eventPrice, String startDate, String startTime) {
        this.name = nombreEvento;
        this.description = descripcionEvento;
        this.latitude = Lat;
		this.longitude = Long;
        this.mId = id;
        this.idTypeEvent = idTypeEvent;
        this.eventPrice = eventPrice;
        this.startDate = startDate;
		this.endDate = "";
		this.startTime = startTime;
		this.endTime = "";
		this.idTypePrivacyEvent = 1;
		this.idTypeStateEvent = 1;
		this.Username = "";
    }
	
	/**
	 * Getters and Setters
	 */

	/**
	 * @return the mId
	 */
	public String getmId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setmId(String mId) {
		this.mId = mId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the eventPrice
	 */
	public String getEventPrice() {
		return eventPrice;
	}

	/**
	 * @param eventPrice the eventPrice to set
	 */
	public void setEventPrice(String eventPrice) {
		this.eventPrice = eventPrice;
	}

	/**
	 * @return the idTypeEvent
	 */
	public int getIdTypeEvent() {
		return idTypeEvent;
	}

	/**
	 * @param idTypeEvent the idTypeEvent to set
	 */
	public void setIdTypeEvent(int idTypeEvent) {
		this.idTypeEvent = idTypeEvent;
	}

	/**
	 * @return the idTypePrivacyEvent
	 */
	public int getIdTypePrivacyEvent() {
		return idTypePrivacyEvent;
	}

	/**
	 * @param idTypePrivacyEvent the idTypePrivacyEvent to set
	 */
	public void setIdTypePrivacyEvent(int idTypePrivacyEvent) {
		this.idTypePrivacyEvent = idTypePrivacyEvent;
	}

	/**
	 * @return the idTypeStateEvent
	 */
	public int getIdTypeStateEvent() {
		return idTypeStateEvent;
	}

	/**
	 * @param idTypeStateEvent the idTypeStateEvent to set
	 */
	public void setIdTypeStateEvent(int idTypeStateEvent) {
		this.idTypeStateEvent = idTypeStateEvent;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}
	
	
	
}
