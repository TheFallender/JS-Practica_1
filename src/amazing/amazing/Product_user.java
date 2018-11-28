package amazing.amazing;

import amazing.inside.IO;
import amazing.inside.Localization;

public class Product_user { //Product User class
	
	private String user_id;			//User ID
	private int product_id; 		//Product ID
	private String product_name; 	//Product Name
	private String product_price; 	//Product price
	private String product_t_price; //Product total price
	private int number_items; 		//Number of ordered items
	
	public Product_user(String[] data) { //Product User constructor
		//Set the data
		this.user_id = data[0]; 						//Set User ID
		this.product_id = Integer.parseInt(data[1]); 	//Set Product ordered ID
		this.product_name = data[2]; 					//Set Product ordered Name
		this.product_price = data[3]; 					//Set Product Price at the moment of the order
		this.number_items = Integer.parseInt(data[4]); 	//Set Number of ordered items
		this.product_t_price = data[5];					//Set the total price value
	}

	public void print() { //Print the Product User
		//Print
		System.out.println(Localization.get("objects", "pu_print_bs")); 									//Order
		System.out.println(Localization.get("objects", "pu_print_n") + this.product_name); 			//Print Product name
		System.out.println(Localization.get("objects", "pu_print_val") + this.product_price); 				//Print the Product Price
		System.out.println(Localization.get("objects", "pu_print_amt") + this.number_items); 		//Print the number of items ordered of this Product
		System.out.println(Localization.get("objects", "pu_print_tval") + this.product_t_price + "\n\n"); 		//Print the number of items ordered of this Product
	}
	
	public void save() { //Save the data on the file
		//String
		String[] aux = new String[6]; 				//Auxiliar string to save
		aux[0] = "pu_u_id=" + this.user_id; 			//Set User ID
		aux[1] = "pu_p_id=" + this.product_id; 			//Set Product ordered ID
		aux[2] = "pu_p_name=" + this.product_name; 		//Set Product ordered Name
		aux[3] = "pu_p_price=" + this.product_price; 	//Set Product Price at the moment of the order
		aux[4] = "pu_number=" + this.number_items; 		//Set Number of ordered items
		aux[5] = "pu_p_tprice=" + this.product_t_price; 		//Set Number of ordered items
		
		//Write data
		IO.write("d_product_user", aux, true); //Writes the data on the file
	}
}
