package klj;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@SuppressWarnings("serial")
public class BakjfhsdkjfServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/plain");
		
		resp.getWriter().println("Ajout dans le data store ... ");


		update_donnees_json update_data = new update_donnees_json();
		
		int num_data = update_data.go_update(resp);
		
		resp.getWriter().println("ajout de "+num_data+" données.");

	}
}
