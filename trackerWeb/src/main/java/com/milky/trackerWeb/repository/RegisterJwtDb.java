package com.milky.trackerWeb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.milky.trackerWeb.model.RegisterJwt;

public interface RegisterJwtDb extends MongoRepository<RegisterJwt, String> {
	boolean existsByPhoneNumber(String phoneNumber);
	void deleteByPhoneNumber(String phoneNumber);
	boolean existsByEmail(String email);
	void deleteByEmail(String email);
	Optional<RegisterJwt> findByPhoneNumber(String phoneNumber);
	Optional<RegisterJwt> findByEmail(String email);
	

}
