package amazing.inside;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.*;

public class Converter {
	//Variables
	private static String active_currency; //Which currency is the active one
	private static int active_currency_pos; //Which pos is the active currency
	private static ArrayList <String[]> conv_val = new ArrayList<>(); //Converter Values list
	
	//Get set functions
	public static String get_currency() { //Gets which is the active currency
		return active_currency;
	}
	
	public static float get_factor() { //Gets the factor value of the active currency
		return Float.parseFloat(conv_val.get(active_currency_pos)[2]); //Selects the data from the active currency and it parses it
	}
	
	private static int get_factor_pos(String from_to_c) { //Gets the factor from the array
		for (int i = 0; i < conv_val.size(); i++) //Searches within the list
			if(conv_val.get(i)[0].equals(from_to_c)) //If the preset equals the currency selected
				return i; //Return the value
		return -1; //Error mark
	}
	
	public static void set_factor(String from_to_c) { //Sets the new factor
		int pos = get_factor_pos(from_to_c);
		active_currency = from_to_c;
		if (pos < 0) { //Factor found on the file
			new_factor(from_to_c);
			active_currency_pos = conv_val.size();
		}
		else
			active_currency_pos = pos;
	}
	
	public static void set_conv_list() { //Sets the converter list
		//Get data from file
		IO.read("d_converter_rate", "", 0, false);
		
		//Get Converter list
		for(int i = 1; i <= IO.data().size()/3; i++) { //Search between the created list
			//Null prevent
			if (IO.data().get((i*3) - 3) == null)
				break;
			
			//Add to the list
			String[] conv_aux = new String[3];
			conv_aux[0] = IO.data().get((i * 3) - 3);	//Sets the currency
			conv_aux[1] = IO.data().get((i * 3) - 2);	//Sets the date check
			conv_aux[2] = IO.data().get((i * 3) - 1);	//Sets the value
			conv_val.add(conv_aux);					//Adds the auxiliary converter
		}
		
		//Updates the obsolete values
		update_data();
	}
	
	public static void update_data() { //Updates all the data from the list and the file
		long actual_date = date();
		for (int i = 0; i < conv_val.size(); i++) //Searches within the list
			if (actual_date >= Long.parseLong(conv_val.get(i)[1]) + 48) { //Checks if it has been at least 1 hour since the last check
	        	try { //Tries to read the data from the net
	        		//Converter update
	        		conv_val.get(i)[1] = "" + actual_date;																					//Actual date set
	                conv_val.get(i)[2] = "" + url_read(new URL("http://currencies.apps.grandtrunk.net/getlatest/" + conv_val.get(i)[0])); 	//Updated value from the net
	                
	                //Modify values
	                IO.modify("d_converter_rate", conv_val.get(i), 0);
	        	}
	        	catch (Exception e){ //If there is any error, it checks for the stored value
	        		System.out.println("ERROR - Couldn't get the data from the net."); //Reports that it couldn't get the value
	        	}
	        	
			}
	}
	
    private static void new_factor(String from_to_c) { //Gets the latest ratio from the net   
    	//Set the value
    	try { //Tries to read the data from the net
    		//Sets the string
    		String[] data = new String[3]; 																			//Data to pass to the file
    		data[0] = from_to_c; 																						//Set the Currency
    		data[1] = "conv_date=" + date();		 																	//Set the Check Date
    		data[2] = "conv_val=" + url_read(new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_to_c)); 	//Value from the net
    		
     		//Writes the data
    		IO.write("d_converter_rate", data, true); 	//Write the data on the file
    		set_conv_list();
    	}
    	catch (Exception e){ //Error, no file and no internet
    		System.out.println("ERROR - Couldn't get the data from the net nor from the stored values."); //Reports that it couldn't set the value
    	}
    }
    
    private static float url_read(URL site) { //Access and reads the website
    	try(BufferedReader r = new BufferedReader(new InputStreamReader(site.openStream()))){ //Read from the website
    		return Float.parseFloat(r.readLine()); //Line that gets from the site
    	}
    	catch (IOException e) { //Unexpected error
    		System.out.print("ERROR - Couldn't create the Reader"); //Reports that there was an error creating the reader
    		return 0;
    	}
    }
    
    public static long date() { //Date get and format
    	//Date set
    	DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyyMMddHH"); 	//Date format to set
    	LocalDateTime date = LocalDateTime.now(); 									//Set the date now

    	//Return value
    	return Long.parseLong(date_format.format(date)); //Date with the defined format
    }
    
    public static float decimal_conv(float original_price, int decimal_places) { //Returns a formated price with the asked decimal places
    	String format = "%." + decimal_places + "f"; //Format to follow
    	return Float.parseFloat(String.format (format, original_price)); //Format the string
    }
}
