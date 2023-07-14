package com.milky.trackerWeb.repository;

import java.util.List;

import com.milky.trackerWeb.model.Product;


public interface ProductCustomRepo {
	List<Product> findAllByEmail(String email);
}

