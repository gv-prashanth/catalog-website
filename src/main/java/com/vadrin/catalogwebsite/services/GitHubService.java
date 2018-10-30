package com.vadrin.catalogwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.vadrin.catalogwebsite.models.RepositoryInfo;

@Service
public class GitHubService {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Cacheable(value = "githubCall")
	public RepositoryInfo[] getCatalogInfo() {
		RepositoryInfo[] toReturn = restTemplateBuilder.build().getForObject("https://api.github.com/users/gv-prashanth/repos", RepositoryInfo[].class);
		return toReturn;
	}

}
