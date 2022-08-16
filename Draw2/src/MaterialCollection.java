import java.util.ArrayList;

public class MaterialCollection {
	
	public ArrayList<Material> materialCollection = new ArrayList<>();
	
	public void addMaterial(Material material) {
		
		materialCollection.add(material);
		
	}
	public void addDefaultMaterial(Material material) {
		
		materialCollection.add(material);
	}
	public ArrayList<Material> getMaterialCollection() {
		return materialCollection;
	}

	public void setMaterialCollection(ArrayList<Material> materialCollection) {
		this.materialCollection = materialCollection;
	}

}
