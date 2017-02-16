/**
 * @file Grammaire.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe Grammaire
 *
**/

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

		bool GoAnalyse(Noeud *noeud);

		void GPAction(int act);

		std::string Scan(std::string code);

};


#endif // GRAMMAIRE_HPP