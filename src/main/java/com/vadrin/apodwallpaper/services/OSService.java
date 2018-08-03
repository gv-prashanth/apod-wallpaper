package com.vadrin.apodwallpaper.services;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

@Service
public class OSService {

	private static final String USER32 = "user32";

	public void setWallpaper(File imageFile) throws IOException {
		User32.INSTANCE.SystemParametersInfo(0x0014, 0, imageFile.getAbsolutePath(), 1);
	}

	public static interface User32 extends Library {
		User32 INSTANCE = (User32) Native.loadLibrary(USER32, User32.class, W32APIOptions.DEFAULT_OPTIONS);

		boolean SystemParametersInfo(int one, int two, String s, int three);
	}
}
