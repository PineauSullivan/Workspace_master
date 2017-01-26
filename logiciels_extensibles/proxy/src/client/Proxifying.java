package client;

import java.lang.reflect.Proxy;

import dependances.IPersonne;
import framework.LogHandler;

public class Proxifying {

	public Proxifying() {
		// TODO Auto-generated constructor stub
	}
	
	public static Object proxify(Object o){
		return Proxy.newProxyInstance(o.getClass().getClassLoader(), 
				concat(o.getClass().getInterfaces(),IPersonne.class.getInterfaces()),
				new LogHandler(o));
	}
	
	public static Class<?>[] concat(Class<?>[] tab1, Class<?>[] tab2){
		Class<?>[] resultat = tab1;
		int taille = resultat.length;
		for(Class<?> o : tab2){
			resultat[taille] = o;
			taille += 1;
		}
		
		return resultat;
		
		
		
	}
	
	
}
