package com.amazing;

public class product {
	private String category_id;
	private int id;
	private String name;
	private float price;
	private int stock;
	
	protected product() { //Default constructor
		String aux[] = new String[5];
		this.name = filter.filter_s("Name of the product: ");
		this.id = filter.filter_i("Id of the product: ", 0, 1000000);
		this.category_id = filter.filter_s("Category of the product: ");
		this.price = filter.filter_f("Value of the product (€): ", 0 , 10000);
		this.stock = filter.filter_i("Stock of this product: ", 0 , 10000);
		
		aux[0] = this.category_id;
		aux[1] = "" + this.id;
		aux[2] = this.name;
		aux[3] = "" + this.price;
		aux[4] = "" + this.stock;
		
		io_text.write("d_product", aux);
	}
	
	protected product(String data[]) { //
		this.category_id = data[0];
		this.id = Integer.parseInt(data[1]);
		this.name = data[2];
		this.price = Float.parseFloat(data[3]);
		this.stock = Integer.parseInt(data[4]);
	}

	protected int r_id () {
		return this.id;
	}
	
	protected String r_name () {
		return this.name;
	}
	
	protected String r_category () {
		return this.category_id;
	}
	
	protected float r_price () {
		return this.price;
	}
	
	protected int r_stock () {
		return this.stock;
	}
	
	protected void print () {
		System.out.println("Product:\n\n");
		System.out.println("Name: " + this.name);
		System.out.println("Id: " + this.id);
		System.out.println("Category: " + this.category_id);
		if (amazing.dollar_a)
			System.out.println("Price: " + this.price * amazing.dollar + "$");
		else
			System.out.println("Price: " + this.price + "€");
		System.out.println("Stock: " + this.stock);
	}
	
	protected String compare() {
		String aux = "";
		aux += this.category_id + "/";
		aux += this.id + "/";
		aux += this.name + "/";
		if (amazing.dollar_a)
			aux += this.price * amazing.dollar + "$/";
		else
			aux += this.price + "€/";
		aux += this.stock;
		return aux;
	}

	protected void buy(String amount) {
		this.stock -= Integer.parseInt(amount);
	}
}