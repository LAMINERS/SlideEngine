package core.texturing;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import core.utils.ImageLoader;

public class Texture2D {

	private int id;
	private int width;
	private int height;
	private int numberOfRows = 1;
	
	public Texture2D() {}
	
	public Texture2D(String file) {
		Texture texture = ImageLoader.loadTexture(file);
		id = texture.getTextureID();
		width = texture.getTextureWidth();
		height = texture.getTextureHeight();
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void generate() {
		id = GL11.glGenTextures();
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
