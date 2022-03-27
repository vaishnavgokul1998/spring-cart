package com.example.springsecurity.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>,JpaRepository<User, Long> {
	
	@Query("Select a from User a where a.email=?1")
	User findDuplicateEmail(String email);
	
	@Query("Select a from User a where a.mobileNo=?1")
	User findDuplicateMobileNo(String mobileNo);
	
	@Query("Select a from User a where a.email=?1")
	User findUserByEmail(String email);
		
	@Query("Select a from User a where a.id=?1")
	User findUserByID(Long id);
	
	@Query("Select a from User a where a.role='USER'")
	List<User> findUserByUserRole();
	
	@Modifying
	@Transactional
	@Query("update User a set a.status = ?1 where a.id = ?2")
	void setStatusValue(String status, Long id);

}
