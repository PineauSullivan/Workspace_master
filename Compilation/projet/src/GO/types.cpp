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

	string regexTerminal = "'*'";
	string regexNonTerminal = "[a-zA-Z][a-zA-Z0-9]*" ;
	string regexSymbole = "[,;(->)(:=)+.\\]\\[\\(\\)]" ;
	string regexAction = "#[0-9]*" ;

	Foret* foret;
	PileGOAction * pileGOAction;
	int scan_col, scan_ligne;
	int scan_col_GPL, scan_ligne_GPL;

	vector<string> grammaire;

	vector<string> code;

	vector<Noeud*> foretsGrammaire;

	map<string,int> dicont;
	map<string,int> dicot;

} VariablesGlobales;