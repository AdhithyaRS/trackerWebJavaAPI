package com.milky.trackerWeb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class PrimaryMongoConfig extends AbstractMongoClientConfiguration  {
	
    @Value("${spring.data.mongodb.primary.uri}")
    private String primaryUri;

    @Value("${spring.data.mongodb.primary.database}")
    private String primaryDatabase;
	@Override
	protected String getDatabaseName() {
		return primaryDatabase;
	}
	
	@Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(primaryUri);
        return MongoClients.create(connectionString);
    }

    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(mongoClient(), primaryDatabase);
    }

}
