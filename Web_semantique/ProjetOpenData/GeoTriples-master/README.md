# GeoTriples
 Publishing geospatial data as Linked Open Geospatial Data 

## Quickstart##
### Use GeoTriples binaries (Unix & Windows) ###
Assuming [Java >=7](https://www.java.com/en/download/) installed:

Download GeoTriples binaries at: http://linkedeodata.github.io/GeoTriples/geotriples-1.1.6-SNAPSHOT-bin.zip
*	Unzip the downloaded file `geotriples-<version>-bin.zip`
*	Change directory to `geotriples-<version>-bin`
*	Under the `bin` directory you can find all available starter scripts for GeoTriples

	> `bin/geotriples-mapping` (`bin/geotriples-mapping.bat` for Windows users) 
	
	> `bin/geotriples-rdf` (`bin/geotriples-rdf.bat` for Windows users) 
	
	> `bin/geotriples-gui` (`bin/geotriples-gui.bat` for Windows users) 
	
	> `bin/geotriples-all` (`bin/geotriples-all.bat` for Windows users) 

#### Scripts ####
GeoTriples' binaries are accompanied with some scripts to accelerate the process of transforming data into RDF. These scripts invoke the RML processor. If you like to use the R2RML processor see the Building From Sources section.
##### Mapping Generation #####
To generate an *RML mapping* file for your data, invoke the `geotriples-mapping` starter script.
Type `bin/geotriples-mapping help` to get the usage message. An example execution is the following:
```bash
$ bin/geotriples-mapping -o mapping.tll input.shp # generate mapping for a Shapefile
$ bin/geotriples-mapping -o mapping.tll input.csv # generate mapping for a CSV
$ bin/geotriples-mapping -o mapping.tll -u username -p password jdbc:postgresql://localhost/database # generate mapping for a Postgresql database
```
which processes the input file or the given database to generate an RML mapping.
##### Dump RDF #####
Given an *RML mapping* file, you can process it with GeoTriples using `geotriples-rdf` to convert data into RDF. Type `bin/geotriples-rdf help` to get the usage message. An example execution is the following:
```bash
$ bin/geotriples-rdf -o output.nt mapping.ttl # using the default N-TRIPLES format
$ bin/geotriples-rdf -o output.nt -f TURTLE mapping.ttl # TURTLE format
```
which processes the mapping file mapping.ttl and generates the RDF graph using N-Triples or TURTLE format in the file output.nt.
##### GeoTriples Graphical Interface #####
GeoTriples graphical interface can be invoked by `bin/geotriples-gui` script

![Architecture](https://cloud.githubusercontent.com/assets/5632546/19274697/3e4abe10-8fd1-11e6-9437-075f9f533114.png "The graphical interface of GeoTriples")

##### GeoTriples command line tool #####
The full functionality of GeoTriples is only available through the GeoTriples command line tool using the script `geotriples-all`
Type `bin/geotriples-all` to get usage information.

----------------------- ------------------------------------
<img src="http://www.clker.com/cliparts/7/t/J/d/u/E/emblem-important-th.png" alt="Tip" width="50px" height="50px"> When generating a mapping consider the extension of the input file; GeoTriples consults the extension to interpret/process the data. For example, input.**csv** should be a **comma separated** value file. If you would like to process a **tab separated** file input.csv, then consider renaming to input.**tsv**

----------------------------------------------------------------

### Building from sources ###
Assuming git, [Maven](http://maven.apache.org/download.cgi =25x25) and [Java >=7](https://www.java.com/en/download/) installed:
```bash
$ git clone https://github.com/LinkedEOData/GeoTriples.git
$ cd GeoTriples
$ mvn clean package
$ java -jar target/geotriples-<version>-cmd.jar [Options] [Argument]

# [Optional for unix based distros: Add an alias for executing the jar file with command `geotriples-cmd`]
$ echo "alias geotriples-cmd='java -jar `pwd`/target/geotriples-<version>-cmd.jar'" >> ~/.bashrc
```

### GeoTriples Modes ###
GeoTriples consists of three modules. The Mapping Generator which automatically produces an R2RML/RML mapping file according to the input source's schema, the R2RML/RML processor which processes an R2RML/RML mapping and generates an RDF graph, and finally the Ontology Based Data Access (OBDA) module that evaluates stSPARQL/GeoSPARQL queries over a relational database.

#### Automatic generation of R2RML/RML mappings ####
- Relational Database 
```bash
$ geotriples-cmd generate_mapping -b baseURI [-u user] [-p password] [-d driver] [-o mappingFile] [-rml] jdbcURL
```
- Shapefile
```bash
$ geotriples-cmd generate_mapping -b baseURI [-o mappingFile] [-rml] fileURL
```
- XML files (Only RML mappings)
```bash
$ geotriples-cmd generate_mapping -b baseURI [-o RMLmappingFile] [-rp rootpath] [-r rootelement] [-onlyns namespace] [-ns namespaces] [-x XSDfile] fileURL
```

#### Transformation into RDF ####
- Relational Database
```bash
$ geotriples-cmd dump_rdf [-rml] [-f format] [-b baseURI] [-o rdfoutfile]  -u user -p password -d driver -j jdbcURL inputmappingfile
```
- Shapefile
```bash
$ geotriples-cmd dump_rdf [-rml] [-f format] [-b baseURI] [-o rdfoutfile] [-s epsgcode] [-sh fileURL] inputmappingfile
```
- XML/JSON (using RML processor)
```bash
$ geotriples-cmd dump_rdf  -rml [-f format] [-b baseURI] [-o rdfoutfile] [-s epsgcode] inputRMLmappingfile
```

### GeoTriples Architecture ###
GeoTriples comprises three main
components: the mapping generator, R2RML/RML processor and the [ontop-spatial](https://github.com/ConstantB/ontop-spatial) OBDA evaluator. The mapping
generator takes as input a data source and creates automatically an R2RML or RML
mapping that transforms it into an RDF graph. The generated mapping is
enriched with subject and predicate-object maps, in order to take into account
the specifities of geospatial data and cater for all transformations that are
needed to produce an RDF graph that is compliant with the GeoSPARQL
vocabulary. To accomplish this task, we extend R2RML mappings to allow
the representation of a transformation function over input data. Afterwards,
the user may edit the generated R2RML mapping document to comply with
her requirements (e.g., use a different vocabulary).

![Architecture](https://cloud.githubusercontent.com/assets/5632546/19224116/f501bb90-8e87-11e6-939d-24cce9c6ff6a.png "The architecture of GeoTriples")

### RML Processor ###
GeoTriples also supports an extended version of [RML](http://rml.io/) mapping language by extending the [RML processor](https://github.com/mmlab/RMLProcessor) to address the spatial information.
[RML](http://rml.io/) is defined as a superset language of [R2RML](http://www.w3.org/TR/r2rml/). The strong point of RML, is that is designed to allow the process of data that *do not necessarily* rely in tables and thus not having an explicit iteration pattern.

For example, the farms.xml (see below) cannot be iterated in per row fashion, because it has nested elements.
```xml
<Farm>
   <Field id="1">
      <Vigor>4</Vigor>
      <Farmer>John Vl</Farmer>
      <Geometry>
       <gml:Polygon>
         <gml:outerBoundaryIs>
           <gml:LinearRing> 
             <gml:posList>0,0 100,0 100,100 0,100 0,0</gml:posList> 
           </gml:LinearRing>
         </gml:outerBoundaryIs>
       </gml:Polygon>
      </Geometry>
   </Field>
   <Field id="2">
      <Vigor>1</Vigor>
      <Farmer>Harper Lee</Farmer>
      <Geometry id=1>
       <gml:Polygon>
         <gml:outerBoundaryIs>
           <gml:LinearRing> 
             <gml:posList>100,100 200,100 200,200 100,200 100,100</gml:posList>
           </gml:LinearRing>
         </gml:outerBoundaryIs>
       </gml:Polygon>
      </Geometry>
   </Field>
   <Field id="3">
      <Vigor>3</Vigor>
      <Farmer>Bruce Pom</Farmer>
   </Field>
</Farm>
```

R2RML uses the property <code>rr:tableName</code> to define which table from the input file or the relational database it going to be used as the source table for the mappings. RML has the equivalent <code>rml:source</code> to define the source for the mappings. The source can be a JDBC URL for a relational database, a Shapefile, an XML, JSON or CSV file. 
The iterator property <code>rml:iterator</code> defines the iterating pattern in order to process non-relational structured files. For the above example the iterator should be an XPath query.

<p>An example RML mapping can be the following</p>
```
<#Field>
	rml:logicalSource [
	 rml:source "/fields.xml";
	 rml:referenceFormulation ql:XPath;
	 rml:iterator "/Farm/Field"];
	
	rr:subjectMap [ 
	 rr:class ont:Farm; 
	 rr:class ogc:Feature;
	 rr:template "http://data.linkedeodata.eu/Field/id/{@id}"];
	
	rr:predicateObjectMap [ 
	 rr:predicate ont:hasVigor; 
	 rr:objectMap [
	   rml:reference "Vigor"]];
	
	rr:predicateObjectMap [ 
	 rr:predicate ont:hasFarmer;
	 rr:objectMap [ 
	   rml:reference "Farmer"]].
	   
	rr:predicateObjectMap [ 
	 rr:predicate ogc:hasGeometry;
	 rr:objectMap [ 
	   rr:template "http://data.linkedeodata.eu/FieldGeometry/id/{Geometry/@id}"]].

<#FieldGeometry>
	rml:logicalSource [
	 rml:source "/fields.xml";
	 rml:referenceFormulation ql:XPath;
	 rml:iterator "/Farm/Field/Geometry"];
	
	rr:subjectMap [ 
	 rr:class ont:FieldGeometry; 
	 rr:class ogc:Geometry
	 rr:template "http://data.linkedeodata.eu/FieldGeometry/id/{@id}"];
	
	rr:predicateObjectMap [ 
	 rr:predicate ogc:dimension; 
	 rr:objectMap [
	   rrx:function rrxf:dimension;
	   rrx:argumentMap ([rml:reference "*"]) ];
	
	rr:predicateObjectMap [ 
	 rr:predicate ogc:asWKT; 
	 rr:objectMap [
	   rrx:function rrxf:asWKT;
	   rrx:argumentMap ([rml:reference "*"]) ].
```

This mapping contains two triples maps: <#Field> and <#FieldGeometry>. Both triples maps uses an XPath iterator, denoted by `rml:referenceFormulation`, as the base iterator pattern that will be used by the mapping processor module for the generation of the graph. The `rml:reference` is used instead of `rr:column` R2RML's property . The value of `rml:reference` property extends the iterator in order to point at an element.

## Combine heterogeneous data, extract topological relations ##
RML can be used to combine heterogeneous data by generating links between resources that share a same attribute.

For example, if you have a Shapefile that contains a field named A, and this field is being used as an `reference key` B to a JSON file, then you can use the RML join conditions to generate links between these two datasets.

GeoTriples implements an extended version of Join Condition class allowing for the generation of links that are not depending on equality of two values, but on the result of a function. Currently, GeoTriples implements the following GeoSPARQL functions:
<ol>
  <li>sfIntersects</li>
  <li>sfContains</li>
  <li>sfTouches</li>
</ol>
