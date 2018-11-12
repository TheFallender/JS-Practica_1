package com.amazing;

public class Test_IO extends Test {
	@Override
	public void test() {
		//Filter test
		System.out.println(Filter.filter_i("Number to check between values: ", Filter.filter_i("Low value to check:", 0, 0), Filter.filter_i("High value to check:", 0, 0)));
		System.out.println(Filter.filter_f("Number to check between values: ", Filter.filter_f("Low value to check:", 0, 0), Filter.filter_f("High value to check:", 0, 0)));
		System.out.println(Filter.filter_s("String to get: "));
		
		
		//IO Class test
		IO.data_check(); //Checks for the files and if they don't exist it creates them
		IO.files_check(IO.data_path + "d_test", false); //Creates a test file
		String d[] = new String[1]; //String to pass as Data
		d[0] = "test_info=" + Filter.filter_s("Info you want to save: "); //Data to save
		IO.write("d_test", d, false); //Creates a test file
		IO.read("d_test", "", 1, false); //Read Test file
		super.print_data();
		
		
		//Phase completed, 
		super.incPhase();
	}
}
