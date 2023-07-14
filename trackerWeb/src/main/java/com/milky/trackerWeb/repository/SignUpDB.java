package com.milky.trackerWeb.repository;


import org.springframework.data.mongodb.repository.MongoRepository;


import com.milky.trackerWeb.model.SignUp;

public interface SignUpDB extends MongoRepository<SignUp,String> {
//	@Query("{'email': ?0, 'verificationCode': ?1}")
//    SignUp findByEmailAndVerificationCode(String email, String verificationCode);

}
