/**
* @file GPL.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe GPL
*
**/

#include "GPL.hpp"

bool GPL::GPLAnalyse(Noeud *noeud,  VariablesGlobales *variables){
	Grammaire gram;
	bool analyse = false;
	int col = 0;
	int ligne = 0;
	int nouveauCode = -1;
	switch(noeud->donneClassname()){
		case CONC:
			if(GPLAnalyse(noeud->gauche(), variables)){
				analyse = GPLAnalyse(noeud->droite(), variables);
			}else{
				analyse = false;
			}
			break;
		
		case UNION:
			col = variables->scan_col_GPL;
			ligne = variables->scan_ligne_GPL;
			if(GPLAnalyse(noeud->gauche(), variables)){
				analyse = true;
			}else{
				variables->scan_col_GPL = col;
				variables->scan_ligne_GPL = ligne;
				analyse =  GPLAnalyse(noeud->droite(), variables);
			}
			break;
		
		case STAR:
			analyse = true;
			col = variables->scan_col_GPL;
			ligne = variables->scan_ligne_GPL;
			while(GPLAnalyse(noeud->gauche(), variables)){
				col = variables->scan_col_GPL;
				ligne = variables->scan_ligne_GPL;
			};
			variables->scan_col_GPL = col;
			variables->scan_ligne_GPL = ligne;
			break;
		
		case UN:
			analyse = true;
			col = variables->scan_col_GPL;
			ligne = variables->scan_ligne_GPL;
			if(GPLAnalyse(noeud->gauche(), variables)){			
			}else{
				variables->scan_col_GPL = col;
				variables->scan_ligne_GPL = ligne;
			}

			break;
		
		case ATOM:

			Noeud* resultScan =  gram.genAtom("-1",-1,Terminal); 
			switch(noeud->donneType()){
				case 0:
					nouveauCode = rechercheInDicoNT(noeud->donneCode(), variables);

					cout<<"code -> "<<resultScan->donneCode()<<endl;

					if(GPLAnalyse(variables->foretsGrammaire[nouveauCode],variables)){
						GPLAction(noeud->donneAction(),resultScan->donneAction(), noeud->donneCode(), resultScan->donneType(), variables);
						analyse = true;
					}
					break;

				case 1:
					resultScan =  Scan(variables);
					// cout<<"code -> "<<resultScan->donneCode()<<endl;
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
							GPLAction(noeud->donneAction(),resultScan->donneAction(), resultScan->donneCode(), resultScan->donneType(), variables);
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


void GPL::GPLAction(int actionGPL, int action, std::string code, ATOMETYPES type, VariablesGlobales* variables){
	cout<<"actionGPL : "<<actionGPL<<" - code : "<<code<<endl;
}

Noeud* GPL::Scan(VariablesGlobales * variables){
	Grammaire gram;
	std::string result = "";
	Noeud* atomResult;
	bool terminal=false;
	int action = 0;
	bool fin = true;
	if(variables->code.size()>variables->scan_ligne_GPL){
		if(variables->code[variables->scan_ligne_GPL].size()>variables->scan_col_GPL){
			int i = variables->scan_col_GPL;
			std::string st(variables->code[variables->scan_ligne_GPL],variables->scan_col_GPL,1);

			if(finLigne(st)){
				terminal = false;
				result = st;
				variables->scan_col_GPL++;
			}else if(estEspace(st)){
				variables->scan_col_GPL++;
				return Scan(variables);
			}else{
				result = getStringSansApostrophe(variables);
				terminal = false;
			}
			if((result=="]"||result=="["||result==":=")){
				terminal = false;
			}

			if(terminal){
				atomResult = gram.genAtom(result, action, Terminal);
			}else{
				atomResult = gram.genAtom(result,action, NonTerminal);
			}
		}
	}

	if(estEspace(result)||estVide(result)){
		variables->scan_col_GPL++;
		if(variables->scan_ligne_GPL<variables->code.size()&&variables->scan_col_GPL<variables->code[variables->scan_ligne_GPL].size()){
			return Scan(variables);
		}
	}

	if(variables->scan_col_GPL==(variables->code[variables->scan_ligne_GPL].size())){
		variables->scan_ligne_GPL++;
		variables->scan_col_GPL=0;
	}
	if(variables->scan_ligne_GPL==(variables->code.size())){
		atomResult = gram.genAtom(".",0, NonTerminal);
	}
	return atomResult;
}


bool GPL::estVariable(std::string chaine){
	if (regex_match(chaine, regex("[a-zA-Z][a-zA-Z0-9]*[-]*[a-zA-Z0-9]*"))){
        return true;
    }
    return false;
}

bool GPL::estVide(std::string chaine){
        return chaine.size()==0;
}

bool GPL::estFleche(std::string chaine){
 	if (regex_match(chaine, regex(":="))){
        return true;
    }
    return false;
}

bool GPL::finLigne(std::string chaine){
 	if (regex_match(chaine, regex("[,;.]"))){
        return true;
    }
    return false;
}

bool GPL::estEspace(std::string chaine){
 	if (regex_match(chaine, regex(" "))||regex_match(chaine, regex("	"))){
        return true;
    }
    return false;
}

std::string GPL::getStringSansApostrophe(VariablesGlobales* variables){
	std::string result = "";
	std::string st(variables->code[variables->scan_ligne_GPL],variables->scan_col_GPL,1);
	std::string stringTest=st;
	bool fin = false;
	while(!estEspace(stringTest)&&!finLigne(stringTest)&&!fin){
		result = result + stringTest;
		variables->scan_col_GPL++;
		if(variables->scan_col_GPL==variables->code[variables->scan_ligne_GPL].size()){
			variables->scan_ligne_GPL++;
			variables->scan_col_GPL=0;
			fin=true;
		}else{
			std::string st(variables->code[variables->scan_ligne_GPL],variables->scan_col_GPL,1);
			stringTest=st;
		}
	}
	return result;
}

int GPL::rechercheInDicoNT(std::string code, VariablesGlobales* variables){
	int ligneDico = -1;
	for (map<string,int>::iterator  i=variables->dicont.begin(); i!=variables->dicont.end(); ++i)
	{
		if(i->first==code){
			ligneDico=i->second;
		} 
	}
	return ligneDico;
}