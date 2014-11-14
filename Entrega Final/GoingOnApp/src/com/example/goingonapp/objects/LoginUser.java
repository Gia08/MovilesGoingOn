package com.example.goingonapp.objects;


/**
 * Represents a Login User Item
 */
public class LoginUser {

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	public String mId;
	
	/**
	 * Item User Email
	 */
	@com.google.gson.annotations.SerializedName("Username")
	public String Username;

	

	/**
	 * Item Password
	 */
	@com.google.gson.annotations.SerializedName("Password")
	public String Password;
	
	/**
	 *  Item Id User Class
	 */
	@com.google.gson.annotations.SerializedName("idClassUser")
	public int idClassUser;

	/**
	 * LoginUser constructor
	 */
	public LoginUser() {

	}

	/**
	 * Initializes a new Login User Item
	 * 
	 * @param id
	 *            The item id
	 * @param Username
	 *   		  The item username
	 * @param password
	 *   
	 * @param userClass
	 *   
	 */
	public LoginUser(String mId, String Username,  String Password, int idClassUser) {
		this.mId = mId;
		this.Username = Username;
		this.Password = Password;
		this.idClassUser = idClassUser;
	}

}
