#Author: Dimitrianos Savva dimis@di.uoa.gr
#Project: GeoTriples
#Description: Step-by-step run GeoTriples (people without experience on using it)
red=`tput setaf 1`
green=`tput setaf 2`
defaultcolor=`tput sgr0`

read -r -p ${red}'--> Select mode:'${defaultcolor}' Generate Mapping,Dump RDF [g/d]:'${defaultcolor} mode
#Generate mapping stuff
if [[ $mode =~ ^([gG][eE][nN][eE][rR][aA][tT][eE]|[gG])$ ]];then 
echo ${green}'Info:'${defaultcolor}' Entering '${defaultcolor}'GenerateMapping'${defaultcolor}' mode...';echo;mode='generate'
#Dump rdf stuff
elif [[ $mode =~ ^([dD][uU][mM][pP]|[dD])$ ]];then
echo ${green}'Info:'${defaultcolor}' Entering '${green}'DumpRDF'${defaultcolor}' mode...';echo;mode='dump'
#Give the input mapping file
read -e -p ${red}'--> Give the path'${defaultcolor}' of input mapping file:'${defaultcolor} mapping
echo ${green}'Info:'${defaultcolor}' Input mapping: '${green}${mapping}${defaultcolor};
else
echo ${red}'Error:'${defaultcolor}' Bad input. Should be `g` for Generate Mapping or `d` for Dump RDF'
exit
fi

read -r -p ${red}'--> Select datasource type'${defaultcolor}': Shapefile,Database [s/d]:'${defaultcolor} datatype
while [[ ! $datatype =~ ^([sS][hH][aA][pP][eE][fF][iI][lL][eE]|[sS])$ ]] && [[ ! $datatype =~ ^([dD][aA][tT][aA][bB][aA][sS][eE]|[dD])$ ]]; do
read -r -p ${red}'Error:'${defaultcolor}' Bad input. Should be '${green}'`shapefile`'${defaultcolor}' or '${green}'`database`'${defaultcolor}'. Type again:' datatype
done

if [[ $datatype =~ ^([sS][hH][aA][pP][eE][fF][iI][lL][eE]|[sS])$ ]];then
datatype='shapefile'
else
datatype='database'
fi


#Give the url of datasource
if [ "$datatype" = 'shapefile' ]; then
echo ${red}'--> Give the path'${defaultcolor}' to the Shapefile file'${defaultcolor}
else
echo ${red}'--> Give the jdbc url'${defaultcolor}' of the Database (e.g jdbc:monetdb://localhost:50000/demobjohn)'${defaultcolor}
fi
read -e url

echo ${green}'Info:'${defaultcolor}' The datasource is '${datatype}': '${green}${url}${defaultcolor}

#Give other properties for database
if [ "$datatype" = 'database' ]; then
echo ${red}'--> Provide the connection properties'${defaultcolor} 
echo ${red}'--> Give the user'${defaultcolor}
read user
echo ${red}'--> Give the password'${defaultcolor}
read password
fi

echo ${red}'--> Give the path of the output file'${defaultcolor}
read -e output

#Give the base iri
echo ${red}'--> Give the base iri (e.g http://linkedeodata.eu/talking-fields)'${defaultcolor}
read baseiri

if [ "$mode" = 'generate' ];then
#Generate mapping stuff
if [ "$datatype" = 'shapefile' ]; then
command='generate_mapping -b '$baseiri' -o '$output' '$url
else
#Dump rdf stuff
command='generate_mapping -u '$user' -p '$password' -b '$baseiri' -o '$output' --r2rml '$url
fi
else
command='dump_rdf -b '$baseiri' -o '$output
if [ "$datatype" = 'shapefile' ]; then
command=$command' -sh '
else
command=$command' -u '$user' -p '$password
command=$command' -jdbc '
fi
command=$command' '$url' '$mapping
fi

echo ${green}'--> Ok you costructed the command:'${defaultcolor}
echo ${green}'java -jar target/geotriples-1.0-SNAPSHOT-cmd.one-jar.jar '${command}${defaultcolor}
read -r -p ${red}'--> Execute it? [y/N]'${defaultcolor} answer
if [[ $answer =~ ^([yY][eE][sS]|[yY])$ ]]; then
java -jar target/geotriples-1.0-SNAPSHOT-cmd.one-jar.jar $command
if [ "$mode" = 'generate' ];then
#Generate mapping stuff
read -r -p ${red}'--> Would you like to dump the source data in RDF? [y/N]'${defaultcolor} answer
#echo answer
if [[ $answer =~ ^([yY][eE][sS]|[yY])$ ]]; then
mapping=$output
read -e -p ${red}'--> Give the path of the output file'${defaultcolor} -i "./output.txt" output

command='dump_rdf -b '$baseiri' -o '$output
if [ "$datatype" = 'shapefile' ]; then
command=$command' -sh '
else
command=$command' -u '$user' -p '$password
command=$command' -jdbc '
fi
command=$command' '$url' '$mapping

echo ${green}'--> Ok you costructed the command:'${defaultcolor}
echo ${green}'java -jar target/geotriples-1.0-SNAPSHOT-cmd.one-jar.jar '${command}${defaultcolor}
read -r -p ${red}'--> Execute it? [y/N]'${defaultcolor} answer
if [[ $answer =~ ^([yY][eE][sS]|[yY])$ ]]; then
java -jar target/geotriples-1.0-SNAPSHOT-cmd.one-jar.jar $command
fi

fi
fi
fi


