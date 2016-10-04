import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import premier_test.creationRessource;

import without_maven.TP1;

import tests_suivants.*;


public class main {
	
	public static void main (String args[]) {
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 0 (WITHOUT_MAVEN) :\n");
		
		TP1 ma_ressource_0 = new TP1();
		ma_ressource_0.execute();
		
		System.out.println("FIN DU TEST 0 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 1 (premier_test) :\n");
		
		creationRessource ma_ressource_1 = new creationRessource();
		ma_ressource_1.execute();
		
		System.out.println("FIN DU TEST 1 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 2 (tests_suivants) :\n");
		
		Ressources ma_ressource_2 = new Ressources();
		ma_ressource_2.execute();
		
		System.out.println("FIN DU TEST 2 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 3 (ressources_internet) :\n");
		
		UnModel model = new UnModel();
		
		NouvelleRessource JohnSmith = new NouvelleRessource("http://somewhere/JohnSmith", 
															"John",
															"Smith"
															);
		NouvelleRessource RebeccaSmith = new NouvelleRessource("http://somewhere/RebeccaSmith", 
															"Rebecca",
															"Smith",
															"Becky Smith"
															);
		
		NouvelleRessource SarahJones = new NouvelleRessource("http://somewhere/SarahJones", 
															"Sarah",
															"Jones"
															);
		NouvelleRessource MattJones = new NouvelleRessource("http://somewhere/MattJones/",
				"Matthew",
				"Jones",
				"Matt Jones"
				);
		
		JohnSmith.ajoutDansUnModel(model);
		RebeccaSmith.ajoutDansUnModel(model);
		SarahJones.ajoutDansUnModel(model);
		MattJones.ajoutDansUnModel(model);
		
		model.write(System.out);
		
		
		System.out.println("FIN DU TEST 3 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		
		
	 }
}
