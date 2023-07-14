package com.milky.trackerWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Product;
import com.milky.trackerWeb.service.ProductService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/dashboard/{email}")
	@CrossOrigin
	public List<Product> getAllProduct(@PathVariable String email){
	    System.out.println(email + "testing");
	    return productService.findAllByEmail(email);
	}
	
//	@GetMapping(value = "/dashboard/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Product>> getAllProduct(@PathVariable String email) {
//	    // ...
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
//	    return ResponseEntity.ok().headers(headers).body(productService.findAllByUname(email));
//	}

}
