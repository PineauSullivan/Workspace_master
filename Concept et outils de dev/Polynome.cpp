template<typename K>
Polynome<K>::Polynome(int degre, vector<int> valeurs){
	coeffs = degre;
	vector<int>::Iterator i;
	for(i=valeurs.begin(); i != valeurs.end(); ++i)
		lesValeurs.push_back(*i);
}
	
template <typename K>
int Polynome<K>::calculer(int x){
	K::Iterator i;
	int resultat = 0;
	int indiceCourant = 0;
	for(i=lesValeurs.begin(); i != lesValeurs.end(); ++i){
		resultat += *i * pow(x,indiceCourant);
		indiceCourant++;
		
	}
	return resultat;
}
