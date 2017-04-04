/**
 * @file Interpreteur.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 04/04/2017
 * @brief Définition d'une classe Interpreteur
 *
**/

#ifndef INTERPRETEUR_HPP
#define INTERPRETEUR_HPP

#include <vector>
#include <string>


class Interpreteur{
	private:
		int curseurP_code;
		std::vector<std::string> p_code;

		std::vector<int> pile_x;

		int nbVariables;

		void remplirPileInitiale();

		int convertirLectureP_code(std::string str);
		int convertirLectureP_code(int entier);

		int convertirEcriturePile(std::string str);
		int convertirEcriturePile(int entier);

		int boolVraie();
		int boolFaux();

		void LDA();
		void LDV();
		void LDC();

		void SUP();
		void SUPE();
		void INF();
		void INFE();
		void EG();
		void DIFF();

		void AND();
		void OR();
		void NOT();

		void JMP();
		void JIF();
		void TSR();
		void RSR();

		void ADD();
		void MOINS();
		void DIV();
		void MULT();
		void NEG();
		void INC();
		void DEC();

		void RD();
		void RDLN();
		void WRT();
		void WRTLN();
		void AFF();
		void STOP();


	public:
		Interpreteur(vector<string> p_code, int nbvariables);
		void executer();
		


};


#endif // INTERPRETEUR_HPP