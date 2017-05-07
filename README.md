# Quick SPARQL Endpoint
A quick SPARQL endpoint with reasoning, written in Java using Jena.

## Build
```
mvn package assembly:single
```

## Run
```
java -jar target/sparql-endpoint-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h
usage: sparql-endpoint [file1.ttl [file2.ttl]] [-h] [-p <arg>]
 -h,--help         prints this message
 -p,--port <arg>   launch the SPARQL endpoint on this port
```
