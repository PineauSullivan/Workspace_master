package premier_test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class creationRessource {
	// some definitions
	static String personURI    = "http://somewhere/JohnSmith";
	static String fullName     = "Johnssss Smith";
	
	public void execute() {
	
	
		// create an empty Model
		Model model = ModelFactory.createDefaultModel();
	
		// create the resource
		Resource johnSmith = model.createResource(personURI);
	
		// add the property
		johnSmith.addProperty(VCARD.FN,fullName);
		model.write(System.out,"Turtle");
		
	 }
}
