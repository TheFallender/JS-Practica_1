package com.amazing;

import java.io.File;

public class Test { //Test class
	private static int phase = 1; //Which phase is the test located in
	
	protected void test() { //Method to override that, by default, calls all the childs
		System.out.println("Test system started:"); //Prints that the Test system has started
		
		//Test calls
		Test_IO t1 = new Test_IO(); 		//Creates the IO Test
		t1.test(); 								//Tests IO
		Test_inside t2 = new Test_inside(); //Creates the Inside Test
		t2.test();								//Tests Inside
		Test_data t3 = new Test_data(); 	//Creates the Data Test
		t3.test(); 								//Tests Data
		
		//Closes the scan
		Filter.scan.close();
		
		//Finished test
		System.out.println("\n\nTest finished.\n"); //Prints that the test is finished
		System.out.println( "	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	  ||\r\n" + 
							"	 /||\\\r\n" + 
							"	/||||\\\r\n" + 
							"	======         __|__\r\n" + 
							"	||||||        / ~@~ \\\r\n" + 
							"	||||||       |-------|\r\n" + 
							"	||||||       |_______|\r\n"); //Clean code is the base of programming.
		
		
		//Delete folder
		File folder = new File ("src/Test/"); 			//Folder to delete
		if (folder.exists()) { 							//Checks the folder exists, just for precaution
	        File files[] = folder.listFiles(); 				//Makes a list of the files inside the folder
	        for (int i = 0; i < files.length; i++) 			//For to delete the files
	        	files[i].delete(); 								//Delete the file
	        folder.delete(); 								//Delete the folder
		}
	}

	protected void incPhase() { //Increases the phase
		Test.phase++;
	}
	
	protected int getPhase() { //Get's the actual phase
		return Test.phase;
	}
	
	protected void print_data() { //Prints the data array from IO class
		for(int i = 0; i < IO.data_a.size(); i++) //For to print data
			System.out.println("Data number " + i + ": " + IO.data_a.get(i)); //Prints the data number and it's value
	}
	
	protected void throw_exc(String msg, Throwable cause) { //Method to create and throw a new exception based on the error
		System.out.println("The error was located in phase: " + phase + "\nError: "); //Phase error location
		throw new Custom_Exception(msg, cause); //Throw exception
	}
}