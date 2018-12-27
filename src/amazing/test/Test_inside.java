package amazing.test;

import amazing.inside.Converter;
import amazing.inside.Encrypter;
import amazing.inside.Filter;
import amazing.inside.Localization;

public class Test_inside extends Test { //Inside Test class
	@Override
	public void test() { //Test of this class
		//Inside test
		System.out.println(Localization.get("test", "test_ins_main")); //Prints that this is the Inside test
		
		
		//Encrypter and decryption test
		System.out.println(Localization.get("test", "test_ins_enc")); 			//Prints that the Encrypter is going to be tested
		String text = Filter.filter_s(Localization.get("test", "test_ins_enc_txt")); 	//Base text
		String[] e_text = new String[2]; 												//Encrypted text
		String[] d_text = new String [2]; 												//Base text
		
		//Encryption and decryption process
		try {
			//Encrypt the base text
			e_text[0] = Encrypter.encrypt(text); //Encrypts the first text
			e_text[1] = Encrypter.encrypt(text); //Encrypts the second text
			
			//Test the encryption
			if((e_text[0].equals(e_text[1]))) { //Source and destination are the same
				System.out.println(Localization.get("test", "test_ins_enc_succ")); //Prints that the encryption process works
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println(Localization.get("test", "test_ins_enc_err")); 											//Prints that the encryption doesn't work
				System.out.println(Localization.get("test", "test_ins_enc_bt") + text); 									//Prints the base encryption
				System.out.println(Localization.get("test", "test_ins_enc_et1") + e_text[0]); 								//Prints the first encrypted text
				System.out.println(Localization.get("test", "test_ins_enc_et2") + e_text[1]); 								//Prints the second encrypted text
			}
			
			//Decrypt the encrypted text
			d_text[0] = Encrypter.decrypt(e_text[0]); //Decrypts the first text
			d_text[1] = Encrypter.decrypt(e_text[1]); //Decrypts the second text
			
			//Test the decryption
			if((d_text[0].equals(d_text[1]))) {  //Source and destination are the same
				System.out.println(Localization.get("test", "test_ins_dec_err")); //Prints that the decryption process works
			}
			else { //Unknown error, check, source and destination are not the same
				System.out.println(Localization.get("test", "test_ins_enc_err")); 											//Prints that the encryption doesn't work
				System.out.println(Localization.get("test", "test_ins_enc_bt") + text); 									//Prints the base encryption
				System.out.println(Localization.get("test", "test_ins_enc_et1") + e_text[0]); 								//Prints the first encrypted text
				System.out.println(Localization.get("test", "test_ins_enc_et2") + e_text[1]); 								//Prints the second encrypted text
				System.out.println(Localization.get("test", "test_ins_enc_dt1") + d_text[0]); 								//Prints the first decrypted text
				System.out.println(Localization.get("test", "test_ins_enc_dt2") + d_text[1]); 								//Prints the second decrypted text
			}
		} catch (Exception e) { //Illegal exception
			super.throw_exc(Localization.get("inside", "enc_err_illgl"), e); //Reports that there was an error in the process
		}
		
		
		//Converter test
		System.out.println(Localization.get("test", "test_ins_conv")); 															//Prints that the converter is going to be tested
		System.out.println(Localization.get("test", "test_ins_conv_acu") + Converter.get_a_currency());							//Prints the current currency
		System.out.println(Localization.get("test", "test_ins_conv_val") + Converter.get_factor()); 							//Prints the converter value of the active currency
		System.out.println(Localization.get("test", "test_ins_conv_dt") + Converter.date()); 									//Prints the date
		
		System.out.println("\n" + Localization.get("test", "test_ins_conv_dec"));												//Prints that is going to test the decimal method
		System.out.println(Converter.decimal_conv(Filter.filter_f(Localization.get("test", "test_ins_conv_dec_nmb"), 0, 0, 0),
		Filter.filter_i(Localization.get("test", "test_ins_conv_dec_dp"), 0, 0)));										//Prints the decimal out of the method
		
		
		//Phase completed, 
		super.incPhase(); //Increase the phase
	}
}
