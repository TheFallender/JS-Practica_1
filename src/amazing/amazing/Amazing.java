package amazing.amazing;

import java.util.ArrayList;
import java.util.List;
import amazing.inside.Converter;
import amazing.inside.Encrypter;
import amazing.inside.Filter;
import amazing.inside.IO;
import amazing.inside.Localization;
import amazing.inside.Login_method;
import amazing.inside.Region;
import amazing.test.Test;

//Menu, marketplace class
public class Amazing {
	//Test variable
	private static boolean test = true; 		//Change this to start the test system
	
	//User data
	private static User active_user = null; 	//The active user at the moment
	
	//Data array
	private static ArrayList<Category> c = new ArrayList<>();		//Category list
	private static ArrayList<Product> p = new ArrayList<>();		//Product list
	private static ArrayList<Product> pc = new ArrayList<>(); 		//Product category list array (list of the actual category)
	private static ArrayList<Product_user> pu = new ArrayList<>();	//Product user list
	
	//Menu
	private static int[] menu = new int[4]; //Menu int values
	
	//Main
	public static void main(String[] args){ //Main code
		//Starting functions
			//Data
			IO.data_check(); //Checks the data files are there, if not, it create them 
					
			//Region and Converter
			Region.region_list();					//Sets the Region list
			Converter.set_conv_list();				//Sets the Converter list
			Localization.set_locale_list();			//Sets the Locale list
			
			//Adds the default regions and locales
			Localization.set_locale("en", "EN");
			Region.region_add("Default", "eur/eur", "en", "€"); 	//Sets the new region
			Region.region_add("ES", "eur/eur", "es", "€"); 			//Sets the new region
			Region.region_add("US", "eur/usd", "en", "$"); 			//Sets the new region
			Region.region_add("GB", "eur/gbp", "en", "£"); 			//Sets the new region
			
			//Sets the default region
			Region.set_ar(0);						//Sets the active region			
		
		if (!test) { //Default enviroment
			//Variables definitions			
				//Boolean changes
				boolean create_success_c = c_category();	//Detects error in the creation process of category
				boolean changes_c = false;					//Detects changes in the category list
				boolean create_success_p = c_product(); 	//Detects error in the creation process of product
				boolean changes_p = false;					//Detects changes in the product list
				boolean create_success_pu = false; 			//Detects error in the creation process of product user
				boolean changes_pu = false;					//Detects changes in the product user
				
				//Product
				Product[] comp_pr = new Product[]{null, null}; 	//Compare product array
				String[] comp_data = new String[6];				//String with the data of the region
				
				//First user will be admin
				boolean first_user = false;
				if (c_user(true).isEmpty()) //No users on the database
					first_user = true; //This is the first user
				
			//Menu
			while(true) { //Loop of the menu
				switch (menu[0]) { //Checks selection within Main menu
					case 0: //Main menu
						//Menu
						System.out.println(Localization.get("main", "main_site") + Region.get_region() + ":"); 	//Amazing (Region):
						System.out.println("1. " + Localization.get("main", "main_menu_basic_cat"));				//1. Search by Category
						System.out.println("2. " + Localization.get("main", "main_menu_basic_acc"));				//2. Account
						if (active_user != null) //User logged in
							System.out.println("3. " + Localization.get("main", "main_menu_basic_lg_i"));			//3. Sing out.
						else //User not logged
							System.out.println("3. " + Localization.get("main", "main_menu_basic_lg_o"));			//3. Sing in.
						System.out.println("4. " + Localization.get("main", "main_menu_basic_cracc"));				//4. Create account.
						System.out.println("5. " + Localization.get("main", "main_menu_basic_reg"));				//5. Select other region.
						System.out.println("6. " + Localization.get("main", "main_menu_ex"));						//6. Exit.
						
						//Menu selection
						menu[0] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, 6); //Request selection
						break;
						
						
					case 1: //Search by category
						if (menu[1] == 0) { 		//Category list
							//Detect error creating
							if(create_success_c) {
								//Menu
								System.out.println(Localization.get("main", "main_menu_cat_mn"));								//Prints the Category
								for (int i = 0; i < c.size(); i++) 																	//Print categories
									System.out.println(i+1 + ". " + c.get(i).r_name()); 												//Print the name of the Category with a dot in front of it 
								System.out.println((c.size() + 1) + ". " + Localization.get("main", "main_menu_ex")); 			//Print exit
								
								//Menu selection
								menu[1] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, c.size() + 1); //Request selection
							}
							else {
								if (!changes_c) { //There were no changes
									System.out.println(Localization.get("main", "main_menu_cat_err")); 	//Reports that there are no categories registered
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
									System.out.println(Localization.get("main", "main_menu_pr_mn") + c.get(menu[1] - 1).r_name() + ":"); 	//Print the info of the Category as a product list
									for (int i = 0; i < p.size(); i++) 												//Prints products
										if (p.get(i).r_category().equals(c.get(menu[1] - 1).r_name())) { 					//The product category name is equal to the category name
											//Print
											System.out.println((pc.size() + 1) + ". " + p.get(i).r_name());					//Print product name
											
											//Add the product to the list
											pc.add(p.get(i));						//Add the p_aux to the list
										}
									System.out.println((pc.size() + 1) + ". " + Localization.get("main", "main_menu_ex")); //Print exit
									
									//Menu selection
									menu[2] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, pc.size() + 1); //Request selection
								}
								else { //There is no data
									if (!changes_p) { //There were no changes
										System.out.println(Localization.get("main", "main_menu_pr_err")); //Reports that there are no products registered
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
										System.out.println("\n" + c.get(menu[1] - 1).r_name() + ":"); 	//Print newline for print of the product and the actual category
										pc.get(menu[2] - 1).print(); 								//Print the selected product
										
										//Menu print
										System.out.println("1. " + Localization.get("main", "main_menu_pr_buy"));			//1. Buy.
										System.out.println("2. " + Localization.get("main", "main_menu_pr_comp"));		//2. Compare.
										System.out.println("3. " + Localization.get("main", "main_menu_ex"));			//3. Exit.
										
										//Menu selection
										menu[3] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, 3); //Request menu selection
										break;
										
										
									case 1: //Buy
										//Check if the user is logged in
										if (active_user != null) { //The user is logged in
																		//Check if there is stock
											if (pc.get(menu[2] - 1).r_stock() > 0) { //There is some stock
												int amount = Filter.filter_i(Localization.get("main", "main_menu_pr_buy_amt"), 1, 10); //Request the number of items to order
												
												//Check if the stock is enough
												if (pc.get(menu[2] - 1).r_stock() - amount >= 0) { //There is enough stock for the order
													//Product user array
													String[] pu_a = new String [6]; 	//Product user from this order
													pu_a[0] = 		active_user.r_email(); 															//User that is going to buy the order
													pu_a[1] = "" + 	pc.get(menu[2] - 1).r_id(); 													//Product ID of the ordered item
													pu_a[2] = 		pc.get(menu[2] - 1).r_name();													//Product Name of the ordered item
													pu_a[3] = "" + 	pc.get(menu[2] - 1).r_price() + Converter.get_a_currency_symbol(); 				//Price at the moment of the order
													pu_a[4] = "" + 	amount; 																		//Amount on the order
													pu_a[5] = "" + 	(pc.get(menu[2] - 1).r_price() * amount) + Converter.get_a_currency_symbol();	//Total price of the order
													
													//Product User
													Product_user bought_product = new Product_user(pu_a); 	//New product user
													bought_product.save(); 									//Saves the data to the file
													
													
													//Product Update
													pc.get(menu[2] - 1).buy(amount); 		//Decrease the value by the amount selected
													
													//Update Product User list
													pu.add(bought_product);		//Adds the latest order
													
													//Changes in product user
													changes_pu = true;
													
													//Product Update File
													String[] data = new String[] {"p_id=" + pc.get(menu[2] - 1).r_id(), "p_stock=" + pc.get(menu[2] - 1).r_stock()}; 	//Data array, first the ID to search, then the stock.
													IO.modify("d_product", data, 4); 														//Modify Product with the new data
													
													//Print info
													System.out.println(Localization.get("main", "main_menu_pr_buy_succ")); //Print that the product was bought
												}
												else //There is not enough stock
													System.out.println(Localization.get("main", "main_menu_pr_buy_err_nestk")); //Report that the product doesn't have enough stock
											}
											else //There is no stock
												System.out.println(Localization.get("main", "main_menu_pr_buy_err_nstk")); //Report that the product is out of stock
										}
										else //User must be logged in to buy the product
											System.out.println(Localization.get("main", "main_menu_pr_buy_err_nlg")); //Report that the user must be logged in to buy
										
										
										Filter.filter_s(Localization.get("main", "main_menu_wait")); //Waits for the user input
										
										menu[3] = 0; //Back to Product basic menu
										break;
										
										
									case 2: //Compare
										//Check if there is a product selected
										if (comp_pr[0] == null) { //No product selected
											comp_pr[0] = pc.get(menu[2] - 1); //First product to compare
											comp_data[0] = Region.get_region();
											comp_data[1] = "" + Converter.get_factor();
											comp_data[2] = Converter.get_a_currency_symbol(); 
										}
										else { //One product selected, check the other one
											//Set product
											comp_pr[1] = pc.get(menu[2] - 1); //Second product to compare
											comp_data[3] = Region.get_region();
											comp_data[4] = "" + Converter.get_factor();
											comp_data[5] = Converter.get_a_currency_symbol(); 
											
											//Compare function
											compare_pr(comp_pr, comp_data); //Compare both products
											
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
									System.out.println(Localization.get("main", "main_menu_acc_mn"));				//Account Menu:
									System.out.println("1. " + Localization.get("main", "main_menu_acc_inf"));				//1. Account Info.
									System.out.println("2. " + Localization.get("main", "main_menu_acc_cpw"));				//2. Change Password
									System.out.println("3. " + Localization.get("main", "main_menu_acc_pru"));				//3. Ordered Products.
									System.out.println("4. " + Localization.get("main", "main_menu_acc_lgo"));				//4. Log out.
									System.out.println("5. " + Localization.get("main", "main_menu_acc_hm") );				//5. Homepage.
									
									//Menu selection
									if (active_user.r_admin()) { //Checks if the user is admin
										System.out.println("6. " + Localization.get("main", "main_menu_acc_adm"));		//6. Admin
										menu[1] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, 6); //Request menu selection
									}
									else
										menu[1] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, 5); //Request menu selection
									break;
									
									
								case 1: //See User Info
									active_user.print(); //Print info of the user
									
									Filter.filter_s(Localization.get("main", "main_menu_wait")); //Waits for the user input
									
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 2: //Change password
									String e_old_pw = ""; 				//Old password
									String[] e_new_pw = new String[2];	//String for new password
									boolean pass_changed = false;
									try { //Try to change the password
										while (true) { //Old password
											e_old_pw = Encrypter.encrypt(Filter.filter_s(Localization.get("main", "main_menu_acc_cpw_old")));		//Request old password
											if (e_old_pw.equals(active_user.r_pass())) { //Inserted password equals old password
												while (true) { //New password match
													e_new_pw[0] = Encrypter.encrypt(Filter.filter_s(Localization.get("main", "main_menu_acc_cpw_new")));		//Request new password
													e_new_pw[1] = Encrypter.encrypt(Filter.filter_s(Localization.get("main", "main_menu_acc_cpw_new_r")));		//Request new password again
													
													//New passwords match
													if (e_new_pw[0].equals(e_new_pw[1])) { //Update user
														active_user.s_pass(e_new_pw[0]); 														//Update the old password
														String[] data_mod = new String[] {active_user.r_email(), "u_password=" + e_new_pw[0]};	//Data for the modify
														IO.modify("d_user", data_mod, 1);														//Modify user
														System.out.println(Localization.get("main", "main_menu_acc_cpw_succ")); 				//Prints that the password was changed
														break;
													}
													else //New passwords don't match
														System.out.println(Localization.get("main", "main_menu_acc_cpw_new_err")); //Reports that the new passwords don't match
												}
												//Changed the pasword
												break;
											}
											else //Actual password doesn't equal old password
												System.out.println(Localization.get("main", "main_menu_acc_cpw_old_err"));
										}
									}
									catch (Exception e) { //Illegal encryption
										System.out.println(Localization.get("inside", "enc_err_illgl"));
									}
									
									menu[1] = 0; //Back to basic account menu
									break;
									
									
								case 3: //Ordered products				
									menu[1] = 0; //Back to Account basic menu
									
									if (create_success_pu) //No error, proceed
										if (!(pu.isEmpty())) //Check if it's empty
											for (int i = 0; i < pu.size(); i++) //Print all the orders
												pu.get(i).print(); 						//Prints the product user
										else
											System.out.println(Localization.get("main", "main_menu_acc_pru_err")); 	//Reports that the user hasn't made any orders
									else
										if (!changes_pu) //There were no changes
											System.out.println(Localization.get("main", "main_menu_acc_pru_err")); 	//Reports that the user hasn't made any orders
										else {
											create_success_pu = c_product_user();	//Recreates the product user to check for new orders
											changes_pu = false; //Disable product user
											menu[1] = 2; //Keep in this menu
										}

									Filter.filter_s(Localization.get("main", "main_menu_wait")); //Waits for the user input
									break;
									
									
								case 4: //Log out
									Login_method.login_method_out(); 											//Log out method
									System.out.println(Localization.get("main", "main_menu_acc_lgo_succ")); 	//Prints that the user logged out
									Filter.filter_s(Localization.get("main", "main_menu_wait")); 				//Waits for the user input
									
									menu[0] = 0; //Resets Main menu
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 5: //Exit account menu
									menu[0] = 0; //Resets Main menu
									menu[1] = 0; //Back to Account basic menu
									break;
									
									
								case 6: //Admin
									switch (menu[2]) { //Checks selection within Admin menu
										case 0: //Admin Menu
											//Print menu
											System.out.println(Localization.get("main", "main_menu_adm_mn")); 		//Admin menu:
											System.out.println("1. " + Localization.get("main", "main_menu_adm_ncat")); //1. New Category.
											System.out.println("2. " + Localization.get("main", "main_menu_adm_npr")); 	//2. New Product.
											System.out.println("3. " + Localization.get("main", "main_menu_adm_uadm")); //3. Give admin access.
											System.out.println("4. " + Localization.get("main", "main_menu_ex")); 		//4. Exit.
											
											//Menu selection
											menu[2] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, 4); //Request menu selection
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
											
											
										case 3: //Give admin access to other users
											List<String> temp_ul = c_user(false); //Temporary user list
											
											if (temp_ul.size() == 1) //The only user is the active user
												System.out.println(Localization.get("main", "main_menu_adm_uadm_err_oys")); //Reports that there is only one user
											else { //More than one user
												//Print
												//Menu
												System.out.println(Localization.get("main", "main_menu_adm_uadm_ul")); //Prints that this is the user list
												//Prints the user list
												for (int i = 0; i < temp_ul.size(); i++) //Print list
													System.out.println((i + 1) + ". " + temp_ul.get(i)); //Print user email with number
												//Print the exit
												System.out.println((temp_ul.size() + 1) + ". " + Localization.get("main", "main_menu_ex"));
												
												//Select
												menu[3] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, (temp_ul.size() + 1)); //Request menu selection
												
												if (menu[3] != (temp_ul.size() + 1)) {//Give admin access
													String[] modify_data = new String[] {"u_email=" + temp_ul.get(menu[3] - 1), "u_admin=1"}; 				//Set string to modify
													IO.modify("d_user", modify_data, 4);																	//Modify the user
													System.out.println(temp_ul.get(menu[3] - 1) + " " + Localization.get("main", "main_menu_adm_uadm_succ") + "\n"); 	//Prints that the selected user now has admin access
													Filter.filter_s(Localization.get("main", "main_menu_wait")); 											//Waits for the user input
												}
											}
											
											
											
											menu[2] = 0; 						//Back to Admin basic menu
											break;
											
											
										case 4: //Exit
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
							System.out.println(Localization.get("main", "main_menu_acc_err")); //Report error, user should be logged in to see this.
							Filter.filter_s(Localization.get("main", "main_menu_wait")); //Waits for the user input
							
							menu[0] = 0; //Resets Main menu
						}
						break;
						
						
					case 3: //Log in/out
						//Checks if it should log in or out
						if (active_user != null) { //Log out
							Login_method.login_method_out(); 											//Logs out the user
							System.out.println(Localization.get("main", "main_menu_acc_lgo_succ")); 	//Prints that the user logged out
						}
						else { //Log in
							if (Login_method.login_method_in()) //Check if the method successfully creates the user
								System.out.println(Localization.get("main", "main_menu_acc_lgi_succ")); 	//Prints that the user logged in
							create_success_pu = c_product_user(); 
						}
						
						Filter.filter_s(Localization.get("main", "main_menu_wait")); 	//Waits for the user input
						
						menu[0] = 0; //Resets Main menu
						break;
						
						
					case 4: //Create account
						//Check if the user is logged in
						if (active_user != null)
							System.out.println(Localization.get("main", "main_menu_cracc_err")); //Report error as the user is logged in
						else {
							active_user = new User(); 												//Creates a new user
							if (first_user) { 				//If this is the first user
								active_user.s_admin("1"); 		//Set admin level to 1
								first_user = false; 			//Disable first user
							}
							active_user.save();														//Save the data on the file
							System.out.println(Localization.get("main", "main_menu_cracc_succ")); 	//Print that the user was created successfully
						}
						
						Filter.filter_s(Localization.get("main", "main_menu_wait")); //Waits for the user input
						
						menu[0] = 0; //Resets Main menu
						break;
						
						
					case 5: //Change region
						//Auxiliary list
						List<String[]> aux_l = Region.get_list();	//Set the list
						
						//Print
						for (int i = 1; i <= aux_l.size(); i++) { //Print the list
							System.out.println(i + ". " + aux_l.get(i - 1)[0] + "."); //Prints the numbered list item
						}
						System.out.println((aux_l.size() + 1) + ". " + Localization.get("main", "main_menu_ex"));
						
						//Menu selection
						menu[1] = Filter.filter_i(Localization.get("main", "main_menu_sel"), 1, (aux_l.size() + 1)); //Request selection
						
						//Selection
						if (menu[1] != (aux_l.size() + 1)) { //Select the region
							Region.set_ar(menu[1] - 1);										//Sets the new active region
							System.out.println(Localization.get("main", "main_menu_reg_wcm") + Region.get_region() + "."); 	//Welcomes the user to the new region
						}
						Filter.filter_s(Localization.get("main", "main_menu_wait")); 	//Waits for the user input
						
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
						System.out.println(Localization.get("main", "main_menu_err")); //Report outside of menu selection
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
		IO.read("d_category", "", 0, false); //Get data
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
		IO.read("d_product", "", 0, false); 	//Get data
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
		IO.read("d_product_user", "", 0, false); //Get data
		if (IO.data().isEmpty()) //If there is no data return
			return false;												//Couldn't get the data
		
		//Variables to use
		String[] aux_i = new String[6]; //Auxiliar string to check
		
		//Get Product User list
		for(int i = 1; i <= IO.data().size()/6; i++) { //Search between the created list
			//Null prevent
			if (IO.data().get((i * 6) - 6) == null)
				break;
			
			aux_i[0] = IO.data().get((i*6) - 6); 						//User Email
			if(aux_i[0].equals(Amazing.active_user.r_email())) { //Checks if the product user equals the active user email
				//Now checks for the rest of the data
				aux_i[1] = IO.data().get((i * 6) - 5); 					//Product ID
				aux_i[2] = IO.data().get((i * 6) - 4); 					//Product Name
				aux_i[3] = IO.data().get((i * 6) - 3); 					//Product Price
				aux_i[4] = IO.data().get((i * 6) - 2); 					//Number of items Ordered
				aux_i[5] = IO.data().get((i * 6) - 1); 					//Price of the total order
				
				//Create a product user
				Product_user pu_aux = new Product_user(aux_i); 	//Sets the new product user
				pu.add(pu_aux);									//Adds the auxiliary product user
			}
		}
		
		return true;
	}
	
	
	private static List<String> c_user(boolean complete) { //User list
		//List to return
		ArrayList<String> user_email = new ArrayList<>();
		
		//Get the data from the file
		IO.read("d_user", "", 0, false); //Get data
		
		//Get User email list
		for(int i = 1; i <= IO.data().size()/5; i++) { //Search between the created list
			//Null prevent
			if (IO.data().get((i * 5) - 5) == null)
				break;
			
			//Add emails
			user_email.add(IO.data().get((i * 5) - 5));	//Adds email of the user	
		}
		
		//Not complete, remove active user
		if (!complete)
			for (int i = 0; i < user_email.size(); i++)
				if (user_email.get(i).equals(active_user.r_email()))
					user_email.remove(i);
		//Return
		return user_email;
	}
	
