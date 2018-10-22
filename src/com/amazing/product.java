package com.amazing;

public class product {
	private int id;
	private String name;
	private int category_id;
	private int stock;
	
	protected int r_id () {
		return this.id;
	}
	
	protected String r_name () {
		return this.name;
	}
}
