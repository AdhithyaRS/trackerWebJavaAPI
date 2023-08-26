package com.milky.trackerWeb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.milky.trackerWeb.model.UserIdCounter;

public interface UniqueIDDB extends MongoRepository<UserIdCounter , String>{

}
