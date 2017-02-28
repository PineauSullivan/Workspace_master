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



bool Grammaire::GOAnalyse(Noeud *noeud) {
	bool analyse = false;
	switch(noeud->donneClassname()){
		case CONC:
			if(GOAnalyse(noeud->gauche())){
				analyse = GOAnalyse(noeud->droite());
			}else{
				analyse = false;
			}
			break;
		
		case UNION:
			if(GOAnalyse(noeud->gauche())){
				analyse = true;
			}else{
				analyse =  GOAnalyse(noeud->droite());
			}
			break;
		
		case STAR:
			analyse = true;
			while(GOAnalyse(noeud->gauche())){}
			break;
		
		case UN:
			analyse = true;
			if(GOAnalyse(noeud->gauche())){};
			break;
		
		case ATOM:
			//REVOIR TOUS ATOM ET FONCTIONS GPAction, Scan
			switch(noeud->donneType()){
				case 0:
					if(noeud->donneCode()==Scan(noeud->donneCode())){//A REVOIR !!!
						analyse = true;
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction());
							//SCAN
						}
					}else{
							analyse=false;
					}
					break;
				case 1:
					if(true){
						if(noeud->donneAction()!=0){
							GOAction(noeud->donneAction());
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

void Grammaire::GOAction(int act){
	Noeud *T1;
	Noeud *T2;
	switch(act){
		case 1:
			
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
	
	}


}


std::string Grammaire::Scan(std::string code){
	return code;
}