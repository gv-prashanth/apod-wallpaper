package com.vadrin.apodwallpaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApodWallpaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApodWallpaperApplication.class, args);
	}
}
