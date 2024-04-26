package com.microservice.productmanagementservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductManagementRepository extends JpaRepository<Product, Long>{

	@Query(value = "SELECT * FROM PRODUCT P where p.product=?1", nativeQuery = true)
	public Optional<Product> findProductByName(String name);
}
