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
  	grammaire.insert (grammaire.end(),"S1 := [ N1 . ':=' . E1 . ','#6 . ] . ';' ,");
  	grammaire.insert (grammaire.end(),"N1 -> 'IDNTER'#2 ,");
  	grammaire.insert (grammaire.end(),"E1 -> T1 . [ '+' . T1#3 ] ,");
  	grammaire.insert (grammaire.end(),"'T1 -> F1 . [ '.' . F1#4 ] ,");
  	grammaire.insert (grammaire.end()," F1 -> 'IDNTER'#5 + 'ELTER'#5 + '(' . E1 . ')' + '[' . E . ']'#6 + '(/' . E . '/)'#7 , ;");
 	// grammaire.insert (grammaire.end(),"abcdefg'dfdfssdf");
  	variables->grammaire=grammaire;

	// std::cout<<std::endl;
	// std::cout<<"------------------"<<std::endl;
	// std::string str_go_analyse = (gram.GOAnalyse(foret[0], * variables))?"\033[1;32mTRUE\033[0m":"\033[1;31mFALSE\033[0m";
	// std::cout<<"|Analyse| -> " + str_go_analyse <<std::endl;
	// std::cout<<"------------------"<<std::endl;
	// std::cout<<std::endl;

	Noeud* result = gram.genAtom("",0,Terminal);
	int regle = 1;
	while(regle!=variables->grammaire.size()+1){
		if(variables->scan_col==0){
			std::cout<<"regle "<<regle<<" : "<<variables->grammaire[regle-1]<<std::endl;
		}
		result = gram.Scan(variables);
		std::cout<<regle<<" -"<<result->toString(1)<<std::endl;
		if(variables->scan_col==0){
			regle++;
			std::cout<<std::endl;
		}
	}
	std::cout<<"FIN"<<std::endl;
	
	std::cout<<"test estVariable : "<<gram.estVariable("aaaa34aAAA")<<std::endl;
	std::cout<<"test estApostrophe : "<<gram.estApostrophe("'")<<std::endl;
	// std::cout<<"test action : -"<<gram.donneActionChaine(variables)<<"-"<<std::endl;
	// std::cout<<"test string : - "<<gram.getString(variables)<<"-"<<std::endl;
	return 0;
}
