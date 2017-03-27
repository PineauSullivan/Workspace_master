/**
 * @file Grammaire.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe Grammaire
 *
**/

#include <regex>

#ifndef GRAMMAIRE_HPP
#define GRAMMAIRE_HPP


class Grammaire{
	public:

		Noeud * genConc(Noeud * p1, Noeud * p2);

		Noeud * genUnion(Noeud * p1, Noeud * p2);

		Noeud * genStar(Noeud * p);

		Noeud * genAtom(std::string code, int action, ATOMETYPES type);
		
		Noeud * genUN(Noeud * p);

		Foret * genForet();

		void afficheForet(Foret * F);

		bool GOAnalyse(Noeud *noeud,  VariablesGlobales variables);

		void GOAction(int actionG0, int actionGPL, std::string code, ATOMETYPES type, VariablesGlobales variables);

		Noeud* Scan(VariablesGlobales * variables);

		std::string rechercheDicoNT(std::string code, VariablesGlobales variables);

		bool estVariable(std::string chaine);

		bool estApostrophe(std::string chaine);

		bool estFleche(std::string chaine);

		bool estDiese(std::string chaine);

		bool finLigne(std::string chaine);

		int donneActionChaine(VariablesGlobales* variables);

		bool estEspace(std::string chaine);

		bool estVide(std::string chaine);

		std::string getString(VariablesGlobales* variables);

		std::string getStringSansApostrophe(VariablesGlobales* variables);
};


#endif // GRAMMAIRE_HPP