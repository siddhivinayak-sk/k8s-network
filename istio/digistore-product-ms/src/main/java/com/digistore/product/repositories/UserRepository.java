package com.digistore.product.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digistore.product.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
