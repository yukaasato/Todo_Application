package com.todo.base.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloController{
	
	@GetMapping({"/hello"})
	public String hello() {

		return "/hello.html";
		
	}
}


