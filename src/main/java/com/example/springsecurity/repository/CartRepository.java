package com.example.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("Select a from Cart a where a.user.id=?1 and a.book.id=?2")
	Cart findByCart(Long user,Long book);
	
	@Query("Select a from Cart a where a.user.id=?1 and a.status='A'")
	List<Cart> findByUser(Long user);
	
	
}
