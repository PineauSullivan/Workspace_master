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
	bool complet = false;
	if(argc == 3){
	  	path = argv[1];
	  	path_code = argv[2];
	  	complet = true;
	}else{
		if(argc == 2){
		  	path = argv[1]; 
		}else{
			std::cout<< "Forme de l'appel du main : ./bin/main <chemin_fichier_grammaire> <chemin_fichier_code>" << std::endl;
			exit(0);
		}
	}
	
	////////////////////
	//Partie GO
	///////////////////

	Grammaire gram;
	
	Foret * foret = gram.genForet();


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

	std::cout<<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<"Veuillez patienter ... ... ..."<<std::endl;
	std::cout<<"------------------"<<std::endl;
	bool analyse = false;
	string str_go_analyse = "\033[1;31mFALSE\033[0m";
	if(gram.GOAnalyse((*variables->foret)["S"], variables)){
		str_go_analyse = "\033[1;32mTRUE\033[0m";
		analyse = true;
	}
	std::cout<<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<"|Analyse GO| -> " + str_go_analyse <<std::endl;
	std::cout<<"------------------"<<std::endl;
	std::cout<<std::endl;
	
	if(analyse&&!complet){
		
		if(path=="src/grammaire/grammaireGO.txt"){

			cout<<"----------------------------------------------------------------"<<endl;
			cout<<"Comparaison entre les deux GO : "<<endl;
			cout<<"----------------------------------------------------------------"<<endl;
		
			gram.afficheForet(foret,"S");
			gram.afficheForetGrammaire(variables->foretsGrammaire,0);
			std::cout<<"------------------"<<std::endl;
			std::cout<<"------------------------------------"<<std::endl;
			std::cout<<"------------------"<<std::endl;
			gram.afficheForet(foret,"N");
			gram.afficheForetGrammaire(variables->foretsGrammaire,1);
			std::cout<<"------------------"<<std::endl;
			std::cout<<"------------------------------------"<<std::endl;
			std::cout<<"------------------"<<std::endl;
			gram.afficheForet(foret,"E");
			gram.afficheForetGrammaire(variables->foretsGrammaire,2);
			std::cout<<"------------------"<<std::endl;
			std::cout<<"------------------------------------"<<std::endl;
			std::cout<<"------------------"<<std::endl;
			gram.afficheForet(foret,"T");
			gram.afficheForetGrammaire(variables->foretsGrammaire,3);
			std::cout<<"------------------"<<std::endl;
			std::cout<<"------------------------------------"<<std::endl;
			std::cout<<"------------------"<<std::endl;
			gram.afficheForet(foret,"F");
			gram.afficheForetGrammaire(variables->foretsGrammaire,4);
			std::cout<<"------------------"<<std::endl;
			std::cout<<"------------------------------------"<<std::endl;
		}else{

			cout<<"----------------------------------------------------------------"<<endl;
			cout<<"Foret GO de base : "<<endl;
			cout<<"----------------------------------------------------------------"<<endl;
			

			gram.afficheForet(foret);


			cout<<"----------------------------------------------------------------"<<endl;
			cout<<"Foret GPL généré : "<<endl;
			cout<<"----------------------------------------------------------------"<<endl;
						
			gram.afficheForetGrammaire(variables->foretsGrammaire);

			// cout<<"----------------------------------------------------------------"<<endl;
			// cout<<"DICONT : "<<endl;
			// cout<<"----------------------------------------------------------------"<<endl;

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

		}
	}else{
		if(!analyse){
			std::cout<<"\033[1;31mError de l'analyse GO, veuillez vérifier la ";
			std::cout<<" ligne : "<<variables->scan_ligne;
			std::cout<<" de votre grammaire GPL !";
			std::cout<<"\033[0m"<<std::endl;
		}
	}


	////////////////////
	//Partie GPL
	///////////////////

	GPL gpl;
	
	if(analyse&&complet){
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
			
			//
			//Ecriture PCode
			//
			ofstream fichier(path_code+".pcode", ios::out | ios::trunc); 
			std::vector<string> p_code_f = gpl.getP_code();
			for(int i =0; i<p_code_f.size(); ++i){
				fichier << p_code_f[i]<<endl;
			}
			fichier.close();
			
		}else{
			std::cout<<"\033[1;31mError de l'analyse GPL, veuillez vérifier la ";
			std::cout<<" ligne : "<<variables->scan_ligne_GPL;
			std::cout<<" (ou en dessous si imbriquement) de votre Code !";
			std::cout<<"\033[0m"<<std::endl;
		}


	}


	////////////////////
	//Partie INTERPRETEUR
	///////////////////

	if(analyse&&complet){
		std::vector<string> variable = gpl.getVariables();
		std::vector<string> p_code = gpl.getP_code();
		std::cout<<"INTERPRETEUR :"<<std::endl;
		Interpreteur *inter = new Interpreteur(p_code, variable.size());
		inter->executer();
	}

	std::cout<<std::endl;
	std::cout<<"------------------------------------"<<std::endl;
	std::cout<<"FIN MAIN"<<std::endl;
	return 0;
}