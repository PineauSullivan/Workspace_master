#include <stack>

typedef Noeud *Foret;

typedef struct _PileGOAction{
	std::stack<Noeud *> pile;
} PileGOAction;

typedef struct _VariablesGlobales{
	Foret * foret;	
	PileGOAction * pileGOAction;
	//ajouter tableaux pour DICONT ET DICOT
} VariablesGlobales;