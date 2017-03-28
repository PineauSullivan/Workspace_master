#include <stack>
#include <vector>
#include <map>
#include <vector>

typedef map<string, Noeud*> Foret;

using namespace std;

typedef struct _PileGOAction{
	std::stack<Noeud *> pile;
} PileGOAction;

typedef struct _VariablesGlobales{
	map<string, vector<string>> dictionnaireG0;

	Foret* foret;
	PileGOAction * pileGOAction;
	int scan_col, scan_ligne;
	vector<string> grammaire;

	vector<Noeud*> foretsGrammaire;

	map<string,std::string> dicont;
	map<string,int> dicot;

} VariablesGlobales;