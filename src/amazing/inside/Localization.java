package amazing.inside;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
	//Region
	private static ArrayList<Locale> locale_list = new ArrayList<>();	//List of the locale
	private static int active_locale = 0;								//Active locale on the list
	
	public static Locale get_a_locale() { //Gets the active locale
		return locale_list.get(active_locale);
	}
	
	public static String get_a_locale_lang() { //Gets the active locale language
		return locale_list.get(active_locale).getLanguage();
	}
	
	public static void set_locale(String lang, String region) { //Sets the locale, if it founds it's set, if not, it's added
		for (int i = 0; i < locale_list.size(); i++) //Search within the locale list
			if (locale_list.get(i).getLanguage().equals(lang)) { //Languages are the same
				active_locale = i; //Update actual language
				return;
			}
		
		add_locale(lang, region); //Add the locale
	}

	public static void add_locale(String lang, String region) { //Adds a new locale
		//Update Locale List
		Locale aux_l = new Locale(lang, region); //Auxiliary locale
		locale_list.add(aux_l); //Adds the locale
		
		//Adds the locale to the file
		String[] aux_s = new String[]{region, "l_lang=" + lang}; 	//Diferent order for easier readding
		IO.write("d_locale", aux_s, true);							//Writes the locale on the file		
	}
	
	public static void set_locale_list() { //Reads the already added locale from the file
		//Reads the locale
		IO.read("d_locale", "", 0, false);
		
		//Add it to the list
		for (int i = 1; i <= IO.data().size()/2; i++) {										//Add locale for each data
			Locale aux_l = new Locale(IO.data().get((i * 2) - 1), IO.data().get((i * 2) - 2));	//Sets the new Locale (Language, Region)
			locale_list.add(aux_l);																//Add locale with the data
		}
	}
	
    public static String get (String type, String prop_key) { //Gets the string from the bundle
        try{ //Tries to check for the bundle
        	//Bundle
            ResourceBundle bundle = ResourceBundle.getBundle("locale." + get_a_locale_lang() + "." + type, get_a_locale()); //Creates the bundle
    
            //Get string
            if (bundle.keySet().contains(prop_key))	//Gets the string from the file
                return bundle.getString(prop_key); //Returns that string
            else //No key found
                return "ERROR - No localization entry found."; //Reports that it couldn't find the key
        }
        catch (Exception e) { //Fails to create the bundle
            return "ERROR - Process of localization failed."; //Reports that it couldn't create the bundle
        }
    }
}
