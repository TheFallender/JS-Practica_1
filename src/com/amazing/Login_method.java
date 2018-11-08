package com.amazing;

import com.amazing.User;

public class Login_method {
	protected static boolean logged_in = false;
	protected static boolean login_method_in() {
		String email;
		String e_email; //Encrypted email
		String e_password; //Encrypted password
		while(true) { //User check
			email = Filter.filter_s("Insert your email: ");
			if (email.equals("exit"))
				return false;
			try {
				e_password = Encrypter.encrypt(Filter.filter_s("Insert your password: "));
			}
			catch (Exception e) {
				throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
			}
			IO.read("d_user", "u_email=" + email, 5, false);
			if (IO.data_a[0] == null) { //User not found
				System.out.println("ERROR - Username and password doesn't match.");
			}
			else { //User exists
				if (e_password.equals(IO.data_a[1])) { //Password match login in
					Amazing.active_user = new User (IO.data_a);
					IO.modify("d_user" , email, 2, 0);
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
		IO.modify("d_user", "u_email=" + Amazing.active_user.r_email(), 2, 0);
		Amazing.active_user.reset();
		Amazing.active_user = null;
		logged_in = false;
	}
}
