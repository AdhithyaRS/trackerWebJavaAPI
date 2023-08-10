package com.milky.trackerWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Product;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.service.JwtUtils;
import com.milky.trackerWeb.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(value = "/dashboard/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProduct(@PathVariable String email, @CookieValue(value = "jwt_token", required = true) String jwtToken){
		try {
			System.out.println("In fetch Products");
            if (!jwtUtils.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            List<Product> products = productService.findAllByEmail(email);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException ex) {
        	System.out.println("In dashboard catch block");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
	
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public MainResponse update(@RequestBody Product product)
    {
		System.out.println(product.toString());
        return productService.save(product);
    }
	
//	@GetMapping(value = "/dashboard/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Product>> getAllProduct(@PathVariable String email) {
//	    // ...
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
//	    return ResponseEntity.ok().headers(headers).body(productService.findAllByUname(email));
//	}

}
