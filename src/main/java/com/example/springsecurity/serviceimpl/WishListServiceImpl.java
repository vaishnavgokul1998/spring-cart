package com.example.springsecurity.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springsecurity.models.WishList;
import com.example.springsecurity.repository.WishListRepository;
import com.example.springsecurity.services.WishListService;

@Component
public class WishListServiceImpl implements WishListService {

	@Autowired
	WishListRepository wishListRepository;
	
	@Override
	public List<WishList> findWishListByUser(Long userId) {
		// TODO Auto-generated method stub
		List<WishList> wishList = wishListRepository.findWishListByUser(userId);
		return wishList;
	}

	@Override
	public WishList findByWishList(Long userid, Long bookId) {
		// TODO Auto-generated method stub
		WishList wishlist = wishListRepository.findByWishList(userid, bookId);
		return wishlist;
	}

	@Override
	public void save(WishList wishlist) {
		// TODO Auto-generated method stub
		wishListRepository.saveAndFlush(wishlist);
	}

}
