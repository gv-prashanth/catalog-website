package com.vadrin.catalogwebsite.models;

import org.apache.commons.text.WordUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryInfo {

	private String name;
	private String description;
	private String html_url;
	private String site_url;
	private String full_name;
	private String base64Screenshot;

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

	public String getSite_url() {
		return site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getBase64Screenshot() {
		return base64Screenshot;
	}

	public void setBase64Screenshot(String base64Screenshot) {
		this.base64Screenshot = base64Screenshot;
	}

}
