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

	std::cout<<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::string str_go_analyse = (gram.GOAnalyse(foret[0]))?"\033[1;32mTRUE\033[0m":"\033[1;31mFALSE\033[0m";
	std::cout<<"|Analyse| -> " + str_go_analyse <<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<std::endl;

	return 0;
}