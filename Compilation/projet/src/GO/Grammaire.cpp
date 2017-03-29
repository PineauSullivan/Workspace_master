/**
* @file Grammaire.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Grammaire
*
**/

#include "Grammaire.hpp"

Noeud * Grammaire::genConc(Noeud * p1, Noeud * p2){
	return new Conc(p1, p2);
}

Noeud * Grammaire::genUnion(Noeud * p1, Noeud * p2){
	return new Union(p1, p2);
}

Noeud * Grammaire::genStar(Noeud * p){
	return new Star(p);
}

Noeud * Grammaire::genAtom(std::string code, int action, ATOMETYPES type){
	return new Atom(code,action, type);
}

Noeud * Grammaire::genUN(Noeud * p){
	return new Un(p);
}


void Grammaire::remplirDictionnaireG0(VariablesGlobales* variables){
	variables->dictionnaireG0["terminaisonGPL"].push_back(";");

	variables->dictionnaireG0["commencementRegle"].push_back("->");
	variables->dictionnaireG0["commencementRegle"].push_back(":=");
	variables->dictionnaireG0["terminaisonRegle"].push_back(",");

	variables->dictionnaireG0["concatenation"].push_back(".");
	variables->dictionnaireG0["union"].push_back("+");

	variables->dictionnaireG0["comencementStar"].push_back("[");	
	variables->dictionnaireG0["terminaisonStar"].push_back("]");

	variables->dictionnaireG0["commencementUn"].push_back("(");	
	variables->dictionnaireG0["terminaisonUn"].push_back(")");
	variables->dictionnaireG0["commencementUn"].push_back("(\\");	
	variables->dictionnaireG0["terminaisonUn"].push_back("\\)");

	variables->dictionnaireG0["commencementTerminaux"].push_back("'");
	variables->dictionnaireG0["terminaisonTerminaux"].push_back("'");

	variables->dictionnaireG0["identificationTerminaux"].push_back("ELTER");
	variables->dictionnaireG0["identificationNonTerminaux"].push_back("IDNTER");
	variables->dictionnaireG0["identificationAction"].push_back("#");


}


// Foret * Grammaire::genForet(){
// 	Foret *g = new Foret[5];

// 	//Règle 1 de la grammaire des grammaires
// 	g[0] = genConc(
// 			genStar(
// 				genConc(
// 					genConc(
// 						genConc(genAtom("N", 0, NonTerminal), 
// 						genAtom("commencementRegle", 0, Terminal)
// 					),
// 					genAtom("E", 0, NonTerminal)
// 				),
// 				genAtom("terminaisonRegle", 1, Terminal)
// 				)
// 			),
// 		genAtom("terminaisonGPL", 0, Terminal)
// 		);


// 	//Règle 2 de la grammaire des grammaires
// 	g[1] = genAtom("identificationNonTerminaux",2,Terminal);


// 	//Règle 3 de la grammaire des grammaires
// 	g[2] = genConc(
// 			genAtom("T",0, NonTerminal),
// 			genStar(
// 				genConc(
// 					genAtom("union",0,Terminal),
// 					genAtom("T",3,NonTerminal)
// 				)
// 			)
// 		);

// 	//Règle 4 de la grammaire des grammaires
// 	g[3] = genConc(
// 			genAtom("F",0, NonTerminal),
// 			genStar(
// 				genConc(
// 					genAtom("concatenation",0,Terminal),
// 					genAtom("F",4,NonTerminal)
// 				)
// 			)
// 		);

