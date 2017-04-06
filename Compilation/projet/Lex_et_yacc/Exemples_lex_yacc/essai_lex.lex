character [a-zA-Z]
digit [0-9]
word ({character}|{digit})+
variable {character}{word}*
double {digit}+\.{digit}
entier [0-9]+
line \n
%%
^program" "{word};$ 											printf("declaration programme\n");
^{variable}(,{variable})*:int$									printf("declaration entier\n");
^{variable}(,{variable})*:double$								printf("declaration de nombre a virgule\n");
{variable}=														printf("(affectation)");
{double}\+{double};												printf("%s (addition, resultat : double)\n",yytext);
{double}\+{entier};												printf("%s (addition, resultat : double)\n",yytext);	
{entier}\+{double};												printf("%s (addition, resultat : double)\n",yytext);
{entier}\+{entier};												printf("%s (addition, resultat : entier)\n",yytext);
FIN																;
DEBUT															;
%%
main() {
	yylex();
}
