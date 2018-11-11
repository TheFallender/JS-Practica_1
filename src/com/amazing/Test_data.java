package com.amazing;

public class Test_data {
	public static void test_data() {
		//Check for correct construction of product
		Category testCategory = new Category("testing");
		Amazing.c_category();
		
		
		String aux[] = {"Cat", "1", "CEEEEE", "32", "88"};

		Product product_1 = new Product(aux);
	}
}
