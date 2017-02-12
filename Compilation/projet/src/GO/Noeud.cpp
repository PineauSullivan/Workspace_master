/**
* @file Noeud.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Noeud
*
**/

#include "Noeud.hpp"

Noeud::Noeud(Noeud * aG, Noeud * aD, OPERATIONS cl){
	arbreGauche = aG;
	arbreDroit = aD;
	classe = cl;

}

Noeud * Noeud::getArbreGauche(){
	return arbreGauche;
}

Noeud * Noeud::getArbreDroit(){
	return arbreDroit;
}

Noeud * Noeud::getClasse(){
	return classe;
}