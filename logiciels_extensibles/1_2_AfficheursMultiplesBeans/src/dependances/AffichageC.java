package dependances;

import client.IAfficheur;

public class AffichageC  implements IAfficheur{

	public void Afficher(Personne p){
		System.out.println(p.getNom()+"-AffichageC");
	}
}
