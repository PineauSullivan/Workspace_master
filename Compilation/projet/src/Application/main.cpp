#include <iostream>
#include <string>

#include "../GO/EnumerationType.cpp"
#include "../GO/Noeud.cpp"
#include "../GO/Atom.cpp"
#include "../GO/Conc.cpp"
#include "../GO/Star.cpp"
#include "../GO/Un.cpp"
#include "../GO/Union.cpp"
#include "../GO/types.cpp"
#include "../GO/Grammaire.cpp"


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