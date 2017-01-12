package framework;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Properties;

public class ChargeurConfigurations {

	public ChargeurConfigurations() {
		// TODO Auto-generated constructor stub
	}
	
	private static boolean isDuckSubTypeOf(Class<?> pSuper, Class<?> pSub){
		if(pSuper.isAssignableFrom(pSub)) 
			return true;
		for(Method mSuper: pSuper.getMethods()){
			try {
				Method mSub = pSub.getMethod(mSuper.getName(), mSuper.getParameterTypes());
				if(!mSuper.getReturnType().isAssignableFrom(mSub.getReturnType())){
					return false;
				}
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		return true;
	}
	
	public static Object charger(String file, Class<?> classe){
		Properties prop = new Properties();
		Object mon_objet;
		
		try {
			prop.load(new FileReader(file));
			
			Class<?> cl = Class.forName((String)prop.get("affichage"));
			
			if(!isDuckSubTypeOf(classe,cl))
				mon_objet = null;
			else
				mon_objet = cl.newInstance();
			
			return mon_objet;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Bonjour, votre ordinateur a explosé");
		}
		
		
		return null;
		
		
		
	}

}
