# Dynamic Ship Domain Linear Velocity Obstacle algorithm
Dynamic Ship Domain Linear Velocity Obstacle (DSDLVO) is a project implemented in Java, which combines the Dynamic Ship Domains of [Bakdi et al, 2020](https://www.mdpi.com/2077-1312/8/1/5) with the linear velocity obstacle algorithm from [Huang et al,2018](https://www.sciencedirect.com/science/article/abs/pii/S0029801818300015). <br>
The project uses AIS data read from a CSV file acquired from [Danish Maritime Authority (FTP link)](ftp://ftp.ais.dk/ais_data/). <br>
This project is made as a part of a bachelor project from Aalborg University.

## Setup of Java program
The program comes with the Java 16 project and a python 3.8 project.<br>
The Java project contains the implementation of the dynamic ship domain and the velocity obstacle algorithm, as well as a CSV reader which reads the AIS data.

* **Define input file:**<br>
  The GitHub repository contains a set of predefined input files [Input InputFiles](https://github.com/dkalaxdk/P6-Projekt/tree/master/InputFiles). To change which file is being used, one currently have to change the input within the Main class of the Java project [Reference til Main](https://www.youtube.com/watch?v=dQw4w9WgXcQ). <br>
  The program repository also contains a python script to define a custom input file. [Input File Creator](#setup-of-input-file-generator)<br>
  If an external input file is used, it should adhere to the same standards as the defined inputs files. The entries are comma separated, as well as SOG, COG ,Length ,Heading ,longitude ,latitude should be floats.
* **Define MMSI**<br>
  When running the program, the Own Ship (OS) should be defined. Therefore, the MMSI of the ship that should be interpreted as the OS, should be defined.
* **Build the project**<br>
  After the correct file have been selected, the project should be built. <br>
* **Run the project**


## Running the program
When running the program the user is presented with an interface containing a ship domain in the center, which is own ship. In the top right the user is presented with 3 sliders.
1. **Time frame** <br> This defines the time frame in which the VO should look for collisions
2. **Time factor** <br> Simulation speed control, which defines the speed of the simulation.
3. **Zoom** <br> Allows you to zoom in and out.




# Setup of input file generator

The python input file creator reads the content of the [input.txt](https://github.com/dkalaxdk/P6-Projekt/blob/master/DataGenerator/input.txt) which should be structured as a CSV file. <br>
The CSV file should have the following structure: <br>

**X**|**Y**|**SOG**|**COG**|**Heading**|**Length**|**Width**
:-----|:-----|:-----|:-----|:-----|:-----|:-----
X Coordinates, will be converted to longitude. |Y coordinates, will be converted to latitude. |Speed of the vessel in knots.| Course over ground, defines the direction the ship is sailing, this will be a constant direction. | Heading as degrees.| Length of the ship. | Width of the ship.<br>

The first line in the inputfile defines a ship with MMSI 1, second row defines a ship with MMSI 2, each line will add a new ship to generated_file.csv. <br>

The input file generator overrides the [InputFiles/generated_file.csv](https://github.com/dkalaxdk/P6-Projekt/blob/master/InputFiles/generated_file.csv), to use this the input file in the Java program should be defined as the generated_file. <br>

After having defined the inputfile.txt the python project should be built and run, upon running the python project will prompt for the amount of minutes which should be simulated.
