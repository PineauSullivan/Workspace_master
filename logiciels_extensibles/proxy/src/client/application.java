package client;

import dependances.*;

public class application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPersonne pers = ((IPersonne)(Proxifying.proxify(new Personne())));
		pers.setNom("toto");
		System.out.println(pers.toString());
		pers.active();
		pers.setNom("lulu");
		System.out.println(pers.toString());
		pers.active();
		System.out.println(pers.toString());
		pers.setAll("popo","nantes",44);
		System.out.println(pers.toString());
		pers.active();
		System.out.println(pers.toString());
		pers.setAll("jean","nantes",48 );
		pers.active();
		System.out.println(pers.toString());
		

	}

}
