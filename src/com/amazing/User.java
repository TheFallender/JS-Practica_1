package com.amazing;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class User { //User login
	private String email;
	private String password;
	private long login;
	private long last_login;
	private String admin = "0";
	
	protected User() { //Creates a user from scratch
		//User
		while(true) { //User check
			String email = Filter.filter_s("Insert your email: ");
			if (email.equals("exit")) {	//Prevent exit as email
				System.out.println("ERROR - You can't enter 'exit' as an email.");
			}
			else {
				try {
					IO.read("d_user", "u_email=" + email, 1, false);
					if (IO.data_a[0] == null) { //User doesn't exist, proceed
						try {
							this.email = email;
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
			//this.password = encrypter.encrypt(filter.filter_s("Insert your password: "));
			this.password = Encrypter.encrypt(Filter.filter_s("Insert your password: "));
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
		}
		Date d = new Date();
		this.login = d.getTime();
		this.last_login = this.login;
		String aux[] = new String[5];
		aux[0] = this.email;
		aux[1] = this.password;
		aux[2] = "" + this.login;
		aux[3] = "" + this.last_login;
		aux[4] = "0";
		IO.write("d_user", aux);
	}
	
	protected User(String data[]) { //Creates a user based on the data String
		this.email = data[0];
		this.password = data[1];
		Date d = new Date();
		this.login = d.getTime();
		this.last_login = Long.parseLong(data[3]);
		this.admin = data[4];
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
	
	protected long r_date(boolean is_login) { //Returns date
		if(is_login) { //Login
			return this.login;
		}
		else { //Last login
			return this.last_login;
		}
	}

	protected boolean r_admin() {
		return admin.equals("1")?true:false;
	}
	protected void print() { //Print user

		try { //Decrypt try
			System.out.println("Email: " + (this.email));
			System.out.println("Password: ********");
			System.out.println("Login: " + date_s("		" + Instant.ofEpochMilli(this.login).atZone(ZoneId.of("Europe/Paris"))));
			System.out.println("Last login: " + date_s("	" + Instant.ofEpochMilli(this.last_login).atZone(ZoneId.of("Europe/Paris"))));
			if(r_admin()) {
				System.out.println("This user has admin access");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
		}
	}
	
	protected String date_s(String input) {
		String aux_p[] = input.split("T");
		aux_p[0].replaceAll("-", "/");
		aux_p[1] = aux_p[1].replaceAll("\\..*", "");
		return aux_p[0] + "	- " + aux_p[1];
	}
}