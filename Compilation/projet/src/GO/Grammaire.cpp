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
						genAtom("F", 0, Terminal)
					),
					genAtom("E", 0, NonTerminal)
				),
				genAtom(",", 1, Terminal)
				)
			),
		genAtom(";", 0, Terminal)
		);


	//Règle 2 de la grammaire des grammaires
	g[1] = genAtom("I",2,Terminal);


	//Règle 3 de la grammaire des grammaires
	g[2] = genConc(
			genAtom("I",0, NonTerminal),
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
							GOAction(noeud->donneAction(), noeud->donneCode(), noeud->donneType(), variables);
							//SCAN
						}
					}else{
							analyse=false;
					}
					break;
				case 1:
					if(true){
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction(), noeud->donneCode(), noeud->donneType(), variables);
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

}

void Grammaire::GOAction(int act, std::string code, ATOMETYPES type, VariablesGlobales variables){
	Noeud *t1;
	Noeud *t2;
	switch(act){
		case 1:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			//A[T2^cod+5]=T1
			
			break;
		case 2:
			variables.pileGOAction->pile.push(
				genAtom(rechercheDico(code, variables, false), act, type)
				);
			break;
		case 3:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();


			break;
		case 4:
			t1 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();
			t2 = variables.pileGOAction->pile.top();
			variables.pileGOAction->pile.pop();

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
	
	}


}


Noeud* Grammaire::Scan(VariablesGlobales * variables){
	std::string result = "";
	// Atom mon_atom;
	if(variables->grammaire.size()>variables->scan_ligne){
		if(variables->grammaire[variables->scan_ligne].size()>variables->scan_col){
			int i = variables->scan_col;
			bool ok = true;
			while(variables->grammaire[variables->scan_ligne].size()>variables->scan_col&&ok){
				std::string st(variables->grammaire[variables->scan_ligne],variables->scan_col,1);
				if(st!=" "&&st!=""){
					result = result + st;
				}else{
					ok= false;
				}
				variables->scan_col++;
			}
			if(variables->grammaire[variables->scan_ligne].size()==variables->scan_col){
				variables->scan_ligne++;
				variables->scan_col = 0;
			}
		}
	}
	if(result==";"){
		variables->scan_ligne++;
		variables->scan_col=0;
		return genAtom(result,0, Terminal);
	}else if(result.size()>=3){
		return genAtom(result.substr(1,result.size()-2),0, Terminal);
	}else{
		return genAtom(result,0, Terminal);
		// result = result + " -> Nonterminal";
	}
}

std::string Grammaire::rechercheDico(std::string code, VariablesGlobales variables, bool terminal){
	return "";
}


