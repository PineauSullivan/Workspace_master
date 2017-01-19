package framework;

import java.lang.reflect.Method;
import Anotations.*;


public class conversion {
	public static Object conversonBrutale(Object source, Class<?> targetClasse ){
		Object obj;
		try {
			obj = targetClasse.newInstance();
			for(Method m : source.getClass().getMethods()){
				if(isGetterAnno(m)){
					Method mm;
					mm = findSetter(m,targetClasse);
					if(mm!=null)
						mm.invoke(obj, m.invoke(source));
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private static boolean isGetter(Method m){
		String verifType = m.getName().substring(0, 3);
		String attribut = m.getName().substring(3);
		return verifType.equals("get")&&!attribut.equals("Class");
	}
	
	private static boolean isGetterAnno(Method m){
		
		if( m.getAnnotation(CallMe.class) == null)
			return false;
		else{
			return m.getAnnotation(CallMe.class).param().equals("get");
		}
			
	}
	
	private static Method findSetter(Method m, Class<?> cl){
		String attribut = m.getName().substring(3);
		try {
			return cl.getMethod("set"+attribut, m.getReturnType());
		} catch (Exception e) {
			System.out.println("perte d'information : "+attribut);
		}
		return null;
	}
	
}
