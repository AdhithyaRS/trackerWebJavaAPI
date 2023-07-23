package com.milky.trackerWeb.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.milky.trackerWeb.model.Product;



public interface ProductDB extends MongoRepository<Product,String>
{
	

}