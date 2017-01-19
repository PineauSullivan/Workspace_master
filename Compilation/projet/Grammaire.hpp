/**
 * @file Grammaire.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe Grammaire
 *
**/
 

#include "EnumerationType.cpp"
#include "Noeud.cpp"

#ifndef GRAMMAIRE_HPP
#define GRAMMAIRE_HPP

class grammaire{
	public:

		Noeud * genConc(Noeud * p1, Noeud * p2);

		Noeud * genUnion(Noeud * p1, Noeud * p2);

		Noeud * genStar(Noeud * p);

		void genAtom(int code, int action, ATOMETYPES type);
		
		Noeud * genUN(Noeud * p);
}


#endif // GRAMMAIRE_HPP