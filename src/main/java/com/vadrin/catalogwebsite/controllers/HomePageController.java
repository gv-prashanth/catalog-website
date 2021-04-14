package com.vadrin.catalogwebsite.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vadrin.catalogwebsite.models.RepositoryInfo;
import com.vadrin.catalogwebsite.services.ClientInfoService;
import com.vadrin.catalogwebsite.services.GitHubService;
import com.vadrin.catalogwebsite.services.ScreenshotService;

@Controller
public class HomePageController {

	@Autowired
	GitHubService gitHubService;

	@Autowired
	ScreenshotService screenshotService;
	
	@Autowired
	ClientInfoService clientInfoService;

	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {
	  clientInfoService.printClientInfo(request);
		List<RepositoryInfo> repoInfo = gitHubService.getCatalogInfo();
		repoInfo.forEach(repositoryInfo -> repositoryInfo
				.setSite_url(gitHubService.getReadmeInfo(repositoryInfo.getFull_name(), repositoryInfo.getHtml_url())));
		repoInfo.forEach(repositoryInfo -> repositoryInfo
				.setBase64Screenshot(screenshotService.getScreenshot(repositoryInfo.getSite_url())));
		model.addAttribute("catalogInfo", repoInfo);
		return "homepageTemplate";
	}

	@GetMapping("/vadrin")
	public String vadrin(HttpServletRequest request, Model model) {
		return home(request, model);
	}

	@GetMapping("/about")
	public String about() {
		return "aboutTemplate";
	}

}
