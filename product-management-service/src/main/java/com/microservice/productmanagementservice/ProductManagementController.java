package com.microservice.productmanagementservice;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductManagementController {
	
	@Autowired
	private ProductManagementService service;
	
	public ProductManagementController(ProductManagementService productManagementService) {
		this.service = productManagementService;
	}

	@GetMapping("/products")
	public List<Product> retrieveAllProducts() {
		return service.getAllProducts();
	}
	
	@GetMapping("/products/id/{id}")
	public Optional<Product> retrieveProductById(@PathVariable long id) {
		return service.getProductById(id);
	}

	@GetMapping("/products/name/{name}")
	public Optional<Product> retrieveProductByName(@PathVariable String name) {
		return service.getProductByName(name);
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product savedProduct = service.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedProduct.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/products/{id}")
	public void retrieveProduct(@PathVariable long id) {
		service.deleteProduct(id);
	}
	
}
