package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.Address;

@Repository
public interface UserAddressRepository extends JpaRepository<Address, Long> {

}
