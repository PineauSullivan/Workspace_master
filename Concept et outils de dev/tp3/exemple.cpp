#include <iostream>

#include "pdflib.hpp"

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
