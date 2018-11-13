package com.amazing;

public class Test_data extends Test {
	@Override
	public void test() {
		//Category
		Category testCategory = new Category(); //Check constructor of category
		IO.read("d_category", testCategory.r_name(), 1, false); //Read category
		super.print_data();
		
		//Product
		Product testProduct = new Product(); //Check constructor of product
		IO.read("d_product", "p_id=" + testProduct.r_id(), 5, false); //Read product
		super.print_data();
		
		//User
		User testUser = new User(); //Check constructor of user
		IO.read("d_user", testUser.r_email(), 5, false); //Read user
		super.print_data();
		
		//Product User
		String aux[] = new String[3];
		aux[0] = "" + testProduct.r_id();
		aux[1] = testUser.r_email();
		aux[2] = "" + Filter.filter_i("Number of items ordered: ", 0, 0);
		Product_user testProduct_User = new Product_user(aux); //Test product_user
		IO.read("d_product_user", testUser.r_email(), 3, false); //Read product user
		super.print_data();
		
		
		//Phase completed, 
		super.incPhase();
	}
}
