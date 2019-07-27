package com.github.seeker.commonhash;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class ResizeTestData {
	public enum ImageType {jpg, jpg_small, png, gif, bmp};
	public Map<ImageType, BufferedImage> testImages;
	public int imageSize = 32; 
	
	
	@Param({"jpg", "jpg_small", "png", "gif", "bmp"})
	public ImageType image;
	
	@Setup(Level.Trial)
	public void setUp() throws IOException, URISyntaxException {
		ImageIO.setUseCache(false);
		
		testImages = new HashMap<ResizeTestData.ImageType, BufferedImage>();
		loadImage(ImageType.jpg, "/image/testImage.jpg");
		loadImage(ImageType.jpg_small, "/image/testImage_small.jpg");
		loadImage(ImageType.png, "/image/testImage.png");
		loadImage(ImageType.gif, "/image/testImage.gif");
		loadImage(ImageType.bmp, "/image/testImage.bmp");
	}
	
	private void loadImage(ImageType type, String path) throws IOException, URISyntaxException {
		testImages.put(type, ImageIO.read(findFilePath(path)));
	}
	
	private InputStream findFilePath(String fileName) throws URISyntaxException {
		return getClass().getResourceAsStream(fileName);
	}
}