// 	//Règle 5 de la grammaire des grammaires
// 	g[4] = genUnion(
// 	  genUnion(
// 	      genUnion(genUnion(genAtom("identificationNonTerminaux", 5, Terminal), genAtom("identificationTerminaux", 5, Terminal)),
// 	             genConc(genAtom("commencementUn", 0, Terminal), genConc(genAtom("E", 0, NonTerminal),
// 	                                                 genAtom("terminaisonUn", 0, Terminal)))),
// 	      genConc(genAtom("comencementStar", 0, Terminal),
// 	            genConc(genAtom("E", 0, NonTerminal), genAtom("terminaisonStar", 6, Terminal)))),
// 	  genConc(genAtom("commencementUn", 0, Terminal),
// 	        genConc(genAtom("E", 0, NonTerminal), genAtom("terminaisonUn", 7, Terminal))));

// 	return g;
// }
Foret * Grammaire::genForet(){
	Foret * g = new Foret;

	//Règle 1 de la grammaire des grammaires
	(*g)["S"] = genConc(
			genStar(
				genConc(
					genConc(
						genConc(genAtom("N", 0, NonTerminal),
							genAtom(":=", 0, Terminal)
					),
					genAtom("E", 0, NonTerminal)
				),
				genAtom(",", 1, Terminal)
				)
			),
		genAtom(";", 0, Terminal)
		);


	//Règle 2 de la grammaire des grammaires
	(*g)["N"] = genAtom("IDNTER",2,Terminal);


	//Règle 3 de la grammaire des grammaires
	(*g)["E"] = genConc(
			genAtom("T",0, NonTerminal),
			genStar(
				genConc(
					genAtom("+",0,Terminal),
					genAtom("T",3,NonTerminal)
				)
			)
		);

	//Règle 4 de la grammaire des grammaires
	(*g)["T"] = genConc(
			genAtom("F",0, NonTerminal),
			genStar(
				genConc(
					genAtom(".",0,Terminal),
					genAtom("F",4,NonTerminal)
				)
			)
		);

	//Règle 5 de la grammaire des grammaires
	(*g)["F"] = genUnion(
	  genUnion(
	      genUnion(genUnion(genAtom("IDNTER", 5, Terminal), genAtom("ELTER", 5, Terminal)),
	             genConc(genAtom("(", 0, Terminal), genConc(genAtom("E", 0, NonTerminal),
	                                                 genAtom(")", 0, Terminal)))),
	      genConc(genAtom("[", 0, Terminal),
	            genConc(genAtom("E", 0, NonTerminal), genAtom("]", 6, Terminal)))),
	  genConc(genAtom("(\\", 0, Terminal),
	        genConc(genAtom("E", 0, NonTerminal), genAtom("\\)", 7, Terminal))));

	return g;
}

void Grammaire::afficheForet(Foret * F, std::string key = ""){
	if(key!=""){
		cout << "A["<<key<<"] :" << endl;
		cout << (*F)[key]->toString(0) << endl;	
		cout << "------------------------------" << endl; 
	}else{
		cout << "A[S] :" << endl;
		cout << (*F)["S"]->toString(0) << endl;
		for (map<string,Noeud*>::iterator  i=F->begin(); i!=F->end(); ++i)
		{
			if(i->first!="S"){
			cout << "A[" << i->first << "] :" << endl;
			cout << i->second->toString(0) << endl;
			cout << "------------------------------" << endl; 
			}
		}
	}
}

void Grammaire::afficheForetGrammaire(vector<Noeud*> foretsGrammaire, int key = -1){
	if(key!=-1){
		cout << "A[" << key << "] :" << endl;
		cout << foretsGrammaire[key]->toString(0) << endl;
		cout << "------------------------------" << endl; 
	}else{
		std::cout<<"taille foret Grammaire : "<<foretsGrammaire.size()<<std::endl;
		for(int i =0; i<foretsGrammaire.size(); ++i){
			cout << "A[" << i << "] :" << endl;
			cout << foretsGrammaire[i]->toString(0) << endl;
			cout << "------------------------------" << endl; 
		}
	}
	
}


