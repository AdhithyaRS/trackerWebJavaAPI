package com.milky.trackerWeb.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.milky.trackerWeb.model.Login;


public interface LoginDB extends MongoRepository<Login,String>
{
    

}