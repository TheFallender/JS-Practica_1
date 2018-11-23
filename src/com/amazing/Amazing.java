package com.amazing;


//Menu, marketplace class
public class Amazing {
	//Test variable
	protected static boolean test = false; 		//Change this to start the test system
	
	//User data
	protected static User active_user = null; 	//The active user at the moment
	
	//Data array
	private static Category[] c = null; 		//Category list
	private static Product[] p = null; 			//Product list
	private static Product[] pl = null; 		//Product list array (list of the actual category)
	private static Product_user[] pu = null; 	//Product user list
	
	//Dollar
	protected static float eur_dollar; 			//Euro dollar ratio
	protected static boolean dollar_a = false; 	//Dollar active in the menu
	
	//Main
	public static void main(String[] args){ //Main code
		if (!test) { //Default enviroment
			//Variables definitions
				//Menu
				int[] menu = new int[4]; //Menu int values
				
				//Euro Dollar ratio
				eur_dollar = Converter.factor("eur", "usd"); //Uses the converter and sets the ratio
				
				//Boolean changes
				boolean changes_c = true; 		//Changes in the category
				boolean changes_pu = true; 		//Changes in the product user
				
				//Product
				String[] comp_pr = new String[]{null, ""}; //Compare product array

			//Starting functions
			IO.data_check(); //Checks the data files are there, if not, it create them 

			
			//Menu
			while(true) { //Loop of the menu
				switch (menu[0]) { //Checks selection within Main menu
					case 0: //Main menu
						//Menu
						System.out.println("\nAmazing:"); 								//Amazing
						System.out.println("1. Search by category.");					//1. Search by Category
						System.out.println("2. Account ");								//2. Account
						if (active_user != null) //User logged in
							System.out.println("3. Sing out.");							//3. Sing out.
						else //User not logged
							System.out.println("3. Sing in.");							//3. Sing in.
						System.out.println("4. Create account.");						//4. Create account.
						if (!dollar_a)
							System.out.println("5. From USA? Change to dollars.");		//5. From USA? Change to dollars.
						else
							System.out.println("5. From Europe? Change to euros.");		//5. From Europe? Change to euros.
						System.out.println("6. Exit.");									//6. Exit.
						
						//Menu selection
						menu[0] = Filter.filter_i("\nMenu select: ", 1, 6); //Request selection
						break;
						
						
					case 1: //Search by category
						if (menu[1] == 0) { 		//Category list
							if (changes_c) { 			//Changes in the category
								c_category(); 				//Create the Category array
								changes_c = false; 			//Changes in the Category disabled
							}
							
							//Menu
							System.out.println("\nCategory menu:");					//Prints the Category
							for (int i = 0; i < c.length; i++) 						//Print categories
								System.out.println(i+1 + ". " + c[i].r_name()); 		//Print the name of the Category with a dot in front of it 
							System.out.println((c.length + 1) + ". Exit"); 			//Print exit
							
							//Menu selection
							menu[1] = Filter.filter_i("\nMenu select: ", 1, c.length + 1); //Request selection
						}
						else if(menu[1] == c.length + 1) { 	//Exit
							menu[0] = 0; 						//Resets Main menu
							menu[1] = 0; 						//Reset Category menu
						}
						else { //Category selected
							if (menu[2] == 0) { 		//Product Menu
															//Product list
								c_product(); 					//Create the base product list
								pl = new Product[p.length]; 	//Resets the product list based on the product length
								
								//Menu print
								System.out.println("\nProduct list of " + c[menu[1] - 1].r_name() + ":"); 	//Print the info of the Category as a product list
								for (int i = 0, pl_s = 0; i < p.length; i++) 								//Prints products
									if (p[i].r_category().equals(c[menu[1] - 1].r_name())) { 					//The product category name is equal to the category name
										//Print
										System.out.println((pl_s + 1) + ". " + p[i].r_name());					//Print product name
										
										//Add the product to the list
										String[] pr_a = new String [5]; //Product array to use for the creation of the products
										pr_a[0] = "" + p[i].r_id(); 	//ID
										pr_a[1] = p[i].r_name();		//Name
										pr_a[2] = p[i].r_category();	//Category
										pr_a[3] = "" + p[i].r_price();	//Price
										pr_a[4] = "" + p[i].r_stock();	//Stock
										pl[pl_s] = new Product (pr_a); //Create the product based on pr_a
										
										//Increase product list size
										pl_s++;
									}
								System.out.println((pl.length + 1) + ". Exit"); //Print exit
								
								//Menu selection
								menu[2] = Filter.filter_i("\nMenu select: ", 1, pl.length + 1); //Request selection
							}
							else if(menu[2] == pl.length + 1) { //Exit
								menu[1] = 0; 						//Reset Category menu
								menu[2] = 0; 						//Reset Product list menu
							}
							else { //Show product
								Product pr_buy = pl[menu[2] -1]; //Set a temp product to make things clean
								switch (menu[3]) { //Checks selection within Product menu
									case 0: //Product Menu
										//Print
										System.out.println("\n"); 				//Print newline for print of the product
										pr_buy.print(); 						//Print the selected product
										
										//Menu print
										System.out.println("1. Buy.");			//1. Buy.
										System.out.println("2. Compare. ");		//2. Compare.
										System.out.println("3. Exit.");			//3. Exit.
										
										//Menu selection
										menu[3] = Filter.filter_i("\nMenu select: ", 1, 3); //Request menu selection
										break;
										
										
									case 1: //Buy
										//Check if the user is logged in
										if (active_user != null) { //The user is logged in
																		//Check if there is stock
											if (pr_buy.r_stock() > 0) { //There is some stock
												int amount = Filter.filter_i("Number of items to order: ", 1, 10); //Request the number of items to order
												
												//Check if the stock is enough
												if (pr_buy.r_stock() - amount >= 0) { //There is enough stock for the order
													//Product user array
													String[] pu_a = new String [5]; 	//Product user from this order
													pu_a[0] = active_user.r_email(); 		//User that is going to buy the order
													pu_a[1] = "" + pr_buy.r_id(); 			//Product ID of the ordered item
													pu_a[2] = pr_buy.r_name();				//Product Name of the ordered item
													pu_a[3] = "" + pr_buy.r_price(); 		//Price at the moment of the order
													pu_a[4] = "" + amount; 					//Amount on the order
													
													//Product User
													Product_user bought_product = new Product_user(pu_a); 	//New product user
													bought_product.save(); 									//Saves the data to the file
													
													//Changes
													changes_pu = true; //Set that there were changes in the Product User
													
													//Product Update
													pr_buy.buy(amount); 		//Decrease the value by the amount selected
													pl[menu[2] - 1] = pr_buy; 	//Update changes to the selected value
													
													//Product Update File
													String[] data = new String[] {"p_id=" + pr_buy.r_id(), "p_stock=" + pr_buy.r_stock()}; 	//Data array, first the ID to search, then the stock.
													IO.modify("d_product", data, 4); 														//Modify Product with the new data
													
													//Print info
													System.out.println("Successfully bought the product"); //Print that the product was bought
												}
												else //There is not enough stock
													System.out.println("ERROR - There's not enough stock for your order."); //Report that the product doesn't have enough stock
											}
											else //There is no stock
												System.out.println("ERROR - Product out of stock."); //Report that the product is out of stock
										}
										else //User must be logged in to buy the product
											System.out.println("ERROR - You must be logged in in order to buy."); //Report that the user must be logged in to buy
										
										
										Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
										
										menu[3] = 0; //Back to Product basic menu
										break;
										
										
									case 2: //Compare
										//Check if there is a product selected
										if (comp_pr[0] == null) //No product selected
											comp_pr[0] = pl[menu[2] - 1].compare(); //First product to compare
										else { //One product selected, check the other one
											//Set product
											comp_pr[1] = pl[menu[2] - 1].compare(); //Second product to compare
											
											//Compare function
											compare_pr(comp_pr); //Compare both products
											
											//Reset product
											comp_pr[0] = null; //Reset to null the first product (second one won't matter)
										}
										
										menu[2] = 0; //Reset Product list menu
										menu[3] = 0; //Reset Product menu
										break;
										
										
									case 3: //Exit
										menu[2] = 0; //Reset Product list menu
										menu[3] = 0; //Reset Product menu
										break;
										
										
									default: //Default case
										menu[0] = -1; //Sends menu to an invalid state to reset it
										break;
								}
							}
						}
						break;
						
						
					case 2:	//Account
						if (active_user != null) { //User is logged in
							switch(menu[1]) { //Checks selection within Account menu
								case 0: //Account Menu
									//Print menu
									System.out.println("\nAccount menu:");				//Account Menu:
									System.out.println("1. Account Info.");				//1. Account Info.
									System.out.println("2. Ordered Products.");			//2. Ordered Products.
									System.out.println("3. Log out of the account.");	//3. Log out.
									System.out.println("4. Homepage.");					//4. Homepage.
									
									//Menu selection
									if (active_user.r_admin()) { //Checks if the user is admin
										System.out.println("5. Admin settings.");		//5. Admin
										menu[1] = Filter.filter_i("\nMenu select: ", 1, 5); //Request menu selection
									}
									else
										menu[1] = Filter.filter_i("\nMenu select: ", 1, 4); //Request menu selection
									break;
									
									
								case 1: //See User Info
									active_user.print(); //Print info of the user
									
									Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
									
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 2: //Ordered products
									//Check for changes in the product user
									if (changes_pu) { 		//Changes were made
										c_product_user(); 		//Recreates the product user list
										changes_pu = false; 	//Disable changes made in product user
									}
									
									//Print
									for (int i = 0; i < pu.length; i++) //Print all the orders
										pu[i].print(); 						//Prints the product user
									
									Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
									
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 3: //Log out
									Login_method.login_method_out(); 					//Log out method
									System.out.println("Successfully logged out."); 	//Prints that the user logged out
									Filter.filter_s("\n\nPress ENTER to continue: "); 	//Waits for the user input
									
									menu[0] = 0; //Resets Main menu
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 4: //Exit account menu
									menu[0] = 0; //Resets Main menu
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 5: //Admin
									switch (menu[2]) { //Checks selection within Admin menu
										case 0: //Admin Menu
											//Print menu
											System.out.println("\nAdmin menu:"); 	//Admin menu:
											System.out.println("1. New Category."); //1. New Category.
											System.out.println("2. New Product."); 	//2. New Product.
											System.out.println("3. Exit."); 		//3. Exit.
											
											//Menu selection
											menu[2] = Filter.filter_i("\nMenu select: ", 1, 3); //Request menu selection
											break;
											
											
										case 1: //Create category
											Category new_c = new Category(); 	//Create category
											new_c.save(); 						//Saves the data on the file
											changes_c = true; 					//Category was changed
											menu[2] = 0; 						//Back to Admin basic menu
											break;
											
											
										case 2: //Create Product
											Product new_p = new Product(); 		//Create new product
											new_p.save();						//Saves the data on the file
											menu[2] = 0; 						//Back to Admin basic menu
											break;
											
											
										case 3: //Exit
											menu[1] = 0; //Resets Account menu
											menu[2] = 0; //Resets Admin menu
											break;
											
											
										default: //Default case
											menu[0] = -1; //Sends menu to an invalid state to reset it
											break;
									}
									break;
									
									
								default: //Default case
									menu[0] = -1; //Sends menu to an invalid state to reset it
									break;
							}
						}
						else { //User not logged in
							System.out.println("ERROR - You have to be logged in to see this."); //Report error, user should be logged in to see this.
							Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
							
							menu[0] = 0; //Resets Main menu
						}
						break;
						
						
					case 3: //Log in/out
						//Checks if it should log in or out
						if (active_user != null) { //Log out
							Login_method.login_method_out(); 					//Logs out the user
							System.out.println("Successfully logged out."); 	//Prints that the user logged out
							Filter.filter_s("\n\nPress ENTER to continue: "); 	//Waits for the user input
						}
						else { //Log in
							if (Login_method.login_method_in()) //Check if the method successfully creates the user
								System.out.println("Successfully logged in."); 	//Prints that the user logged in
							Filter.filter_s("\n\nPress ENTER to continue: "); 	//Waits for the user input
						}
						
						menu[0] = 0; //Resets Main menu
						break;
						
						
					case 4: //Create account
						//Check if the user is logged in
						if (active_user != null)
							System.out.println("ERROR - You are already logged in."); //Report error as the user is logged in
						else {
							active_user = new User(); 								//Creates a new user
							active_user.save();										//Save the data on the file
							System.out.println("Successfully created an account."); //Print that the user was created successfully
						}
						
						Filter.filter_s("\n\nPress ENTER to continue: "); //Waits for the user input
						
						menu[0] = 0; //Resets Main menu
						break;
						
						
					case 5: //Change dollar
						dollar_a = !dollar_a; //Changes value of the boolean to the opposite one
						
						System.out.println("Changed the currency."); 		//Prints the change in the currency
						Filter.filter_s("\n\nPress ENTER to continue: "); 	//Waits for the user input
						
						menu[0] = 0; //Resets Main menu
						break;
						
						
					case 6: //Close program
						Filter.scan.close(); //Closes the scan
						
						if (active_user != null) //In case that the user is logged in, it logs him out
							Login_method.login_method_out();
						return; //Closes the program by getting out of the main function
						
						
					default: //Default case
						for (int i = 0; i < menu.length; i++) //Resets every menu value to 0
							menu[i] = 0; //Reset menu on the i array
						System.out.println("ERROR - We had some trouble doing your request. Please, try again."); //Report outside of menu selection
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
		c = new Category[IO.data_a.length]; //Reset the size of the category
		
		//Get Category list
		for (int i = 0; i < IO.data_a.length; i++) 	//Create categories
			c[i] = new Category(IO.data_a[i]); 		//Sets the new category
	}
	
	protected static void c_product () { //Product list
		//Get the data from the file
		IO.read("d_product", "", 100, true); //Get data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to use
		
		//Product object
		p = new Product[IO.data_a.length/5]; //Reset the size of the product
		
		//Get Product list
		for(int i = 1; i <= IO.data_a.length/5; i++) { 	//Write every product in the list
			aux_i[0] = IO.data_a[(i*5) - 5]; 			//Id
			aux_i[1] = IO.data_a[(i*5) - 4]; 			//Name
			aux_i[2] = IO.data_a[(i*5) - 3]; 			//Category
			aux_i[3] = IO.data_a[(i*5) - 2]; 			//Price
			aux_i[4] = IO.data_a[(i*5) - 1]; 			//Stock
			
			//Create a product
			p[i-1] = new Product(aux_i); //Sets the new product
		}
	}
	
	protected static void c_product_user() { //Product User list
		//Get the data from the file
		IO.read("d_product_user", "", 1200, false); //Get data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to check
		
		//Product User object
		pu = new Product_user[IO.data_a.length/5]; //Reset the size of the product
		int pu_s = 0; //Size of the product user
		
		//Get Product User list
		for(int i = 1; i <= IO.data_a.length/5; i++) { //Search between the created list
			aux_i[0] = IO.data_a[(i*5) - 5]; 						//User Email
			if(aux_i[0].equals(Amazing.active_user.r_email())) { //Checks if the product user equals the active user email
				//Now checks for the rest of the data
				aux_i[1] = IO.data_a[(i*5) - 4]; 					//Product ID
				aux_i[2] = IO.data_a[(i*5) - 3]; 					//Product Name
				aux_i[3] = IO.data_a[(i*5) - 2]; 					//Product Price
				aux_i[4] = IO.data_a[(i*5) - 1]; 					//Number of items Ordered
				
				//Create a product user
				pu[pu_s] = new Product_user(aux_i); //Sets the new product user
				pu_s++; //Increases the number of product user count
			}
		}
	}
	
	private static void compare_pr (String[] data) { //Compares two products
		//Products to pass
		String[] pr_1 = data[0].split("/"); //Splits the first data
		String[] pr_2 = data[1].split("/"); //Splits the second data
		String spacing = "	|	"; //Spacing
		//Print comparison
		System.out.println("Product Name:	" 	+ pr_1[2] + spacing + pr_2[2]); 	//Product Name 		(Pr1 name)		(Pr2 name)
		System.out.println("Product ID:	" 		+ pr_1[1] + spacing + pr_2[1]);		//Product Id 		(Pr1 id)		(Pr2 id)
		System.out.println("Category:	" 		+ pr_1[0] + spacing + pr_2[0]);		//Product Category 	(Pr1 category)	(Pr2 category)
		System.out.println("Price:		" 		+ pr_1[3] + spacing + pr_2[3]);		//Product Price 	(Pr1 price)		(Pr2 price)
		System.out.println("Stock:		" 		+ pr_1[4] + spacing + pr_2[4]);		//Product Stock 	(Pr1 stock)		(Pr2 stock)
	}
}