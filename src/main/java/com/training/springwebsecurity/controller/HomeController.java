package com.training.springwebsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.training.springwebsecurity.service.MyService;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Controller
public class HomeController {
	
	@Autowired
	MyService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String privateHome() {
		service.getAdminDetails();
		//service.getUserDetails();
		return "privatePage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/csrf", method = RequestMethod.GET)
	public String csrf() {
		return "csrf";
	}
	
	@RequestMapping(value = "/csrfSubmit", method = RequestMethod.POST)
	public String csrfSubmit(String name) {
		System.out.println("Processing the csrfSubmit Request: "+name);
		return "/csrf";
	}

}
