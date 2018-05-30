package core.kernel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class Window {
	
	private static Window instance = null;
	
	private int width = 1280;
	private int height = 720;
	
	private boolean resizable = true;
	private boolean borderless = false;
	
	public static Window getInstance() {
		if(instance == null) {
			instance = new Window();
		}
		return instance;
	}
	
	public void init() {	}
	
	public void create(int width, int height) {
		setWidth(width);
		setHeight(height);
		
		ContextAttribs attribs = new ContextAttribs(3,3)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat().withSamples(8).withDepthBits(24), attribs);
			Display.setTitle("SlideEngine");
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		GL11.glViewport(0, 0, width, height);
		if(resizable) Display.setResizable(true);
	}
	
	public void update() {
		if(Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		Display.update();
		Display.sync(core.utils.Constants.FPS_CAP);
	}
	
	public void destroy() {
		Display.destroy();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isBorderless() {
		return borderless;
	}

	public void setBorderless(boolean borderless) {
		this.borderless = borderless;
	}

	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
}
