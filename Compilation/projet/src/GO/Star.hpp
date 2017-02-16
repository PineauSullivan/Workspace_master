/**
 * @file Star.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Star
 *
**/ 

#ifndef STAR_HPP
#define STAR_HPP

class Star : public Noeud{
	private:
		Noeud *star;

	public:
		Star(Noeud *s);
		virtual std::string toString(int level);
		virtual Noeud* gauche();

};

#endif // STAR_HPP