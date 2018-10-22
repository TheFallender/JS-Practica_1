package com.amazing;

import java.util.Scanner;

public class filter {
	public static Scanner scan = new Scanner(System.in); //Scanner definition
	protected static int filter_i(String ask,int low, int high) { //Filters Int input
		
		while (true) {
			System.out.println(ask); //Ask string
			String input = scan.nextLine(); //Scan the next line
			try {  
				int val = Integer.parseInt(input); //Value to return
				if (val >= low && val <= high) { //Check if the integer is valid
					return val;
				}
				else { //Error
					System.out.println("ERROR - The interger has to be between " + low + " and " + high + ".");
				}
			}  
			catch(NumberFormatException e)  { //Catch error
				System.out.println("ERROR - You must enter an integer.");
			}
		}
	}
	protected static String filter_s(String ask) { //Filters Strings
		System.out.println(ask); //Ask string
		String input = scan.nextLine(); //Scan the next line
		return input;
	}
}
