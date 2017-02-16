/**
* @file Un.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Un
*
**/

#include "Un.hpp"


Un::Un(Noeud *s){
	setOperations(UN);
	this->un = s;
}


std::string Un::toString(int depth){
	string branch = "---";

	for(int i=0; i<depth; i++) {
		branch += "---";
	}

    return "---> Un \n" + branch + this->un->toString(depth +1) + "\n";
}


Noeud* Un::gauche(){
	this->un;
}