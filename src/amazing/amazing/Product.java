package amazing.amazing;

import amazing.inside.Converter;
import amazing.inside.Filter;
import amazing.inside.IO;
import amazing.inside.Localization;

public class Product { //Product class
	
	private int id; 				//ID of the Product
	private String name; 			//Name of the Product
	private String category_id; 	//Category ID of the Product
	private float price; 			//Price of the Product
	private int stock; 				//Stock of the Product
	
	public Product() { //Product basic Constructor 
		//Request data
		this.id = Filter.filter_i(Localization.get("objects", "pr_crt_id"), 0, 0); 			//Request Product ID
		this.name = Filter.filter_s(Localization.get("objects", "pr_crt_nm")); 				//Request Product Name
		this.category_id = Filter.filter_s(Localization.get("objects", "pr_crt_cat")); 		//Request Product Category
		this.price = Filter.filter_f(Localization.get("objects", "pr_crt_val"), 0 , 0, 2); 	//Request Product Price
		this.stock = Filter.filter_i(Localization.get("objects", "pr_crt_stk"), 0 , 0); 	//Request Product Stock
	}
	
	public Product(String[] data) { //Product data Consturctor
		//Set the data
		this.id = Integer.parseInt(data[0]); 		//Set Product ID
		this.name = data[1]; 						//Set Name Id
		this.category_id = data[2]; 				//Set Product Category
		this.price = Float.parseFloat(data[3]); 	//Set Product Price
		this.stock = Integer.parseInt(data[4]); 	//Set Product Stock
	}

	public int r_id () { //Return Product ID
		return this.id;
	}
	
	public String r_name () { //Return Product Name
		return this.name;
	}
	
	public String r_category () { //Return Product Category
		return this.category_id;
	}
	
	public Float r_price () { //Return Product Price
		return Converter.decimal_conv(this.price * Converter.get_factor(), 2);
	}
	
	public Float r_raw_price () { //Return Product Price
		return this.price;
	}
	
	public int r_stock () { //Return Product Stock
		return this.stock;
	}
	
	public void print () { //Print Product
		//Print
		System.out.println(Localization.get("objects", "pr_print_bs")); 																									//Product
		System.out.println(Localization.get("objects", "pr_print_n") + this.name); 																								//Print Product name
		System.out.println(Localization.get("objects", "pr_print_id") + this.id); 																								//Print Product name
		System.out.println(Localization.get("objects", "pr_print_cat") + this.category_id); 																					//Print Product name
		System.out.println(Localization.get("objects", "pr_print_val") + Converter.decimal_conv(this.price * Converter.get_factor(), 2) + Converter.get_a_currency_symbol()); 	//Print the Product Price
		System.out.println(Localization.get("objects", "pr_print_stk") + this.stock); 																							//Print the number of items available
	}

	public void buy(int n_ordered) { //This method decreases the product stock by the amount entered
		this.stock -= n_ordered;
	}
	
	public void save() { //Saves the data on the file
		//String
		String[] aux = new String[5]; 					//Auxiliar string to save
		aux[0] = "p_id=" + this.id; 						//Set ID
		aux[1] = "p_name=" + this.name; 					//Set Name
		aux[2] = "p_category=" + this.category_id; 			//Set Category
		aux[3] = "p_price=" + this.price; 					//Set Price
		aux[4] = "p_stock=" + this.stock; 					//Set Stock
		
		//Write data
		IO.write("d_product", aux, true); //Writes the data on the file
	}
}