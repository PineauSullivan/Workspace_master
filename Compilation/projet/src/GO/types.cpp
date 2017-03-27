#include <stack>
#include <vector>
#include <map>
#include <vector>

typedef Noeud *Foret;

typedef struct _PileGOAction{
	std::stack<Noeud *> pile;
} PileGOAction;

typedef struct _VariablesGlobales{
	Foret* foret;
	PileGOAction * pileGOAction;
	int scan_col, scan_ligne;
	std::vector<std::string> grammaire;

	std::vector<Noeud*> foretsGrammaire;  //Crée un tableau de 5 entiers valant tous 3

	std::map<std::string,int> dicont;
	std::map<std::string,int> dicot;

} VariablesGlobales;