package com.example.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

	@Query("Select a from WishList a where a.user.id=?1 and a.book.id=?2")
	WishList findByWishList(Long user,Long book);
	
	@Query("Select a from WishList a where a.user.id=?1 and a.isLiked='YES'")
	List<WishList> findWishListByUser(Long user);
}
