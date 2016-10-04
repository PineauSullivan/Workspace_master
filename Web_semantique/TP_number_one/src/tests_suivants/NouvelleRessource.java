package tests_suivants;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

public class NouvelleRessource implements Ressource {

	String personURI;
	String givenName;
	String familyName;
	String fullName;
	
	public NouvelleRessource(String personURI, String givenName,
			String familyName, String fullname) {
		super();
		this.personURI = personURI;
		this.givenName = givenName;
		this.familyName = familyName;
		this.fullName = fullname;
	}
	
	public NouvelleRessource(String personURI, String givenName,
			String familyName) {
		super();
		this.personURI = personURI;
		this.givenName = givenName;
		this.familyName = familyName;
		this.fullName = givenName + " " + familyName;
	}
	
	public void ajoutDansUnModel(UnModel le_model){
		Resource une_ressource = le_model.getLe_modele().createResource(personURI);
		
		une_ressource.addProperty(VCARD.FN, fullName);
		une_ressource.addProperty(VCARD.N,
		                      le_model.getLe_modele().createResource()
		                           .addProperty(VCARD.Given, givenName)
		                           .addProperty(VCARD.Family, familyName));

		
		
	}
	
	
	
		
}
