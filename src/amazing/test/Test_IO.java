package amazing.test;

import amazing.inside.Filter;
import amazing.inside.IO;
import amazing.inside.Localization;

public class Test_IO extends Test { //IO Test class
	@Override
	public void test() { //Test of this class
		//Inside test
		System.out.println(Localization.get("test", "test_io")); //Prints that this is the IO test
		
		
		//Filter test
		System.out.print(Localization.get("test", "test_io_fltr")); 								//Prints that the Filter is going to be tested
		//Int
		System.out.println(Localization.get("test", "test_io_fltr_int")); 							//Prints that this is the Filter of the int
		int[] i_lh = new int[2]; 																	//Int low high values
		i_lh[0] = Filter.filter_i(Localization.get("test", "test_io_fltr_low"), 0, 0); 					//Get the low value of the int
		i_lh[1] = Filter.filter_i(Localization.get("test", "test_io_fltr_high"), 0, 0); 				//Get the high value of the int
		Filter.filter_i(Localization.get("test", "test_io_fltr_btw"), i_lh[0], i_lh[1]); 			//Checks if the value works in between works
		
		//Float
		System.out.println(Localization.get("test", "test_io_fltr_flt")); 											//Prints that this is the Filter of the float
		float[] f_lhd = new float[3]; 																				//Float low, high & decimal values
		f_lhd[0] = Filter.filter_f(Localization.get("test", "test_io_fltr_low"), 0, 0, 0); 								//Get the low value of the float
		f_lhd[1] = Filter.filter_f(Localization.get("test", "test_io_fltr_high"), 0, 0, 0); 							//Get the high value of the float
		f_lhd[2] = Filter.filter_i(Localization.get("test", "test_ins_conv_dec_dp"), 0, 0); 							//Decimals places
		Filter.filter_f(Localization.get("test", "test_io_fltr_btw"), f_lhd[0], f_lhd[1], Math.round(f_lhd[2])); 	//Checks if the value works in between works
		
		//String
		System.out.println(Localization.get("test", "test_io_fltr_str")); 								//Prints that this is the Filter of the string
		Filter.filter_s(Localization.get("test", "test_io_fltr_str_get")); 									//Gets a string
		
		
		//IO Class test
		System.out.println(Localization.get("test", "test_io_fltr_wr")); 												//Prints that the files are going to be written and read
		IO.data_check(); 																								//Checks and creates the files
		System.out.println(Localization.get("test", "test_io_fltr_wr_w")); 												//Prints that data is going to be written
		String[] d = new String[] {"test_info=" + Filter.filter_s(Localization.get("test", "test_io_fltr_wr_w_str"))}; 		//String to pass as Data
		IO.write("d_test", d, false); 																					//Creates a test file
		System.out.println(Localization.get("test", "test_io_fltr_wr_r")); 												//Prints that data is going to be read
		IO.read("d_test", "", 1, false); 																				//Reads the Test file
		System.out.println(Localization.get("test", "test_io_fltr_wr_r_pr") + IO.data().get(0));						//Print data
		super.print_data(); 																							//Print the data from the data array
		
		
		//Phase completed
		super.incPhase(); //Increase the phase
	}
}
