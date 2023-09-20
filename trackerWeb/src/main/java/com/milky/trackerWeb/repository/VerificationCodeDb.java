package com.milky.trackerWeb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.milky.trackerWeb.model.VerificationCode;

public interface VerificationCodeDb extends MongoRepository<VerificationCode,String>{
	Optional<VerificationCode> findByPhoneNumber(String phoneNumber);
	Optional<VerificationCode> findByEmail(String email);
	boolean existsByPhoneNumber(String phoneNumber);
	void deleteByPhoneNumber(String phoneNumber);
	boolean existsByEmail(String email);
	void deleteByEmail(String email);

}
