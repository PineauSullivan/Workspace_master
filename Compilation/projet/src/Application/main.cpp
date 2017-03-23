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


	VariablesGlobales * variables = new VariablesGlobales;
	variables->foret = foret;
	variables->pileGOAction = new PileGOAction;
	variables->scan_col = 0;
	variables->scan_ligne = 0;

	std::vector<std::string> grammaire;
  	grammaire.insert (grammaire.end(),"'s' := 'a' '+' 'b' ,");
  	grammaire.insert (grammaire.end(),"'z' := 'c' '+' 'x' ,");
  	grammaire.insert (grammaire.end(),"'r' := 'a' '+' 'z' ,");
  	grammaire.insert (grammaire.end(),"'h' := 'm' '+' 't' ,");
  	grammaire.insert (grammaire.end(),"'p' := 'q' '+' 'x' ;");
  	variables->grammaire=grammaire;

	std::cout<<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::string str_go_analyse = (gram.GOAnalyse(foret[0], * variables))?"\033[1;32mTRUE\033[0m":"\033[1;31mFALSE\033[0m";
	std::cout<<"|Analyse| -> " + str_go_analyse <<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<std::endl;

	Noeud* result;
	int regle = 1;
	do{
		result = gram.Scan(variables);
		if(result->donneCode()!=";"){
			std::cout<<regle<<" -> "<<result->donneCode()<<std::endl;
		}
		if(variables->scan_col==0){
			regle++;
			std::cout<<std::endl;
		}
	}while(result->donneCode()!=";");
	

	return 0;
}