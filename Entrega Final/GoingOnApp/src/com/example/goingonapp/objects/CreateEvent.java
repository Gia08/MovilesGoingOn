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
	
}
