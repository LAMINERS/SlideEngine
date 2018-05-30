package modules.entities;

import core.math.Vec3f;

public class Light {

	private Vec3f position;
	private Vec3f colour;
	private Vec3f attenuation = new Vec3f(1, 0, 0);
	
	public Light(Vec3f position, Vec3f colour) {
		this.position = position;
		this.colour = colour;
	}
	
	public Light(Vec3f position, Vec3f colour, Vec3f attenuation) {
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Vec3f getColour() {
		return colour;
	}

	public void setColour(Vec3f colour) {
		this.colour = colour;
	}

	public Vec3f getAttenuation() {
		return attenuation;
	}
}
