package com.amazing;

public class Test_data extends Test { //Data Test class
	@Override
	public void test() { //Test of this class
		//Data test
		System.out.println("\n\n\n\nData test:"); //Prints that this is the Data test
		
		//Category
		System.out.println("\n\nCategory:\n"); 							//Prints that the Category is going to be tested
		Category testCategory = new Category(); 						//Check constructor of Category
		testCategory.save(); 											//Save the data on the file
		IO.read("d_category", testCategory.r_name(), 1, false); 		//Read Category
		super.print_data(); 											//Print the data from the data array
		
		//Product
		System.out.println("\n\nProduct:\n"); 							//Prints that the Product is going to be tested
		Product testProduct = new Product(); 							//Check constructor of Product
		testProduct.save(); 											//Save the data on the file
		IO.read("d_product", "p_id=" + testProduct.r_id(), 5, false); 	//Read Product
		super.print_data(); 											//Print the data from the data array
		
		//User
		System.out.println("\n\nUser:\n"); 								//Prints that the User is going to be tested
		User testUser = new User(); 									//Check constructor of User
		IO.read("d_user", testUser.r_email(), 5, false); 				//Read User
		super.print_data(); 											//Print the data from the data array
		
		//Product User
		System.out.println("\n\nProduct user:\n"); 							//Prints that the Product User is going to be tested
		String[] aux = new String[5]; 										//Auxiliar string
		aux[0] = testUser.r_email(); 										//Set the Email
		aux[1] = "" + testProduct.r_id(); 									//Set the ID
		aux[2] = testProduct.r_name(); 										//Set the Name
		aux[3] = "" + testProduct.r_price(); 								//Set the Price
		aux[4] = "" + Filter.filter_i("Number of items ordered: ", 0, 0); 	//Set the number of items to order
		Product_user testProduct_User = new Product_user(aux); 				//Test Product User
		testProduct_User.save(); 											//Save the data on the file
		IO.read("d_product_user", testUser.r_email(), 5, false); 			//Read Product User
		super.print_data(); 												//Print the data from the data array
		
		
		//Phase completed, 
		super.incPhase(); //Increase the phase
	}
}
