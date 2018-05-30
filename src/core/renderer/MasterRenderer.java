package core.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import core.kernel.Camera;
import core.math.Mat4;
import core.math.Maths;
import core.math.Vec3f;
import core.model.Model;
import core.utils.Constants;
import modules.entities.Entity;
import modules.entities.Light;
import modules.entities.StaticShader;

public class MasterRenderer {
	
	private static final float RED = 0.1f;
	private static final float GREEN = 0.4f;
	private static final float BLUE = 0.2f;
	
	private Mat4 projectionMatrix;
	
	private StaticShader staticShader = StaticShader.getInstance();
	private StaticRenderer renderer;
	
	private Map<Model, List<Entity>> entities = new HashMap<Model, List<Entity>>();
	
	public MasterRenderer() {
		enableCulling();
		createProjectionMatrix();
		renderer = new StaticRenderer(projectionMatrix);
	}
	
	public Mat4 getProjectionMatrix() {
		return this.projectionMatrix;
	}
	
	public void render(List<Light> lights, Camera camera) {
		prepare();
		staticShader.start();
		staticShader.loadSkyColor(new Vec3f(RED, GREEN, BLUE));
		staticShader.loadLights(lights);
		staticShader.loadViewMatrix(Maths.createViewMatrix(camera));
		renderer.render(entities);
		staticShader.stop();
		entities.clear();
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void processEntity(Entity entity) {
		Model entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp() {
		staticShader.cleanUp();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	private void createProjectionMatrix(){
    	projectionMatrix = new Mat4();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(Constants.FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = Constants.FAR_PLANE - Constants.NEAR_PLANE;

		projectionMatrix.set(0, 0, x_scale);
		projectionMatrix.set(1, 1, y_scale);
		projectionMatrix.set(2, 2, -((Constants.FAR_PLANE + Constants.NEAR_PLANE) / frustum_length));
		projectionMatrix.set(3, 3, -1);
		projectionMatrix.set(3, 2, -((2 * Constants.NEAR_PLANE * Constants.FAR_PLANE) / frustum_length));
		projectionMatrix.set(3, 3, 0);
    }
}
