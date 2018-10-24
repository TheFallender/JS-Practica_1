package com.amazing;

import com.amazing.user;

public class login_method {
	protected static boolean logged_in = false;
	protected static boolean login_method_in() {
		String email;
		String e_email; //Encrypted email
		String e_password; //Encrypted password
		while(true) { //User check
			email = filter.filter_s("Insert your email: ");
			e_email = email;
			if (email.equals("exit"))
				return false;
			/*
			try {
				e_email = encrypter.encrypt(email);
				System.out.println(e_email);
				e_password = encrypter.encrypt(filter.filter_s("Insert your password: "));
			}
			catch (Exception e) {
				throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
			}
			*/
			e_password = filter.filter_s("Insert your password: ");
			io_text.read("d_user", "u_email=" + e_email, 5, false);
			if (io_text.data_a[0] == null) { //User not found
				System.out.println("ERROR - Username and password doesn't match.");
			}
			else { //User exists
				if (e_password.equals(io_text.data_a[1])) { //Password match login in
					amazing.active_user = new user (io_text.data_a);
					io_text.modify("d_user" , e_email, 2, 0);
					logged_in = true;
					return true;
				}
				else { //Password doesn't match
					System.out.println("ERROR - Username and password doesn't match.");
				}
			}
		}
	}
	
	protected static void login_method_out() {
		io_text.modify("d_user", "u_email=" + amazing.active_user.r_email(), 2, 0);
		amazing.active_user.reset();
		amazing.active_user = null;
		logged_in = false;
	}
}
