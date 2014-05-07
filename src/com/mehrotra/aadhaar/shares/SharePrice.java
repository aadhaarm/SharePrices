package com.mehrotra.aadhaar.shares;

public class SharePrice {

	String year;
	
	String month;
	
	Integer price;
	
	public SharePrice(String year, String month, Integer price) {
		super();
		this.year = year;
		this.month = month;
		this.price = price;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
