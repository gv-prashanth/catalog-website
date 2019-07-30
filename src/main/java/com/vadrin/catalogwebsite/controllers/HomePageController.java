package com.vadrin.catalogwebsite.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vadrin.catalogwebsite.models.RepositoryInfo;
import com.vadrin.catalogwebsite.services.GitHubService;
import com.vadrin.catalogwebsite.services.ScreenshotService;

@Controller
public class HomePageController {

	@Autowired
	GitHubService gitHubService;

	@Autowired
	ScreenshotService screenshotService;

	@GetMapping("/")
	public String home(Model model) {
		RepositoryInfo[] repoInfo = gitHubService.getCatalogInfo();
		Arrays.stream(repoInfo).forEach(repositoryInfo -> repositoryInfo
				.setSite_url(gitHubService.getReadmeInfo(repositoryInfo.getFull_name(), repositoryInfo.getHtml_url())));
		Arrays.stream(repoInfo).forEach(repositoryInfo -> repositoryInfo
				.setBase64Screenshot(screenshotService.getScreenshot(repositoryInfo.getSite_url())));
		model.addAttribute("catalogInfo", repoInfo);
		return "homepageTemplate";
	}

	@GetMapping("/vadrin")
	public String vadrin(Model model) {
		return home(model);
	}

	@GetMapping("/about")
	public String about() {
		return "simpleTemplate";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contactTemplate";
	}

}
