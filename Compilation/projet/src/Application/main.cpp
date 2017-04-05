#include <iostream>
#include <string>
#include <regex>
#include <fstream>

#include "../GO/EnumerationType.cpp"
#include "../GO/Noeud.cpp"
#include "../GO/Atom.cpp"
#include "../GO/Conc.cpp"
#include "../GO/Star.cpp"
#include "../GO/Un.cpp"
#include "../GO/Union.cpp"
#include "../GO/types.cpp"
#include "../GO/Grammaire.cpp"
#include "../GPL/GPL.cpp"

#include "../Interpreteur/Interpreteur.cpp"


int main(int argc, char ** argv)
{

	std::string path;
	std::string path_code;
	if(argc == 3){
	  	path = argv[1];
	  	path_code = argv[2];
	}else{
		std::cout<< "Forme de l'appel du main : ./bin/main <chemin_fichier_grammaire> <chemin_fichier_code>" << std::endl;
		exit(0);
	}
	
	////////////////////
	//Partie GO
	///////////////////

	std::cout << "Test gen foret : " << std::endl;
	Grammaire gram;
	
	Foret * foret = gram.genForet();
	// gram.afficheForet(foret);


	VariablesGlobales * variables = new VariablesGlobales;
	variables->foret = foret;
	variables->pileGOAction = new PileGOAction;
	variables->scan_col = 0;
	variables->scan_ligne = 0;
	gram.remplirDictionnaireG0(variables);



  	ifstream fichier(path, ios::in);  // on ouvre en lecture

	std::vector<std::string> grammaire;

    if(fichier)  // si l'ouverture a fonctionné
    {
		    string ligne;
		    while(getline(fichier, ligne))  // tant que l'on peut mettre la ligne dans "contenu"
		    {
				grammaire.insert (grammaire.end(),ligne);
		    }
            fichier.close();
    }
    else{
	    cerr << "Impossible d'ouvrir le fichier !" << endl;
    }

  	variables->grammaire=grammaire;

	// Noeud* result = gram.genAtom("",0,Terminal);
	// int regle = 1;
	// while(regle!=variables->grammaire.size()+1){
	// 	if(variables->scan_col==0){
	// 		std::cout<<"regle "<<regle<<" : "<<variables->grammaire[regle-1]<<std::endl;
	// 	}
	// 	result = gram.Scan(variables);
	// 	std::cout<<regle<<" -"<<result->toString(1)<<std::endl;
	// 	if(variables->scan_col==0){
	// 		regle++;
	// 		std::cout<<std::endl;
	// 	}
	// }

	std::cout<<std::endl;
	std::cout<<"------------------"<<std::endl;
	bool analyse = false;
	string str_go_analyse = "\033[1;31mFALSE\033[0m";
	if(gram.GOAnalyse((*variables->foret)["S"], variables)){
		str_go_analyse = "\033[1;32mTRUE\033[0m";
		analyse = true;
	}
	std::cout<<"|Analyse GO| -> " + str_go_analyse <<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<std::endl;
	
	if(analyse){

		//
		//Affichage dicont
		//
		//
		// std::cout<<std::endl;
		// cout << "------------------------------" << endl; 
		// std::cout<<std::endl;
		// std::cout<<"dicont : "<<std::endl;
		// for (map<string,int>::iterator  i=variables->dicont.begin(); i!=variables->dicont.end(); ++i)
		// {
		// 	cout << i->first <<" -> "<<i->second << endl;
		// 	cout << "------------------------------" << endl; 
		// }
		// std::cout<<std::endl;
		// cout << "------------------------------" << endl; 
		// std::cout<<std::endl;

		//Affichage FORET
		//
		// gram.afficheForetGrammaire(variables->foretsGrammaire);
		//


		// 	gram.afficheForet(foret,"S");
		// 	gram.afficheForetGrammaire(variables->foretsGrammaire,0);
		// 	std::cout<<"------------------"<<std::endl;
		// 	std::cout<<"------------------------------------"<<std::endl;
		// 	std::cout<<"------------------"<<std::endl;
		// 	gram.afficheForet(foret,"N");
		// 	gram.afficheForetGrammaire(variables->foretsGrammaire,1);
		// 	std::cout<<"------------------"<<std::endl;
		// 	std::cout<<"------------------------------------"<<std::endl;
		// 	std::cout<<"------------------"<<std::endl;
		// 	gram.afficheForet(foret,"E");
		// 	gram.afficheForetGrammaire(variables->foretsGrammaire,2);
		// 	std::cout<<"------------------"<<std::endl;
		// 	std::cout<<"------------------------------------"<<std::endl;
		// 	std::cout<<"------------------"<<std::endl;
		// 	gram.afficheForet(foret,"T");
		// 	gram.afficheForetGrammaire(variables->foretsGrammaire,3);
		// 	std::cout<<"------------------"<<std::endl;
		// 	std::cout<<"------------------------------------"<<std::endl;
		// 	std::cout<<"------------------"<<std::endl;
		// 	gram.afficheForet(foret,"F");
		// 	gram.afficheForetGrammaire(variables->foretsGrammaire,4);
		// 	std::cout<<"------------------"<<std::endl;
		// 	std::cout<<"------------------------------------"<<std::endl;
	}else{
		std::cout<<"\033[1;31mError de l'analyse GO, veuillez vérifier la ";
		std::cout<<" ligne : "<<variables->scan_ligne;
		std::cout<<" de votre grammaire GPL !";
		std::cout<<"\033[0m"<<std::endl;
	}


	
	// std::cout<<"test estVariable : "<<gram.estVariable("aaaa34aAAA")<<std::endl;
	// std::cout<<"test estApostrophe : "<<gram.estApostrophe("'")<<std::endl;
	// std::cout<<"test action : -"<<gram.donneActionChaine(variables)<<"-"<<std::endl;
	// std::cout<<"test string : - "<<gram.getString(variables)<<"-"<<std::endl;


	////////////////////
	//Partie GPL
	///////////////////

	GPL gpl;
	
	if(analyse){
		variables->scan_col_GPL = 0;
		variables->scan_ligne_GPL = 0;
	  	    
	  	ifstream fichierCode(path_code, ios::in);  // on ouvre en lecture

		std::vector<std::string> grammaire_code;

	    if(fichierCode)  // si l'ouverture a fonctionné
	    {
			    string ligne;
			    while(getline(fichierCode, ligne))  // tant que l'on peut mettre la ligne dans "contenu"
			    {
					grammaire_code.insert (grammaire_code.end(),ligne);
			    }
	            fichierCode.close();
	    }
	    else{
		    cerr << "Impossible d'ouvrir le fichierCode !" << endl;
	    }

	  	variables->code=grammaire_code;


		

		std::cout<<std::endl;
		std::cout<<"------------------"<<std::endl;
		analyse = false;
		string str_gpl_analyse = "\033[1;31mFALSE\033[0m";
		if(gpl.GPLAnalyse(variables->foretsGrammaire[0], variables)){
			str_gpl_analyse = "\033[1;32mTRUE\033[0m";
			analyse = true;
		}
		std::cout<<"|Analyse GPL| -> " + str_gpl_analyse <<std::endl;
		std::cout<<"------------------"<<std::endl;
		std::cout<<std::endl;

		if(analyse){
			//
			//Affiche variables
			//
			// std::vector<string> variable = gpl.getVariables();
			// cout<<"variable :"<<endl;
			// for(int i =0; i<variable.size(); ++i){
			// 	cout<<variable[i]<<endl;
			// }

			
			//
			//Affiche PCode
			//
			// std::vector<string> p_code = gpl.getP_code();
			// cout<<"p code :"<<endl;
			// for(int i =0; i<p_code.size(); ++i){
			// 	cout<<i<<" -> "<<p_code[i]<<endl;
			// }
			
		}else{
			std::cout<<"\033[1;31mError de l'analyse GPL, veuillez vérifier la ";
			std::cout<<" ligne : "<<variables->scan_ligne_GPL;
			std::cout<<" de votre Code !";
			std::cout<<"\033[0m"<<std::endl;
		}


	}


	////////////////////
	//Partie INTERPRETEUR
	///////////////////

	if(analyse){
		std::vector<string> variable = gpl.getVariables();
		std::vector<string> p_code = gpl.getP_code();
		std::cout<<"INTERPRETEUR :"<<std::endl;
		Interpreteur *inter = new Interpreteur(p_code, variable.size());
		inter->executer();
		std::cout<<"FIN MAIN"<<std::endl;
	}

	return 0;
}