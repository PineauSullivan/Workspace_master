package client;

import dependances.basicClass;
import dependances.forcedClass;
import framework.conversion;

public class application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		basicClass basicCl = new basicClass("nom","prenom",21);
		Object obj = conversion.conversonBrutale(basicCl,forcedClass.class);
		System.out.println(obj.toString());
		if(obj.equals(null)){
			System.out.println("GROS PB");
		}
		System.out.println("fin");
	}

}
