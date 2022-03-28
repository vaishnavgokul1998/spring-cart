package com.example.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.Book;
import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.services.BookService;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

	@Autowired
	private BookService bookService;


	@RequestMapping(value = "api/books/getBooks", method = RequestMethod.GET)
	public ResponseEntity<Response> getAllBooksList() {

		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		List<Book> booksList = bookService.getBooksList();

		if (booksList != null && booksList.size() > 0) {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setStatus(KeyWords.SUCCESS);
			resCode.setMessage("List retrived successfully");
			res.setResponseCode(resCode);
			res.setResponseBody(booksList);
		} else {
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("No data found");
			res.setResponseCode(resCode);
		}

		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}

}
