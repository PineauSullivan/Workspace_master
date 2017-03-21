#include <stack>
#include <vector>

typedef Noeud *Foret;

typedef struct _PileGOAction{
	std::stack<Noeud *> pile;
} PileGOAction;

typedef struct _VariablesGlobales{
	Foret * foret;	
	PileGOAction * pileGOAction;
	int scan_col, scan_ligne;
	// std::map<int, std::string> dicont;
	// std::map<int, std::string> dicot;
	std::vector<std::string> grammaire;

	//ajouter tableaux pour DICONT ET DICOT
} VariablesGlobales;