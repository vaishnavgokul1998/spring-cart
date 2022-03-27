package com.example.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.requestVO.CartRequestVo;
import com.example.springsecurity.requestVO.OrderRequestVO;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "api/order/placeOrder", method = RequestMethod.POST)
	public ResponseEntity<Response> createAuthenticationToken(@RequestBody OrderRequestVO orderVo) throws Exception {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		if (orderVo.getBookId() == null) {
			resCode.setCode(KeyWords.BAD_REQUEST_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("Bad request");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userRepository.findUserByEmail(username);
		Book book = booksRepository.getOne(orderVo.getBookId());

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



		resCode.setCode(KeyWords.SUCCESS_CODE);
		resCode.setStatus(KeyWords.SUCCESS);
		resCode.setMessage("Book added to cart");
		res.setResponseCode(resCode);

		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}
}
