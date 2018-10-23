package com.amazing;

import java.io.File;
import java.io.IOException;

//Menu, marketplace function
public class amazing {
	protected static user active_user = null;
	
	//Data array
	private static category c[] = null;
	private static product p[] = null;
	private static product_user pu[] = null;
	
	//Main
	public static void main(String args[]){
		//Variables definitions
		int menu_1 = 0, menu_2 = 0, menu_3 = 0; //Menu
		String cache_a[]; //Cache array
		int cache_c; //Cache size
		String aux_a[]; //Auxiliar data string

		//Starting functions
		io_text.data_check();

		//Menu
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
					if (menu_2 == 0) { //Category list
						c_category();
						System.out.println("Category menu:");
						for (int i = 0; i < c.length; i++) {
							System.out.println(i+1 + "." + c[i].name());
						}
						System.out.println((c.length + 1) + ". Exit");
						menu_2 = filter.filter_i("Menu select: ", 1, c.length + 1);
					}
					else if(menu_2 == c.length + 1) { //Out of category
						menu_1 = 0;
						menu_2 = 0;
					}
					else {
						io_text.read("d_product", c[menu_2]);
						cache_c = io_text.c;
						cache_a = new String [cache_c];
						System.arraycopy(io_text.data, 0, cache_a, 0, cache_c);
						System.out.println("Product list of " + category[menu_2]);
						for (int i = 1; i == cache_c; i++) {
							System.out.println(i + "." + cache_a[i]);
						}
						System.out.println(cache_c + 1 + ". Exit");
						menu_3 = filter.filter_i("Menu select: ", 1, cache_c);
						if (menu_3 == cache_c + 1) {
							menu_2 = 0;
						}
						else {
							io_text.read_d("d_product", cache_a[menu_3]);
							cache_c = io_text.c;
							cache_a = new String [cache_c];
							System.arraycopy(io_text.data, 0, cache_a, 0, cache_c);
							System.out.println(cache_a[1] + ":");
							System.out.println("Id: " + cache_a[0]);
							System.out.println("Name: " + cache_a[1]);
							System.out.println("Category: " + cache_a[2]);
							System.out.println("Stock: " + cache_a[3]);
							filter.filter_s("\n\nPress ENTER to continue: ");
							menu_2 = 0;
						}
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
								active_user.print();
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu_2 = 0;
								break;
							case 2: //Ordered products
								for (int i = 0; i < cache_c; i += 3) { //Print the products bought
									pu[i].print();
								}
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
						System.out.println("Login in...");
						//Load products bought
						io_text.read("d_product_user", "pu_u_id=" + active_user.r_email(), 90, 1, 0); //Get data
						cache_c = io_text.data_c;
						cache_a = new String[cache_c];
						System.arraycopy(io_text.data_a, 0, cache_a, 0, cache_c); //Load ordered products
						pu = new product_user[cache_c]; //Reset the size of the product
						for (int i = 1; i <= cache_c; i++) { //Create product_user
							String aux_i[] = new String [3];
							aux_i[0] = cache_a[(i*3) - 3];
							aux_i[1] = cache_a[(i*3) - 2];
							aux_i[2] = cache_a[(i*3) - 1];
							pu[i-1] = new product_user(aux_i);
						}
						System.out.println("Successfully logged in.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					menu_1 = 0;
					break;
				case 4: //Create account
					amazing.active_user = new user();
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
	
	private static void c_category() { //Category create
		String cache_a[]; //Cache array
		int cache_c; //Cache size
		
		//Category check and create
		io_text.read("d_category", "category=", 15, 0, 0); //Get data
		cache_c = io_text.data_c; //Cache data counter
		cache_a = new String[cache_c]; //Cache data array
		System.arraycopy(io_text.data_a, 0, cache_a, 0, cache_c); //Load ordered products
		c = new category[cache_c]; //Reset the size of the product
		
		for (int i = 0; i < cache_c; i++) { //Create product_user
			c[i] = new category(cache_a[i]);
		}
	}
}