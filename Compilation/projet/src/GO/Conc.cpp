/**
* @file Conc.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Conc
*
**/

#include "Conc.hpp"


Conc::Conc(Noeud *l, Noeud *r){
	setOperations(CONC);
	this->right = r;
	this->left = l;
}


std::string Conc::toString(int depth){
	string branchLeft = "---";
	string branchRight = "---"; 	

	for(int i=0; i<depth; i++) {
		branchLeft += "---";
		branchRight += "---";
	}

    return "---> Conc \n" + branchLeft + this->left->toString(depth + 1) + "\n" + branchRight + this->right->toString(depth + 1);
}


Noeud* Conc::droite(){
	return this->right;
}

Noeud* Conc::gauche(){
	return this->left;
}