package com.vadrin.catalogwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vadrin.catalogwebsite.services.GitHubService;

@Controller
public class HomePageController {

	@Autowired
	GitHubService gitHubService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("catalogInfo", gitHubService.getCatalogInfo());
		return "vadrin";
	}
	
	@GetMapping("/vadrin")
	public String vadrin( Model model) {
		return home(model);
	}

	@GetMapping("/aboutus")
	public String aboutus() {
		return "aboutus";
	}
	
	@GetMapping("/contactus")
	public String contactus() {
		return "contactus";
	}
}
