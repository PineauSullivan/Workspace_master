/**
 * @file Noeud.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Noeud
 *
**/

#include "enumerationType.cpp"

#ifndef NOEUD_HPP
#define NOEUD_HPP

class Noeud{
	private:
		Noeud * arbreGauche;
		Noeud * arbreDroit;
		OPERATIONS classe;


	public:
		Noeud(Noeud * aG, Noeud * aD, OPERATIONS cl);

		Noeud * getArbreGauche();

		Noeud * getArbreDroit();

		Noeud * getClasse();

};

#endif // NOEUD_HPP