package com.vadrin.apodwallpaper.services;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vadrin.apodwallpaper.models.Feed;

@Service
public class ImageService {

	private static final String TEMPFILENAME = "apod-wallpaper";
	private static final int XSTART = 10;
	private static final int YSTART = 50;
	private static final float TITLEFONTSIZE = 30f;
	private static final float DESCRIPTIONFONTSIZE = 15f;
	private static final int OFFSET = 60;

	@Value("${com.vadrin.apod-wallpaper.screenwidth:640}")
	private int SCREEN_WIDTH;
	@Value("${com.vadrin.apod-wallpaper.screenheight:480}")
	private int SCREEN_HEIGHT;

	private BufferedImage typeTitleAndDescriptionOnImage(BufferedImage toEdit, String title, String description)
			throws IOException {
		Graphics graphics = toEdit.getGraphics();

		// Paint Title Background
		graphics.setFont(graphics.getFont().deriveFont(TITLEFONTSIZE));
		graphics.setColor(Color.BLACK);
		graphics.fillRect(XSTART, YSTART - graphics.getFontMetrics().getAscent(),
				(int) graphics.getFontMetrics().getStringBounds(title, graphics).getWidth(),
				(int) graphics.getFontMetrics().getStringBounds(title, graphics).getHeight());

		// Paint Title
		graphics.setColor(Color.WHITE);
		graphics.drawString(title, XSTART, YSTART);

		// Paint Description
		graphics.setFont(graphics.getFont().deriveFont(DESCRIPTIONFONTSIZE));
		List<String> words = Arrays.asList(description.split(" "));
		int currentWordRead = 0;
		List<String> lines = new ArrayList<String>();
		while (currentWordRead < words.size()) {
			String thisLine = "";
			while (currentWordRead < words.size()
					&& graphics.getFontMetrics().getStringBounds(thisLine + " " + words.get(currentWordRead), graphics)
							.getWidth() < toEdit.getWidth() - 2 * XSTART) {
				thisLine = thisLine + " " + words.get(currentWordRead);
				currentWordRead++;
			}
			lines.add(thisLine);
		}
		int y = toEdit.getHeight() - OFFSET - (graphics.getFontMetrics().getHeight() * lines.size());
		for (String thisLine : lines) {
			graphics.setColor(Color.YELLOW);
			graphics.drawString(thisLine, XSTART, y += graphics.getFontMetrics().getHeight());
		}

		// Wrap up
		graphics.dispose();
		return toEdit;
	}

	private BufferedImage downloadImageToFile(URL imageUrl) throws IOException {
		return ImageIO.read(imageUrl);
	}

	public File getProcessedImageFromFeed(Feed feed) throws IOException {
		BufferedImage downloadedImage = downloadImageToFile(feed.getImageUrl());
		BufferedImage resizedImage = resizeImage(downloadedImage);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");
		String title = feed.getTitle() + " (" + feed.getDate().format(dateTimeFormatter) + ")";

		BufferedImage editedImage = typeTitleAndDescriptionOnImage(resizedImage, title, feed.getDescription());
		String format = feed.getImageUrl().toString().split("\\.")[feed.getImageUrl().toString().split("\\.").length
				- 1];
		File tempFile = File.createTempFile(TEMPFILENAME, "." + format);
		ImageIO.write(editedImage, format, tempFile);
		return tempFile;
	}

	private BufferedImage resizeImage(BufferedImage originalImage) {
		double scaleW = (double) SCREEN_WIDTH / (double) originalImage.getWidth();
		double scaleH = (double) SCREEN_HEIGHT / (double) originalImage.getHeight();
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		double scale = scaleW < scaleH ? scaleW : scaleH;
		BufferedImage resizedImage = new BufferedImage((int) (originalImage.getWidth() * scale),
				(int) (originalImage.getHeight() * scale), type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), null);
		g.dispose();
		return resizedImage;
	}

}
