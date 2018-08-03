package com.vadrin.apodwallpaper.services;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.vadrin.apodwallpaper.models.Feed;

@Service
public class APODFeedService {

	private static final String BASEURL = "https://apod.nasa.gov/apod/";
	private static final String URLDATEFORMAT = "YYMMdd";
	private static final String AP = "ap";
	private static final String DOTHTML = ".html";
	private static final String HREF = "href";
	private static final String EXPLANATION = "Explanation: ";
	private static final String TITLESELECTOR = "body > center:nth-child(2) > b:nth-child(1)";
	private static final String DESCRIPTIONSELECTOR = "body > p:nth-child(3)";
	private static final String IMAGEURLSELECTOR = "body > center:nth-child(1) > p:nth-child(3) > a";

	public Feed fetch(LocalDate randomDay) throws IOException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(URLDATEFORMAT);
		Document doc = Jsoup.connect(BASEURL + AP + dateTimeFormatter.format(randomDay) + DOTHTML).get();
		Elements title = doc.select(TITLESELECTOR);
		Elements desc = doc.select(DESCRIPTIONSELECTOR);
		Elements imageUrl = doc.select(IMAGEURLSELECTOR);
		return new Feed(title.text(), desc.text().replaceAll(EXPLANATION, ""), randomDay,
				new URL(BASEURL + imageUrl.attr(HREF)));
	}

}
