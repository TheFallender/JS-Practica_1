package com.amazing;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Converter {
    public static float factor(String from_c, String to_c) { //Gets the latest ratio from the net
    	//Set default value
    	float last_value;
    	
    	//Read the data from the file
    	IO.read("d_converter_rate", "", 2, false); //Read the data from the file
    	
    	//Set the value
    	if (IO.data_a[0] == null) //There is no data found in the file
        	try { //Tries to read the data from the net
        		//Reads the data from the net
                URL converter_site = new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_c + "/" + to_c); 	//Selects the currency
                last_value = url_read(converter_site); 																		//Value from the method
        	}
        	catch (Exception e){ 	//If there is any error, it sets the default value.
        		last_value = 1.14f; 	//Default value
        	}
    	else //Data found on the file
    		if (date() >= Long.parseLong(IO.data_a[0]) + 1) //Checks if it has been at least 1 hour since the last check
            	try { //Tries to read the data from the net
            		//Reads the data from the net
                    URL converter_site = new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_c + "/" + to_c); //Selects the currency
                    last_value = url_read(converter_site); 																	//Value from the method
            	}
            	catch (Exception e){ //If there is any error, it checks for the stored value
            		last_value = Float.parseFloat(IO.data_a[1]); //Stored value
            	}
        	else //Use stored value
        		last_value = Float.parseFloat(IO.data_a[1]); //Stored value
    	
    	//Return value
		return last_value;
    }
    
    private static float url_read(URL site) { //Access and reads the website
    	//String to return
    	String line = "";
    	
    	//Read data
    	try(BufferedReader r = new BufferedReader(new InputStreamReader(site.openStream()))){ //Read from the website
	        line = r.readLine(); //Line that gets from the site
    	}
    	catch (Exception e) { //Unexpected error
    		System.out.print("ERROR - Couldn't create the Reader"); //Reports that there was an error creating the reader
    	}
    	
        //Value to return from the string
        float val = Float.parseFloat(line); //Value to return
		
		//Sets the string
		String[] data = new String[2]; 				//Data to pass to the file
		data[0] = "latest_check=" + date(); 		//Set the Check Date
		data[1] = "latest_converter_rate=" + val; 	//Set the Converter Rate
		
		//Writes
		IO.write("d_converter_rate", data, false); 	//Write the data on the file
		
    	//Return value
		return val;
    }
    
    protected static long date() { //Date get and format
    	//Date set
    	DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyyMMddHH"); 	//Date format to set
    	LocalDateTime date = LocalDateTime.now(); 									//Set the date now

    	//Return value
    	return Long.parseLong(date_format.format(date)); //Date with the defined format
    }
}
