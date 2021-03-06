package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
