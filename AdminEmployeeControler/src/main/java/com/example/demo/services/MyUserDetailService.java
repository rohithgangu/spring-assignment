package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;

@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private AdminService adminService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Admin admin = adminService.getAdminauth(username);
		return new User(admin.getAdminName(),admin.getAdminPassword(),new ArrayList<>());
	}
}
