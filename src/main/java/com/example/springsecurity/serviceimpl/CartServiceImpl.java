package com.example.springsecurity.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springsecurity.models.Cart;
import com.example.springsecurity.repository.BooksRepository;
import com.example.springsecurity.repository.CartRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.services.CartService;

@Component
public class CartServiceImpl implements CartService{


	@Autowired
	BooksRepository booksRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Override
	public Cart findByUserIdAndBookId(Long userId, Long bookId) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findByCart(userId, bookId);
		return cart;
	}

	@Override
	public Cart save(Cart cart) {
		// TODO Auto-generated method stub
		Cart savedcart = cartRepository.saveAndFlush(cart);
		return savedcart;
		
	}

	@Override
	public List<Cart> findByUserId(Long id) {
		// TODO Auto-generated method stub
		List<Cart> cartList = cartRepository.findByUser(id);
		return cartList;
	}

	@Override
	public Optional<Cart> findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Cart> cart = cartRepository.findById(id);
		return cart;
	}
}
