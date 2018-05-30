package core.model;

import java.util.ArrayList;
import java.util.List;

import core.math.Vec3f;

public class Vertex {
	
	private static final int NO_INDEX = -1;

	private Vec3f position;
	private int textureIndex = NO_INDEX;
	private int normalIndex = NO_INDEX;
	private Vertex duplicateVertex = null;
	private int index;
	private float length;
	private List<Vec3f> tangents = new ArrayList<Vec3f>();
	private Vec3f averagedTangent = new Vec3f(0,0,0);
	
	public Vertex(int index, Vec3f position) {
		this.index = index;
		this.position = position;
		this.length = position.length();
	}
	
	public void addTangent(Vec3f tangent) {
		tangents.add(tangent);
	}
	
	public void averageTangents() {
		if(tangents.isEmpty())
			return;
		for(Vec3f tangent : tangents) {
			averagedTangent.add(tangent);
		}
		averagedTangent.normalize();
	}
	
	public Vec3f getAverageTangent(){
		return averagedTangent;
	}
	
	public int getIndex(){
		return index;
	}
	
	public float getLength(){
		return length;
	}
	
	public boolean isSet() {
		return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
	}
	
	public boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther) {
		return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
	}
	
	public void setTextureIndex(int textureIndex){
		this.textureIndex = textureIndex;
	}
	
	public void setNormalIndex(int normalIndex){
		this.normalIndex = normalIndex;
	}

	public Vec3f getPosition() {
		return position;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

	public Vertex getDuplicateVertex() {
		return duplicateVertex;
	}

	public void setDuplicateVertex(Vertex duplicateVertex) {
		this.duplicateVertex = duplicateVertex;
	}
}