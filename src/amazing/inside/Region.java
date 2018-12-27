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
	
	public static int get_region_pos() {		//Gets region pos
		return active_region;
	}
	
	public static String get_currency() { 		//Gets the currency of the region
		return region_list.get(active_region)[1];
	}
	
	public static String get_lang() { 		//Gets the currency of the region
		return region_list.get(active_region)[2];
	}
	
	public static void set_ar(int new_pos) { 	//Sets the new actual region
		active_region = new_pos;
		Converter.set_factor(get_currency(), "");				//Sets the new factor
		Localization.set_locale(get_lang(), get_region());		//Sets the new locale
	}
	
	
	public static void region_list() {			//Sets the region list based on the file
		IO.read("d_region", "", 0, false);
		
		for (int i = 1; i <= IO.data().size()/3; i++) {	//Add region for each data
			String[] aux_s = new String[3];					//Temp data
			aux_s[0] = IO.data().get((i * 3) - 3);			//Sets the Region
			aux_s[1] = IO.data().get((i * 3) - 2);			//Sets it's currency
			aux_s[2] = IO.data().get((i * 3) - 1);			//Sets it's currency
			region_list.add(aux_s);							//Add region with the data
		}
	}
	
	public static void region_add(String new_region, String currency, String lang, String symb) { //Add region based on the string
		//Check if it exists
		for (int i = 0; i < region_list.size(); i++)	//Search that there isn't any region already
			if(region_list.get(i)[0].equals(new_region))	//If the preset equals the currency selected
				return;
		
		//Update Region list
		String[] aux_s = new String[]{new_region, currency, lang};
		region_list.add(aux_s);
		
		//Set the factor and locale in case it doesn't exist
		Converter.set_factor(currency, symb);
		Localization.set_locale(lang, new_region);
		
		//Data array
		String[] string_data = new String[]{new_region, "r_currency=" + currency, "r_lang=" + lang};
		
		//Write on file
		IO.write("d_region", string_data, true);
	}
	
	public static List<String[]> get_list() { //Returns the region list
		return region_list;
	}
}