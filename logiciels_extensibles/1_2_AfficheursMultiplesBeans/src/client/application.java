package client;

import dependances.*;

public class application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IAfficheur a;
		
		Personne p = new Personne();
		p.setNom("George");
		p.setVille("Nantes");
		p.setAge(45);
		
		a = (IAfficheur)framework.
				ChargeurConfigurations.charger("src/configuration/config.txt",IAfficheur.class);
		
		if(a==null){
			System.out.println("Erreur de chargement de la configuration !!");
		}else{
			a.Afficher(p);
		}
	}

}
