package com.amazing;

public class Test_inside extends Test {
	@Override
	public void test() {		
		//Converter test
		System.out.println("\n\n\n\nInside test:\n\n\nConverter:\n");
		System.out.println(Converter.factor("eur", "usd")); //Get the value from the net
		System.out.println(Converter.factor("TEST", "ERROR")); //Will find and error and check for the stored value
		
		
		//Test if the encrypter and the decrypter works
		System.out.println("\n\nEncrypter and Decrypter:\n");
		String text = Filter.filter_s("Text to test the encryption: "); //Base text
		String e_text[] = new String[2]; //Encrypted text
		String d_text[] = new String [2]; //Base text
		
		//Encryption and decryption process
		try {
			//Encrypt the base text
			e_text[0] = Encrypter.encrypt(text);
			e_text[1] = Encrypter.encrypt(text);
			
			//Test the encryption
			if((e_text[0].equals(e_text[1]))) { //Source and destination are the same
				System.out.println("The enryption process works properly.");
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println("ERROR - The encryption process didn't work, unknown cause.");
				System.out.println("Base encryption: " + text);
				System.out.println("Encrypted text 1: " + e_text[0]);
				System.out.println("Encrypted text 2: " + e_text[1]);
			}
			
			//Decrypt the encrypted text
			d_text[0] = Encrypter.encrypt(e_text[0]);
			d_text[1] = Encrypter.encrypt(e_text[1]);
			
			//Test the decryption
			if((d_text[0].equals(d_text[1]))) {  //Source and destination are the same
				System.out.println("The decryption process works properly.");
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println("ERROR - The encryption process didn't work, unknown cause.");
				System.out.println("Base encryption: " + text);
				System.out.println("Encrypted text 1: " + e_text[0]);
				System.out.println("Encrypted text 2: " + e_text[1]);
				System.out.println("Decrypted text 1: " + d_text[0]);
				System.out.println("Decrypted text 2: " + d_text[1]);
			}
		} catch (Exception e) {
			super.throw_exc("ERROR - Illegal operation detected.", e);
		}
		
		//Phase completed, 
		super.incPhase();
	}
}
