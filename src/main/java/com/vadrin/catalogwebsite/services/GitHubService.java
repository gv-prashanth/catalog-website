package com.vadrin.catalogwebsite.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.vadrin.catalogwebsite.models.RepositoryInfo;

@Service
public class GitHubService {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	// Pattern for recognizing a URL, based off RFC 3986
	private static final Pattern urlPattern = Pattern.compile(
			"(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
					+ "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	@Cacheable(value = "githubCatalogInfo")
	public RepositoryInfo[] getCatalogInfo() {
		RepositoryInfo[] toReturn = restTemplateBuilder.build()
				.getForObject("https://api.github.com/users/gv-prashanth/repos", RepositoryInfo[].class);
		return toReturn;
	}

	@Cacheable(value = "githubReadmeInfo")
	public String getReadmeInfo(String fullName, String fallbackHtmlUrl) {
		try {
			String readMeContent = restTemplateBuilder.build().getForObject(
					"https://raw.githubusercontent.com/" + fullName + "/master/README.md",
					String.class);
			Matcher matcher = urlPattern.matcher(readMeContent);
			while (matcher.find()) {
			    int matchStart = matcher.start(1);
			    int matchEnd = matcher.end();
			    return readMeContent.substring(matchStart, matchEnd);
			}
		} catch (HttpClientErrorException e) {
			System.out.println(fullName + " does not have a README.md file.");
		}
		return fallbackHtmlUrl;
	}

}
