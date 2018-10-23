package com.amazing;

public class product_user {
	private int user_id;
	private int product_id;
	private int number_items;
	
	protected product_user(String data[]) {
		user_id = Integer.parseInt(data[0]);
		product_id = Integer.parseInt(data[1]);
		number_items = Integer.parseInt(data[2]);
	}

	protected void print() {
		System.out.println("Product:\n\n");
		io_text.read("d_product", "p_id=" + this.user_id, 1, 1, 0); //Get one data
		System.out.println("Name:" + io_text.data_a[0]);
		System.out.println("Ordered:" + this.number_items);
	}
}
