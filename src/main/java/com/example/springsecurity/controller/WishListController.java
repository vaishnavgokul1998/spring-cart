package com.example.springsecurity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.Book;
import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.models.User;
import com.example.springsecurity.models.WishList;
import com.example.springsecurity.requestVO.WishListResponse;
import com.example.springsecurity.services.BookService;
import com.example.springsecurity.services.UserService;
import com.example.springsecurity.services.WishListService;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishListController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	WishListService wishListService;

	@RequestMapping(value = "api/wishlist/getWishList", method = RequestMethod.GET)
	public ResponseEntity<Response> getWishList() {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userService.findUserByEmail(username);
		List<WishList> wishList = wishListService.findWishListByUser(user.getId());
		
		if (wishList != null && wishList.size() > 0) {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setStatus(KeyWords.SUCCESS);
			resCode.setMessage("Wish List retrived successfully");
			res.setResponseCode(resCode);
			List<WishListResponse> wishListResponseVo = new ArrayList<>();
			for(WishList list:wishList) {
				WishListResponse wvo = new WishListResponse();
				wvo.setId(list.getId());
				wvo.setBookId(list.getBook().getId());
				wishListResponseVo.add(wvo);
				
			}
			res.setResponseBody(wishListResponseVo);
		} else {
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("No data found");
			res.setResponseCode(resCode);
		}

		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "api/wishlist/updateWishList/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateWishList(@PathVariable Long id) {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userService.findUserByEmail(username);
		Book book = bookService.getBookById(id);

		WishList wishlist = wishListService.findByWishList(user.getId(), book.getId());
		String message = "";
		if (wishlist == null) {
			wishlist = new WishList();
			wishlist.setBook(book);
			wishlist.setUser(user);
			wishlist.setIsLiked("YES");
			message = "Book added to wishlist";
		} else {
			String isLiked = wishlist.getIsLiked().equalsIgnoreCase("YES") ? "NO" : "YES";
			wishlist.setBook(book);
			wishlist.setUser(user);
			wishlist.setId(wishlist.getId());
			wishlist.setIsLiked(isLiked);
			message = isLiked.equalsIgnoreCase("YES") ? "Book added to wishlist" : "Book removed from wishlist";
		}

		wishListService.save(wishlist);
		resCode.setCode(KeyWords.SUCCESS_CODE);
		resCode.setStatus(KeyWords.SUCCESS);
		resCode.setMessage(message);
		res.setResponseCode(resCode);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

}
