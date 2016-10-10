// $Id$
#include <stdio.h>
#include "hello.h"
#include "messages.h"



static const char *CVSid="@(#) $Id$";

int main(int argc, char *argv[])
{
  if (argc != 3) {
    afficher_aide();
    return 1;
  }

  dire_bonjour(stdout,argv[1],string_vers_langue(argv[2]));

  return 0;
}
