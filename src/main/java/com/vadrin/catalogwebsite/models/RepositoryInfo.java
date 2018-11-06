package com.vadrin.catalogwebsite.models;

import org.apache.commons.text.WordUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryInfo {

	private String name;
	private String description;
	private String html_url;

	public String getFormattedName() {
		return WordUtils.capitalizeFully(name.replace("-", " "));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtml_url() {
		return html_url;
	}

	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}

}
