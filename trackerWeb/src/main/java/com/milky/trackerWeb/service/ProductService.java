package com.milky.trackerWeb.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.milky.trackerWeb.model.Product;
import com.milky.trackerWeb.repository.ProductCustomRepo;
import com.milky.trackerWeb.response.MainResponse;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Component
public class ProductService implements ProductCustomRepo {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    MongoTemplate primaryMongoTemplate;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Product> findAllByEmail(String email) {
        final List<Product> product = new ArrayList<>();

        // Instead of getting the database and collection from the MongoClient, use the primaryMongoTemplate
        MongoCollection<Document> collection = primaryMongoTemplate.getCollection("product");
        Bson query = Filters.eq("email", email); // Use Filters.eq to create the equality query
        FindIterable<Document> result = collection.find(query);

        result.forEach(doc -> product.add(converter.read(Product.class, doc)));
        System.out.println(product);
        return product;
    }

	public MainResponse save(Product product) {
		// TODO Auto-generated method stub
		return null;
	}
}
