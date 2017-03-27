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


Foret * Grammaire::genForet(){
	Foret *g = new Foret[5];

	//Règle 1 de la grammaire des grammaires
	g[0] = genConc(
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
	g[1] = genAtom("IDNTER",2,Terminal);


	//Règle 3 de la grammaire des grammaires
	g[2] = genConc(
			genAtom("T",0, NonTerminal),
			genStar(
				genConc(
					genAtom("+",0,Terminal),
					genAtom("T",3,NonTerminal)
				)
			)
		);

	//Règle 4 de la grammaire des grammaires
	g[3] = genConc(
			genAtom("F",0, NonTerminal),
			genStar(
				genConc(
					genAtom(".",0,Terminal),
					genAtom("F",4,NonTerminal)
				)
			)
		);

	//Règle 5 de la grammaire des grammaires
	g[4] = genUnion(
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

void Grammaire::afficheForet(Foret * F){

   for (int i = 0; i < 5; ++i)
   {
      cout << "A[" << i << "] :" << endl;
      cout << F[i]->toString(0) << endl;
      cout << "------------------------------" << endl; 
   }
}



bool Grammaire::GOAnalyse(Noeud *noeud,  VariablesGlobales variables) {
	bool analyse = false;
	switch(noeud->donneClassname()){
		case CONC:
			if(GOAnalyse(noeud->gauche(), variables)){
				analyse = GOAnalyse(noeud->droite(), variables);
			}else{
				analyse = false;
			}
			break;
		
		case UNION:
			if(GOAnalyse(noeud->gauche(), variables)){
				analyse = true;
			}else{
				analyse =  GOAnalyse(noeud->droite(), variables);
			}
			break;
		
		case STAR:
			analyse = true;
			while(GOAnalyse(noeud->gauche(), variables)){}
			break;
		
		case UN:
			analyse = true;
			if(GOAnalyse(noeud->gauche(), variables)){};
			break;
		
		case ATOM:
			//REVOIR TOUS ATOM ET FONCTIONS GPAction, Scan
			switch(noeud->donneType()){
				case 0:
					if(noeud->donneCode()==Scan(&variables)->donneCode()){//A REVOIR !!!
						analyse = true;
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction(),0, noeud->donneCode(), noeud->donneType(), variables);
							//SCAN
						}
					}else{
							analyse=false;
					}
					break;
				case 1:
					if(true){
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction(),0, noeud->donneCode(), noeud->donneType(), variables);
							analyse = true;
						}else{
							analyse = false;
						}
					}
					break;
			}
		break;
	}
    std::cout << "ANALYSE "<<analyse<<std::endl;;
	return analyse;

}

void Grammaire::GOAction(int actionG0, int actionGPL, std::string code, ATOMETYPES type, VariablesGlobales variables){
	Noeud *t1;
	Noeud *t2;
	switch(actionG0){
		case 0:
		variables.pileGOAction->pile.push(
				genAtom(code, actionGPL, type)
				);
		break;
		case 1:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			variables.foretsGrammaire.push_back(t1);
			// variables.dicont[t2->donneCode()] = variables.foretsGrammaire.size()-1;
			
			break;
		case 2:
			variables.pileGOAction->pile.push(
				genAtom(rechercheDicoNT(code, variables), actionGPL, Terminal)
				);
			break;
		case 3:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			variables.pileGOAction->pile.push(genUnion(t1,t2));

			break;
		case 4:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			variables.pileGOAction->pile.push(genConc(t1,t2));

			break;
		case 5:
			if(type == 0){
				variables.pileGOAction->pile.push(
				genAtom(code, actionGPL, Terminal)
				);
			}
			else{
				variables.pileGOAction->pile.push(
				genAtom(rechercheDicoNT(code, variables), actionGPL, NonTerminal)
				);
			}

			break;
		case 6:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			variables.pileGOAction->pile.push(
				genStar(t1)
				);

			break;
		case 7:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			variables.pileGOAction->pile.push(
				genUN(t1)
				);
			break;
	
	}


}

//A revoir !!!!
Noeud* Grammaire::Scan(VariablesGlobales * variables){
	std::string result = "";
	Noeud* atomResult;
	bool terminal=false;
	int action = 0;
	bool fin = true;
	
	if(variables->grammaire.size()>variables->scan_ligne){
		if(variables->grammaire[variables->scan_ligne].size()>variables->scan_col){
			int i = variables->scan_col;
			std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
			if(finLigne(st)){
				terminal = true;
				result = st;
			}else if(estEspace(st)){
				variables->scan_col++;
				return Scan(variables);
			}else if(estApostrophe(st)){
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
			if(terminal==true){
				atomResult = genAtom(result, action, Terminal);
			}else{
				atomResult = genAtom(result,action, NonTerminal);
			}

		}
	}

	if(estEspace(result)||estVide(result)){
		variables->scan_col++;
		return Scan(variables);
	}

	if(estFleche(result)){
		atomResult = genAtom(result,action, Terminal);
	}
	if(finLigne(result)&&fin){
		variables->scan_ligne++;
		variables->scan_col=0;
	}

	return atomResult;
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

std::string Grammaire::rechercheDicoNT(std::string code, VariablesGlobales variables){
	return "";
}


