package dependances;

import Anotations.*;

public class basicClass {
	private String nom;
	private String prenom;
	private int age;

	public basicClass() {
		super();
	}
	
	
	public basicClass(String nom, String prenom, int age) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
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
	public String getPrenom() {
		return prenom;
	}
	
	@CallMe(param="set")
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	@CallMe(param="get")
	public int getAge() {
		return age;
	}

	@CallMe(param="set")
	public void setAge(int age) {
		this.age = age;
	}

	
}
