package core.math;

import core.kernel.Camera;

public class Maths {
	
	public static Mat4 createTransformationMatrix(Vec2f translation, Vec2f scale) {
		Mat4 matrix = new Mat4();
		matrix.Identity();
		matrix.Translation(new Vec3f(translation.getX(), translation.getY(), 0));
		matrix.Scaling(new Vec3f(scale.getX(), scale.getY(), 1f));
		return matrix;
	}

	public static Mat4 createTransformationMatrix(Vec3f translation, float rx, float ry,
			float rz, float scale) {
		Mat4 matrix = new Mat4();
		matrix.Identity();
		matrix.Translation(translation);
		matrix.Rotation(new Vec3f((float)Math.toRadians(rx),0,0));
		matrix.Rotation(new Vec3f(0, (float) Math.toRadians(ry), 0));
		matrix.Rotation(new Vec3f(0, 0, (float) Math.toRadians(rz)));
		matrix.Scaling(new Vec3f(scale, scale, scale));
		return matrix;
	}
	
	public static float barryCentric(Vec3f p1, Vec3f p2, Vec3f p3, Vec2f pos) {
		float det = (p2.getZ() - p3.getZ()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getZ() - p3.getZ());
		float l1 = ((p2.getZ() - p3.getZ()) * (pos.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (pos.getY()- p3.getY())) / det;
		float l2 = ((p3.getZ() - p1.getZ()) * (pos.getX() - p3.getX()) + (p1.getX() - p3.getX()) * (pos.getY() - p3.getZ())) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.getY() + l2 * p2.getY() + l3 * p3.getY();
	}
	
	public static Mat4 createViewMatrix(Camera camera) {
		Mat4 viewMatrix = new Mat4();
		viewMatrix.Identity();
		viewMatrix.Rotation(new Vec3f((float) Math.toRadians(camera.getPitch()), 0, 0));
		viewMatrix.Rotation(new Vec3f(0, (float) Math.toRadians(camera.getYaw()), 0));
		Vec3f cameraPos = camera.getPosition();
		Vec3f negativeCameraPos = new Vec3f(-cameraPos.getX(),-cameraPos.getY(),-cameraPos.getZ());
		viewMatrix.Translation(negativeCameraPos);
		return viewMatrix;
	}
}