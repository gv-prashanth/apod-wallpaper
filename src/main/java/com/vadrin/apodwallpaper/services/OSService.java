package com.vadrin.apodwallpaper.services;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class OSService {

	public int setWallpaper(File imageFile) throws InterruptedException, IOException {
		Process process = new ProcessBuilder("WallpaperChanger.exe",imageFile.getAbsolutePath(),"3").start();
		int exitCode = process.waitFor();
		return exitCode;
	}

}
