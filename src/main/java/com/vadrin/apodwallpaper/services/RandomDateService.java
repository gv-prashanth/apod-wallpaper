package com.vadrin.apodwallpaper.services;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomDateService {

	public LocalDate nextDate(LocalDate minDate, LocalDate maxDate) {
		Random random = new Random();
		int minDay = (int) minDate.toEpochDay();
		int maxDay = (int) maxDate.toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
		return LocalDate.ofEpochDay(randomDay);
	}

}