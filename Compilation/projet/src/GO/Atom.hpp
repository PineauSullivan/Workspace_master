/**
 * @file Atom.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Atom
 *
**/
using namespace std;
 
#ifndef ATOM_HPP
#define ATOM_HPP

class Atom : public Noeud{
	private:
		std::string code;
		int action;
		ATOMETYPES type;		

	public:
		Atom(std::string code, int action, ATOMETYPES type);
		Atom(int code, int action, ATOMETYPES type);
		std::string toString(int level);
		virtual ATOMETYPES donneType();
		virtual int donneAction();
		virtual std::string donneCode();
};

#endif // ATOM_HPP