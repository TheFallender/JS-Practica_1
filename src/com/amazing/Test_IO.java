package com.amazing;

public class Test_IO extends Test {
	@Override
	public void test() {
		//Filter test
		System.out.println("\n\n\n\nIO test:\n\nFilter int:");
		Filter.filter_i("Int to check between values: ", Filter.filter_i("Low value to check: ", 0, 0), Filter.filter_i("High value to check: ", 0, 0));
		System.out.println("\n\nFilter float:");
		Filter.filter_f("Float to check between values: ", Filter.filter_f("Low value to check: ", 0, 0), Filter.filter_f("High value to check: ", 0, 0));
		System.out.println("\n\nFilter string:");
		Filter.filter_s("String to get: ");
		
		
		//IO Class test
		System.out.println("\n\nCheck files exist:\n");
		IO.data_check();
		IO.files_check(IO.data_path + "d_test", false); //Creates a test file
		String d[] = new String[1]; //String to pass as Data
		System.out.println("Checks data save on file:\n");
		d[0] = "test_info=" + Filter.filter_s("Info you want to save: "); //Data to save
		System.out.println("Checks file write:\n");
		IO.write("d_test", d, false); //Creates a test file
		System.out.println("Checks file read:");
		IO.read("d_test", "", 1, false); //Read Test file
		super.print_data();
		
		
		//Phase completed, 
		super.incPhase();
	}
}
