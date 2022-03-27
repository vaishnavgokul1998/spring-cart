package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.BillDetail;
@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

}
