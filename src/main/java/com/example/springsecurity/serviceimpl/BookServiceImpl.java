package com.example.springsecurity.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springsecurity.models.Book;
import com.example.springsecurity.repository.BooksRepository;
import com.example.springsecurity.services.BookService;

@Component
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BooksRepository booksRepository;

	@Override
	public List<Book> getBooksList() {
		// TODO Auto-generated method stub
		List<Book> booksList = booksRepository.findAll();
		return booksList;
	}

	@Override
	public Book getBookById(Long id) {
		// TODO Auto-generated method stub
		Book book = booksRepository.getOne(id);
		return book;
	}

}
