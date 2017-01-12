package dependances;

import client.IAfficheur;

public class AffichageB implements IAfficheur{

	public void Afficher(Personne p){
		System.out.println(p.getNom()+"-AffichageB");
	}

}
