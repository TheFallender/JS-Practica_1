package com.amazing;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Filter {
	public static Scanner scan = new Scanner(System.in); //Scanner definition
	private static InputStream skipper = new ByteArrayInputStream("".getBytes());
	
	
	protected static int filter_i(String ask,int low, int high) { //Filters Int input		
		while (true) {
			System.out.print(ask); //Ask string
			String input = scan.nextLine(); //Scan the next line
			try {  
				int val = Integer.parseInt(input); //Value to return
				if (low == high) { //No limit
					System.setIn(skipper);
					return val;
				}
				else if (val >= low && val <= high) { //Check if the integer is valid
					System.setIn(skipper);
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
	
	protected static float filter_f(String ask, float low, float high) { //Filters Float input
		while (true) {
			System.out.print(ask); //Ask string
			String input = scan.nextLine(); //Scan the next line
			try {  
				Float val = Float.parseFloat(input); //Value to return
				if (low == high) { //No limit
					System.setIn(skipper);
					return val;
				}
				else if (val >= low && val <= high) { //Check if the integer is valid
					System.setIn(skipper);
					return val;
				}
				else { //Error
					System.out.println("ERROR - The value has to be between " + low + " and " + high + ".");
				}
			}  
			catch(NumberFormatException e)  { //Catch error
				System.out.println("ERROR - You must enter valid value.");
			}
		}
	}
	
	protected static String filter_s(String ask) { //Filters Strings
		System.out.print(ask); //Ask string
		String input = scan.nextLine(); //Scan the next line
		return input;
	}
}
