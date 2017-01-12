package dependances;

import client.IAfficheur;

public class AffichageD  implements IAfficheur{

	public void Afficher(Personne p){
		System.out.println(p.getNom()+"-AffichageD");
	}
}
