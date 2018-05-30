package core.math;

public class Vec2f {
	
	private float X;
	private float Y;
	
	public Vec2f() {
		setX(0);
		setY(0);
	}
	
	public Vec2f(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Vec2f(Vec2f v) {
		setX(v.getX());
		setY(v.getY());
	}
	
	public float length() {
		return (float) Math.sqrt(X*X + Y*Y);
	}
	
	public float dot(Vec2f r) {
		return X * r.getX() + Y * r.getY();
	}
	
	public Vec2f normalize(Vec2f v) {
		float length = length();
		
		X /= length;
		Y /= length;
		
		return this;
	}
	
	public Vec2f add(Vec2f v) {
		return new Vec2f(this.X + v.getX(), this.Y + v.getY());
	}
	
	public Vec2f add(float r) {
		return new Vec2f(this.X + r, this.Y + r);
	}
	
	public void setX(float x) {
		this.X = x;
	}
	
	public Vec2f sub(Vec2f v) {
		return new Vec2f(this.X - v.getX(), this.Y + v.getY());
	}
	
	public Vec2f sub(float r) {
		return new Vec2f(this.X - r, this.Y - r);
	}
	
	public Vec2f mul(Vec2f v) {
		return new Vec2f(this.X * v.getX(), this.Y * v.getY());
	}
	
	public Vec2f mul(float r) {
		return new Vec2f(this.X * r, this.Y * r);
	}
	
	public Vec2f div(Vec2f v) {
		return new Vec2f(this.X / v.getX(), this.Y / v.getY());
	}
	
	public Vec2f div(float r) {
		return new Vec2f(this.X / r, this.Y / r);
	}
	
	public String toString() {
		return "[" + this.X + "," + this.Y + "]";
	}
	
	public void setY(float y) {
		this.Y = y;
	}
	
	public float getX() {
		return this.X;
	}
	
	public float getY() {
		return this.Y;
	}	
}