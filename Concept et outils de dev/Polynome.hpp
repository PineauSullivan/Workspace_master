#include <vector>
#include <list>

template <typename K>
class Polynome{
	private:
		K lesValeurs;
		int coeffs;
		
		
	public :
	Polynome(int, vector<int>);
	int calculer(int);

};

#if TAB == 0
#define Poly Polynome< list< int > >
#else 
#define Poly Polynome< vector< int > >
#endif
