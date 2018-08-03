package com.vadrin.apodwallpaper.services;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.vadrin.apodwallpaper.models.Feed;

@Service
public class ImageService {

	private static final String TEMPFILENAME = "apod-wallpaper";
	private static final int XSTART = 10;
	private static final int YSTART = 50;
	private static final float TITLEFONTSIZE = 30f;
	private static final float DESCRIPTIONFONTSIZE = 20f;

	private BufferedImage typeTitleAndDescriptionOnImage(BufferedImage toEdit, String title, String description)
			throws IOException {
		Graphics graphics = toEdit.getGraphics();
		graphics.setColor(Color.YELLOW);
		graphics.setFont(graphics.getFont().deriveFont(TITLEFONTSIZE));
		graphics.drawString(title, XSTART, YSTART);
		graphics.setFont(graphics.getFont().deriveFont(DESCRIPTIONFONTSIZE));
		int y = toEdit.getHeight() - 100;
		for (String line : description.split("\\.")) {
			graphics.drawString(line, XSTART, y += graphics.getFontMetrics().getHeight());
		}
		graphics.dispose();
		return toEdit;
	}

	private BufferedImage downloadImageToFile(URL imageUrl) throws IOException {
		return ImageIO.read(imageUrl);
	}

	public File downloadImageAndImposeTitle(Feed feed) throws IOException {
		BufferedImage editedImage = typeTitleAndDescriptionOnImage(downloadImageToFile(feed.getImageUrl()),
				feed.getTitle(), feed.getDescription());
		String format = feed.getImageUrl().toString().split("\\.")[feed.getImageUrl().toString().split("\\.").length
				- 1];
		File tempFile = File.createTempFile(TEMPFILENAME, "." + format);
		ImageIO.write(editedImage, format, tempFile);
		return tempFile;
	}

}
