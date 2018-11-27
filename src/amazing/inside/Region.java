package amazing.inside;

import java.util.ArrayList;
import java.util.List;

public class Region {
	//Region
	private static ArrayList<String[]> region_list = new ArrayList<>();	//List of the regions
	private static int active_region = 0;							//Active region on the list
	
	public static String get_region() { 		//Gets the actual region
		return region_list.get(active_region)[0];
	}
	
	public static String get_currency() { 		//Gets the currency of the region
		return region_list.get(active_region)[1];
	}
	
	public static void set_ar(int new_pos) { 	//Sets the new actual region
		active_region = new_pos;
	}
	
	
	public static void region_list() {			//Sets the region list based on the file
		IO.read("d_region", "", 0, false);
		
		for (int i = 1; i <= IO.data().size()/2; i++) {	//Add region for each data
			String[] aux_s = new String[2];					//Temp data
			aux_s[0] = IO.data().get((i * 2) - 2);			//Sets the Region
			aux_s[1] = IO.data().get((i * 2) - 1);			//Sets it's currency
			region_list.add(aux_s);							//Add region with the data
		}
	}
	
	public static void region_add(String new_region, String currency) { //Add region based on the string
		//Check if it exists
		for (int i = 0; i < region_list.size(); i++)	//Search that there isn't any region already
			if(region_list.get(i)[0].equals(new_region)) {	//If the preset equals the currency selected
				active_region = i;
				return;
			}
		
		//Update Region list
		String[] aux_s = new String[]{new_region, currency};
		region_list.add(aux_s);
		
		
		//Data array
		String[] string_data = new String[]{new_region, "r_currency=" + currency};
		
		//Write on file
		IO.write("d_region", string_data, true);
	}
	
	public static List<String[]> get_list() { //Returns the region list
		return region_list;
	}
}