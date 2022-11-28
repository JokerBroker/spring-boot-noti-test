package com.karthik.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "stocks")
public class Stock {
	@Id
	private String stock;
	public String getStock() {
		return stock;
	}
	private int price;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
