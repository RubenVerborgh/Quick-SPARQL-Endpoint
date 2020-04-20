# Quick SPARQL Endpoint
A quick SPARQL endpoint with reasoning, written in Java using Jena.

## Build
```
mvn package assembly:single
```

## Run
```
java -jar target/sparql-endpoint-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h
usage: sparql-endpoint [file1 [file2 ...] [-f <arg>] [-h] [-p <arg>]
 -f,--format <arg>   the format of the files. One of 'RDF/XML',
                     'N-TRIPLE', 'TURTLE' (or 'TTL') and 'N3'. Default
                     'TURTLE'
 -h,--help           prints this message
 -p,--port <arg>     launch the SPARQL endpoint on this port
```
