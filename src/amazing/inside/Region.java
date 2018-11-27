package amazing.inside;

import java.util.ArrayList;

public class Region {
	//Region
	private static ArrayList <String> region = new ArrayList<>();	//List of the regions
	private static int active_region = 0;							//Active region on the list
	
	public static String get_region() { 		//Gets the actual region
		return region.get(active_region);
	}
	
	public static void set_ar(int new_pos) { 	//Sets the new actual region
		active_region = new_pos;
	}
	
	public static void region_list() {			//Sets the region list based on the file
		IO.read("d_region", "", 0, false);
		
		for (int i = 0; i < IO.data().size(); i++) 	//Add region for each data
			region.add(IO.data().get(i));				//Add region with the data
	}
	
	public static void region_add(String new_region) { //Add region based on the string
		//Update Region list
		region.add(new_region);
		
		String[] string_data = new String[]{new_region};
		
		//Write on file
		IO.write("d_region", string_data, true);
	}
}