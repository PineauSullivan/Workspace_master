using namespace std;
#include "Polynome.hpp"
#include <iostream>
#


int main() { 
	vector<int> nos_coeffs;
	int degre;
	Poly notre_polynome;
	cout<<"entrez le degre du polynome :";
	cin>>degre;
	cout<<endl;

	int coeffCourant;
	for(int i =0; i<= degre;++i){
		cout<<"entrez le coefficient à la puissance "<<i<<" : ";
		cin>>coeffCourant;
		nos_coeffs.push_back(coeffCourant);
		cout<<endl;
		
	}

	notre_polynome = new Poly(degre,nos_coeffs);
	int resultat, x;
	bool continuer = true;

	while(continuer){
		cout<<"choisissez la valeur de X : ";
		cin>>x;
		cout<<endl<<"le resultat dun calcul est = "<<notre_polynome.calculer(x)<<endl;
		
		cout<<"continuer ?";

	}
	
	return 0; 
}



