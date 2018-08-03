package com.vadrin.apodwallpaper.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vadrin.apodwallpaper.models.Feed;
import com.vadrin.apodwallpaper.services.APODFeedService;
import com.vadrin.apodwallpaper.services.ImageService;
import com.vadrin.apodwallpaper.services.OSService;
import com.vadrin.apodwallpaper.services.RandomDateService;

@Component
public class ScheduledTaskController {

	@Autowired
	APODFeedService apodFeedService;

	@Autowired
	OSService osService;

	@Autowired
	RandomDateService randomDateService;

	@Autowired
	ImageService imageService;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTaskController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final int APODSTARTYEAR = 1995;
	private static final int APODSTARTMONTH = 5;
	private static final int APODSTARTDATE = 16;

	@Scheduled(fixedDelayString = "${com.vadrin.apod-wallpaper.frequency}")
	public void setNewWallpaper() {
		try {
			log.info("Attempting to set new wallpaper at {}", dateFormat.format(new Date()));
			LocalDate randomDay = randomDateService.nextDate(LocalDate.of(APODSTARTYEAR, APODSTARTMONTH, APODSTARTDATE),
					LocalDate.now());
			Feed apodFeed = apodFeedService.fetch(randomDay);
			log.info("Received the feed {}", apodFeed.toString());
			File wallpaper = imageService.getProcessedImageFromFeed(apodFeed);
			osService.setWallpaper(wallpaper);
			wallpaper.delete();
			log.info("Completed setting new wallpaper at {}", dateFormat.format(new Date()));
		} catch (IOException e) {
			log.error("Exception while setting new wallpaper", e);
		}
	}

}
