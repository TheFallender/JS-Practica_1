package com.amazing;

public class Test_inside {
	public static void test_inside() {
		IO.data_check();
		System.out.println(Conversor.factor("eur", "usd"));

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
