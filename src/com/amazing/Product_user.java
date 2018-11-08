package com.amazing;

public class Product_user {
	private int product_id;
	private String user_id;
	private int number_items;
	
	protected Product_user(String data[], boolean nothing) {
		product_id = Integer.parseInt(data[0]);
		user_id = (data[1]);
		number_items = Integer.parseInt(data[2]);
	}
	
	protected Product_user(String data[]) {
		String aux[] = new String[3];
		product_id = Integer.parseInt(data[0]);
		user_id = (data[1]);
		number_items = Integer.parseInt(data[2]);
		aux[0] = "" + this.product_id;
		aux[1] = this.user_id;
		aux[2] = "" + this.number_items;
		IO.write("d_product_user", aux);
	}

	protected void print() {
		System.out.println("Product:\n\n");
		IO.read("d_product", "p_id=" + this.product_id, 3, false); //Get one data
		System.out.println("Name: " + IO.data_a[1]);
		if (Amazing.dollar_a)
			System.out.println("Price: " + Float.parseFloat(IO.data_a[2]) * Amazing.dollar + "$");
		else
			System.out.println("Price: " + IO.data_a[2] + "€");
		System.out.println("Ordered: " + this.number_items + "\n\n");
	}
}
