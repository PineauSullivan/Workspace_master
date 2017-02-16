/**
 * @file Conc.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Conc
 *
**/ 

#ifndef CONC_HPP
#define CONC_HPP

class Conc : public Noeud{
	private:
  		Noeud *right;
  		Noeud *left;
	public:
		Conc(Noeud *r, Noeud *l);
		virtual std::string toString(int level);
		virtual Noeud* droite();
		virtual Noeud* gauche();

};

#endif // CONC_HPP