package amazing.amazing;

import java.util.ArrayList;
import java.util.List;

import amazing.inside.Converter;
import amazing.inside.Filter;
import amazing.inside.IO;
import amazing.inside.Login_method;
import amazing.inside.Region;
import amazing.test.Test;

//Menu, marketplace class
public class Amazing {
	//Test variable
	private static boolean test = false; 		//Change this to start the test system
	
	//User data
	private static User active_user = null; 	//The active user at the moment
	
	//Data array
	private static ArrayList<Category> c = new ArrayList<>();		//Category list
	private static ArrayList<Product> p = new ArrayList<>();		//Product list
	private static ArrayList<Product> pc = new ArrayList<>(); 		//Product category list array (list of the actual category)
	private static ArrayList<Product_user> pu = new ArrayList<>();	//Product user list
	
	//Main
	public static void main(String[] args){ //Main code
		if (!test) { //Default enviroment
			//Starting functions
				//Data
				IO.data_check(); //Checks the data files are there, if not, it create them 
						
				//Region and Converter
				Region.region_list();					//Sets the Region List
				Converter.set_conv_list();				//Sets the Converter list
				
				//Adds the default regions
				Region.region_add("ES", "eur/eur", "es", "€"); 	//Sets the new region
				Region.region_add("US", "eur/usd", "en", "$"); 	//Sets the new region
				Region.region_add("UK", "eur/gbp", "en", "£"); 	//Sets the new region
				
				//Sets the default region
				Region.set_ar(0);						//Sets the active region			
				
				
			//Variables definitions
				//Menu
				int[] menu = new int[4]; //Menu int values
				
				//Boolean changes
				boolean create_success_c = c_category();	//Detects error in the creation process of category
				boolean changes_c = false;					//Detects changes in the category list
				boolean create_success_p = c_product(); 	//Detects error in the creation process of product
				boolean changes_p = false;					//Detects changes in the product list
				boolean create_success_pu = false; 			//Detects error in the creation process of product user
				
				//Product
				String[] comp_pr = new String[]{null, ""}; //Compare product array
				
				
			//Menu
			while(true) { //Loop of the menu
				switch (menu[0]) { //Checks selection within Main menu
					case 0: //Main menu
						//Menu
						System.out.println("\nAmazing " + Region.get_region() + ":"); 	//Amazing (Region):
						System.out.println("1. Search by category.");					//1. Search by Category
						System.out.println("2. Account ");								//2. Account
						if (active_user != null) //User logged in
							System.out.println("3. Sing out.");							//3. Sing out.
						else //User not logged
							System.out.println("3. Sing in.");							//3. Sing in.
						System.out.println("4. Create account.");						//4. Create account.
						System.out.println("5. Select other region.");					//5. Select other region.
						System.out.println("6. Exit.");									//6. Exit.
						
						//Menu selection
						menu[0] = Filter.filter_i("\nMenu select: ", 1, 6); //Request selection
						break;
						
						
					case 1: //Search by category
						if (menu[1] == 0) { 		//Category list
							//Detect error creating
							if(create_success_c) {
								//Menu
								System.out.println("\nCategory menu:");					//Prints the Category
								for (int i = 0; i < c.size(); i++) 						//Print categories
									System.out.println(i+1 + ". " + c.get(i).r_name()); 		//Print the name of the Category with a dot in front of it 
								System.out.println((c.size() + 1) + ". Exit"); 			//Print exit
								
								//Menu selection
								menu[1] = Filter.filter_i("\nMenu select: ", 1, c.size() + 1); //Request selection
							}
							else {
								if (!changes_c) { //There were no changes
									System.out.println("There are no categories registered."); 	//Reports that there are no categories registered
									menu[0] = 0; //Reset to base menu
								}
								else {
									create_success_c = c_category(); //Reset the error
									changes_c = false;
								}
							}
						}
						else if(menu[1] == c.size() + 1) { 	//Exit
							menu[0] = 0; 						//Resets Main menu
							menu[1] = 0; 						//Reset Category menu
						}
						else { //Category selected
							if (menu[2] == 0) { 		//Product Menu
								//Product list
								pc = new ArrayList<>(); 		//Resets the product list
								
								if (create_success_p) { //No error, there is data
									//Menu print
									System.out.println("\nProduct list of " + c.get(menu[1] - 1).r_name() + ":"); 	//Print the info of the Category as a product list
									for (int i = 0; i < p.size(); i++) 												//Prints products
										if (p.get(i).r_category().equals(c.get(menu[1] - 1).r_name())) { 					//The product category name is equal to the category name
											//Print
											System.out.println((pc.size() + 1) + ". " + p.get(i).r_name());					//Print product name
											
											//Add the product to the list
											pc.add(p.get(i));						//Add the p_aux to the list
										}
									System.out.println((pc.size() + 1) + ". Exit"); //Print exit
									
									//Menu selection
									menu[2] = Filter.filter_i("\nMenu select: ", 1, pc.size() + 1); //Request selection
								}
								else { //There is no data
									if (!changes_p) { //There were no changes
										System.out.println("There are no products registered."); //Reports that there are no products registered
										menu[1] = 0; //Reset to category menu
									}
									else {
										create_success_p = c_product(); //Reset the error
										changes_p = false;
									}
								}
							}
							else if(menu[2] == pc.size() + 1) { //Exit
								menu[1] = 0; 						//Reset Category menu
								menu[2] = 0; 						//Reset Product list menu
							}
							else { //Show product
								switch (menu[3]) { //Checks selection within Product menu
									case 0: //Product Menu
										//Print
										System.out.println("\n"); 				//Print newline for print of the product
										pc.get(menu[2] - 1).print(); 						//Print the selected product
										
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
											if (pc.get(menu[2] - 1).r_stock() > 0) { //There is some stock
												int amount = Filter.filter_i("Number of items to order: ", 1, 10); //Request the number of items to order
												
												//Check if the stock is enough
												if (pc.get(menu[2] - 1).r_stock() - amount >= 0) { //There is enough stock for the order
													//Product user array
													String[] pu_a = new String [5]; 	//Product user from this order
													pu_a[0] = 		active_user.r_email(); 				//User that is going to buy the order
													pu_a[1] = "" + 	pc.get(menu[2] - 1).r_id(); 		//Product ID of the ordered item
													pu_a[2] = 		pc.get(menu[2] - 1).r_name();		//Product Name of the ordered item
													pu_a[3] = "" + 	pc.get(menu[2] - 1).r_price(); 		//Price at the moment of the order
													pu_a[4] = "" + 	amount; 							//Amount on the order
													
													//Product User
													Product_user bought_product = new Product_user(pu_a); 	//New product user
													bought_product.save(); 									//Saves the data to the file
													
													
													//Product Update
													pc.get(menu[2] - 1).buy(amount); 		//Decrease the value by the amount selected
													
													//Update Product User list
													pu.add(bought_product);		//Adds the latest order
													
													//Update error if there is one
													if (!create_success_pu)
														create_success_pu = c_product_user(); //Reset the error
													
													//Product Update File
													String[] data = new String[] {"p_id=" + pc.get(menu[2] - 1).r_id(), "p_stock=" + pc.get(menu[2] - 1).r_stock()}; 	//Data array, first the ID to search, then the stock.
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
											comp_pr[0] = pc.get(menu[2] - 1).compare(); //First product to compare
										else { //One product selected, check the other one
											//Set product
											comp_pr[1] = pc.get(menu[2] - 1).compare(); //Second product to compare
											
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
									if (create_success_pu) {//No error, proceed
										if (!(pu.isEmpty())) //Check if it's empty
											for (int i = 0; i < pu.size(); i++) //Print all the orders
												pu.get(i).print(); 						//Prints the product user
										else
											System.out.println("You have no orders on your account."); 	//Reports that the user hasn't made any orders
									}
									else {
										System.out.println("You have no orders on your account."); 	//Reports that the user hasn't made any orders
									}

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
											c.add(new_c);						//Add the new category
											changes_c = true;					//Changes in the Category
											menu[2] = 0; 						//Back to Admin basic menu
											break;
											
											
										case 2: //Create Product
											Product new_p = new Product(); 		//Create new product
											new_p.save();						//Saves the data on the file
											p.add(new_p);						//Add the new product
											changes_p = true;					//Changes in the Product
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
							create_success_pu = c_product_user(); 
							
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
						
						
					case 5: //Change region
						//Auxiliary list
						List<String[]> aux_l = Region.get_list();	//Set the list
						
						//Print
						for (int i = 1; i <= aux_l.size(); i++) { //Print the list
							System.out.println(i + ". " + aux_l.get(i - 1)[0] + "."); //Prints the numbered list item
						}
						System.out.println((aux_l.size() + 1) + ". Exit.");
						
						//Menu selection
						menu[1] = Filter.filter_i("\nMenu select: ", 1, (aux_l.size() + 1)); //Request selection
						
						//Selection
						if (menu[1] != (aux_l.size() + 1)) { //Select the region
							Region.set_ar(menu[1] - 1);										//Sets the new active region
							System.out.println("Welcome to " + Region.get_region() + "."); 	//Welcomes the user to the new region
						}
						Filter.filter_s("\n\nPress ENTER to continue: "); 	//Waits for the user input
						
						//Menu resets
						menu[0] = 0; //Resets Main menu
						menu[1] = 0; //Resets Region menu
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
	
	
	
	
	//Create
	private static boolean c_category() { //Category create
		//Reset list
		c = new ArrayList<>();
		
		//Get the data from the file
		IO.read("d_category", "", 15, false); //Get data
		if (IO.data().isEmpty()) //If there is no data return
			return false;												//Couldn't get the data		
		
		//Get Category list
		for (int i = 0; i < IO.data().size(); i++) 			//Create categories
			if (IO.data().get(i) != null) {							//Prevents creating null objects
				Category c_aux = new Category (IO.data().get(i));		//Creates an auxiliary category
				c.add(c_aux);										//Adds the category
			}
		
		return true;
	}
	
	
	private static boolean c_product () { //Product list
		//Reset list
		p = new ArrayList<>();
		
		//Get the data from the file
		IO.read("d_product", "", 100, false); 	//Get data
		if (IO.data().isEmpty()) //If there is no data return
			return false;												//Couldn't get the data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to use
		
		//Get Product list
		for(int i = 1; i <= IO.data().size()/5; i++) {	//Write every product in the list		
			//Null prevent
			if (IO.data().get((i*5) - 5) == null) //Prevents creating null objects
				break;

			//Auxiliary array
			aux_i[0] = IO.data().get((i*5) - 5); 				//Id
			aux_i[1] = IO.data().get((i*5) - 4); 				//Name
			aux_i[2] = IO.data().get((i*5) - 3); 				//Category
			aux_i[3] = IO.data().get((i*5) - 2); 				//Price
			aux_i[4] = IO.data().get((i*5) - 1); 				//Stock
			
			//Create a product
			Product p_aux = new Product (aux_i);		//Creates an auxiliary product
			p.add(p_aux);								//Adds the product
		}
			
		
		return true;
	}
	
	
	private static boolean c_product_user() { //Product User list
		//Reset list
		pu = new ArrayList<>();
		
		//Get the data from the file
		IO.read("d_product_user", "", 1200, false); //Get data
		if (IO.data().isEmpty()) //If there is no data return
			return false;												//Couldn't get the data
		
		//Variables to use
		String[] aux_i = new String[5]; //Auxiliar string to check
		
		//Get Product User list
		for(int i = 1; i <= IO.data().size()/5; i++) { //Search between the created list
			//Null prevent
			if (IO.data().get((i*5) - 5) == null)
				break;
			
			aux_i[0] = IO.data().get((i*5) - 5); 						//User Email
			if(aux_i[0].equals(Amazing.active_user.r_email())) { //Checks if the product user equals the active user email
				//Now checks for the rest of the data
				aux_i[1] = IO.data().get((i*5) - 4); 					//Product ID
				aux_i[2] = IO.data().get((i*5) - 3); 					//Product Name
				aux_i[3] = IO.data().get((i*5) - 2); 					//Product Price
				aux_i[4] = IO.data().get((i*5) - 1); 					//Number of items Ordered
				
				//Create a product user
				Product_user pu_aux = new Product_user(aux_i); 	//Sets the new product user
				pu.add(pu_aux);									//Adds the auxiliary product user
			}
		}
		
		return true;
	}
	
	
	
	
	//Compare
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
	
	
	
	
	//Active User
	public static User get_a_user() {
		return active_user;
	}
	
	public static void set_a_user(boolean set) {
		if (set)
			active_user = new User (IO.data().toArray(new String[IO.data().size()])); //Set the new user based on the array
		else
			active_user = null;
	}
	
	
	
	
	//Test
	public static boolean get_test() {
		return test;
	}
}