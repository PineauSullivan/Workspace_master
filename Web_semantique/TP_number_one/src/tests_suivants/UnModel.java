package tests_suivants;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class UnModel {
	Model le_modele;

	public UnModel() {
		super();
		this.le_modele = ModelFactory.createDefaultModel();
	}

	public Model getLe_modele() {
		return le_modele;
	}

	public void setLe_modele(Model le_modele) {
		this.le_modele = le_modele;
	}
	
	
	
	
}
