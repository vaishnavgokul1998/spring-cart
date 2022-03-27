package com.example.springsecurity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.Book;
import com.example.springsecurity.models.Cart;
import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.models.User;
import com.example.springsecurity.repository.BooksRepository;
import com.example.springsecurity.repository.CartRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.requestVO.CartRequestVo;
import com.example.springsecurity.requestVO.CartResponseVo;
import com.example.springsecurity.requestVO.LoginRequestVO;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@RequestMapping(value = "api/cart/addBookToCart", method = RequestMethod.POST)
	public ResponseEntity<Response> createAuthenticationToken(@RequestBody CartRequestVo cartReq) throws Exception {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		if (cartReq.getBookId() == null) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Bad request");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userRepository.findUserByEmail(username);
		Book book = booksRepository.getOne(cartReq.getBookId());

		if (user == null) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("User not found");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		if (book == null) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Book not found");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		Cart cart = cartRepository.findByCart(user.getId(), book.getId());
		if (cart == null) {
			cart = new Cart();
			cart.setBook(book);
			cart.setUser(user);
			cart.setQuantity(1);
			cart.setStatus("A");
		} else {		
			if(cart.getStatus().equalsIgnoreCase("C")) {
				cart.setId(cart.getId());
				cart.setBook(book);
				cart.setUser(user);
				cart.setQuantity(1);
				cart.setStatus("A");
			}else {
				resCode.setCode(KeyWords.FAILURE_CODE);
				resCode.setStatus(KeyWords.FAILURE);
				resCode.setMessage("Book already in cart");
				res.setResponseCode(resCode);
				return new ResponseEntity<Response>(res, HttpStatus.OK);
			}

		}

		cartRepository.saveAndFlush(cart);
		resCode.setCode(KeyWords.SUCCESS_CODE);
		resCode.setStatus(KeyWords.SUCCESS);
		resCode.setMessage("Book added to cart");
		res.setResponseCode(resCode);

		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "api/cart/getCartItemsByUser", method = RequestMethod.GET)
	public ResponseEntity<Response> getCartItemsByUser() {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userRepository.findUserByEmail(username);

		List<Cart> cartList = cartRepository.findByUser(user.getId());

		if (cartList == null || cartList.size() == 0) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Cart not found");
		} else {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setStatus(KeyWords.SUCCESS);
			resCode.setMessage("Cart details");
			List<CartResponseVo> cartResponseVos = new ArrayList<>();
			for(Cart list:cartList) {
				CartResponseVo cvo = new CartResponseVo();
				cvo.setId(list.getId());
				cvo.setBook(list.getBook());
				cvo.setQuantity(list.getQuantity());
				cvo.setStatus(list.getStatus());
				cartResponseVos.add(cvo);
				
			}
			res.setResponseBody(cartResponseVos);
		}
		res.setResponseCode(resCode);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "api/cart/updateCartItem/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateCartItem(@RequestBody CartRequestVo cartReq, @PathVariable Long id) {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();

		Optional<Cart> cart = cartRepository.findById(id).map(response -> {
			if(response.getStatus().equalsIgnoreCase("A")) {
				response.setQuantity(cartReq.getQuantity());
				resCode.setCode(KeyWords.SUCCESS_CODE);
			}else {
				resCode.setCode(KeyWords.FAILURE_CODE);
			}
			return cartRepository.save(response);
		});

		if (cart == null || cart.isPresent() == false || resCode.getCode() == KeyWords.FAILURE_CODE) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Cart not found");
		} else {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setStatus(KeyWords.SUCCESS);
			resCode.setMessage("Cart details updated");
		}
		res.setResponseCode(resCode);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "api/cart/deleteCartItem/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteCartItem(@PathVariable Long id) {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
				
		Optional<Cart> cart = cartRepository.findById(id).map( response ->{
			if(response.getStatus().equalsIgnoreCase("A")) {
				response.setStatus("C");
				resCode.setCode(KeyWords.SUCCESS_CODE);
			}else {
				resCode.setCode(KeyWords.FAILURE_CODE);
			}
			return cartRepository.save(response);
		});
		
		if(cart == null || cart.isPresent() == false) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Cart not found");		
		}else if(resCode.getCode() == KeyWords.FAILURE_CODE) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Cart already deleted");		
		}else {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setStatus(KeyWords.SUCCESS);
			resCode.setMessage("Cart details deleted");
		}
		res.setResponseCode(resCode);
		return new ResponseEntity<Response>(res,HttpStatus.OK);

	}
}
