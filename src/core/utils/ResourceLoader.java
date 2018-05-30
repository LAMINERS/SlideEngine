package core.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResourceLoader {

	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader("./res/" + fileName));
			String line;
			while((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Shader not found");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("ERROR: Failed to read shaderfile");
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
}
