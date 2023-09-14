package com.digistore.product.model;

import java.io.Serializable;

/**
 * 
 * @author kumar-sand
 *
 */
public class ProductDTO implements Serializable {
	
	private static final long serialVersionUID = 0l;
	
	private long id;
	private String name;
	private String productType;
	private String returnable;
	private Float price;
	private Float weight;
	private String brand;
	private String message;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getReturnable() {
		return returnable;
	}
	public void setReturnable(String returnable) {
		this.returnable = returnable;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
