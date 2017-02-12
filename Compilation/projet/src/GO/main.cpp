#include <iostream>
#include <string>

#include "EnumerationType.cpp"
#include "Noeud.cpp"
#include "Atom.cpp"
#include "Conc.cpp"
#include "Star.cpp"
#include "Un.cpp"
#include "Union.cpp"
#include "types.cpp"
#include "Grammaire.cpp"


int main()
{
	std::cout << "Test gen foret : " << std::endl;
	Grammaire gram;
	Foret * foret = gram.genForet();
	gram.afficheForet(foret);
	if(gram.GoAnalyse(foret[1]))
		std::cout<<"Analyse ok"<<std::endl;
	else	
		std::cout<<"Analyse Non"<<std::endl;
	return 0;
}