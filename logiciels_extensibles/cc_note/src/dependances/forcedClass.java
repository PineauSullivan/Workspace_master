package dependances;

import Anotations.CallMe;

public class forcedClass {
	private String nom ="null";
	private int age=0;
	
	public forcedClass() {
		super();
	}
	
	@CallMe(param="get")
	public String getNom() {
		return nom;
	}
	
	@CallMe(param="set")
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@CallMe(param="get")
	public int getAge() {
		return age;
	}
	
	@CallMe(param="set")
	public void setAge(int age) {
		this.age = age;
	}

	
	public String toString() {
		return "forcedClass [nom=" + nom + ", age=" + age + "]";
	}
	
	
	
}
