package amazing.amazing;

import amazing.inside.Filter;
import amazing.inside.IO;

public class Category { //Category class
	
	private String name; //Name of the Category

	public Category() { //Category basic Constructor
		this.name = Filter.filter_s("Name of the new category: "); //Gets the desired name
	}
	
	public Category(String input) { 	//Category data Consturctor
		this.name = input; 					//Set name to the input
	}
	
	public String r_name() { //Return the Category name
		return this.name;
	}
	
	public void save() { //Saves the data on the file
		//String
		String[] aux = new String[] {"category=" + this.name}; //Auxiliar string to save
		
		//Write data
		IO.write("d_category", aux, true); //Writes the data on the file
	}
}
