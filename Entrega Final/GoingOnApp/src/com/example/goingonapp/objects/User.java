package com.example.goingonapp.objects;


/**
 * Represents an item in a ToDo list
 */
public class User {

	/**
	 * Item text
	 */
	@com.google.gson.annotations.SerializedName("user_email")
	private String mEmail;

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	/**
	 * Indicates if the item is completed
	 */
	@com.google.gson.annotations.SerializedName("user_password")
	private String mPassword;

	/**
	 * ToDoItem constructor
	 */
	public User() {

	}

	@Override
	public String toString() {
		return getEmail();
	}

	/**
	 * Initializes a new ToDoItem
	 * 
	 * @param text
	 *            The item text
	 * @param id
	 *            The item id
	 */
	public User(String email, String id) {
		this.setEmail(email);
		this.setId(id);
	}

	/**
	 * Returns the item text
	 */
	public String getEmail() {
		return mEmail;
	}

	/**
	 * Sets the item text
	 * 
	 * @param text
	 *            text to set
	 */
	public final void setEmail(String email) {
		mEmail = email;
	}

	/**
	 * Returns the item id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Sets the item id
	 * 
	 * @param id
	 *            id to set
	 */
	public final void setId(String id) {
		mId = id;
	}

	/**
	 * Indicates if the item is marked as completed
	 */
	public String getPassword() {
		return mPassword;
	}

	/**
	 * Marks the item as completed or incompleted
	 */
	public void setPassword(String password) {
		mPassword = password;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof User && ((User) o).mId == mId;
	}
}