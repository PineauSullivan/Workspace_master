package client;

import dependances.*;


public class application {
	public static void main(String[] args){
		INomLocal la_personne = (INomLocal)framework.ChargeurDeBean.charger("src/donnees/bean.txt");
		
		if(la_personne != null)
			System.out.println(la_personne.toString());
		
	}
}
