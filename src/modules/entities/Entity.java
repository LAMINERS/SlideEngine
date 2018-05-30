package modules.entities;

import core.math.Vec3f;
import core.model.Model;

public class Entity {
	
	private Model model;
	private Vec3f position;
	private Vec3f rotation;
	private Vec3f scale;
	
	private int textureIndex = 0;
	
	public Entity(Model model, Vec3f position, Vec3f rotation, Vec3f scale) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Entity(Model model, Vec3f position, Vec3f rotation, Vec3f scale, int index) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.textureIndex = index;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getMaterial().getDiffuseMap().getNumberOfRows();
		return (float)column / (float)model.getMaterial().getDiffuseMap().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex / model.getMaterial().getDiffuseMap().getNumberOfRows();
		return (float)row / (float)model.getMaterial().getDiffuseMap().getNumberOfRows();
	}
	
	public void increasePosition(Vec3f delta) {
		this.position.add(delta);
	}
	
	public void increaseRotation(Vec3f delta) {
		this.rotation.add(delta);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Vec3f getRotation() {
		return rotation;
	}

	public void setRotation(Vec3f rotation) {
		this.rotation = rotation;
	}

	public Vec3f getScale() {
		return scale;
	}

	public void setScale(Vec3f scale) {
		this.scale = scale;
	}
}
