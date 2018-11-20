package com.amazing;


//Menu, marketplace class
public class Amazing {
	//Test variable
	protected static boolean test = false; //Change this to start the test system
	
	//User data
	protected static User active_user = null; //The active user at the moment
	
	//Data array
	private static Category[] c = null; //Category list
	private static Product[] p = null; //Product list
	private static Product[] pl = null; //Product list array (list of the actual category)
	private static Product_user[] pu = null; //Product user list
	
	//Dollar
	protected static float eur_dollar; //Euro dollar ratio
	protected static boolean dollar_a = false; //Dollar active in the menu
	
	//Main
	public static void main(String[] args){ //Main code
		if (!test) { //Default enviroment
			//Variables definitions
				//Menu
				int[] menu = new int[4];
				
				//Euro Dollar ratio
				eur_dollar = Converter.factor("eur", "usd"); //Uses the converter and sets the ratio
				
				//Boolean changes
				boolean changes_c = true; //Changes in the category
				boolean changes_pu = true; //Changes in the product user
				
				//Product
				int pl_n = 0; //Product list number
				String[] comp_pr = new String[]{"", ""}; //Compare product array

			//Starting functions
			IO.data_check(); //Checks the data files are there, if not, it create them 

			
			//Menu
			while(true) { //Loop of the menu
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
							menu[0] = 0; //Resets the menu first value to the base menu
							menu[1] = 0;
						}
						else { //Category selected
							if (menu[2] == 0) {
								pl_n=0;
								c_product();
								System.out.println("\nProduct list of " + c[menu[1] - 1].r_name() + ":");
								String[] pr_a = new String [5];
								pl = new Product[p.length];
								for (int i = 0; i < p.length; i++) { //Print products
									if (p[i].r_category().equals(c[menu[1] - 1].r_name())) {
										System.out.println((pl_n + 1) + ". " + p[i].r_name());
										pr_a[0] = "" + p[i].r_id();
										pr_a[1] = p[i].r_name();
										pr_a[2] = p[i].r_category();
										pr_a[3] = "" + p[i].r_price();
										pr_a[4] = "" + p[i].r_stock();
										pl[pl_n] = new Product (pr_a);
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
												String[] pu_a = new String [3]; //Product user from this order
												pu_a[0] = "" + pl[menu[2] - 1].r_id();
												pu_a[1] = active_user.r_email();
												int amount = Filter.filter_i("Number of items to order: ", 1, 10);
												pu_a[2] = "" + amount;
												if (pl[menu[2] - 1].r_stock() - Integer.parseInt(pu_a[2]) >= 0) {
													Product_user bought_product = new Product_user(pu_a);
													pl[menu[2] - 1].buy(pu_a[2]);
													String[] data = new String[2];
													data[0] = "p_id=" + pu_a[0];
													data[1] = "p_stock=" + (pl[menu[2] - 1].r_stock());
													IO.modify("d_product", data, 4);
													changes_pu = true;
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
									default: //Default case
										menu[3] = 0; //Reset the menu
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
										changes_pu = false;
									}
									for (int i = 0; i < pu.length; i++) { //Print the products bought
										pu[i].print();
									}
									Filter.filter_s("\n\nPress ENTER to continue: ");
									menu[1] = 0;
									break;
								case 3: //Log out
									Login_method.login_method_out();
									System.out.println("Successfully logged out.");
									Filter.filter_s("\n\nPress ENTER to continue: ");
									menu[0] = 0; //Resets the menu first value to the base menu
									menu[1] = 0;
									break;
								case 4: //Go back
									menu[0] = 0; //Resets the menu first value to the base menu
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
											menu[2] = 0;
											break;
										case 3:
											menu[1] = 0;
											menu[2] = 0;
											break;
										default: //Default case
											menu[2] = 0;
											break;
									}
									break;
								default: //Default case
									for (int i = 1; i < menu.length; i++) //Resets every menu value to 0
										menu[i] = 0;
									break;
							}
						}
						else { //Log in
							System.out.println("ERROR - You have to be logged in to see this.");
							Filter.filter_s("\n\nPress ENTER to continue: ");
							menu[0] = 0; //Resets the menu first value to the base menu
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
						menu[0] = 0; //Resets the menu first value to the base menu
						break;
					case 4: //Create account
						if (Login_method.logged_in)
							System.out.println("ERROR - You are already logged in.");
						else {
							System.out.println("Successfully created an account.");
							active_user = new User();
							Login_method.logged_in = true;
						}
						Filter.filter_s("\n\nPress ENTER to continue: ");
						menu[0] = 0; //Resets the menu first value to the base menu
						break;
						
					case 5: //Change dollar
						dollar_a = !dollar_a; //Changes value of the boolean to the opposite one
						
						System.out.println("Changed the currency."); //Prints the info of the change
						Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
						
						menu[0] = 0; //Resets the menu first value to the base menu
						break;
						
					case 6: //Close program
						Filter.scan.close(); //Closes the scan
						
						if (Login_method.logged_in) //In case that the user is logged in, it logs him out
							Login_method.login_method_out();
						
						return; //Closes the program by getting out of the main function
					default: //Default case
						for (int i = 0; i < menu.length; i++) //Resets every menu value to 0
							menu[i] = 0;
						break;
				}
			}
		}
		else { //Test enviroment
			Test t = new Test(); //Creates a new test object
			t.test(); //Tests the object
		}
	}
	
