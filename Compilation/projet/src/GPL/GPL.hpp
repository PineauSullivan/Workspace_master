/**
 * @file GPL.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe GPL
 *
**/

#ifndef GPL_HPP
#define GPL_HPP


class GPL{
	private:
		vector<string> p_code;
		vector<string> operateurs;
		vector<string> variables;
		vector<int> jump;
		vector<int> jumpif;

	public:
		bool GPLAnalyse(Noeud *noeud,  VariablesGlobales *variables);

		void GPLAction(int actionG0, int actionGPL, std::string code, ATOMETYPES type, VariablesGlobales* variables);

		Noeud* Scan(VariablesGlobales * variables);

		bool estVariable(std::string chaine);

		bool estVide(std::string chaine);

		bool estFleche(std::string chaine);

		bool finLigne(std::string chaine);

		bool estEspace(std::string chaine);

		std::string getStringSansApostrophe(VariablesGlobales* variables);

		int rechercheInDicoNT(std::string code, VariablesGlobales* variables);

		int rechercheInVariable(std::string code, std::vector<string> v);

		bool estEntier(std::string chaine);

		std::vector<string> getP_code();

		std::vector<string> getVariables();
};


#endif // GPL_HPP