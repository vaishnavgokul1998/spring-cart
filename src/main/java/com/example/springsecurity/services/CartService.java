package com.example.springsecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springsecurity.models.Cart;

@Service
public interface CartService {
 
	Cart findByUserIdAndBookId(Long userId,Long bookId);
	
	Cart save(Cart cart);
	
	List<Cart> findByUserId(Long id);
	
	Optional<Cart> findById(Long id);
}
