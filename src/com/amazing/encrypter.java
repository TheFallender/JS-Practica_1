package com.amazing;

import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class encrypter {
	//Variables
	private static final byte[] keyBytes = "BestPasSword2018".getBytes();				//Set the secret key
	private static final SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");		//Define the secret key as an "AES" key
	/*
	public static String encrypt(String input_s) throws Exception {					//Encrypts and writes
		//Pre-encryption definitions
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");						//Define the Cypher
		byte[] input = input_s.getBytes();												//Get bytes of the input
		
		//Encryption section
		cipher.init(Cipher.ENCRYPT_MODE, key);											//Start the encryption 
		byte[] e_text = new byte[cipher.getOutputSize(input.length)];					//Get the encrypted text
		int e_text_l = cipher.update(input, 0, input.length, e_text, 0);				//Get the length of the encrypted text
		e_text_l += cipher.doFinal(e_text, e_text_l);									//Updates the text

		//Convert byte[] to String
		String e_text_f = new String (e_text);											//Redefines the e_text_f (encrypted text final) based on the bytes of e_text
		
		//Returns string
		return e_text_f;
	}
	
	public static String decrypt(String input_s) throws Exception {					//Encrypts and writes
		//Pre-decryption definitions
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Add provider
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");						//Define the Cypher
		String d_text = "";																//String to return
		
		//Decryption section
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(input_s.getBytes());
		d_text = new String(decrypted);	
		
		//Returns String
		return d_text;
	}
	*/
}