package com.amazing;

public class Test_IO extends Test { //IO Test class
	@Override
	public void test() { //Test of this class
		//Inside test
		System.out.println("\n\n\n\nIO test:"); //Prints that this is the IO test
		
		
		//Filter test
		System.out.println("\n\nFilter test:"); 								//Prints that the Filter is going to be tested
		//Int
		System.out.println("\n\nFilter int:"); 									//Prints that this is the Filter of the int
		int[] i_lh = new int[2]; 												//Int low high values
		i_lh[0] = Filter.filter_i("Low value to check: ", 0, 0); 					//Get the low value of the int
		i_lh[1] = Filter.filter_i("High value to check: ", 0, 0); 					//Get the high value of the int
		Filter.filter_i("Int to check between values: ", i_lh[0], i_lh[1]); 		//Checks if the value works in between works
		//Float
		System.out.println("\n\nFilter float:"); 								//Prints that this is the Filter of the float
		float[] f_lh = new float[2]; 											//Float low high values
		f_lh[0] = Filter.filter_f("Low value to check: ", 0, 0); 					//Get the low value of the float
		f_lh[1] = Filter.filter_f("High value to check: ", 0, 0); 					//Get the high value of the float
		Filter.filter_f("Float to check between values: ", f_lh[0], f_lh[1]); 		//Checks if the value works in between works
		//String
		System.out.println("\n\nFilter string:"); 								//Prints that this is the Filter of the string
		Filter.filter_s("String to get: "); 									//Gets a string
		
		
		//IO Class test
		System.out.println("\n\nWrite and read tests:\n"); 										//Prints that the files are going to be written and read
		IO.data_check(); 																		//Checks and creates the files
		System.out.println("Saves and reads a file:\n"); 										//Prints that is going to print the data on saved
		String[] d = new String[] {"test_info=" + Filter.filter_s("Info you want to save: ")}; 	//String to pass as Data
		System.out.println("Checks file write:\n"); 											//Prints that data is going to be written
		IO.write("d_test", d, false); 																//Creates a test file
		System.out.println("Checks file read:"); 												//Prints that data is goin to be read
		IO.read("d_test", "", 1, false); 															//Read Test file
		super.print_data(); 																	//Print the data from the data array
		
		
		//Phase completed
		super.incPhase(); //Increase the phase
	}
}
