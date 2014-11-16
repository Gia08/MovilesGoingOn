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
	
	public RegisterUser(String userName) {
		this.userName = userName;
	}

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the idClassUser
	 */
	public int getIdClassUser() {
		return idClassUser;
	}

	/**
	 * @param idClassUser the idClassUser to set
	 */
	public void setIdClassUser(int idClassUser) {
		this.idClassUser = idClassUser;
	}

	/**
	 * @return the idTypeUser
	 */
	public int getIdTypeUser() {
		return idTypeUser;
	}

	/**
	 * @param idTypeUser the idTypeUser to set
	 */
	public void setIdTypeUser(int idTypeUser) {
		this.idTypeUser = idTypeUser;
	}

	/**
	 * @return the idTypeUserLocal
	 */
	public int getIdTypeUserLocal() {
		return idTypeUserLocal;
	}

	/**
	 * @param idTypeUserLocal the idTypeUserLocal to set
	 */
	public void setIdTypeUserLocal(int idTypeUserLocal) {
		this.idTypeUserLocal = idTypeUserLocal;
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
	
}
