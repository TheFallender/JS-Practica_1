package com.amazing;

public class Product { //Product class
	
	private int id; 				//ID of the Product
	private String name; 			//Name of the Product
	private String category_id; 	//Category ID of the Product
	private float price; 			//Price of the Product
	private int stock; 				//Stock of the Product
	
	protected Product() { //Product basic Constructor 
		//Request data
		this.id = Filter.filter_i("Id of the product: ", 0, 0); 			//Request Product ID
		this.name = Filter.filter_s("Name of the product: "); 				//Request Product Name
		this.category_id = Filter.filter_s("Category of the product: "); 	//Request Product Category
		this.price = Filter.filter_f("Value of the product (€): ", 0 , 0, 2); 	//Request Product Price
		this.stock = Filter.filter_i("Stock of this product: ", 0 , 0); 	//Request Product Stock
	}
	
	protected Product(String[] data) { //Product data Consturctor
		//Set the data
		this.id = Integer.parseInt(data[0]); 		//Set Product ID
		this.name = data[1]; 						//Set Name Id
		this.category_id = data[2]; 				//Set Product Category
		this.price = Float.parseFloat(data[3]); 	//Set Product Price
		this.stock = Integer.parseInt(data[4]); 	//Set Product Stock
	}

	protected int r_id () { //Return Product ID
		return this.id;
	}
	
	protected String r_name () { //Return Product Name
		return this.name;
	}
	
	protected String r_category () { //Return Product Category
		return this.category_id;
	}
	
	protected float r_price () { //Return Product Price
		return this.price;
	}
	
	protected int r_stock () { //Return Product Stock
		return this.stock;
	}
	
	protected void print () { //Print Product
		//Print
		System.out.println("Product:\n\n"); 												//Product
		System.out.println("Name: " + this.name); 												//Print Product name
		System.out.println("ID: " + this.id); 													//Print Product name
		System.out.println("Category: " + this.category_id); 									//Print Product name
		if (Amazing.dollar_a) //Check if it has the dollar currency
			System.out.println("Price: " + Converter.decimal_conv(this.price * Amazing.eur_dollar, 2) + "$"); 				//Print the Product Price
		else //The currency is the euro
			System.out.println("Price: " + this.price + "€"); 									//Print the Product Price
		System.out.println("Stock: " + this.stock); 											//Print the number of items available
	}
	
	protected String compare() { //Compare Products
		//Auxiliar String
		String aux = ""; //Auxiliar String
		
		//Save String
		aux += this.id + "/"; 									//Save ID
		aux += this.name + "/"; 								//Save Name
		aux += this.category_id + "/"; 							//Save Category
		if (Amazing.dollar_a) //Check if it has the dollar currency
			aux += "" + Converter.decimal_conv(this.price * Amazing.eur_dollar, 2) + "$/"; 		//Save Price
		else //The currency is the euro
			aux += this.price + "€/"; 							//Save Price
		aux += this.stock; 										//Save Stock
		
		//Return the String
		return aux;
	}

	protected void buy(int n_ordered) { //This method decreases the product stock by the amount entered
		this.stock -= n_ordered;
	}
	
	protected void save() { //Saves the data on the file
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