#include <iostream>
#include <fstream>
#include "pdflib.hpp"
#include <vector>
#include <sstream>

using namespace std;

void lectureMetaDonnee(ifstream fichier){


}



vector<string> lectureTableauString(ifstream fichier){
	string ligne;

	vector<string> mesStrings;

	string mot;

	getline(fichier, ligne);

	std::stringstream ss(ligne);

    while(ss.good()){
    	ss >> mot;
        mesStrings.push_back(mot);
    }


}

vector<int> lectureTableauInt(ifstream fichier){
	string ligne;

	vector<int> mesInts;

    double nb;

	getline(fichier, ligne);

	std::stringstream ss(ligne);


    while(ss.good()){
    	ss >> nb;
        mesInts.push_back(nb);
    }


}


int main(void)
{
	try {
		PDFlib *p;


		/*	
			Création du conteneur PDF
		 */
		p = new PDFlib();

		if (p->begin_document("exemple.pdf","") == -1) {
			std::cerr << p->get_errmsg() << std::endl;
			return 2;
		}

		// Création d'une page A4 (595pts x 842pts
		p->begin_page_ext(595,842,"");
		// Déplacement du point courant au milieu
		p->translate(595/2,842/2);

		// Définition de la couleur courante en RVBA: bleu
		p->setcolor("fillstroke","rgb",0,0,1,0);

		
		ifstream fichier("meta-donnees.txt", ios::in);  // on ouvre le fichier en lecture
		vector<string> mesStrings;
    	vector<int> mesInts;
 

        if(fichier)  // si l'ouverture a réussi

        {       
 
    		char * buffer = new char [1];
			// read data as a block:
    		fichier.read (buffer,1);


    		if(buffer[0] != '>'){
    			mesStrings = lectureTableauString(fichier);
    			mesInts = lectureTableauInt(fichier);
    		}
    		else
    			;

    		

            







            fichier.close();  // on ferme le fichier

        }

        else  // sinon

                cerr << "Impossible d'ouvrir le fichier !" << endl;



        for(int i = 0; i<mesStrings.size(); ++i)
        	cout<<mesStrings[i]<<"__";
        cout<<endl;
        for(int i = 0; i<mesInts.size(); ++i)
        	cout<<mesStrings[i]<<"__";
        cout<<endl;

































		p->setlinewidth(2);
		// Dessin d'un cercle dans la couleur et la taille de trait
		// courants
		p->circle(0,0,40);
		p->stroke();

		p->translate(100,100);
		p->moveto(0,0);
		p->lineto(40,0);
		p->arc(0,0,40,0,30);
		p->lineto(0,0);
		p->stroke();		
		
		p->end_page_ext("");
		p->end_document("");
	 } catch (PDFlib::Exception &ex) {
		std::cerr << "PDFlib exception: " << std::endl;
		std::cerr << "[" << ex.get_errnum() << "] " << ex.get_apiname()
	    	<< ": " << ex.get_errmsg() << std::endl;
		return 2;
    }

    return 0;		
}
