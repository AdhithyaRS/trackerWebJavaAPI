package com.milky.trackerWeb.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.milky.trackerWeb.model.Customer;

public interface CustomerDb extends MongoRepository<Customer, String>{
	boolean existsByPhoneNumber(String phoneNumber);
	boolean existsByEmail(String email);
	Optional<Customer> findByPhoneNumber(String phoneNumber);
	Optional<Customer> findByEmail(String phoneNumber);

}
