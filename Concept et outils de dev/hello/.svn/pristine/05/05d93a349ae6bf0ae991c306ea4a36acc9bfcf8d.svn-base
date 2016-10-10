// $Id$
#include <stdio.h>
#include "hello.h"


static const char *CVSid="@(#) $Id$";

void afficher_aide(void)
{
  fprintf(stderr,"hello -- $Id$\n");
  fprintf(stderr,"Usage: \n");
  fprintf(stderr,"  hello <nom> <langue>\n");
}

int main(int argc, char *argv[])
{
  if (argc != 3) {
    afficher_aide();
    return 1;
  }

  dire_bonjour(stdout,argv[1],string_vers_langue(argv[2]));

  return 0;
}
