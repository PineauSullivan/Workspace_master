package framework;


import java.io.FileReader;
import java.lang.reflect.*;
import java.util.*;

public class ChargeurDeBean {

	public ChargeurDeBean() {
		
	}
	
	public static Object charger(String file){
		Properties prop = new Properties();
		Object mon_objet;
		try {
			prop.load(new FileReader(file));
			
			Class<?> cl = Class.forName((String)prop.get("class"));
			
			mon_objet = cl.newInstance();
			
			for(Object key : prop.keySet()){
				if(!key.equals("class")){
					//Method m = cl.getMethod("set"+(String)key, String.class);
					Method getter = cl.getMethod("get"+(String)key);
					Method m = cl.getMethod("set" + key, getter.getReturnType());
					
					if(getter.getReturnType().equals(int.class))
						m.invoke(mon_objet, Integer.parseInt((String)prop.get(key)));
					else
						m.invoke(mon_objet, (String)prop.get(key));
				}
				
			}
			return mon_objet;
			
		} 
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Bonjour, votre ordinateur a explosé");
		}
		
		
		return null;
		
		
		
	}

}
