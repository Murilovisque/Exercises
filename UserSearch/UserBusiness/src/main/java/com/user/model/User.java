package com.user.model;

public class User {

	private String email;
	private int inbox;
	private int size;
	
	public User(String p_email, int p_inbox, int p_size) {
		setEmail(p_email);
		setInbox(p_inbox);
		setSize(p_size);
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getInbox() {
		return inbox;
	}
	
	public int getSize() {
		return size;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setInbox(int inbox) {
		this.inbox = inbox;
	}

	private void setSize(int size) {
		this.size = size;
	}	
	
}
