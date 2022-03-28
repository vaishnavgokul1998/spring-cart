package com.example.springsecurity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springsecurity.models.Book;

@Service
public interface BookService {
  
	List<Book> getBooksList();
	
	Book getBookById(Long id);
}
