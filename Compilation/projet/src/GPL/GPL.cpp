/**
* @file GPL.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe GPL
*
**/

#include "GPL.hpp"
//-----------------------------------------------------------------------
//GPLAnalyse
//-----------------------------------------------------------------------
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
			col = variables->scan_col_GPL;
			ligne = variables->scan_ligne_GPL;
			Noeud* resultScan =  gram.genAtom("-1",-1,Terminal); 
			switch(noeud->donneType()){
				case 0:
					nouveauCode = rechercheInDicoNT(noeud->donneCode(), variables);
					if(GPLAnalyse(variables->foretsGrammaire[nouveauCode],variables)){
						if(noeud->donneAction()!=0){
							GPLAction(noeud->donneAction(),resultScan->donneAction(), noeud->donneCode(), resultScan->donneType(), variables);
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
					if( noeud->donneCode()!="IDNTER" && noeud->donneType() && resultScan->donneType()){
						suite = true;
					}
					if( noeud->donneCode()=="ENTIER" && resultScan->donneType() && estEntier(resultScan->donneCode())){
						suite = true;
					}
					if( noeud->donneCode()=="TODO" ){
						suite = true;
					}
					if( suite ||noeud->donneCode()==resultScan->donneCode()){
						analyse = true;
						if(noeud->donneAction()!=0){
							GPLAction(noeud->donneAction(),resultScan->donneAction(), resultScan->donneCode(), resultScan->donneType(), variables);
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
//GPLAction
//-----------------------------------------------------------------------
void GPL::GPLAction(int actionGPL, int action, std::string code, ATOMETYPES type, VariablesGlobales* variables){
	// cout<<"actionGPL : "<<actionGPL<<" - code : "<<code<<endl;
	int posVariable = -1;
	string operateur = "";
	string adressep_code = "";
	int adressejumpif = -1;
	switch(actionGPL){

		case 1:
			this->variables.push_back(code);
			break;
		case 3:
			if(code != "Writeln" && code != "FIN" && code != "COND" && code != "WHILE" && code != "ENDWHILE" && code != "Read" && code != "WRITE" && code != "NON" && code != "ET" && code != "OU" && code != "IF" && code != "ELSE" && code != "ENDIF"){
				this->p_code.push_back("LDA");
				posVariable = rechercheInVariable(code, this->variables);
				this->p_code.push_back(to_string(posVariable));
			}else{
				if(code == "WHILE" || code == "IF"){
					this->jump.push_back(this->p_code.size());
				}
			}
			break;
		case 50:
			this->p_code.push_back("JMP");
			posVariable = this->jump.back();
			this->jump.pop_back();
			this->p_code.push_back(to_string(posVariable));
			this->p_code[this->jumpif.back()]=to_string(this->p_code.size());
			this->jumpif.pop_back();
			break;
		case 4:
			if(code != "NON" && code != "ET" && code != "OU"){
				this->p_code.push_back("LDV");
				posVariable = rechercheInVariable(code, this->variables);
				this->p_code.push_back(to_string(posVariable));
			}
			break;
		case 5:
			if(code != "ENTIER"){
				this->p_code.push_back("LDC");
				this->p_code.push_back(code);
			}
			break;
		case 14:
			this->p_code.push_back("RD");
			this->p_code.push_back("AFF");
			break;
		case 27:
			this->p_code.push_back("AFF");
			break;
		case 28:
			this->p_code.push_back("STOP");
			break;
		case 61:
			this->operateurs.push_back("ADD");
			break;
		case 62:
			this->operateurs.push_back("INC");
			break;
		case 63:
			this->operateurs.push_back("MOINS");
			break;
		case 64:
			this->operateurs.push_back("DEC");
			break;
		case 65:
			this->operateurs.push_back("MULT");
			break;
		case 66:
			this->operateurs.push_back("DIV");
			break;
		case 67:
			this->operateurs.push_back("MOD");
			break;
		case 68:
			this->operateurs.push_back("NEG");
			break;
		case 15:
			this->p_code.push_back("WRTL");
			break;
		case 16:
			this->p_code.push_back("WRTLN");
			break;
		case 41:
			this->operateurs.push_back("EG");
			break;
		case 42:
			this->operateurs.push_back("SUP");
			break;
		case 43:
			this->operateurs.push_back("INF");
			break;
		case 44:
			this->operateurs.push_back("SUPE");
			break;
		case 45:
			this->operateurs.push_back("INFE");
			break;
		case 46:
			this->operateurs.push_back("DIFF");
			break;
		case 30:
			this->operateurs.push_back("OR");
			break;
		case 31:
			do{
				operateur = this->operateurs.back();
				this->operateurs.pop_back();
				this->p_code.push_back(operateur);
				if(this->operateurs.size()>0){
					operateur = this->operateurs.back();
				}
			}while((operateur=="NOT"||operateur=="AND"||operateur=="OR")&&(this->operateurs.size()>0));		
			break;
		case 32:
			this->operateurs.push_back("AND");
			break;
		case 33:
			this->operateurs.push_back("NOT");
			break;
		case 1000:
			operateur = this->operateurs.back();
			this->operateurs.pop_back();
			this->p_code.push_back(operateur);
			break;
		case 2000:
			this->p_code.push_back("JIF");
			this->jumpif.push_back(this->p_code.size());
			this->p_code.push_back("?????");
			break;
		case 2001:
			adressep_code = to_string(this->p_code.size()+2);
			adressejumpif = this->jumpif.back();
			this->jumpif.pop_back();
			this->p_code.push_back("JMP");
			this->jumpif.push_back(this->p_code.size());
			this->p_code.push_back("?????");
			this->p_code[adressejumpif]=adressep_code;
			break;
		case 2003:
			adressep_code = to_string(this->p_code.size());
			adressejumpif = this->jumpif.back();
			this->jumpif.pop_back();
			this->p_code[adressejumpif]=adressep_code;
			break;
		case 3000:
			operateur = this->operateurs.back();
			this->operateurs.pop_back();
			this->p_code.push_back(operateur);
			break;
	}
}

//-----------------------------------------------------------------------
//scan
//-----------------------------------------------------------------------
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
				if(variables->scan_col_GPL==variables->code[variables->scan_ligne_GPL].size()-1){
					variables->scan_col_GPL=0;
					variables->scan_ligne_GPL++;
				}else{
					variables->scan_col_GPL++;
				}
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
	if(estEntier(result)){
		atomResult = gram.genAtom(result,5, Terminal);
	}
	return atomResult;
}

//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine pourrait une variable
//-----------------------------------------------------------------------
bool GPL::estVariable(std::string chaine){
	if (regex_match(chaine, regex("[a-zA-Z][a-zA-Z0-9]*[-]*[a-zA-Z0-9]*"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine est vide
//-----------------------------------------------------------------------
bool GPL::estVide(std::string chaine){
        return chaine.size()==0;
}

//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine est une fleche (dans notre cas :=)
//-----------------------------------------------------------------------
bool GPL::estFleche(std::string chaine){
 	if (regex_match(chaine, regex(":="))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine est une fin de ligne ou non , ; .
//-----------------------------------------------------------------------
bool GPL::finLigne(std::string chaine){
 	if (regex_match(chaine, regex("[,;.]"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine est un espace
//-----------------------------------------------------------------------
bool GPL::estEspace(std::string chaine){
 	if (regex_match(chaine, regex(" "))||regex_match(chaine, regex("	"))){
        return true;
    }
    return false;
}


//-----------------------------------------------------------------------
//fonction permetant de savoir si la chaine est un entier
//-----------------------------------------------------------------------
bool GPL::estEntier(std::string chaine){
 	if (regex_match(chaine, regex("[0-9][0-9]*"))){
        return true;
    }
    return false;
}

//-----------------------------------------------------------------------
//fonction permetant de retourner la string sans les apostrophe
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//fonction permetant de retourner la position de la variable (code) dans le variables->dicont
//-----------------------------------------------------------------------
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

//-----------------------------------------------------------------------
//fonction permetant de retourner la position de la variable (code) dans le vector
//-----------------------------------------------------------------------
int GPL::rechercheInVariable(std::string code, std::vector<string> v){
	int ligneDico = -1;
	int i = 0;
	while((i<v.size())&&(ligneDico==-1)){
		if(v[i]==code){
			ligneDico=i;
		}
		++i; 
	}
	return ligneDico;
}

//-----------------------------------------------------------------------
//fonction permetant de retourner le p_code
//-----------------------------------------------------------------------
std::vector<string> GPL::getP_code(){
	return this->p_code;
}

//-----------------------------------------------------------------------
//fonction permetant de retourner variables
//-----------------------------------------------------------------------
std::vector<string> GPL::getVariables(){
	return this->variables;
}