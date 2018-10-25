package com.amazing;

public class Test {
	public void test() {
		//check for correct construction of product
		category testCategory = new category("testing");
		amazing.c_category();
		
		
		String aux[] = {"Cat", "1", "CEEEEE", "32", "88"};

		product product_1 = new product(aux);
		
		
		
		//Test if the encrypter and the decrypter works
		String text = "Coolpassword";
		String e_text;
		try {
			e_text = encrypter.encrypt(text);
			if((text.equals(encrypter.encrypt("Coolpassword")))) {
				System.out.println("WORKS");
			}
			if((encrypter.decrypt(e_text).equals(encrypter.decrypt(e_text)))) {
				System.out.println("WORKS");
			}
		} catch (Exception e) {}
		
	}
}
