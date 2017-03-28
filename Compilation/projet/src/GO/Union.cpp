/**
* @file Union.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Union
*
**/

#include "Union.hpp"


Union::Union(Noeud *l, Noeud *r){
	setOperations(UNION);
	this->right = r;
	this->left = l;
}


std::string Union::toString(int depth){
	string branchLeft = "---";
	string branchRight = "---"; 	

	for(int i=0; i<depth; i++) {
		branchLeft += "---";
		branchRight += "---";
	}
    return "---> Union \n" + branchLeft + this->left->toString(depth + 1) + "\n" + branchRight + this->right->toString(depth + 1) + "\n";
}

Noeud* Union::droite(){
	return this->right;
}

Noeud* Union::gauche(){
	return this->left;
}