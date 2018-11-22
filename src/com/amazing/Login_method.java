package com.amazing;

import java.util.Date;

public class Login_method {
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
					String[] data = new String[2];
					data[0] = email;
					Date d = new Date(); //Get date
					data[1] = "u_login=" + d.getTime() + "\r\n"; //Login
					IO.modify("d_user" , data, 2);
					return true;
				}
				else { //Password doesn't match
					System.out.println("ERROR - Username and password doesn't match.");
				}
			}
		}
	}
	
	protected static void login_method_out() {
		String[] data = new String[3];
		data[0] = "u_email=" + Amazing.active_user.r_email();
		data[1] = "u_login=0\r\n";
		Date d = new Date(); //Get date
		data[2] = "u_last_login=" + d.getTime() + "\r\n"; //Login
		IO.modify("d_user", data, 2);
		Amazing.active_user.reset();
		Amazing.active_user = null;
	}
}
