/**
* @file Star.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Star
*
**/

#include "Star.hpp"


Star::Star(Noeud *s){
	setOperations(STAR);
	this->star = s;
}


std::string Star::toString(int depth){
	string branch = "---";

	for(int i=0; i<depth; i++) {
		branch += "---";
	}

    return "---> Star \n" + branch + this->star->toString(depth + 1) + "\n";
}

Noeud* Star::gauche(){
	if (this->star==NULL)
	{
		std::cout<<"null!!! "<<std::endl;
	}
	return this->star;
}