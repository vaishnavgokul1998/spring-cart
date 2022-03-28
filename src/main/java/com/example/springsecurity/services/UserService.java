package com.example.springsecurity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springsecurity.models.User;
import com.example.springsecurity.requestVO.UserDetailVO;

@Service
public interface UserService {

	List<User> getUsers();
	
	List<UserDetailVO> prepareForm(List<User> users);
	
	User findUserByEmail(String email);
	
	boolean changeStatus(Long id);
	
	boolean changeStatusValue(Long id);
}
