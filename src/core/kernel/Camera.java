package core.kernel;

import core.math.Vec3f;

public class Camera {
	
	private static Camera instance = null;

	private Vec3f position;
	private Vec3f rotation;
	
	public static Camera getInstance() {
		if(instance == null) 
			instance = new Camera();
		return instance;
	}
	
	public void update() {
		
	}
	
	public void move() {
		
	}
	
	public void checkInputs() {
		
	}
	
	public void invertPitch() {
		this.rotation.setX(-rotation.getX());
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
	
	public float getPitch() {
		return this.rotation.getX();
	}
	
	public float getYaw() {
		return this.rotation.getY();
	}
	
	public float getRoll() {
		return this.rotation.getZ();
	}
}
