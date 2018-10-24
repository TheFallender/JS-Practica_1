package com.amazing;

public class product {
	private String category_id;
	private int id;
	private String name;
	private float price;
	private int stock;
	
	protected int r_id () {
		return this.id;
	}
	
	protected String r_name () {
		return this.name;
	}
}
