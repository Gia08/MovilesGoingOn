package com.example.goingonapp.objects;

/**
 * Represents a Register User Item
 */
public class RegisterUser {
	

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	public String mId;
	
	/**
	 * Item User Name
	 */
	@com.google.gson.annotations.SerializedName("name")
	public String name;

	/**
	 * Item Password
	 */
	@com.google.gson.annotations.SerializedName("password")
	public String password;
	
	/**
	 * Item Email
	 */
	@com.google.gson.annotations.SerializedName("userName")
	public String userName;
	
	/**
	 * Item id Class User
	 */
	@com.google.gson.annotations.SerializedName("idClassUser")
	public int idClassUser;
	
	
	/**
	 * Item id Type User
	 */
	@com.google.gson.annotations.SerializedName("idTypeUser")
	public int idTypeUser;
	
	
	/**
	 * Item id Type User Local
	 */
	@com.google.gson.annotations.SerializedName("idTypeUserLocal")
	public int idTypeUserLocal;
	
	/**
	 *  Item description
	 */
	@com.google.gson.annotations.SerializedName("description")
	public String description;
	
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
	 * RegisterUser constructor
	 */
	public RegisterUser() {

	}

	/**
	 * Initializes a new Register User Item
	 *   
	 */
	public RegisterUser(String mId, String name,  String password, String userName, int idClassUser, int idTypeUser, int idTypeUserLocal, String description, double latitude, double longitude) {
		this.mId = mId;
		this.name = name;
		this.password = password;
		this.userName = userName;
		this.idClassUser = idClassUser;
		this.idTypeUser = idTypeUser;
		this.idTypeUserLocal = idTypeUserLocal;
		this.description = description;
		this.latitude= latitude;
		this.longitude = longitude;
	}
}