bool Grammaire::GOAnalyse(Noeud *noeud,  VariablesGlobales* variables) {
	bool analyse = false;
	int col = 0;
	int ligne = 0;
	switch(noeud->donneClassname()){
		case CONC:
			if(GOAnalyse(noeud->gauche(), variables)){
				analyse = GOAnalyse(noeud->droite(), variables);
			}else{
				analyse = false;
			}
			break;
		
		case UNION:
			col = variables->scan_col;
			ligne = variables->scan_ligne;
			if(GOAnalyse(noeud->gauche(), variables)){
				analyse = true;
			}else{
				variables->scan_col = col;
				variables->scan_ligne = ligne;
				analyse =  GOAnalyse(noeud->droite(), variables);
			}
			break;
		
		case STAR:
			analyse = true;
			col = variables->scan_col;
			ligne = variables->scan_ligne;
			while(GOAnalyse(noeud->gauche(), variables)){
				col = variables->scan_col;
				ligne = variables->scan_ligne;
			};
			variables->scan_col = col;
			variables->scan_ligne = ligne;
			break;
		
		case UN:
			analyse = true;
			col = variables->scan_col;
			ligne = variables->scan_ligne;
			if(GOAnalyse(noeud->gauche(), variables)){			
			}else{
				variables->scan_col = col;
				variables->scan_ligne = ligne;
			}

			break;
		
		case ATOM:
			//REVOIR TOUS ATOM ET FONCTIONS GPAction, Scan
			Noeud* resultScan =  genAtom("-1",-1,Terminal); 
			switch(noeud->donneType()){
				case 0:
					if(GOAnalyse((*variables->foret)[noeud->donneCode()],variables)){
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction(),resultScan->donneAction(), resultScan->donneCode(), resultScan->donneType(), variables);
							
						}
						analyse = true;
					}
					break;

				case 1:
					resultScan =  Scan(variables);
					bool suite = false;
					if( noeud->donneCode()=="IDNTER" && !resultScan->donneType() && estVariable(resultScan->donneCode())){
						suite = true;
					}
					if( noeud->donneCode()=="ELTER" && resultScan->donneType()){
						suite = true;
					}
					if( suite ||noeud->donneCode()==resultScan->donneCode()){
						analyse = true;
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction(),resultScan->donneAction(), resultScan->donneCode(), resultScan->donneType(), variables);
							//SCAN
						}
					}else{
							analyse=false;
					}
					break;
				
			}
		break;
	}
	return analyse;

}

void Grammaire::GOAction(int actionG0, int actionGPL, std::string code, ATOMETYPES type, VariablesGlobales* variables){
	Noeud *t1;
	Noeud *t2;
	switch(actionG0){
		case 1:
			t1 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();
			t2 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();

			variables->foretsGrammaire.push_back(t1);
			// variables.dicont[t2->donneCode()] = variables.foretsGrammaire.size()-1;
			
			break;
		case 2:
			variables->pileGOAction->pile.push(
				genAtom(rechercheDicoNT(code, variables), actionGPL, Terminal)
				);
			break;
		case 3:
			t1 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();
			t2 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();

			variables->pileGOAction->pile.push(genUnion(t2,t1));

			break;
		case 4:
			t1 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();
			t2 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();

			variables->pileGOAction->pile.push(genConc(t2,t1));

			break;
		case 5:
			if(type == 0){
				variables->pileGOAction->pile.push(
				genAtom(code, actionGPL, Terminal)
				);
			}
			else{
				variables->pileGOAction->pile.push(
				genAtom(rechercheDicoNT(code, variables), actionGPL, NonTerminal)
				);
			}

			break;
		case 6:
			t1 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();

			variables->pileGOAction->pile.push(
				genStar(t1)
				);

			break;
		case 7:
			t1 = variables->pileGOAction->pile.top();
			variables->pileGOAction->pile.pop();

			variables->pileGOAction->pile.push(
				genUN(t1)
				);
			break;
	
	}


}

