import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.IteratorFactory;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.*;

	/** Tutorial 1 creating a simple model
	 */

	public class TP1 {

		public static void readingRDF(String filename){

			// Open RDF file
			InputStream in = FileManager.get().open(filename);
			if (in == null) {
			    throw new IllegalArgumentException("File not found");
			}
	        Model model = ModelFactory.createDefaultModel() ;

			// Load file content
			model.read(in, null);
			//model.write(System.out, "Turtle");
		}

		public static String readQuery(String filename){
			try {
	            File file = new File(filename);
	            FileInputStream inputStream = new FileInputStream(file);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            StringBuilder data = new StringBuilder();
	            String line;

	            while ((line = reader.readLine()) != null) {
	                data.append(line + "\n");
	            }

	            inputStream.close();
				return data.toString();
	        }
	        catch(Exception ex){
	            return null;
	        }
		}
		public static void doRequest(String filename){

			String request = readQuery(filename);

			System.out.println("\n-> Request on file: \"" + filename + "\"");
			System.out.println(request);
			System.out.println("-> Perform request...\n");
	        Model model = ModelFactory.createDefaultModel() ;

			Query query = QueryFactory.create(request);
			QueryExecution qexec = QueryExecutionFactory.create(query, model);
			ResultSet results =  qexec.execSelect();

			// Output query results	
			ResultSetFormatter.out(System.out, results, query);

			// Important - free up resources used running the query
			qexec.close();
		}
	      public static void main (String args[]) {
	        // create an empty model
	        Model model = ModelFactory.createDefaultModel() ;
	        model.read("donnees.rdf");
	        model.write( System.out, "RDF/XML");
	        
	        String requete1 = "PREFIX ex: <http://example.org/>" +
	        		" " +
	        		"select ?name_author ?title where{ " +
	    	        "?book a ex:Book ." +
	    	        "?book ex:title ?title ." +
	    	        "optional{" +
		    	        "?author ex:name ?name_author ." +
		    	        "?author  ex:wrote ?book ." +
		    	        "?author a ex:Person ." +
		    	        "}"+
	        		"}";
	       
//	        String requete1 = "PREFIX ex: <http://example.org/> " +
//	        		"select * where{?s ?p ?o .}";
	        
	        String requete_insert ="PREFIX ex: <http://example.org/> " +
	        		"INSERT DATA { " +
	        		" ex:book42 ex:title 'book42' . " +
	        		" ex:book42 a ex:Book ." +
	        		" ex:auteur42 ex:wrote ex:book42 ." +
	        		" ex:auteur42 ex:name 'auteur42' ." +
	        		" ex:auteur42 a ex:Person ." +
	        		"ex:book22 ex:title 'book22' . " +
	        		" ex:book22 a ex:Book ." +
	        		"}";
	        
	        UpdateAction.parseExecute( requete_insert, model );
	        
	        System.out.println(" ");
	        System.out.println("-------------------------------------------");
	        System.out.println(" ");

	        model.write( System.out, "RDF/XML");

	        Query query1 = QueryFactory.create(requete1) ;
	        QueryExecution qexec = QueryExecutionFactory.create(query1, model) ;
	        // Execution de la requete
	        try {
//	           Pour l'instant nous nous limitons a des requetes de type SELECT
	          ResultSet rs = qexec.execSelect() ;
	     
	          // Affichage des resultats
	        //Affichage du resultat
	          
          		while(rs.hasNext()) {
		 
			        QuerySolution row = (QuerySolution)rs.next();
			        System.out.print(row.toString());
			        System.out.println(" ");
	          	}
	          
	        }
	        finally{
	          qexec.close() ;
	        }
      }
	      
	        
	     
}

