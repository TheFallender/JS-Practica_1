package com.amazing;

import com.amazing.user;

public class login_method {
	protected static boolean logged_in = false;
	protected static void login_method_in() {
		while(true) { //User check
			String e_email;
			String e_password;
			try {
				e_email = encrypter.encrypt(filter.filter_s("Insert your email: "));
				e_password = encrypter.encrypt(filter.filter_s("Insert your password: "));
			} catch (Exception e) {
				throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
			}
			if (e_email == "exit") {
				return;
			}
			if (io_text.search_user(e_email) == 0) {
				System.out.println("ERROR - Username and password doesn't match.");
			}
			else { //User exists
				String l_data[] = new String[4];
				io_text.read_d("d_user", "");
				System.arraycopy(io_text.data, 0, l_data, 0, 4);
				if (e_password.equals(l_data[2])) {
					amazing.active_user = new user (l_data);
					logged_in = true;
				}
				else {
					System.out.println("ERROR - Username and password doesn't match.");
				}
			}
		}
	}
	
	protected static void login_method_out() {
		amazing.active_user.reset();
		amazing.active_user = null;
		logged_in = false;
	}
}
