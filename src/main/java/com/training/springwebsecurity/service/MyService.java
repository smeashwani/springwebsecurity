package com.training.springwebsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void getAdminDetails() {
		System.out.println("get Admin details....");
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public void getUserDetails() {
		System.out.println("get User details....");
	}

}
