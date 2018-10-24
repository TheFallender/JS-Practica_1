package com.amazing;

//Menu, marketplace function
public class amazing {
	protected static user active_user = null;
	
	//Data array
	private static category c[] = null;
	private static product p[] = null;
	private static product pl[] = null;
	private static product_user pu[] = null;
	private static int pu_s = 0;
	
	//Dollar
	protected static float dollar = 1.14f;
	protected static boolean dollar_a = false;
	
	//Main
	public static void main(String args[]){
		//Variables definitions
		int menu[] = new int[4]; //Menu
		boolean changes_c = true, changes_pu = true, changes_p = true;
		int pl_n = 0; //Product list number
		String aux_a[] = null; //Auxiliar array
		String comp_pr[] = new String[2]; //Compare product
		comp_pr[0] = ""; //Set base
		//Starting functions
		io_text.data_check();
		
		//Menu
		while(true) {
			switch (menu[0]) {
				case 0: //Menu
					System.out.println("\nAmazing:");
					System.out.println("1. Search by category.");
					System.out.println("2. Account ");
					System.out.println("3. Sing in or sing out.");
					System.out.println("4. Create account.");
					if (!dollar_a)
						System.out.println("5. From USA? Change to dollars.");
					else
						System.out.println("5. From Europe? Change to euros.");
					System.out.println("6. Exit.");
					menu[0] = filter.filter_i("\nMenu select: ", 1, 6);
					break;
				case 1: //Search by category
					if (menu[1] == 0) { //Category list
						if (changes_c) { //Changes in the category
							c_category();
							changes_c = false;
						}
						//Menu
						System.out.println("\nCategory menu:");
						for (int i = 0; i < c.length; i++) //Print categories
							System.out.println(i+1 + ". " + c[i].r_name());
						System.out.println((c.length + 1) + ". Exit"); //Print exit
						menu[1] = filter.filter_i("\nMenu select: ", 1, c.length + 1); //Request selection
					}
					else if(menu[1] == c.length + 1) { //Exit
						menu[0] = 0;
						menu[1] = 0;
					}
					else { //Category selected
						if (menu[2] == 0) {
							pl_n=0;
							c_product();
							System.out.println("\nProduct list of " + c[menu[1] - 1].r_name() + ":");
							String aux_pr[] = new String [5];
							pl = new product[p.length];
							for (int i = 0; i < p.length; i++) { //Print products
								if (p[i].r_category().equals(c[menu[1] - 1].r_name())) {
									System.out.println((pl_n + 1) + ". " + p[i].r_name());
									aux_pr[0] = p[i].r_category();
									aux_pr[1] = "" + p[i].r_id();
									aux_pr[2] = p[i].r_name();
									aux_pr[3] = "" + p[i].r_price();
									aux_pr[4] = "" + p[i].r_stock();
									pl[pl_n] = new product (aux_pr);
									pl_n++;
								}
							}
							System.out.println((pl_n + 1) + ". Exit"); //Print exit
							menu[2] = filter.filter_i("\nMenu select: ", 1, pl_n + 1); //Request selection
						}
						else if(menu[2] == pl_n + 1) { //Exit
							menu[1] = 0;
							menu[2] = 0;
						}
						else { //Show product
							switch (menu[3]) { //Menu for product
								case 0: //Menu
									System.out.println("\n");
									pl[menu[2] - 1].print();
									System.out.println("1. Buy.");
									System.out.println("2. Compare. ");
									System.out.println("3. Exit.");
									menu[3] = filter.filter_i("\nMenu select: ", 1, 3);
									break;
								case 1: //Buy
									if (login_method.logged_in) {
										if (pl[menu[2] - 1].r_stock() > 0) {
											aux_a = new String [3];
											aux_a[0] = "" + pl[menu[2] - 1].r_id();
											aux_a[1] = active_user.r_email();
											int amount = filter.filter_i("How many do you want to buy? ", 1, 10);
											aux_a[2] = "" + amount;
											if (pl[menu[2] - 1].r_stock() - Integer.parseInt(aux_a[2]) >= 0) {
												product_user bought_product = new product_user(aux_a);
												pl[menu[2] - 1].buy(aux_a[2]);
												io_text.modify("d_product", aux_a[0], 3, amount);
												changes_pu = true;
												changes_p = true;
											}
											else {
												System.out.println("ERROR - There's not enough stock.");
												filter.filter_s("\n\nPress ENTER to continue: ");
											}
										}
										else {
											System.out.println("ERROR - There's no stock.");
											filter.filter_s("\n\nPress ENTER to continue: ");
										}
									}
									else {
										System.out.println("ERROR - You must be logged in in order to buy.");
										filter.filter_s("\n\nPress ENTER to continue: ");
									}
									menu[3] = 0;
									break;
								case 2: //Compare
									if (comp_pr[0].equals("")) {
										comp_pr[0] = pl[menu[2] - 1].compare();
									}
									else {
										comp_pr[1] = pl[menu[2] - 1].compare();
										compare_pr(comp_pr);
										comp_pr[0] = "";
										comp_pr[1] = "";
									}
									menu[2] = 0;
									menu[3] = 0;
									break;
								case 3: //Exit
									menu[2] = 0;
									menu[3] = 0;
									break;
							}
						}
					}
					break;
				case 2:	//Account
					if (login_method.logged_in) { //Log out
						switch(menu[1]) {
							case 0: //Menu
								System.out.println("\nAccount menu:");
								System.out.println("1. Account info.");
								System.out.println("2. Ordered Products.");
								System.out.println("3. Log out of the account.");
								System.out.println("4. Homepage.");
								if (active_user.r_admin()) {
									System.out.println("5. Admin settings.");
									menu[1] = filter.filter_i("\nMenu select: ", 1, 5);
								}
								else {
									menu[1] = filter.filter_i("\nMenu select: ", 1, 4);
								}
								break;
							case 1: //See info
								active_user.print();
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu[1] = 0;
								break;
							case 2: //Ordered products
								if (changes_pu) {
									c_product_user();
								}
								for (int i = 0; i < pu_s; i++) { //Print the products bought
									pu[i].print();
								}
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu[1] = 0;
								break;
							case 3: //Log out
								login_method.login_method_out();
								System.out.println("Successfully logged out.");
								filter.filter_s("\n\nPress ENTER to continue: ");
								menu[0] = 0;
								menu[1] = 0;
								break;
							case 4: //Go back
								menu[0] = 0;
								menu[1] = 0;
								break;
							case 5: //Admin
								switch (menu[2]) {
									case 0:
										System.out.println("\nAdmin menu:");
										System.out.println("1. New category.");
										System.out.println("2. New Product.");
										System.out.println("3. Exit.");
										menu[2] = filter.filter_i("\nMenu select: ", 1, 3);
										break;
									case 1:
										category new_c = new category();
										changes_c = true;
										menu[2] = 0;
										break;
									case 2:
										product new_p = new product();
										changes_p = true;
										menu[2] = 0;
										break;
									case 3:
										menu[1] = 0;
										menu[2] = 0;
										break;
								}
								break;
						}
					}
					else { //Log in
						System.out.println("ERROR - You have to be logged in to see this.");
						filter.filter_s("\n\nPress ENTER to continue: ");
						menu[0] = 0;
					}
					break;
				case 3: //Log in
					if (login_method.logged_in) { //Log out
						login_method.login_method_out();
						System.out.println("Successfully logged out.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					else { //Log in
						if (login_method.login_method_in()) {
							System.out.println("Login in...");
							System.out.println("Successfully logged in.");
						}
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					menu[0] = 0;
					break;
				case 4: //Create account
					if (login_method.logged_in) {
						System.out.println("ERROR - You are already logged in.");
						filter.filter_s("\n\nPress ENTER to continue: ");
					}
					else {
						active_user = new user();
						login_method.logged_in = true;
					}
					menu[0] = 0;
					break;
				case 5: //Change dollar
					dollar_a = !dollar_a;
					System.out.println("Changed the currency.");
					filter.filter_s("\n\nPress ENTER to continue: ");
					menu[0] = 0;
					break;
				case 6: //Close program
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
		
		//Load categories
		io_text.read("d_category", "", 15, false); //Get data
		cache_c = io_text.data_c; //Cache data counter
		cache_a = new String[cache_c]; //Cache data array
		System.arraycopy(io_text.data_a, 0, cache_a, 0, cache_c); //Load categories
		c = new category[cache_c]; //Reset the size of the category
		
		for (int i = 0; i < cache_c; i++) { //Create categories
			c[i] = new category(cache_a[i]);
		}
	}
	
	private static void c_product () { //Product create
		String aux_i[] = new String[5];
		io_text.read("d_product", "", 30, true); //Get data
		p = new product[io_text.data_c/5]; //Reset the size of the product
		
		//Product list
		for(int i = 1; i <= io_text.data_c/5; i++) {
			aux_i[0] = io_text.data_a[(i*5) - 5];
			aux_i[1] = io_text.data_a[(i*5) - 4];
			aux_i[2] = io_text.data_a[(i*5) - 3];
			aux_i[3] = io_text.data_a[(i*5) - 2];
			aux_i[4] = io_text.data_a[(i*5) - 1];
			p[i-1] = new product(aux_i);
		}
	}
	
	private static void c_product_user() { //Product user create
		String aux_i[] = new String[5];
		io_text.read("d_product_user", "", 900, false); //Get data
		pu = new product_user[io_text.data_c/3]; //Reset the size of the product
		pu_s = 0;
		int size = 0;
		//Product list
		for(int i = 1; i <= io_text.data_c/3; i++) {
			aux_i[0] = io_text.data_a[(i*3) - 3];
			aux_i[1] = io_text.data_a[(i*3) - 2];
			aux_i[2] = io_text.data_a[(i*3) - 1];
			if(aux_i[1].equals(amazing.active_user.r_email())) {
				pu[pu_s] = new product_user(aux_i, false);
				pu_s++;
			}
		}
	}
	
	private static void compare_pr (String data[]) {
		String[] pr_1 = data[0].split("/");
		String[] pr_2 = data[1].split("/");
		System.out.println(pr_1[2] + "			" + pr_2[2]);
		System.out.println(pr_1[1] + "			" + pr_2[1]);
		System.out.println(pr_1[0] + "			" + pr_2[0]);
		System.out.println(pr_1[3] + "			" + pr_2[3]);
		System.out.println(pr_1[4] + "			" + pr_2[4]);
	}
}