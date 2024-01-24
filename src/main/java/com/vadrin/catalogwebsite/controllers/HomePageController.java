package com.vadrin.catalogwebsite.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vadrin.catalogwebsite.models.RepositoryInfo;
import com.vadrin.catalogwebsite.services.ClientInfoService;
import com.vadrin.catalogwebsite.services.FirestoreService;
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
	
	@Autowired
	FirestoreService firestoreService;

	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {
	  clientInfoService.printClientInfo(request);
	  List<RepositoryInfo> repoInfo = new ArrayList<>();
	try {
		repoInfo = firestoreService.getRepositoryInfos();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  model.addAttribute("catalogInfo", repoInfo);
	  return "homepageTemplate";
	}

	@GetMapping("/vadrin")
	public String vadrin(HttpServletRequest request, Model model) {
		return home(request, model);
	}
	
	@GetMapping("/refresh/vadrin")
	@ResponseBody
	public String refreshVadrin() {
		List<RepositoryInfo> repoInfo = gitHubService.getCatalogInfo();
		repoInfo.stream().forEach(repositoryInfo -> {
			try {
				repositoryInfo
				.setSite_url(gitHubService.getReadmeInfo(repositoryInfo.getFull_name(), repositoryInfo.getHtml_url()));
				repositoryInfo
				.setBase64Screenshot(screenshotService.getScreenshot(repositoryInfo.getSite_url()));
				firestoreService.saveRepositoryInfo(repositoryInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return "done";
	}

	@GetMapping("/about")
	public String about() {
		return "aboutTemplate";
	}

}
