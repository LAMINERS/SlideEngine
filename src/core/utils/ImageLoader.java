package core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ImageLoader {

	public static Texture loadTexture(String file) {
		Texture texture = null;
		
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + file + ".png"));
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Texture not found");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("ERROR: Failed to load Texture: " + file + ".png");
			e.printStackTrace();
			System.exit(1);
		}
		return texture;
	}
}
