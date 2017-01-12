package dependances;

import client.IAfficheur;

public class AffichageA implements IAfficheur {

	public void Afficher(Personne p){
		System.out.println(p.getNom()+"-AffichageA");
	}

}
