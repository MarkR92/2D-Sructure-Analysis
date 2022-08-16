
public class Material {
	
	private String E;
	private String A;
	private String I;
	private String Name;
	
	public Material(String E, String A, String I, String Name) {
		
		this.E=E;
		this.A=A;
		this.I=I;
		this.Name=Name;
		
	}

	public String getE() {
		return E;
	}

	public void setE(String e) {
		E = e;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getI() {
		return I;
	}

	public void setI(String i) {
		I = i;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
