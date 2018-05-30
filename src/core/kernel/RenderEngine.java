package core.kernel;

import core.configs.Default;

public class RenderEngine {
	
	private Window window;
	
	public RenderEngine() {
		window = new Window();
	}
	
	public void init() {
		window.init();
	}
	
	public void render() {
		Default.clearScreen();
		
		// Rendercalls
		
		// Update Window
		window.update();
	}
	
	public void update() {
		
	}
	
	public void shutdown() {
		
	}
}
