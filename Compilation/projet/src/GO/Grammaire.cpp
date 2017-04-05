/**
* @file Grammaire.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Grammaire
*
**/

#include "Grammaire.hpp"

//-----------------------------------------------------------------------
//Fonction permetant de générer une Conc
//-----------------------------------------------------------------------
Noeud * Grammaire::genConc(Noeud * p1, Noeud * p2){
	return new Conc(p1, p2);
}

//-----------------------------------------------------------------------
//Fonction permetant de générer une Union
//-----------------------------------------------------------------------
Noeud * Grammaire::genUnion(Noeud * p1, Noeud * p2){
	return new Union(p1, p2);
}

//-----------------------------------------------------------------------
//Fonction permetant de générer une Star
//-----------------------------------------------------------------------
Noeud * Grammaire::genStar(Noeud * p){
	return new Star(p);
}

//-----------------------------------------------------------------------
//Fonction permetant de générer un Atom
//-----------------------------------------------------------------------
Noeud * Grammaire::genAtom(std::string code, int action, ATOMETYPES type){
	return new Atom(code,action, type);
}

//-----------------------------------------------------------------------
//Fonction permetant de générer un Un
//-----------------------------------------------------------------------
Noeud * Grammaire::genUN(Noeud * p){
	return new Un(p);
}

//-----------------------------------------------------------------------
//Fonction permetant de remplir de dictionnaireGO
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//Fonction permetant de genérer la foret
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//utile pour afficher la foret de GO
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//utile pour afficher la foret de GPL
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//GOAnalyse
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//GoAction
//-----------------------------------------------------------------------
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
				genAtom(code, actionGPL, NonTerminal)
				);
			}
			else{
				variables->pileGOAction->pile.push(
				genAtom(code , actionGPL, Terminal)
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

//-----------------------------------------------------------------------
//scan
//-----------------------------------------------------------------------
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
				if(variables->scan_col==variables->grammaire[variables->scan_ligne].size()-1){
					variables->scan_col=0;
					variables->scan_ligne++;
				}else{
					variables->scan_col++;
				}
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

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est sous la forme d'une variable (par exemple : Aaaa1)
//-----------------------------------------------------------------------
bool Grammaire::estVariable(std::string chaine){
	if (regex_match(chaine, regex("[a-zA-Z][a-zA-Z0-9]*[-]*[a-zA-Z0-9]*"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est un apostrophe
//-----------------------------------------------------------------------
bool Grammaire::estApostrophe(std::string chaine){
 	if (regex_match(chaine, regex("[']"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est vide
//-----------------------------------------------------------------------
bool Grammaire::estVide(std::string chaine){
        return chaine.size()==0;
}

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est un dièse
//-----------------------------------------------------------------------
bool Grammaire::estDiese(std::string chaine){
 	if (regex_match(chaine, regex("[#]"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est une fleche (dans notre cas un :=)
//-----------------------------------------------------------------------
bool Grammaire::estFleche(std::string chaine){
 	if (regex_match(chaine, regex(":="))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est un ; ou ,
//-----------------------------------------------------------------------
bool Grammaire::finLigne(std::string chaine){
 	if (regex_match(chaine, regex("[,;]"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//donne l'action qui se trouve après un dièse (ne l'appeler que si un dièse se trouvait avant !)
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//Fonction permetant de savoir si la chaine est un espace
//-----------------------------------------------------------------------
bool Grammaire::estEspace(std::string chaine){
 	if (regex_match(chaine, regex(" "))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//Fonction permetant de retourner la suite de string jusqu'à un espace ou apostrophe
//-----------------------------------------------------------------------
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


//-----------------------------------------------------------------------
//Fonction permetant de retourner la suite de string sans les apostrophe
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//Fonction permetant de remplir correctement le DicoNT
//-----------------------------------------------------------------------
std::string Grammaire::rechercheDicoNT(std::string code, VariablesGlobales* variables){
	bool trouver = false;
	variables->dicont[code]=variables->dicont.size()-1;
	return code;
}
