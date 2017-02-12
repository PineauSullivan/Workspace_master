/**
 * @file Noeud.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Noeud
 *
**/

#ifndef NOEUD_HPP
#define NOEUD_HPP

class Noeud{
	private:
		OPERATIONS classname;

	public:
		Noeud();
		void setOperations(OPERATIONS name);
		virtual std::string toString(int depth) = 0;
};

#endif // NOEUD_HPP