package com.milky.trackerWeb.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Document(collection = "product")
@Component
@CompoundIndexes({
    @CompoundIndex(name = "uniqueIndex", def = "{'location': 1, 'productName': 1, 'type': 1}", unique = true)
})
public class Product {
	@Id
    private ObjectId _id;
	@Field("email")
	private String email;
	@Field("userName")
	private String userName;
	@Field("productName")
	private String productName;
	@Field("type")
	private String type;
	@Field("location")
	private String location;
	@Field("quantity")
	private int quantity;
	
	public String getUname() {
		return userName;
	}

	public void setUname(String userName) {
		this.userName = userName;
	}
	
	public String getName() {
		return productName;
	}

	public void setName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

	public Product() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Product [_id=" + _id + ", email=" + email + ", userName=" + userName + ", productName=" + productName
				+ ", type=" + type + ", location=" + location + ", quantity=" + quantity + "]";
	}
	

}