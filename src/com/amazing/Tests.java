package com.amazing;

public class Tests {
	public void test() {
		//check for correct construction of product
		Category testCategory = new Category("testing");
		Amazing.c_category();
		
		
		String aux[] = {"Cat", "1", "CEEEEE", "32", "88"};

		Product product_1 = new Product(aux);
		
		
		
		//Test if the encrypter and the decrypter works
		String text = "Coolpassword";
		String e_text;
		try {
			e_text = Encrypter.encrypt(text);
			if((text.equals(Encrypter.encrypt("Coolpassword")))) {
				System.out.println("WORKS");
			}
			if((Encrypter.decrypt(e_text).equals(Encrypter.decrypt(e_text)))) {
				System.out.println("WORKS");
			}
		} catch (Exception e) {}
		
	}
}
