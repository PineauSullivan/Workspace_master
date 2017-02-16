/**
* @file Noeud.cpp
* @author P. Sullivan, V. Sebastien
* @since 19/01/2017
* @brief implémentation des méthodes de la classe Noeud
*
**/

#include "Noeud.hpp"

Noeud::Noeud(){
}

void Noeud::setOperations(OPERATIONS name){
	this->classname =  name;
}

OPERATIONS Noeud::donneClassname(){
	return this->classname;
}
