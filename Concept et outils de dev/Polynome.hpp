#if TAB == 0
using Poly = Polynome<std::List<int>>,
#else 
using Poly = Polynome<Vector<int>>;

template <typename K>
class Polynome{
	private:
		K lesValeurs;
		int coeffs;
		
		
	public :
	int calculer(int);

};
