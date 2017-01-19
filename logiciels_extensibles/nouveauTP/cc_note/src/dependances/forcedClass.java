package dependances;

public class forcedClass {
	private String nom ="null";
	private int age=0;
	
	public forcedClass() {
		super();
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	
	public String toString() {
		return "forcedClass [nom=" + nom + ", age=" + age + "]";
	}
	
	
	
}
