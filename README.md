# Dynamic Ship Domain Linear Velocity Obstacle algorithm
Dynamic Ship Domain Linear Velocity Obstacle algorithm (DSDLVO) is a project implemented in Java, which combines the Dynamic Ship Domains of [Bakdi et al, 2020](https://www.mdpi.com/2077-1312/8/1/5) with the linear velocity algorithm from [Huang et al,2018](https://www.sciencedirect.com/science/article/abs/pii/S0029801818300015). <br>
The project is based on AIS data given from a CSV file from [Danish Maritime Authority (FTP link)](ftp://ftp.ais.dk/ais_data/).<br>
Made for a bachelor project from Aalborg University.

## Setup
The program comes with the Java project, as well as a python project.<br>
The Java 16 project contains the implementation of the two algorithms as well as a CSV reader which read the AIS data.

* **Define input file:**<br>
  The GitHub repository contains a set of predefined input files [Input InputFiles](https://github.com/dkalaxdk/P6-Projekt/tree/master/InputFiles) to change which file is being used, one currently have to change the input within the Main class of the Java project [Reference til Main](https://www.youtube.com/watch?v=dQw4w9WgXcQ). <br>
  The program repository also contains a python script to define a custom input file. [Input File Creator](#input-file-creator)<br>
  If an external input file is used, it should adhere to the same standards as the defined inputs files. The entries are comma separated, as well as SOG,COG,Length,Heading,longitude,latitude should be floats.
* **Define MMSI**<br>
  When running the program, the Own Ship should be defined, therefore the MMSI of the ship which should be interpreted as the OS, should be defined.
* **Build the project**<br>
  After the correct file have been selected, the project should be built. <br>
* **Run the project**





## What is it




# Input file creator

The python input file creator reads the content of the [input.txt](https://github.com/dkalaxdk/P6-Projekt/blob/master/DataGenerator/input.txt) which should be structured as a CSV file. <br>
The CSV file should have the following structure: <br>

**X**|**Y**|**SOG**|**COG**|**Heading**|**Length**|**Width**
:-----|:-----|:-----|:-----|:-----|:-----|:-----
X Coordinates, will be converted to longitude. |Y coordinates, will be converted to latitude. |Speed of the vessel in knots.| Course over ground, defines the direction the ship is sailing, this will be a constant direction. | Heading as degrees.| Length of the ship. | Width of the ship.<br>

The first line in the inputfile defines a ship with MMSI 1, second row defines a ship with MMSI 2, each line will add a new ship to generated_file.csv. <br>

The input file generator overrides the [InputFiles/generated_file.csv](https://github.com/dkalaxdk/P6-Projekt/blob/master/InputFiles/generated_file.csv), to use this the input file in the java file should be defined as the generated_file. <br>

After having defined the inputfile.txt the python project should be built and run, upon running the python project will prompt for the amount of minutes which should be simulated.