Noeud* Grammaire::Scan(VariablesGlobales * variables){
	std::string result = "";
	Noeud* atomResult;
	bool terminal=false;
	int action = 0;
	bool fin = true;
	bool entreApo = false;	
	if(variables->grammaire.size()>variables->scan_ligne){
		if(variables->grammaire[variables->scan_ligne].size()>variables->scan_col){
			int i = variables->scan_col;
			std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
			if(finLigne(st)){
				terminal = false;
				result = st;
				variables->scan_col++;
			}else if(estEspace(st)){
				variables->scan_col++;
				return Scan(variables);
			}else if(estApostrophe(st)){
				entreApo = true;
				fin = false;
				variables->scan_col++;
				result = getString(variables);
				variables->scan_col++;
				std::string actionTest= variables->grammaire[variables->scan_ligne].substr(variables->scan_col,1);
				action = 0;
				if(estDiese(actionTest)){
					action = donneActionChaine(variables);
				}
				terminal = true;

			}else{
				result = getStringSansApostrophe(variables);
				std::string actionTest=variables->grammaire[variables->scan_ligne].substr(variables->scan_col,1);
				if(estDiese(actionTest)){
					action = donneActionChaine(variables);
				}
				terminal = false;
				if(actionTest==";"){
					terminal = true;	
				}
			}
			if((result=="]"||result=="["||result==":=")&&!entreApo){
				terminal = false;
			}
			// std::cout<<"result -> "<<result<<" -> entreApo ? "<<entreApo<<", terminal ?"<<terminal<<std::endl;

			if(terminal){
				atomResult = genAtom(result, action, Terminal);
			}else{
				atomResult = genAtom(result,action, NonTerminal);
			}
		}
	}

	if(estEspace(result)||estVide(result)){
		variables->scan_col++;
		if(variables->scan_ligne<variables->grammaire.size()&&variables->scan_col<variables->grammaire[variables->scan_ligne].size()){
			return Scan(variables);
		}
	}

	if(variables->scan_col==(variables->grammaire[variables->scan_ligne].size())){
		variables->scan_ligne++;
		variables->scan_col=0;
	}
	if(variables->scan_ligne==(variables->grammaire.size())){
		atomResult = genAtom(";",0, NonTerminal);
	}
	return atomResult;
}

Noeud* Grammaire::ScanBis(VariablesGlobales * variables,int colonne, int ligne, std::string chaine){
	if(variables->grammaire.size()>ligne){
		if(variables->grammaire[ligne].size()>colonne){
			chaine = chaine + variables->grammaire[variables->scan_ligne].substr(colonne,1);

			if(estEspace(chaine)){
				chaine = "";
				return ScanBis(variables, colonne+1,ligne,chaine);
			}
			else{

				if(regex_match(chaine, regex(variables->regexTerminal))){
					variables->scan_col = colonne+1;
					variables->scan_ligne = ligne;

					return genAtom(chaine.substr(1,chaine.size()-2),ScanBisAction(variables, colonne+1, ligne), Terminal);
				}
				else if(regex_match(chaine, regex(variables->regexNonTerminal))){
					variables->scan_col = colonne+1;
					variables->scan_ligne = ligne;

					Noeud* tmp = ScanBis(variables,colonne+1,ligne, chaine);

					if(tmp == NULL)
						return genAtom(chaine,ScanBisAction(variables, colonne+1, ligne), NonTerminal);
					else
						return tmp;				
					

				}
				else if(regex_match(chaine, regex(variables->regexSymbole))){
					variables->scan_col = colonne+1;
					variables->scan_ligne = ligne;

					return genAtom(chaine,ScanBisAction(variables, colonne+1, ligne), Symbole);
		
				}
				else{
					return ScanBis(variables, colonne+1, ligne, chaine);
				}
			}

		}
		else{
			return ScanBis(variables, 0, ligne+1, chaine);
		}

	}
	else{
		return NULL;
	}
}

