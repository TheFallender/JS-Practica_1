package com.amazing;

import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
	//Variables
	private static final byte[] keyBytes = "BestPasSword2018".getBytes(); //Set the secret key
	private static final SecretKeySpec key = new SecretKeySpec(keyBytes, "AES"); //Define the secret key as an "AES" key
	
	public static String encrypt(String input_s) throws Exception { //Encrypts the string
		//Cipher
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); 						//Define the Cipher
		cipher.init(Cipher.ENCRYPT_MODE, key);											//Cipher mode
		
		//Data
		String encodedString = Base64.getEncoder().encodeToString(input_s.getBytes()); 	//Encode the string to byte64
		byte[] input = encodedString.getBytes(); 										//Get bytes of the input
			
		//Returns string
		return "" + cipher.getOutputSize(input.length);
	}
	
	public static String decrypt(String input_s) throws Exception {	//Decrypts the string
		//Cypher
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");						//Define the Cipher
		cipher.init(Cipher.DECRYPT_MODE, key);											//Cipher mode

		//Data
		byte[] input = cipher.doFinal(input_s.getBytes());
		
		
		//Returns String
		return "" + Base64.getDecoder().decode(input);
	}
	
}