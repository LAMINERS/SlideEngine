package core.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import core.math.Mat4;
import core.math.Quaternion;
import core.math.Vec2f;
import core.math.Vec3f;

public class Shader {
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	private int program;
	private HashMap<String, Integer> uniforms;
	private HashMap<String, Integer>attribs;
	
	public Shader() {
		program = GL20.glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		attribs = new HashMap<String, Integer>();
		
		if(program == 0) {
			System.err.println("Shader creation failed");
			System.exit(1);
		}
	}
	
	public void start() {
		GL20.glUseProgram(program);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		GL20.glDeleteProgram(program);
	}
	
	public void addUniform(String uniform) {
		int uniformLocation = GL20.glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF) {
			System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addAttribute(String attribute) {
		int attributeLocation = GL20.glGetAttribLocation(program, attribute);
		
		if(attributeLocation == 0xFFFFFFFF) {
			System.err.println(this.getClass().getName() + "Error: Could not find attribute" +  attribute);
			new Exception().printStackTrace();
			System.exit(1);
		}
		attribs.put(attribute, attributeLocation);
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL20.GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String text)
	{
		addProgram(text, GL32.GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String text)
	{
		addProgram(text, GL20.GL_FRAGMENT_SHADER);
	}
	
	public void addTessellationControlShader(String text)
	{
		addProgram(text, GL40.GL_TESS_CONTROL_SHADER);
	}
	
	public void addTessellationEvaluationShader(String text)
	{
		addProgram(text, GL40.GL_TESS_EVALUATION_SHADER);
	}
	
	public void addComputeShader(String text)
	{
		addProgram(text, GL43.GL_COMPUTE_SHADER);
	}
	
	private void addProgram(String text, int type)
	{
		int shader = GL20.glCreateShader(type);
		
		if (shader == 0)
		{
			System.err.println(this.getClass().getName() + " Shader creation failed");
			System.exit(1);
		}	
		
		GL20.glShaderSource(shader, text);
		GL20.glCompileShader(shader);
		
		if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0)
		{
			System.err.println(this.getClass().getName() + " " + GL20.glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(program, shader);
	}
	
	public void compileShader()
	{
		GL20.glLinkProgram(program);

		if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
		{
			System.out.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(program);
		
		if(GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(this.getClass().getName() +  " " + GL20.glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	public void setUniformb(String uniformName, boolean value) {
		float toLoad = 0;
		if(value)
			toLoad = 1;
		GL20.glUniform1f(uniforms.get(uniformName), toLoad);
	}
	
	public void setUniform1i(String uniformName, int value) {
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform1f(String uniformName, float value) {
		GL20.glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform2f(String uniformName, Vec2f value) {
		GL20.glUniform2f(uniforms.get(uniformName), value.getX(), value.getY());
	}
	
	public void setUniform3f(String uniformName, Vec3f value) {
		GL20.glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform4f(String uniformName, Quaternion value) {
		GL20.glUniform4f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ(), value.getW());
	}
	
	public void setUniformMat4(String uniformName, Mat4 matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(uniforms.get(uniformName), false, matrixBuffer);
	}
	
	public int getProgram()
	{
		return this.program;
	}
}