package com.amazing;

import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
	//Variables
	private static final byte[] keyBytes = "BestPasSword2018".getBytes(); //Set the secret key
	private static final SecretKeySpec key = new SecretKeySpec(keyBytes, "AES"); //Define the secret key as an "AES" key
	
	public static String encrypt(String input) throws Exception { //Encrypts the string
		//Cipher
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 					//Define the Cipher
		cipher.init(Cipher.ENCRYPT_MODE, key);											//Cipher mode
		
		//Data
		byte[] input_bytes = input.getBytes(); //Gets the bytes from the input
		byte[] encrypted = cipher.doFinal(input_bytes); //Gets the bytes of the string and encrypts them
		
		//Returns string
		return Base64.getEncoder().encodeToString(encrypted); //Returns the encrypted byte array in Base64
	}
	
	public static String decrypt(String input) throws Exception {	//Decrypts the string
		//Cypher
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");						//Define the Cipher
		cipher.init(Cipher.DECRYPT_MODE, key);											//Cipher mode

		//Data
		byte[] s_nt_64 = Base64.getDecoder().decode(input); //Decodes from Base64
		byte[] decrypted = cipher.doFinal(s_nt_64); //Gets the bytes of the input and decrypts them

		//Returns String
		return new String (decrypted); //Returns the string from the bytes of the decrypted input
	}
	
}