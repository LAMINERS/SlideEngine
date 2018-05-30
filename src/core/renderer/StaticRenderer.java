package core.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.math.Mat4;
import core.math.Maths;
import core.math.Vec2f;
import core.model.Mesh;
import core.model.Model;
import core.texturing.Texture2D;
import modules.entities.Entity;
import modules.entities.StaticShader;

public class StaticRenderer {

	private StaticShader shader = StaticShader.getInstance();
	
	public StaticRenderer(Mat4 projectionMatrix) {
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Map<Model, List<Entity>> entities) {
		for(Model model : entities.keySet()) {
			prepareModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
	}
	
	private void prepareModel(Model model) {
		Mesh mesh = model.getMesh();
		GL30.glBindVertexArray(mesh.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		Texture2D texture = model.getMaterial().getDiffuseMap();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if(model.getMaterial().getAlpha() > 0.5) {
			MasterRenderer.disableCulling();
		}
		shader.loadShininess(model.getMaterial().getShininess());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
	}
	
	private void unbindModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void prepareInstance(Entity entity) {
		Mat4 transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
																						   entity.getRotation().getY(),
																						   entity.getRotation().getZ(), 
																						   entity.getScale().getX());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(new Vec2f(entity.getTextureXOffset(), entity.getTextureYOffset()));		
	}
}