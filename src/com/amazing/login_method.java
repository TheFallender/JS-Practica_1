package com.amazing;

import com.amazing.user;

public class login_method {
	protected static boolean logged_in = false;
	protected static void login_method_in() {
		while(true) { //User check
			String e_email; //Encrypted email
			String e_password; //Encrypted password
			try {
				e_email = encrypter.encrypt(filter.filter_s("Insert your email: "));
				e_password = encrypter.encrypt(filter.filter_s("Insert your password: "));
			} catch (Exception e) {
				throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
			}
			if (e_email == "exit") {
				return;
			}
			if (io_text.search_user(e_email) == 0) { //User not found
				System.out.println("ERROR - Username and password doesn't match.");
			}
			else { //User exists
				String aux_data[] = new String[4];
				io_text.read("d_user", "u_email=" + e_email, 4, 0, 0);
				System.arraycopy(io_text.data_c, 0, aux_data, 0, 4);
				if (e_password.equals(aux_data[2])) { //Password match login in
					amazing.active_user = new user (aux_data);
					io_text.write_login(e_email, true);
					logged_in = true;
				}
				else { //Password doesn't match
					System.out.println("ERROR - Username and password doesn't match.");
				}
			}
		}
	}
	
	protected static void login_method_out() {
		io_text.write_login(amazing.active_user.r_email(), false);
		amazing.active_user.reset();
		amazing.active_user = null;
		logged_in = false;
	}
}
