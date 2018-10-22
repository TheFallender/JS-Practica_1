package com.amazing;

import java.io.File;
import java.io.IOException;

//Menu, marketplace function
public class amazing {
	protected static user active_user = null;
	public static void main(String args[]) throws IOException {
		File file = new File("src/Data/Initialized");
		boolean exist = file.exists();
		if (!exist) {
			file = new File("src/Data/Initialized");
			file.createNewFile();
			file = new File("src/Data/d_category");
			file.createNewFile();
			file = new File("src/Data/d_user");
			file.createNewFile();
			file = new File("src/Data/d_pu");
			file.createNewFile();
			file = new File("src/Data/d_product");
			file.createNewFile();
		}
		int menu_1 = 0, menu_2 = 0, menu_3 = 0;
		String category[] = new String [5];
		io_text.read_every("d_category", "");
		System.arraycopy(io_text.data, 0, category, 0, 5);
		String cache_a[];
		int cache_c;
		while(true) {
			switch (menu_1) {
				case 0: //Menu
					System.out.println("Amazing:");
					System.out.println("1. Search by category.");
					System.out.println("2. Account ");
					System.out.println("3. Sing in or sing out.");
					System.out.println("4. Create account");
					System.out.println("5. Exit.");
					menu_1 = filter.filter_i("Menu select: ", 1, 5);
					break;
				case 1: //Search by category
					if (menu_2 == 0) {
						System.out.println("Category menu:");
						for (int i = 0; i < 5; i++) {
							System.out.println(i + "." + category[i]);
						}
						menu_2 = filter.filter_i("Menu select: ", 1, 5);
					}
					else {
						io_text.read_every("d_product", category[menu_2]);
						cache_c = io_text.c;
						cache_a = new String [cache_c];
						System.arraycopy(io_text.data, 0, cache_a, 0, cache_c);
						System.out.println("Product list of " + category[menu_2]);
						menu_3 = filter.filter_i("Menu select: ", 1, 5);
					}
					break;
				case 2:	//Account
					if (login_method.logged_in) { //Log out
						switch(menu_2) {
							case 0: //Menu
								System.out.println("Account menu:");
								System.out.println("1. Account info.");
								System.out.println("2. Ordered Products.");
								System.out.println("3. Log out of the account.");
								System.out.println("4. Homepage.");
								menu_1 = filter.filter_i("Menu select: ", 1, 4);
								break;
							case 1: //See info
								String data[] = new String [4];
								//data = io_text.read_d("d_user",active_user.r_email());
								try {
									System.out.println("Email: " + encrypter.decrypt(data[0]));
									System.out.println("Password: " + encrypter.decrypt(data[1]));
									System.out.println("Login: " + data[2]);
									System.out.println("Last login" + data[3]);
								} catch (Exception e) {
									throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");
								}
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu_2 = 0;
								break;
							case 2: //Ordered products
								
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu_2 = 0;
								break;
							case 3: //Log out
								login_method.login_method_out();
								System.out.println("Successfully logged out.");
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu_1 = 0;
								menu_2 = 0;
								break;
							case 4: //Go back
								menu_1 = 0;
								menu_2 = 0;
								break;
						}
						menu_1 = 0;
					}
					else { //Log in
						System.out.println("ERROR - You have to be logged in to see this.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					break;
				case 3: //Log in
					if (login_method.logged_in) { //Log out
						login_method.login_method_out();
						System.out.println("Successfully logged out.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					else { //Log in
						login_method.login_method_in();
						System.out.println("Successfully logged in.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					menu_1 = 0;
					break;
				case 4: //Create account
					amazing.active_user = new user();
					io_text.write("d_user");
					login_method.logged_in = true;
					menu_1 = 0;
					break;
				case 5: //Close program
					filter.scan.close();
					if (login_method.logged_in) { //Log out
						login_method.login_method_out();
					}
					return;
			}
		}
	}
}