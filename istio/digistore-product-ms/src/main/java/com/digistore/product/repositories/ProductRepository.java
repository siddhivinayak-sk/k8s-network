package com.digistore.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digistore.product.entities.Product;

/**
 * 
 * @author kumar-sand
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("select case when count(p) > 0 then TRUE else FALSE end from Product p where name = ?1")
	boolean existByName(String name);

	@Query("select p from Product p where name = ?1")
	Product findByName(String name);
}
