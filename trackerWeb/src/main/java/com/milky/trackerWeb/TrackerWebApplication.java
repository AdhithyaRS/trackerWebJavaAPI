package com.milky.trackerWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.milky.trackerWeb")
public class TrackerWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrackerWebApplication.class, args);
	}

}
