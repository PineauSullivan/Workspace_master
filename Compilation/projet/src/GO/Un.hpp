/**
 * @file Un.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Un
 *
**/ 

#ifndef UN_HPP
#define UN_HPP

class Un : public Noeud{
	private:
		Noeud *un;

	public:
		Un(Noeud *u);
		std::string toString(int level);
		Noeud* gauche();

};

#endif // UN_HPP