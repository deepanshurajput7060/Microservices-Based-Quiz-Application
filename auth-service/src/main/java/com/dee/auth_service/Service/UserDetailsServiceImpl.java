package com.dee.auth_service.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dee.auth_service.Entity.User;
import com.dee.auth_service.Repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		
		return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
