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

Noeud * Grammaire::genAtom(int code, int action, ATOMETYPES type){
	return new Atom(code,action, type);
}

Noeud * Grammaire::genUN(Noeud * p){
	return new Noeud(p,NULL, UN);
}

Noeud * Grammaire::genForet(){
	Noeud *g = new Noeud[5];

	//Règle 1 de la grammaire des grammaires
	g[0] = genConc(
			genStar(
				genConc(
					genConc(
						genConc(genAtom(000, 0, NonTerminal), 
						genAtom(000, 0, Terminal)
					),
					genAtom(000, 0, NonTerminal)
				),
				genAtom(000, 1, Terminal)
				)
			),
		genAtom(000, 0, Terminal)
		);


	//Règle 2 de la grammaire des grammaires
	g[1] = genAtom(000,2,Terminal);


	//Règle 3 de la grammaire des grammaires
	g[2] = genConc(
			genAtom(000,0, NonTerminal),
			genStar(
				genConc(
					genAtom(000,0,Terminal),
					genAtom(000,3,NonTerminal)
				)
			)
		);

	//Règle 4 de la grammaire des grammaires
	g[3] = genConc(
			genAtom(000,0, NonTerminal),
			genStar(
				genConc(
					genAtom(000,0,Terminal),
					genAtom(000,4,NonTerminal)
				)
			)
		);

	//Règle 5 de la grammaire des grammaires
	g[4] = genUnion(
			genAtom(000,0, NonTerminal),
			genAtom(000,0, NonTerminal)
		);

	return g;
}