package com.amazing;

public class product_user {
	private int product_id;
	private String user_id;
	private int number_items;
	
	protected product_user(String data[], boolean nothing) {
		product_id = Integer.parseInt(data[0]);
		user_id = (data[1]);
		number_items = Integer.parseInt(data[2]);
	}
	
	protected product_user(String data[]) {
		String aux[] = new String[3];
		product_id = Integer.parseInt(data[0]);
		user_id = (data[1]);
		number_items = Integer.parseInt(data[2]);
		aux[0] = "" + this.product_id;
		aux[1] = this.user_id;
		aux[2] = "" + this.number_items;
		io_text.write("d_product_user", aux);
	}

	protected void print() {
		System.out.println("Product:\n\n");
		io_text.read("d_product", "p_id=" + this.product_id, 3, false); //Get one data
		System.out.println("Name: " + io_text.data_a[1]);
		if (amazing.dollar_a)
			System.out.println("Price: " + Float.parseFloat(io_text.data_a[2]) * amazing.dollar + "$");
		else
			System.out.println("Price: " + io_text.data_a[2] + "€");
		System.out.println("Ordered: " + this.number_items + "\n\n");
	}
}
