package com.microservice.productmanagementservice;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.productmanagementservice.exceptions.ResourceNotFoundException;

@Service
public class ProductManagementService {
	
	@Autowired
	private ProductManagementRepository repository;
	
	@Autowired
	EntityManager em;
	
	public ProductManagementService(ProductManagementRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Product> getAllProducts(){
		return (List<Product>) repository.findAll();
	}
	
	public Optional<Product> getProductById(long id) {
		Optional<Product> productbyId = repository.findById(id);
		if(productbyId.isEmpty()) {
			throw new ResourceNotFoundException("Not found id: " + id);
		}
		return productbyId;
	}
	
	public Optional<Product> getProductByName(String name) {
		return repository.findProductByName(name);
	}
	
	@Transactional
	public Product addProduct(Product product) {		
		repository.save(product);
		em.persist(product);	 
		return product;
	}
	
	@Transactional
	public void deleteProduct(long id){
		repository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Not found id: " + id));
			repository.deleteById(id);
	}

}
