# carNavigationLoader
A data loader for the [carNavigation](https://github.com/aar0np/carNavigation) project.

## Requirements

 - Java 21 (JRE)
 - Environment variables
     - `ASTRA_DB_ENDPOINT`
     - `ASTRA_DB_TOKEN`

## To build:

### Map

So the map file has not been parameterized, and is coded into line #17:

    String mapFile = "city_map_2";

This directs it to the [city_map_2_vectors.txt](city_map_2_vectors.txt) file, which is quite literally just a text file of vectors.

### Build Requirements

 - Maven

The [pom.xml](pom.xml) file can be adjusted to build with earlier versions of Java, but it is not recommended to go below 17.

### Build command

    mvn clean install

### Running the build

    java -jar target/carnavigationloader-0.0.1-SNAPSHOT.jar
