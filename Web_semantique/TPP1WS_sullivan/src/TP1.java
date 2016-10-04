import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.IteratorFactory;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.util.iterator.ExtendedIterator;

	/** Tutorial 1 creating a simple model
	 */

	public class TP1 {
	    // some definitions
	    static String personURI1    = "http://somewhere/JohnSmith";
	    static String personURI2    = "http://somewhere/RebeccaSmith";
	    static String personURI3    = "http://somewhere/SarahJones";
	    static String personURI4    = "http://somewhere/MattJones";
	    static String fullName1     = "John Smith";
	    static String fullName2     = "Becky Smith";
	    static String fullName3     = "Sarah Jones";
	    static String fullName4     = "Matt Jones";
	    static String Familyname12     = "Smith";
	    static String Familyname34    = "Jones";
	    static String Given1    = "John";
	    static String Given2    = "Becky";
	    static String Given3    = "Sarah";
	    static String Given4    = "Matt";
	    
	    
	      public static void main (String args[]) {
	        // create an empty model
	        Model model = ModelFactory.createDefaultModel();

	       // create the resource
		       Resource johnSmith =
		    		    model.createResource(personURI1)
		    	         .addProperty(VCARD.N,
		    	                      model.createResource()
		    	                   	       .addProperty(VCARD.Family, Familyname12)
		    	                   	       .addProperty(VCARD.Given, Given1))
		    	         .addProperty(VCARD.FN, fullName1);
		       
		       Resource rebeccaSmith =
		    		    model.createResource(personURI2)
			    	         .addProperty(VCARD.N,
			    	                      model.createResource()
			    	                   	       .addProperty(VCARD.Family, Familyname12)
			    	                   	       .addProperty(VCARD.Given, Given2))
			    	         .addProperty(VCARD.FN, fullName2);
		       
		       Resource sarahJones =
		    		    model.createResource(personURI3)
			    	         .addProperty(VCARD.N,
			    	                      model.createResource()
			    	                   	       .addProperty(VCARD.Family, Familyname34)
			    	                   	       .addProperty(VCARD.Given, Given3))
			    	  		 .addProperty(VCARD.FN, fullName3);
		       
		       Resource mattJones =
		    		    model.createResource(personURI4)
			    	         .addProperty(VCARD.N,
			    	                      model.createResource()
			    	                   	       .addProperty(VCARD.Family, Familyname34)
			    	                   	       .addProperty(VCARD.Given, Given4))
			    	         .addProperty(VCARD.FN, fullName4);

	      // add the property

	      model.write(System.out);
	      
	      ResIterator iter1 = model.listSubjectsWithProperty(VCARD.FN);
	      
	      if (iter1.hasNext()) {
	          System.out.println("La base de données contient les vcard de :");
	          while (iter1.hasNext()) {
	              System.out.println("  " + iter1.next()
	                                            .getProperty(VCARD.FN)
	                                            .getString());
	          }
	      } else {
	          System.out.println("Aucune vcard n'a été trouvée dans la base de données");
	      }
	      StmtIterator iter = model.listStatements(
	              new 
	                  SimpleSelector(null, VCARD.FN, (RDFNode) null) {
	                      public boolean selects(Statement s) {
	                              return s.getString().matches(".*t.*");
	                      }
	                  });
	          if (iter.hasNext()) {
	              System.out.println("The database contains vcards for:");
	              while (iter.hasNext()) {
	                  System.out.println("  " + iter.nextStatement()
	                                                .getString());
	              }
	          } else {
	              System.out.println("No .*t.* were found in the database");
	          }            
	      }
	      
	        
	     
	}

