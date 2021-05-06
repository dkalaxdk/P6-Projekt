import math

import pandas as pd
import datetime

from classes.ship import Ship

earthRadius = 6378137.0
knotsToMetersPerSecond = 0.514444
outputFile = "../InputFiles/generated_file.csv"
inputFile = "input.txt"


def un_project_y(y):
    n = math.exp(y / earthRadius)
    a = (2 * math.atan(n)) - (math.pi / 2)
    return math.degrees(a)


def un_project_x(x):
    return math.degrees(x / earthRadius)


def read_file():
    inputString = pd.read_csv(inputFile)

    MMSI = 0
    Ships = []
    for Heading, X, Y, COG, SOG, Length, Width in zip(inputString["Heading"],
                                                      inputString["X"], inputString["Y"],
                                                      inputString["COG"], inputString["SOG"],
                                                      inputString["Length"], inputString["Width"]):
        MMSI += 1
        Ships.append(Ship(MMSI, Heading, X, Y, COG, SOG, Length, Width))
    return Ships


def create_output(time_in_minutes, ships):
    output = "Timestamp,Type of mobile,MMSI,Latitude,Longitude,Navigational status,ROT,SOG,COG,Heading,IMO,Callsign," \
             "Name,Shiptype,Cargo type,Width,Length,Type of position fixing device,Draught,Destination,ETA," \
             "Data source type,A,B,C,D\n"

    time_in_minutes = int(time_in_minutes)
    dt = datetime.datetime.now().replace(hour=0, minute=0, second=0, microsecond=0).timestamp()

    for i in range(0, time_in_minutes * 60+1):
        for ship in ships:
            date = datetime.datetime.fromtimestamp(dt + i).strftime("%d/%m/%Y %H:%M:%S")
            uv = ship.get_unit_vector()
            ship.X = ship.X + uv[0] * (ship.SOG * knotsToMetersPerSecond)
            ship.Y = ship.Y + uv[1] * (ship.SOG * knotsToMetersPerSecond)
            output += str(date) + ",," + str(ship.MMSI) + "," + str(un_project_y(ship.Y)) + "," + str(un_project_x(
                ship.X)) + ",,," + str(ship.SOG) + "," + str(ship.COG) + ",,,,,,," + str(ship.Width) + "," + str(
                ship.Length) + ",,,,,,,,,\n"
    file = open(outputFile, "w")
    file.write(output)


if __name__ == "__main__":
    create_output(input("How long should it simulate? (Minutes) \n"), read_file())
