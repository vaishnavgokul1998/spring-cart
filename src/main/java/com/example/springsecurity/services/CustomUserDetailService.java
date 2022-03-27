package com.example.springsecurity.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface CustomUserDetailService extends UserDetailsService{

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
}
