package com.cognizant.sales.pojo;

public class Item {
	
//	
//	private String id;
	
	private String itemName;
	 
	private int quantity;
	
	private double price;

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	

	public Item() {
		super();
	}

	@Override
	public String toString() {
		return getItemName()+","+getQuantity()+","+getPrice();
	}
	

}
