package amazing.test;

import amazing.inside.Converter;
import amazing.inside.Encrypter;
import amazing.inside.Filter;
import amazing.inside.Region;

public class Test_inside extends Test { //Inside Test class
	@Override
	public void test() { //Test of this class
		//Inside test
		System.out.println("\n\n\n\nInside test:"); //Prints that this is the Inside test
		
		
		//Encrypter and decryption test
		System.out.println("\n\nEncrypter and Decrypter:\n"); 			//Prints that the Encrypter is going to be tested
		String text = Filter.filter_s("Text to test the encryption: "); //Base text
		String[] e_text = new String[2]; 								//Encrypted text
		String[] d_text = new String [2]; 								//Base text
		
		//Encryption and decryption process
		try {
			//Encrypt the base text
			e_text[0] = Encrypter.encrypt(text); //Encrypts the first text
			e_text[1] = Encrypter.encrypt(text); //Encrypts the second text
			
			//Test the encryption
			if((e_text[0].equals(e_text[1]))) { //Source and destination are the same
				System.out.println("The enryption process works properly."); //Prints that the encryption process works
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println("ERROR - The encryption process didn't work, unknown cause."); 	//Prints that the encryption doesn't work
				System.out.println("Base encryption: " + text); 									//Prints the base encryption
				System.out.println("Encrypted text 1: " + e_text[0]); 								//Prints the first encrypted text
				System.out.println("Encrypted text 2: " + e_text[1]); 								//Prints the second encrypted text
			}
			
			//Decrypt the encrypted text
			d_text[0] = Encrypter.decrypt(e_text[0]); //Decrypts the first text
			d_text[1] = Encrypter.decrypt(e_text[1]); //Decrypts the second text
			
			//Test the decryption
			if((d_text[0].equals(d_text[1]))) {  //Source and destination are the same
				System.out.println("The decryption process works properly."); //Prints that the decryption process works
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println("ERROR - The encryption process didn't work, unknown cause."); 	//Prints that the decryption doesn't work
				System.out.println("Base encryption: " + text); 									//Prints the base encryption
				System.out.println("Encrypted text 1: " + e_text[0]); 								//Prints the first encrypted text
				System.out.println("Encrypted text 2: " + e_text[1]); 								//Prints the second encrypted text
				System.out.println("Decrypted text 1: " + d_text[0]); 								//Prints the first decrypted text
				System.out.println("Decrypted text 2: " + d_text[1]); 								//Prints the second decrypted text
			}
		} catch (Exception e) { //Illegal exception
			super.throw_exc("ERROR - Illegal operation detected.", e); //Reports that there was an error in the process
		}
		
		
		//Converter test
		System.out.println("\n\n\nConverter:\n"); 																															//Prints that the converter is going to be tested
		System.out.println("Active currency: " + Converter.get_a_currency());																									//Prints the current currency
		System.out.println("Converter value: " + Converter.get_factor()); 																									//Prints the converter value of the active currency
		System.out.println("Date now: " + Converter.date()); 																												//Prints the date
		System.out.print("Decimal price: ");																																//Prints that is going to test the decimal method
		System.out.println(Converter.decimal_conv(Filter.filter_f("Number to round to X decimal places:", 0, 0, 0), Filter.filter_i("Number of decimal places", 0, 0)));	//Prints the decimal out of the method
		
		//Region
		Region.region_add(Filter.filter_s("New region name: "), Filter.filter_s("Region currency: "), Filter.filter_s("Region Language: "), Filter.filter_s("Region currency symb: "));
		System.out.println("Actual Region: " + Region.get_region());
		
		
		//Phase completed, 
		super.incPhase(); //Increase the phase
	}
}