	protected static void c_category() { //Category create
		//Get the data from the file
		IO.read("d_category", "", 15, false); //Get data
		
		//Category object
		c = new Category[IO.data_c]; //Reset the size of the category
		
		//Get Category list
		for (int i = 0; i < IO.data_c; i++) //Create categories
			c[i] = new Category(IO.data_a[i]); //Sets the new category
	}
	
	protected static void c_product () { //Product list
		//Get the data from the file
		IO.read("d_product", "", 100, true); //Get data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to use
		
		//Product object
		p = new Product[IO.data_c/5]; //Reset the size of the product
		
		//Get Product list
		for(int i = 1; i <= IO.data_c/5; i++) { //Write every product in the list
			aux_i[0] = IO.data_a[(i*5) - 5]; //Id
			aux_i[1] = IO.data_a[(i*5) - 4]; //Name
			aux_i[2] = IO.data_a[(i*5) - 3]; //Category
			aux_i[3] = IO.data_a[(i*5) - 2]; //Price
			aux_i[4] = IO.data_a[(i*5) - 1]; //Stock
			
			//Create a product
			p[i-1] = new Product(aux_i); //Sets the new product
		}
	}
	
	protected static void c_product_user() { //Product User list
		//Get the data from the file
		IO.read("d_product_user", "", 900, false); //Get data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to check
		
		//Product User object
		pu = new Product_user[IO.data_c/3]; //Reset the size of the product
		int pu_s = 0; //Size of the product user
		
		//Get Product User list
		for(int i = 1; i <= IO.data_c/3; i++) { //Search between the created list
			aux_i[0] = IO.data_a[(i*3) - 3]; //Email
			if(aux_i[0].equals(Amazing.active_user.r_email())) { //Checks if the product user equals the active user email
				//Now checks for the rest of the data
				aux_i[1] = IO.data_a[(i*3) - 2]; //Id
				aux_i[2] = IO.data_a[(i*3) - 1]; //Ordered
				
				//Create a product user
				pu[pu_s] = new Product_user(aux_i, false); //Sets the new product user
				pu_s++; //Increases the number of product user count
			}
		}
	}
	
	private static void compare_pr (String[] data) { //Compares two products
		//Products to pass
		String[] pr_1 = data[0].split("/"); //Splits the first data
		String[] pr_2 = data[1].split("/"); //Splits the second data
		
		//Print comparison
		System.out.println("Product name:	" + pr_1[2] + "			" + pr_2[2]);
		System.out.println("Product id:	" + pr_1[1] + "			" + pr_2[1]);
		System.out.println("Category:	" + pr_1[0] + "			" + pr_2[0]);
		System.out.println("Price:		" + pr_1[3] + "			" + pr_2[3]);
		System.out.println("Stock:		" + pr_1[4] + "			" + pr_2[4]);
	}
}