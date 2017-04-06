/**
 * @file Interpreteur.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe Interpreteur
 *
**/

#include "../Interpreteur/Interpreteur.hpp"

#include <iostream>

using namespace std;

Interpreteur::Interpreteur(vector<string> p_code, int nbvariables){
	this->p_code = p_code;
	curseurP_code = 0;


	this->nbVariables = nbvariables;
	remplirPileInitiale();

}

void Interpreteur::Interpreteur::executer(){
	while(curseurP_code<(int)(p_code.size())){
		string instructionCourante = p_code[curseurP_code];
		//cout<<"INSTRUCT :"<<curseurP_code<<" == "<<instructionCourante<<endl;

		if(instructionCourante.compare("LDA") == 0)
            LDA();

		else if (instructionCourante.compare("LDV") == 0)  
            LDV(); 

		else if (instructionCourante.compare("LDC") == 0)  
            LDC(); 

		else if (instructionCourante.compare("SUP") == 0)  
            SUP(); 

		else if (instructionCourante.compare("SUPE") == 0)  
            SUPE(); 

		else if (instructionCourante.compare("INF") == 0)  
            INF(); 

		else if (instructionCourante.compare("INFE") == 0)  
            INFE(); 

		else if (instructionCourante.compare("EG") == 0)  
            EG(); 

		else if (instructionCourante.compare("DIFF") == 0)  
            DIFF(); 

		else if (instructionCourante.compare("AND") == 0)  
            AND(); 

        else if (instructionCourante.compare("OR") == 0)  
            OR(); 

        else if (instructionCourante.compare("NOT") == 0)  
            NOT(); 

        else if (instructionCourante.compare("JMP") == 0)  
            JMP(); 
    	    
        else if (instructionCourante.compare("JIF") == 0)  
            JIF(); 
    	    
        else if (instructionCourante.compare("TSR") == 0)  
            TSR(); 
    	    
        else if (instructionCourante.compare("RSR") == 0)  
            RSR(); 
    	    
        else if (instructionCourante.compare("ADD") == 0)  
            ADD(); 
    	    
        else if (instructionCourante.compare("MOINS") == 0)  
            MOINS(); 
    	    
        else if (instructionCourante.compare("DIV") == 0)  
            DIV(); 
    	    
        else if (instructionCourante.compare("MULT") == 0)  
            MULT(); 
    	    
        else if (instructionCourante.compare("NEG") == 0)  
            NEG(); 
    	    
        else if (instructionCourante.compare("INC") == 0)  
            INC(); 
    	    
        else if (instructionCourante.compare("DEC") == 0)  
            DEC(); 
    	    
        else if (instructionCourante.compare("RD") == 0)  
            RD(); 
    	    
        else if (instructionCourante.compare("RDLN") == 0)  
            RDLN(); 
    	    
        else if (instructionCourante.compare("WRT") == 0)  
            WRT(); 
    	    
        else if (instructionCourante.compare("WRTLN") == 0)  
            WRTLN(); 
    	    
        else if (instructionCourante.compare("AFF") == 0)  
            AFF(); 
    	      	
        else 
        	STOP();
	        	  
    }  
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ FONCTION INTERESSANTE SI MODIFICATION FONCTIONNEMENT GPL////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
void Interpreteur::remplirPileInitiale(){
	for(int i = 0; i< nbVariables; ++i)
		pile_x.push_back(-666); 
}

int Interpreteur::convertirLectureP_code(string str){
	return std::stoi( str );
}

int Interpreteur::convertirLectureP_code(int entier){
	return entier;
}

int Interpreteur::convertirEcriturePile(string str){
	return std::stoi( str );
}

int Interpreteur::convertirEcriturePile(int entier){
	return entier;
}

int Interpreteur::boolVraie(){
	return 1;
}

int Interpreteur::boolFaux(){
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ FIN/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////
//!\ INSTRUCTIONS DE CHANGEMENT//////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
void Interpreteur::LDA(){
	pile_x.push_back(convertirLectureP_code(p_code[curseurP_code+1]));
	curseurP_code+=2;
}

void Interpreteur::LDV(){
	pile_x.push_back(pile_x[convertirLectureP_code(p_code[curseurP_code+1])]);
	curseurP_code+=2;
}

void Interpreteur::LDC(){
	pile_x.push_back(convertirLectureP_code(p_code[curseurP_code+1]));
	curseurP_code+=2;
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ OPERATEURS RELATIONNELLES///////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

void Interpreteur::SUP(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();


	if(variableCible>variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction

}

void Interpreteur::SUPE(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();


	if(variableCible>=variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::INF(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();


	if(variableCible<variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::INFE(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();


	if(variableCible<=variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::EG(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();

	if(variableCible==variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::DIFF(){
	// on consomme 2 variables et on écrit une fois

	int variableCible, variableComp;
	variableComp = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();


	if(variableCible!=variableComp)
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ OPERATEURS LOGIQUE//////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
void Interpreteur::AND(){
	// on consomme 2 variables et on écrit une fois

	int variable1, variable2;
	variable2 = pile_x.back();
	pile_x.pop_back();
	variable1 = pile_x.back();
	pile_x.pop_back();

	if(variable1 >= boolVraie() && variable2 >= boolVraie())
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::OR(){
	// on consomme 2 variables et on écrit une fois

	int variable1, variable2;
	variable2 = pile_x.back();
	pile_x.pop_back();
	variable1 = pile_x.back();
	pile_x.pop_back();

	if(variable1 >= boolVraie() || variable2 >= boolVraie())
		pile_x.push_back(boolVraie());
	else
		pile_x.push_back(boolFaux());

	curseurP_code++; // on ne consomme qu'une instruction
}

void Interpreteur::NOT(){
	// on consomme 1 variables et on écrit une fois donc pas de changement du curseur de la pile

	int variable1;
	variable1 = pile_x.back();
	pile_x.pop_back();
	if(variable1 >= boolVraie())
		pile_x.push_back(boolFaux());
	else
		pile_x.push_back(boolVraie());

	curseurP_code++; // on ne consomme qu'une instruction
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ INSTRUCTIONS DE SAUT ///////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

void Interpreteur::JMP(){
	//on ne comsomme rien de la pile
	curseurP_code = convertirLectureP_code(p_code[curseurP_code+1]);
	//pas de changement d'instruction
}

void Interpreteur::JIF(){
	//on consomme un element de la pile

	int variable;
	variable = pile_x.back();
	pile_x.pop_back();

	if(variable >= boolVraie())
		curseurP_code+=2;//on passe a l'instruction suivante
	else
		curseurP_code = convertirLectureP_code(p_code[curseurP_code+1]); // on fait un saut

}

void Interpreteur::TSR(){
	//TODO : revoir si bon

	//on ajoute un element dans la pile

	pile_x.push_back(curseurP_code); // on enregistre la position de la procedure

	curseurP_code = convertirLectureP_code(p_code[curseurP_code+1]); // on fait un saut

}

void Interpreteur::RSR(){
	//TODO : revoir si bon

	//on consomme un element de la pile

	curseurP_code = pile_x.back(); // jump sur l'instruction avant le saut de procedure
	pile_x.pop_back();

	curseurP_code++;// on passe le saut d'instruction

}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ OPERATIONS ARITHMETIQUE ///////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

void Interpreteur::ADD(){
	// on consomme deux elements de la pile et on ecrit une fois

	int variable1, variable2;
	variable2 = pile_x.back();
	pile_x.pop_back();
	variable1 = pile_x.back();
	pile_x.pop_back();

	pile_x.push_back(convertirEcriturePile(variable1 + variable2));

	curseurP_code++; // on consomme une instruction

}

void Interpreteur::MOINS(){
	// on consomme deux elements de la pile et on ecrit une fois

	int variableCible, variableMOINS;

	variableMOINS = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();
	
	pile_x.push_back(convertirEcriturePile(variableCible - variableMOINS));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::DIV(){
	// on consomme deux elements de la pile et on ecrit une fois

	int variableCible, variableDIV;
	variableDIV = pile_x.back();
	pile_x.pop_back();
	variableCible = pile_x.back();
	pile_x.pop_back();

	pile_x.push_back(convertirEcriturePile((int)(variableCible/variableDIV)));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::MULT(){
	// on consomme deux elements de la pile et on ecrit une fois

	int variable1, variable2;
	variable2 = pile_x.back();
	pile_x.pop_back();
	variable1 = pile_x.back();
	pile_x.pop_back();

	pile_x.push_back(convertirEcriturePile((int)(variable1*variable2)));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::NEG(){
	// on consomme un element de la pile et on ecrit a une adresse

	int variable1;
	variable1 = pile_x.back();
	pile_x.pop_back();

	pile_x[variable1] = convertirEcriturePile(-(pile_x[variable1]));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::INC(){
	// on consomme un element de la pile et on ecrit a une adresse
	int variable1;
	variable1 = pile_x.back();
	pile_x.pop_back();;
	// cout<<variable1<<" ++ "<<pile_x[variable1]<<endl;

	pile_x.push_back(pile_x[variable1]+1);
	// pile_x[variable1] = convertirEcriturePile(pile_x[variable1]+1);

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::DEC(){
	// on consomme un element de la pile et on ecrit a une adresse

	int variable1;
	variable1 = pile_x.back();
	pile_x.pop_back();;

	pile_x.push_back(pile_x[variable1]-1);
	// pile_x[variable1] = convertirEcriturePile(pile_x[variable1]-1);

	curseurP_code++; // on consomme une instruction
}

/////////////////////////////////////////////////////////////////////////////////////////
//!\ INSTRUCTIONS INPUT/OUTPUT///////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
void Interpreteur::RD(){
	// on ne consomme pas d'element de la pile et on ecrit une fois dans la pile

	int variable1;
	cin >> variable1;

	pile_x.push_back(convertirEcriturePile(variable1));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::RDLN(){
	// on ne consomme pas d'element de la pile et on ecrit une fois dans la pile

	int variable1;
	cin >> variable1;
	cout<<endl;

	pile_x.push_back(convertirEcriturePile(variable1));

	curseurP_code++; // on consomme une instruction
}

void Interpreteur::WRT(){
	// on consomme un element de la pile

	int variable1 = pile_x.back();
	pile_x.pop_back();
	cout<<variable1;
	curseurP_code++; // on consomme une instruction
}

void Interpreteur::WRTLN(){
	// on consomme un element de la pile(adresse de la variable)

	int variable1 = pile_x.back();
	pile_x.pop_back();
	cout<<variable1<<endl;
	curseurP_code++; // on consomme une instruction
}

void Interpreteur::AFF(){
	int valeur = pile_x.back();
	pile_x.pop_back();
	int adresse = pile_x.back();
	pile_x.pop_back();

	pile_x[adresse] = valeur;

	curseurP_code++;
	 
}
void Interpreteur::STOP(){
	bool erreur = false;
	if((int)(pile_x.size()) != nbVariables){
		cout<<"ERREUR, PILE NON VIDE!! INSTRUCTIONS MANQUANTES."<<endl;
		erreur = true;
	}
	if(curseurP_code!=(int)(p_code.size())-1){
		cout<<"ERREUR, P_CODE NON TERMINE!!"<<endl;
		erreur = true;
	}
	if(erreur)
		cout<<"le programme ne s'est pas terminé correctement."<<endl;
	else
		cout<<"le programme s'est terminé correctement."<<endl;

	curseurP_code = (int)(p_code.size());

}