package com.amazing;

import java.net.*;
import java.io.*;

public class Conversor {
	private static float last_value = 1.14f;
	
    public static float factor(String from_c, String to_c) { //Gets the latest ratio from the net
    	try {
    		//Reads the data from the net
            URL conversor_site = new URL("http://currencies.apps.grandtrunk.net/getlatest/" + from_c + "/" + to_c); //Selects the currency
            BufferedReader r = new BufferedReader(new InputStreamReader(conversor_site.openStream())); //Prepares to read the website
            String line = r.readLine(); //Line that gets from the site
            r.close(); 
            
            //Creates the Float to Write
            last_value = Float.parseFloat(line); //Value to return
			
			//Writes the new data
			String data[] = new String[1];
			data[0] = "" + last_value;
			IO.write("d_conversor_rate", data, false);
    	}
    	catch (Exception e){ //If there is any error, it checks for the stored value
        	IO.read("d_conversor_rate", "", 1, false); //Read the data from the file
        	
        	//Check the data
        	if (!(IO.data_a[0].equals(""))) { //There is data, change last_value
        		last_value = Float.parseFloat(IO.data_a[0]);
        	}
        	
        	//If there is no data found it returns the default value
    	}
		return last_value;
    }
}
