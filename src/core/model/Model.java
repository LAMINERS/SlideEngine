package core.model;

public class Model {

	private Mesh mesh;
	private Material material;
	
	public Model(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public Material getMaterial() {
		return material;
	}
}
