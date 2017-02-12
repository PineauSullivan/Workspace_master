/**
 * @file Union.hpp
 * @author P. Sullivan, V. Sebastien
 * @since 19/01/2017
 * @brief Définition d'une classe de Union
 *
**/ 

#ifndef UNION_HPP
#define UNION_HPP

class Union : public Noeud{
	private:
  		Noeud *right;
  		Noeud *left;
	public:
		Union(Noeud *r, Noeud *l);
		virtual std::string toString(int level);


};

#endif // UNION_HPP