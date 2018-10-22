package com.amazing;

import java.util.Date;

public class user { //User login
	private String email;
	private String password;
	private long login;
	private long last_login;
	
	protected user() { //Creates a user from scratch
		//User
		while(true) { //User check
			String e_email = "";
			String temp_email = filter.filter_s("Insert your email: ");
			if (temp_email == "exit") {	//Prevent exit as email
				System.out.println("ERROR - You can't enter 'exit' as an email.");
			}
			else {
				try {
					e_email = encrypter.encrypt(temp_email);
					if (io_text.search_user(temp_email) == 0) { //User doesn't exist, proceed
						try {
							this.email = e_email;
							break;
						}
						catch (Exception e) {
							throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
						}
					}
					else { //User already exists
						System.out.println("ERROR - User already exist, try another email.");
					}
				} catch (Exception e) {
					throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
				}
			}
		}
		//Password
		try {
			this.password = encrypter.encrypt(filter.filter_s("Insert your password: "));
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
		}
		Date d = new Date();
		this.login = d.getTime();
		this.last_login = 0;
	}
	
	protected user(String data[]) { //Creates a user based on the data String
		this.email = data[0];
		this.password = data[1];
		Date d = new Date();
		this.login = d.getTime();
		this.last_login = Long.parseLong(data[3]);
	}
	
	protected void reset() { //Resets the class
		this.email = "";
		this.password = "";
		this.login = 0;
		this.last_login = 0;
	}
	
	protected String r_email () {
		return this.email;
	}
	
	protected String r_pass () {
		return this.password;
	}
	
	protected long r_date(boolean login_t) { //Returns date
		if(login_t) { //Login
			return this.login;
		}
		else { //Last login
			return this.last_login;
		}
	}
}