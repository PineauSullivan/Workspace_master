import premier_test.creationRessource;

import without_maven.TP1;


public class main {
	
	public static void main (String args[]) {
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 0 (WITHOUT_MAVEN) :\n");
		
		TP1 ma_ressource_0 = new TP1();
		ma_ressource_0.execute();
		
		System.out.println("FIN DU TEST 0 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("TEST 1 (premier_test) :\n");
		
		creationRessource ma_ressource_1 = new creationRessource();
		ma_ressource_1.execute();
		
		System.out.println("FIN DU TEST 1 \n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
		
		
		
	 }
}
