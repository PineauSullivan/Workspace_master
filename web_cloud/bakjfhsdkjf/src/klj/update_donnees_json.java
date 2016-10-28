package klj;

import java.io.FileReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class update_donnees_json {
	
	private boolean update;
	
	public update_donnees_json(){
		this.update=false;
	}
	
	public int go_update(HttpServletResponse resp){
		int count = 0;
		if(! this.update){
			 JSONParser parser = new JSONParser();
			 try {
	
				 Object obj = parser.parse(new FileReader(
				 "donneestest.json"));
				 
	
	//			 Object obj = parser.parse(new FileReader(
	//			 "donnees.json"));
				
				 JSONArray jsonarray;
				
				 JSONObject jsonObject = (JSONObject) obj;
				
				 jsonObject = (JSONObject) jsonObject.get("results");
				
				 jsonarray = (JSONArray) jsonObject.get("bindings");
				
				
				 for (Object film : jsonarray){
					 count++;
					 JSONObject filmobj = (JSONObject) film;
					 JSONObject filmobjname = (JSONObject) filmobj.get("name");
					 String name = (String) filmobjname.get("value");
					 
					 filmobjname = (JSONObject) filmobj.get("name_director");
					 String name_director = (String) filmobjname.get("value");
					 
					 filmobjname = (JSONObject) filmobj.get("date");
					 String date = (String) filmobjname.get("value");
					 
					 filmobjname = (JSONObject) filmobj.get("country");
					 String country = (String) filmobjname.get("value");
					 
					 filmobjname = (JSONObject) filmobj.get("lat");
					 String lat = (String) filmobjname.get("value");
					 
					 filmobjname = (JSONObject) filmobj.get("longi");
					 String longi = (String) filmobjname.get("value");
					 
					 if(resp==null){
						 System.out.println("name : "+name);
						 System.out.println("name_director : "+name_director);
						 System.out.println("date : "+date);
						 System.out.println("country : "+country);
						 System.out.println("lat : "+lat);
						 System.out.println("longi : "+longi);
						 System.out.println("-----------------------------------------------------");
					 }else{
						resp.getWriter().println("name : "+name);
						resp.getWriter().println("name_director : "+name_director);
						resp.getWriter().println("date : "+date);
						resp.getWriter().println("country : "+country);
						resp.getWriter().println("lat : "+lat);
						resp.getWriter().println("longi : "+longi);
						
						DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
						Entity e = new Entity("Movie", "m" + count);
						e.setProperty("name", name);
						e.setProperty("name_director", name_director);
						e.setProperty("date", date);
						e.setProperty("country", country);
						e.setProperty("lat", lat);
						e.setProperty("longi", longi);
						datastore.put(e);
						
						resp.getWriter().println("Ajouté !");
						resp.getWriter().println("-----------------------------------------------------");
	
					 }
				 }
			 } catch (Exception e) {
			 e.printStackTrace();
			 }
			 
			 if(resp!=null){
				 this.update = true;
			 } 
		}
		return count;

	}
	
}
