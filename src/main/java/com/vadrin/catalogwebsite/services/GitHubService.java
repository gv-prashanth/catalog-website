package com.vadrin.catalogwebsite.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.vadrin.catalogwebsite.models.RepositoryInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitHubService {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	private static final String GITHUBAPIENDPOINT = "https://api.github.com/";
	private static final String GITHUBCONTENTURL = "https://raw.githubusercontent.com/";
	private static final String USERS = "users";
	private static final String REPOS = "repos";
	// Pattern for recognizing a URL, based off RFC 3986
	private static final Pattern URLPATTERN = Pattern.compile(
			"(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
					+ "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	private static final String READMEPATH = "/master/README.md";
	private static final String[] GITHUBACCOUNTS = {"gv-prashanth"};//disabling for now "bj-krishna"

	@Cacheable(value = "githubCatalogInfo")
	public List<RepositoryInfo> getCatalogInfo() {
		List<RepositoryInfo> toReturn = new ArrayList<RepositoryInfo>();
		for (String thisAccount : GITHUBACCOUNTS) {
			RepositoryInfo[] thisAccountRepos = restTemplateBuilder.build()
					.getForObject(GITHUBAPIENDPOINT + USERS + "/" + thisAccount + "/" + REPOS, RepositoryInfo[].class);
			toReturn.addAll(Arrays.asList(thisAccountRepos));
		}
		return toReturn.stream().filter(r -> !r.isFork()).collect(Collectors.toList());
	}

	@Cacheable(value = "githubReadmeInfo")
	public String getReadmeInfo(String fullName, String fallbackHtmlUrl) {
		try {
			String readMeContent = restTemplateBuilder.build().getForObject(GITHUBCONTENTURL + fullName + READMEPATH,
					String.class);
			Matcher matcher = URLPATTERN.matcher(readMeContent);
			while (matcher.find()) {
				int matchStart = matcher.start(1);
				int matchEnd = matcher.end();
				return readMeContent.substring(matchStart, matchEnd);
			}
		} catch (HttpClientErrorException e) {
			log.error(fullName + " does not have a README.md file.");
		}
		return fallbackHtmlUrl;
	}

}
