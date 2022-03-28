package com.example.springsecurity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springsecurity.models.WishList;

@Service
public interface WishListService {

	List<WishList> findWishListByUser(Long userId);
	
	WishList findByWishList(Long userid, Long bookId);
	
	void save(WishList wishlist);
}
