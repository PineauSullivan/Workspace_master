/**
* @file Grammaire.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Grammaire
*
**/

#include "Grammaire.hpp"

Noeud * Grammaire::genConc(Noeud * p1, Noeud * p2){
	return new Noeud(p1, p2, CONC);
}

Noeud * Grammaire::genUnion(Noeud * p1, Noeud * p2){
	return new Noeud(p1, p2, UNION);
}

Noeud * Grammaire::genStar(Noeud * p){
	return new Noeud(p,NULL, STAR);
}

void Grammaire::genAtom(int code, int action, ATOMETYPES type){

}

Noeud * Grammaire::genUN(Noeud * p){
	return new Noeud(p,NULL, UN);
}