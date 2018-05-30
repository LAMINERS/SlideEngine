package modules.gui;

import core.math.Vec2f;

public class GuiTexture {

	private int id;
	private Vec2f position;
	private Vec2f scale;
	
	public GuiTexture(int id, Vec2f position, Vec2f scale) {
		this.id = id;
		this.position = position;
		this.scale = scale;
	}

	public int getId() {
		return id;
	}

	public Vec2f getPosition() {
		return position;
	}

	public Vec2f getScale() {
		return scale;
	}
}
