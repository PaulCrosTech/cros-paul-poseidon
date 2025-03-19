package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model)
	{
		log.info("====> GET / <====");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		log.info("====> GET /admin/home redirect to /bidList/list <====");
		return "redirect:/bidList/list";
	}


}
