package com.example.springsecurity.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.services.CustomUserDetailService;

@Component
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
          com.example.springsecurity.models.User user = userRepository
				.findUserByEmail(username);
		if (user != null) {
			List<GrantedAuthority> x = new ArrayList<>();
			return new User(user.getEmail(), user.getPassword(), x);

		} else {
			throw new UsernameNotFoundException("User Name not found");
		}
	}


}
