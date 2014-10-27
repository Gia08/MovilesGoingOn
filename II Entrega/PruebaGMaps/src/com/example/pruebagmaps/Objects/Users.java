package com.example.pruebagmaps.Objects;


/**
 * Represents an item in a ToDo list
 */
public class Users {
	
	public String mId;
	public String mUserName;
	public String mPassword;
	public String mEmail;
	public int mIdClassUser;
	public int mIdTypeUser;
	public int mIdTypeUserLocal;
	public String mDescription;
	private double latitude;
	private double longitude;
	
	public Users() {

	}
	
	public String getmEmail() {
		return mEmail;
	}


	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmUsername() {
		return mUserName;
	}


	public void setmUsername(String mUsername) {
		this.mUserName = mUsername;
	}


	public String getmId() {
		return mId;
	}


	public void setmId(String mId) {
		this.mId = mId;
	}


	public String getmPassword() {
		return mPassword;
	}


	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}


	public int getmIdTypeUser() {
		return mIdTypeUser;
	}


	public void setmIdTypeUser(int mIdTypeUser) {
		this.mIdTypeUser = mIdTypeUser;
	}


	public int getmIdTypeUserLocal() {
		return mIdTypeUserLocal;
	}


	public void setmIdTypeUserLocal(int mIdTypeUserLocal) {
		this.mIdTypeUserLocal = mIdTypeUserLocal;
	}


	public int getmIdClassUser() {
		return mIdClassUser;
	}


	public void setmIdClassUser(int mIdClassUser) {
		this.mIdClassUser = mIdClassUser;
	}


	public String getmDescription() {
		return mDescription;
	}


	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}