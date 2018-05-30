package core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.math.Vec2f;
import core.math.Vec3f;
import core.model.Mesh;
import core.model.Vertex;

public class OBJLoader {

	private static final String MODEL_LOC = "res/models/";
	
	public static Mesh loadOBJ(String file, Loader loader) {
		FileReader isr = null;
		File obFile = new File(MODEL_LOC + file + ".obj");
		try {
			isr = new FileReader(obFile);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: File not found: " + MODEL_LOC + file + ".obj"); 
		}
		BufferedReader reader = new BufferedReader(isr);
		String line;
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vec2f> textures = new ArrayList<Vec2f>();
		List<Vec3f> normals = new ArrayList<Vec3f>();
		List<Integer> indices = new ArrayList<Integer>();
		try {
			while(true) {
				line = reader.readLine();
				if(line.startsWith("v ")) {
					String[] currentLine = line.split(" ");
					Vec3f vertex = new Vec3f((float) Float.valueOf(currentLine[1]),
											 (float) Float.valueOf(currentLine[2]),
											 (float) Float.valueOf(currentLine[3]));
					Vertex newVertex = new Vertex(vertices.size(), vertex);
					vertices.add(newVertex);
				} else if(line.startsWith("vt ")) {
					String[] currentLine = line.split(" ");
					Vec2f texture = new Vec2f((float) Float.valueOf(currentLine[1]),
											  (float) Float.valueOf(currentLine[2]));
					textures.add(texture);													  
				} else if(line.startsWith("vn ")) {
					String[] currentLine = line.split(" ");
					Vec3f normal = new Vec3f((float) Float.valueOf(currentLine[1]),
											 (float) Float.valueOf(currentLine[2]),
											 (float) Float.valueOf(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					break;
				}
			}
			while(line != null && line.startsWith("f ")) {
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				Vertex v0 = processVertex(vertex1, vertices, indices);
				Vertex v1 = processVertex(vertex2, vertices, indices);
				Vertex v2 = processVertex(vertex3, vertices, indices);
				calculateTangents(v0, v1, v2, textures);
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e) {
			System.err.println("ERROR: Failed to read file: " + file + ".obj");
		}
		removeUnusedVertices(vertices);
		float[] verticesArray = new float[vertices.size() * 3];
		float[] texturesArray = new float[vertices.size() * 2];
		float[] normalsArray = new float[vertices.size() * 3];
		int[] indicesArray = convertIndicesListToArray(indices);
		
		return loader.loadToVAO(verticesArray, texturesArray, normalsArray, indicesArray);
	}
	
	private static void calculateTangents(Vertex v0, Vertex v1, Vertex v2, List<Vec2f> textures) {
		Vec3f deltaPos1 = v1.getPosition().sub(v0.getPosition());
		Vec3f deltaPos2 = v2.getPosition().sub(v0.getPosition());
		Vec2f uv0 = textures.get(v0.getTextureIndex());
		Vec2f uv1 = textures.get(v1.getTextureIndex());
		Vec2f uv2 = textures.get(v2.getTextureIndex());
		Vec2f deltaUv1 = uv1.sub(uv0);
		Vec2f deltaUv2 = uv2.sub(uv0);

		float r = 1.0f / (deltaUv1.getX() * deltaUv2.getY() - deltaUv1.getY() * deltaUv2.getX());
		deltaPos1.mul(deltaPos2.getY());
		deltaPos2.mul(deltaPos1.getY());
		Vec3f tangent = deltaPos1.sub(deltaPos2);
		tangent.mul(r);
		v0.addTangent(tangent);
		v1.addTangent(tangent);
		v2.addTangent(tangent);
	}
	
	private static Vertex processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
		int index = Integer.parseInt(vertex[0]) - 1;
		Vertex currentVertex = vertices.get(index);
		int textureIndex = Integer.parseInt(vertex[1]) - 1;
		int normalIndex = Integer.parseInt(vertex[2]) - 1;
		if (!currentVertex.isSet()) {
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
			return currentVertex;
		} else {
			return dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
					vertices);
		}
	}
	
	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	private static Vertex dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
			int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
			return previousVertex;
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				return dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex,
						newNormalIndex, indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
				return duplicateVertex;
			}

		}
	}

	private static void removeUnusedVertices(List<Vertex> vertices) {
		for (Vertex vertex : vertices) {
			vertex.averageTangents();
			if (!vertex.isSet()) {
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}
}
