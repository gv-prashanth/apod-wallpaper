package com.vadrin.apodwallpaper.models;

import java.net.URL;
import java.time.LocalDate;

public class Feed {

	private String title;
	private String description;
	private LocalDate date;
	private URL imageUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Feed(String title, String description, LocalDate date, URL imageUrl) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
		this.imageUrl = imageUrl;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public URL getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Feed() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Feed [title=" + title + ", date=" + date + ", imageUrl=" + imageUrl + ", description=" + description
				+ "]";
	}

}
