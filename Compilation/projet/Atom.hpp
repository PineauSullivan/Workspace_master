/**
 * @file Atom.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Atom
 *
**/
 
#include "enumerationType.cpp"

#ifndef ATOM_HPP
#define ATOM_HPP

class Atom : Noeud{
	private:
		int code;
		int action;
		ATOMETYPES type;


	public:
		Atom(int code, int action, ATOMETYPES type);



}

#endif // ATOM_HPP