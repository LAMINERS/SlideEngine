package modules.entities;

import java.util.List;

import core.math.Mat4;
import core.math.Vec2f;
import core.math.Vec3f;
import core.shader.Shader;
import core.utils.Constants;
import core.utils.ResourceLoader;

public class StaticShader extends Shader {

	private static StaticShader instance = null;
	
	private static final String VERTEX_FILE = "/res/shaders/static.vert";
	private static final String FRAG_FILE = "/res/shaders/static.frag";
	
	public static StaticShader getInstance() {
		if(instance == null)
			instance = new StaticShader();
		return instance;
	}
	
	public StaticShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader(VERTEX_FILE));
		addFragmentShader(ResourceLoader.loadShader(FRAG_FILE));
		compileShader();
		
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("shininess");
		addUniform("skyColor");
		addUniform("numberOfRows");
		addUniform("offset");
		
		for(int i = 0; i < Constants.MAX_LIGHTS; i++) {
			addUniform("lightPosition[" + i + "]");
			addUniform("lightColor[" + i + "]");
			addUniform("lightAttenuation[" + i + "]");
		}
		
		addAttribute("position");
		addAttribute("textureCoordinates");
		addAttribute("normal");
	}
	
	public void connectTextureUnit() {
		setUniform1i("diffuseMap", 0);
		setUniform1i("specularMap", 1);
	}
	
	public void loadTransformationMatrix(Mat4 matrix) {
		setUniformMat4("transformationMatrix", matrix);
	}
	
	public void loadProjectionMatrix(Mat4 matrix) {
		setUniformMat4("projectionMatrix", matrix);
	}
	
	public void loadViewMatrix(Mat4 matrix) {
		setUniformMat4("viewMatrix", matrix);
	}
	
	public void loadLights(List<Light> lights) {
		for(int i = 0; i < Constants.MAX_LIGHTS; i++) {
			if(i < lights.size()) {
				setUniform3f("lightPosition[" + i + "]", lights.get(i).getPosition());
				setUniform3f("lightColour[" + i + "]", lights.get(i).getColour());
				setUniform3f("lightattenuation[" + i + "]", lights.get(i).getAttenuation());
			} else {
				setUniform3f("lightPosition[" + i + "]", new Vec3f(0,0,0));
				setUniform3f("lightColour[" + i + "]", new Vec3f(0,0,0));
				setUniform3f("lightattenuation[" + i + "]", new Vec3f(0,0,0));
			}
		}
	}
	
	public void loadShininess(float shininess) {
		setUniform1f("shininess", shininess);
	}
	
	public void loadSkyColor(Vec3f skyColor) {
		setUniform3f("skyColor", skyColor);
	}
	
	public void loadNumberOfRows(int numberOfRows) {
		setUniform1i("numberOfRows", numberOfRows);
	}
	
	public void loadOffset(Vec2f offset) {
		setUniform2f("offset", offset);
	}
}
