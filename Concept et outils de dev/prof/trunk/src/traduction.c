// $Id$
#include <stdio.h>
#include "traduction.h"

static const char *CVSid="@(#) $Id$";

const char* traduire_bonjour(langue lng)
{
  switch (lng) {
  case anglais:
    return "Hello";
  case francais:
    return "Bonjour";
  case neerlandais:
    return "Hallo";
  case allemand:
    return "Guten Tag";
<<<<<<< .mine
  case toto:
  	return "floders";
  case espagnol:
	return "hola";
  case suedois:
	return "halla";
=======
  case zoulou:
  	return "Sawubona";
  case thai:
  	return "Sawatdi";
  case toto:
  	return "floders";
  case suedois:
    return "Hallå";
  case russe:
	  return "привет";
  case maori:
    return "Tena koe";
	case espagnol:
		return "hola";
	case suedois:
		return "hallå";
>>>>>>> .r24
  case russe:
<<<<<<< .mine
	return "привет";
=======
	  return "привет";
  case yoruba:
	return "e ku aaro";
case italien:
	return "ciao";

	  return " E ku aaro";
  case thai :
	  return "Sawatdi";
>>>>>>> .r24
  default:
    return "?????";
  }
}

langue string_vers_langue(const char *const str)
{
  if (!strcmp(str,"anglais")) {
    return anglais;
  }
  if (!strcmp(str,"francais")) {
    return francais;
  }
  if (!strcmp(str,"neerlandais")) {
    return neerlandais;
  }
  if (!strcmp(str,"allemand")) {
    return allemand;
  }
<<<<<<< .mine

  if (!strcmp(str,"toto")) {
    return toto;

=======
  if (!strcmp(str,"zoulou")) {
    return zoulou;
  }
  if (!strcmp(str,"thai")) {
    return thai;
  }
    if (!strcmp(str,"toto")) {
    return toto;
  if (!strcmp(str,"russe")) {
    return russe;
  if (!strcmp(str,"espagnol")) {
    return espagnol;
  }
>>>>>>> .r24
  if (!strcmp(str,"maori")) {
    return maori;
  }
  if (!strcmp(str,"suedois")) {
    return suedois;
  }
  if (!strcmp(str,"russe")) {
    return russe;
  }
  if (!strcmp(str,"espagnol")) {
    return espagnol;
  }
<<<<<<< .mine

=======
  if (!strcmp(str,"italien")) {
    return italien;
  }
  if (!strcmp(str,"thai")) {
	  return thai;
  }
>>>>>>> .r24
  return inconnu;
}
