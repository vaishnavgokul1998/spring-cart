package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

}
