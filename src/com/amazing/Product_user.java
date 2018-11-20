package com.amazing;

public class Product_user {
	private int product_id;
	private String user_id;
	private int number_items;
	
	protected Product_user(String[] data, boolean b) {
		user_id = (data[0]);
		product_id = Integer.parseInt(data[1]);
		number_items = Integer.parseInt(data[2]);
	}
	
	protected Product_user(String[] data) {
		String[] aux = new String[3];
		product_id = Integer.parseInt(data[0]);
		user_id = (data[1]);
		number_items = Integer.parseInt(data[2]);
		aux[0] = "pu_u_id=" + this.user_id;
		aux[1] = "pu_p_id=" + this.product_id;
		aux[2] = "pu_number=" + this.number_items;
		IO.write("d_product_user", aux, true);
	}

	protected void print() {
		System.out.println("Product:\n\n");
		IO.read("d_product", "p_id=" + this.product_id, 5, false); //Get data
		System.out.println("Name: " + IO.data_a[1]);
		if (Amazing.dollar_a)
			System.out.println("Price: " + Float.parseFloat(IO.data_a[3]) * Amazing.eur_dollar + "$");
		else
			System.out.println("Price: " + IO.data_a[3] + "€");
		System.out.println("Ordered: " + this.number_items + "\n\n");
	}
}
