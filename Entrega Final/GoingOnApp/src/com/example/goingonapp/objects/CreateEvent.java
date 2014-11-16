package com.example.goingonapp.objects;

public class CreateEvent {

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	public String mId;
	
	/**
	 * Item Name
	 */
	@com.google.gson.annotations.SerializedName("name")
	public String name;

	/**
	 * Item Description
	 */
	@com.google.gson.annotations.SerializedName("description")
	public String description;
	
	/**
	 * Item start Date
	 */
	@com.google.gson.annotations.SerializedName("startDate")
	public String startDate;
	
	/**
	 * Item end Date
	 */
	@com.google.gson.annotations.SerializedName("endDate")
	public String endDate;
	
	
	/**
	 * Item start Time
	 */
	@com.google.gson.annotations.SerializedName("startTime")
	public String startTime;
	
	
	/**
	 * Item end Time
	 */
	@com.google.gson.annotations.SerializedName("endTime")
	public String endTime;
	
	/**
	 *  Item event Price
	 */
	@com.google.gson.annotations.SerializedName("eventPrice")
	public String eventPrice;
	
	/**
	 *  Item id Type Event
	 */
	@com.google.gson.annotations.SerializedName("idTypeEvent")
	public int idTypeEvent;
	
	/**
	 *  Item id Type Privacy Event
	 */
	@com.google.gson.annotations.SerializedName("idTypePrivacyEvent")
	public int idTypePrivacyEvent;
	
	/**
	 *  Item id Type State Event
	 */
	@com.google.gson.annotations.SerializedName("idTypeStateEvent")
	public int idTypeStateEvent;
	
	/**
	 *  Item latitude
	 */
	@com.google.gson.annotations.SerializedName("latitude")
	public double latitude;
	
	/**
	 *  Item longitude
	 */
	@com.google.gson.annotations.SerializedName("longitude")
	public double longitude;
	
	/**
	 *  Item Email
	 */
	@com.google.gson.annotations.SerializedName("Username")
	public String Username;

	/**
	 * CreateEvent constructor
	 */
	public CreateEvent() {

	}

	/**
	 * Initializes a new Event Item
	 *   
	 */
	public CreateEvent(String mId, String name,  String description, String startDate, String endDate, String startTime, String endTime, String eventPrice, int idTypeEvent, int idTypePrivacyEvent, int idTypeStateEvent, double latitude, double longitude, String Username) {
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