int Grammaire::ScanBisAction(VariablesGlobales * variables,int colonne, int ligne, std::string chaine){
	if(variables->grammaire.size()>ligne){
		if(variables->grammaire[ligne].size()>colonne){
			chaine = chaine + variables->grammaire[variables->scan_ligne].substr(colonne,1);
			if(estEspace(chaine)){
				chaine = "";
				return ScanBisAction(variables, colonne+1,ligne,chaine);
			}
			else{
				if(regex_match(chaine, regex(variables->regexAction))){
					variables->scan_col = colonne+1;
					variables->scan_ligne = ligne;

					int tmp = ScanBisAction(variables,colonne+1,ligne, chaine);

					if(tmp == -1){
						if(chaine.size()>=2)
							return std::stoi (chaine.substr(1,chaine.size()-1));
						else
							return 0;
					}
					else
						return tmp;	

				}
				else{
					return 0;
				}
			}
		}
		else{
			return ScanBisAction(variables, 0, ligne+1, chaine);
		}

	}
	else{
		return -1;
	}
}


bool Grammaire::estVariable(std::string chaine){
	if (regex_match(chaine, regex("[a-zA-Z][a-zA-Z0-9]*"))){
        return true;
    }
    return false;
}


bool Grammaire::estApostrophe(std::string chaine){
 	if (regex_match(chaine, regex("[']"))){
        return true;
    }
    return false;
}

bool Grammaire::estVide(std::string chaine){
        return chaine.size()==0;
}

bool Grammaire::estDiese(std::string chaine){
 	if (regex_match(chaine, regex("[#]"))){
        return true;
    }
    return false;
}

bool Grammaire::estFleche(std::string chaine){
 	if (regex_match(chaine, regex(":="))){
        return true;
    }
    return false;
}

bool Grammaire::finLigne(std::string chaine){
 	if (regex_match(chaine, regex("[,;]"))){
        return true;
    }
    return false;
}

int Grammaire::donneActionChaine(VariablesGlobales* variables){
 	std::string result = "";
	int i = variables->scan_col;
	variables->scan_col++;
	std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
	std::string stringTest=st;
	while(variables->grammaire[variables->scan_ligne].size()>variables->scan_col&&!estEspace(stringTest)){
		std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
		stringTest=st;
		result = result + stringTest;
		variables->scan_col++;
	}
	if(variables->grammaire[variables->scan_ligne].size()==variables->scan_col){
		variables->scan_ligne++;
		variables->scan_col = 0;
	}
	int action = -1;
	if (result != "")
	{
		action = std::atoi(result.c_str());
	}
    return action;
}

bool Grammaire::estEspace(std::string chaine){
 	if (regex_match(chaine, regex(" "))){
        return true;
    }
    return false;
}

std::string Grammaire::getString(VariablesGlobales* variables){
	std::string result = "";
	std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
	std::string stringTest=st;
	while(!estApostrophe(stringTest)&&!estEspace(stringTest)){
		result = result + stringTest;
		variables->scan_col++;
		std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
		stringTest=st;
	}
	return result;
}

std::string Grammaire::getStringSansApostrophe(VariablesGlobales* variables){
	std::string result = "";
	std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
	std::string stringTest=st;
	while(!estDiese(stringTest)&&!estEspace(stringTest)){
		result = result + stringTest;
		variables->scan_col++;
		std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
		stringTest=st;
	}
	return result;
}

std::string Grammaire::rechercheDicoNT(std::string code, VariablesGlobales* variables){
	bool trouver = false;
	std::string ligneDico = code;
	for (map<std::string,vector<string>>::iterator  i=variables->dictionnaireG0.begin(); i!=variables->dictionnaireG0.end(); ++i)
	{
		for(string valeur : i->second){
			if(code==valeur){
				ligneDico= i->first;
			}
		}
	}
	variables->dicont[code]=ligneDico;

	
	return code;
}


