package com.milky.trackerWeb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.milky.trackerWeb.model.Retailer;

public interface RetailerDb extends MongoRepository<Retailer, String> {
	boolean existsByPhoneNumber(String phoneNumber);
	Optional<Retailer> findByPhoneNumber(String phoneNumber);
	Optional<Retailer> findByEmail(String phoneNumber);

}
