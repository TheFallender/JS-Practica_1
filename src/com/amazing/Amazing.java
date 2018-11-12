package com.amazing;

//Menu, marketplace function
public class Amazing {
	//Test variable
	public static boolean test = false; //Change this to start the test system
	
	//User data
	protected static User active_user = null;
	
	//Data array
	private static Category c[] = null;
	private static Product p[] = null;
	private static Product pl[] = null;
	private static Product_user pu[] = null;
	private static int pu_s = 0;
	
	//Dollar
	protected static float eur_dollar;
	protected static boolean dollar_a = false;
	
	//Main
	public static void main(String args[]){
		if (!test) { //Default enviroment
			//Variables definitions
			int menu[] = new int[4]; //Menu
			boolean changes_c = true, changes_pu = true, changes_p = true;
			int pl_n = 0; //Product list number
			String aux_a[] = null; //Auxiliar array
			String comp_pr[] = new String[2]; //Compare product
			comp_pr[0] = ""; //Set base
			
			//Starting functions
			IO.data_check();
			eur_dollar = Conversor.factor("eur", "usd");
			
			//Menu
			while(true) {
				switch (menu[0]) {
					case 0: //Menu
						System.out.println("\nAmazing:");
						System.out.println("1. Search by category.");
						System.out.println("2. Account ");
						if (Login_method.logged_in)
							System.out.println("3. Sing out.");
						else
							System.out.println("3. Sing in.");
						System.out.println("4. Create account.");
						if (!dollar_a)
							System.out.println("5. From USA? Change to dollars.");
						else
							System.out.println("5. From Europe? Change to euros.");
						System.out.println("6. Exit.");
						menu[0] = Filter.filter_i("\nMenu select: ", 1, 6);
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
							menu[1] = Filter.filter_i("\nMenu select: ", 1, c.length + 1); //Request selection
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
								pl = new Product[p.length];
								for (int i = 0; i < p.length; i++) { //Print products
									if (p[i].r_category().equals(c[menu[1] - 1].r_name())) {
										System.out.println((pl_n + 1) + ". " + p[i].r_name());
										aux_pr[0] = "" + p[i].r_id();
										aux_pr[1] = p[i].r_name();
										aux_pr[2] = p[i].r_category();
										aux_pr[3] = "" + p[i].r_price();
										aux_pr[4] = "" + p[i].r_stock();
										pl[pl_n] = new Product (aux_pr);
										pl_n++;
									}
								}
								System.out.println((pl_n + 1) + ". Exit"); //Print exit
								menu[2] = Filter.filter_i("\nMenu select: ", 1, pl_n + 1); //Request selection
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
										menu[3] = Filter.filter_i("\nMenu select: ", 1, 3);
										break;
									case 1: //Buy
										if (Login_method.logged_in) {
											if (pl[menu[2] - 1].r_stock() > 0) {
												aux_a = new String [3];
												aux_a[0] = "" + pl[menu[2] - 1].r_id();
												aux_a[1] = active_user.r_email();
												int amount = Filter.filter_i("How many do you want to buy? ", 1, 10);
												aux_a[2] = "" + amount;
												if (pl[menu[2] - 1].r_stock() - Integer.parseInt(aux_a[2]) >= 0) {
													Product_user bought_product = new Product_user(aux_a);
													pl[menu[2] - 1].buy(aux_a[2]);
													IO.modify("d_product", "p_id=" + aux_a[0], 3, amount);
													changes_pu = true;
													changes_p = true;
												}
												else {
													System.out.println("ERROR - There's not enough stock.");
													Filter.filter_s("\n\nPress ENTER to continue: ");
												}
											}
											else {
												System.out.println("ERROR - There's no stock.");
												Filter.filter_s("\n\nPress ENTER to continue: ");
											}
										}
										else {
											System.out.println("ERROR - You must be logged in in order to buy.");
											Filter.filter_s("\n\nPress ENTER to continue: ");
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
						if (Login_method.logged_in) { //Log out
							switch(menu[1]) {
								case 0: //Menu
									System.out.println("\nAccount menu:");
									System.out.println("1. Account info.");
									System.out.println("2. Ordered Products.");
									System.out.println("3. Log out of the account.");
									System.out.println("4. Homepage.");
									if (active_user.r_admin()) {
										System.out.println("5. Admin settings.");
										menu[1] = Filter.filter_i("\nMenu select: ", 1, 5);
									}
									else {
										menu[1] = Filter.filter_i("\nMenu select: ", 1, 4);
									}
									break;
								case 1: //See info
									active_user.print();
									Filter.filter_s("\n\nPress ENTER to continue: ");
									menu[1] = 0;
									break;
								case 2: //Ordered products
									if (changes_pu) {
										c_product_user();
									}
									for (int i = 0; i < pu_s; i++) { //Print the products bought
										pu[i].print();
									}
									Filter.filter_s("\n\nPress ENTER to continue: ");
									menu[1] = 0;
									break;
								case 3: //Log out
									Login_method.login_method_out();
									System.out.println("Successfully logged out.");
									Filter.filter_s("\n\nPress ENTER to continue: ");
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
											menu[2] = Filter.filter_i("\nMenu select: ", 1, 3);
											break;
										case 1:
											Category new_c = new Category();
											changes_c = true;
											menu[2] = 0;
											break;
										case 2:
											Product new_p = new Product();
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
							Filter.filter_s("\n\nPress ENTER to continue: ");
							menu[0] = 0;
						}
						break;
					case 3: //Log in
						if (Login_method.logged_in) { //Log out
							Login_method.login_method_out();
							System.out.println("Successfully logged out.");
							Filter.filter_s("\n\nPress ENTER to continue: ");
						}
						else { //Log in
							if (Login_method.login_method_in()) {
								System.out.println("Login in...");
								System.out.println("Successfully logged in.");
							}
							Filter.filter_s("\n\nPress ENTER to continue: ");
						}
						menu[0] = 0;
						break;
					case 4: //Create account
						if (Login_method.logged_in) {
							System.out.println("ERROR - You are already logged in.");
							Filter.filter_s("\n\nPress ENTER to continue: ");
						}
						else {
							active_user = new User();
							Login_method.logged_in = true;
						}
						menu[0] = 0;
						break;
					case 5: //Change dollar
						dollar_a = !dollar_a;
						System.out.println("Changed the currency.");
						Filter.filter_s("\n\nPress ENTER to continue: ");
						menu[0] = 0;
						break;
					case 6: //Close program
						Filter.scan.close();
						if (Login_method.logged_in) { //Log out
							Login_method.login_method_out();
						}
						return;
				}
			}
		}
		else { //Test enviroment
			Test t = new Test();
			t.test();
		}
	}
	
	protected static void c_category() { //Category create
		String cache_a[]; //Cache array
		int cache_c; //Cache size
		
		//Load categories
		IO.read("d_category", "", 15, false); //Get data
		cache_c = IO.data_c; //Cache data counter
		cache_a = new String[cache_c]; //Cache data array
		System.arraycopy(IO.data_a, 0, cache_a, 0, cache_c); //Load categories
		c = new Category[cache_c]; //Reset the size of the category
		
		for (int i = 0; i < cache_c; i++) { //Create categories
			c[i] = new Category(cache_a[i]);
		}
	}
	
	protected static void c_product () { //Product create
		String aux_i[] = new String[5];
		IO.read("d_product", "", 100, true); //Get data
		p = new Product[IO.data_c/5]; //Reset the size of the product
		
		//Product list
		for(int i = 1; i <= IO.data_c/5; i++) {
			aux_i[0] = IO.data_a[(i*5) - 5]; //Id
			aux_i[1] = IO.data_a[(i*5) - 4]; //Name
			aux_i[2] = IO.data_a[(i*5) - 3]; //Category
			aux_i[3] = IO.data_a[(i*5) - 2]; //Price
			aux_i[4] = IO.data_a[(i*5) - 1]; //Stock
			p[i-1] = new Product(aux_i);
		}
	}
	
	protected static void c_product_user() { //Product user create
		String aux_i[] = new String[5];
		IO.read("d_product_user", "", 900, false); //Get data
		pu = new Product_user[IO.data_c/3]; //Reset the size of the product
		pu_s = 0;
		int size = 0;
		//Product user list
		for(int i = 1; i <= IO.data_c/3; i++) {
			aux_i[0] = IO.data_a[(i*3) - 3]; //Email
			aux_i[1] = IO.data_a[(i*3) - 2]; //Id
			aux_i[2] = IO.data_a[(i*3) - 1]; //Ordered
			if(aux_i[0].equals(Amazing.active_user.r_email())) {
				pu[pu_s] = new Product_user(aux_i, false);
				pu_s++;
			}
		}
	}
	
	private static void compare_pr (String data[]) {
		String[] pr_1 = data[0].split("/");
		String[] pr_2 = data[1].split("/");
		System.out.println("Product name:	" + pr_1[2] + "			" + pr_2[2]);
		System.out.println("Product id:	" + pr_1[1] + "			" + pr_2[1]);
		System.out.println("Category:	" + pr_1[0] + "			" + pr_2[0]);
		System.out.println("Price:		" + pr_1[3] + "			" + pr_2[3]);
		System.out.println("Stock:		" + pr_1[4] + "			" + pr_2[4]);
	}
}