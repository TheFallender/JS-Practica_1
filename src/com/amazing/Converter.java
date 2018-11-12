package com.amazing;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Converter {
	private static float last_value = 1.14f;
	
    public static float factor(String from_c, String to_c) { //Gets the latest ratio from the net
    	IO.read("d_converter_rate", "", 2, false); //Read the data from the file
    	
    	//
    	if (IO.data_a[0].equals("")) { //There is no data found in the file
        	try {
        		//Reads the data from the net
                URL converter_site = new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_c + "/" + to_c); //Selects the currency
                BufferedReader r = new BufferedReader(new InputStreamReader(converter_site.openStream())); //Prepares to read the website
                String line = r.readLine(); //Line that gets from the site
                r.close(); 
                
                //Creates the Float to Write
                last_value = Float.parseFloat(line); //Value to return
    			
    			//Writes the new data
    			String data[] = new String[2];
    			data[0] = "latest_check=" + date();
    			data[1] = "latest_converter_rate=" + last_value;
    			IO.write("d_converter_rate", data, false);
        	}
        	catch (Exception e){ //If there is any error, it sets the default value.
        		last_value = 1.14f;
        	}
    	}
    	else {
    		if (date() >= Long.parseLong(IO.data_a[0]) + 1) { //Checks if it has been at least 1 hour since the last check
            	try {
            		//Reads the data from the net
                    URL converter_site = new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_c + "/" + to_c); //Selects the currency
                    BufferedReader r = new BufferedReader(new InputStreamReader(converter_site.openStream())); //Prepares to read the website
                    String line = r.readLine(); //Line that gets from the site
                    r.close(); 
                    
                    //Creates the Float to Write
                    last_value = Float.parseFloat(line); //Value to return
        			
        			//Writes the new data
        			String data[] = new String[2];
        			data[0] = "latest_check=" + date();
        			data[1] = "latest_converter_rate=" + last_value;
        			IO.write("d_converter_rate", data, false);
            	}
            	catch (Exception e){ //If there is any error, it checks for the stored value
            		last_value = Float.parseFloat(IO.data_a[1]);
            	}
        	}
        	else { //Use stored value
        		last_value = Float.parseFloat(IO.data_a[1]);
        	}
    	}
		return last_value;
    }
    
    private static long date() { //Date get and format
    	DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH");
    	LocalDateTime date = LocalDateTime.now();
    	String fixed_date = "" + date_format.format(date);
    	fixed_date = fixed_date.replaceAll("/", "");
    	return Long.parseLong(fixed_date);
    }
}
