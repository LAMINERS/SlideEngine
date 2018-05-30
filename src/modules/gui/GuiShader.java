package modules.gui;

import core.math.Mat4;
import core.shader.Shader;

public class GuiShader extends Shader {

	private static final String VERTEX_FILE = "res/shaders/gui.vert";
	private static final String FRAG_FILE = "res/shaders/gui.frag";
	
	public GuiShader() {
		super();

		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAG_FILE);
		compileShader();
		
		addUniform("transformationMatrix");
		addAttribute("position");
	}
	
	public void loadTransformationMatrix(Mat4 matrix) {
		setUniformMat4("transformationMatrix", matrix);
	}
	
	protected void bindAttributes() {
		bindAttributes();
	}
}
