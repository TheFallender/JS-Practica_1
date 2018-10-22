package com.amazing;

import com.amazing.user;

public class login_method {
	protected static boolean logged_in = false;
	protected static void login_method_in() {
		int menu = 0;
		switch(menu) {
			case 0: //Menu
				System.out.println("Loggin menu:");
				System.out.println("1. Log in.");
				System.out.println("2. Create account.");
				System.out.println("3. Homepage.");
				filter.filter_i("Menu select: ", 1, 3);
				break;
			case 1: //Log in
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
						String data[] = new String[4];
						data = io_text.read_d("d_user", "");
						if (e_password.equals(data[2])) {
							amazing.active_user = new user (data);
							logged_in = true;
						}
						else {
							System.out.println("ERROR - Username and password doesn't match.");
						}
					}
				}
			case 2: //Create
				amazing.active_user = new user();
				io_text.write("d_user");
				break;
			case 3: //Homepage
				return;
		}

	}
	
	protected static void login_method_out() {
		amazing.active_user.reset();
		amazing.active_user = null;
		logged_in = false;
	}
}