	//Compare
	private static void compare_pr (Product[] pr_data, String[] comp_currency) { //Compares two products
		//Formatting
		String spacing = "			|		"; 	//Spacing
		//Prices
		Float[] prices = new Float[2];			//Float with the prices converted
		prices[0] = Converter.decimal_conv((pr_data[0].r_raw_price() * Float.parseFloat(comp_currency[1])), 2); 	//Gets the price with the decimal places
		prices[1] = Converter.decimal_conv((pr_data[1].r_raw_price() * Float.parseFloat(comp_currency[4])), 2); 	//Gets the price with the decimal places
		String[] prices_s = new String[] {prices[0] + comp_currency[2], prices[1] + comp_currency[5]};		//String with the prices and the symbol
		
		//Print comparison
		System.out.println(Localization.get("main", "main_comp_pr_n")			+ pr_data[0].r_name()		+ spacing + 	pr_data[1].r_name()); 		//Product Name 		(Pr1 name)		(Pr2 name)
		System.out.println(Localization.get("main", "main_comp_pr_id") 			+ pr_data[0].r_id()			+ spacing + 	pr_data[1].r_id());			//Product Id 		(Pr1 id)		(Pr2 id)
		System.out.println(Localization.get("main", "main_comp_pr_cat") 		+ pr_data[0].r_category()	+ spacing + 	pr_data[1].r_category());	//Product Category 	(Pr1 category)	(Pr2 category)
		System.out.println(Localization.get("main", "main_comp_pr_val") 		+ prices_s[0]				+ spacing + 	prices_s[1]);				//Product Price 	(Pr1 price)		(Pr2 price)
		System.out.println(Localization.get("main", "main_comp_pr_stk") 		+ pr_data[0].r_stock() 		+ spacing + 	pr_data[1].r_stock());		//Product Stock 	(Pr1 stock)		(Pr2 stock)
		System.out.println(Localization.get("main", "main_comp_pr_reg")			+ comp_currency[0] 			+ spacing +		comp_currency[3]);			//Product Region	(Region of Pr1)	(Region of Pr2)
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