package com.amazing;

public class Test {
	private static int phase = 0; //Which phase is the test located in
	
	protected void test() { //Method to override that, by default, calls all the childs
		//Test calls
		Test_IO t1 = new Test_IO();
		t1.test();
		Test_inside t2 = new Test_inside();
		t2.test();
		Test_data t3 = new Test_data();
		t3.test();
		
		//Finish test
		//Close the scan
		Filter.scan.close();
		
		//Finished test
		System.out.println("\n\nTest finished.");
		
	}

	protected void incPhase() { //Increases the phase
		Test.phase++;
	}
	
	protected int getPhase() { //Get's the actual phase
		return Test.phase;
	}
	
	protected void print_data() {
		for(int i = 0; i < IO.data_c; i++) {
			System.out.println("Data number " + i + ": " + IO.data_a[i]);
		}
	}
	
	protected void throw_exc(String msg, Throwable cause) { //Method to create and throw a new exception based on the error
		System.out.println("The error was located in phase: " + phase + "\nError: "); //Phase error location
		throw new Custom_Exception(msg, cause); //Throw exception
	}
}