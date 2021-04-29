# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import math
from time import strftime, localtime

import pandas as pd
import datetime

earthRadius = 6378137.0


def un_project_y(y):
    n = math.exp(y / earthRadius)
    a = (2 * math.atan(n)) - (math.pi / 2)
    return math.degrees(a)


def read_file():
    timeinHours = 1
    output = pd.DataFrame(
        columns=["Timestamp", "Type of mobile", "MMSI", "Latitude", "Longitude", "Navigational status", "ROT", "SOG",
                 "COG", "Heading", "IMO", "Callsign", "Name", "Ship" "type", "Cargo type", "Width", "Length",
                 "Type of position fixing device", "Draught", "Destination", "ETA", "Data source type", "A", "B", "C",
                 "D"])
    inputString = pd.read_csv("input.txt")
    calculatedX = 0
    for MMSI, Heading, X, Y, COG, SOG, Length, Width in zip(inputString["MMSI"], inputString["Heading"],
                                                            inputString["X"], inputString["Y"],
                                                            inputString["COG"], inputString["SOG"],
                                                            inputString["Length"], inputString["Width"]):
        for i in range(1, timeinHours * 60 * 60):
            calculatedX += SOG
            output["Timestamp"] = pd.to_datetime(datetime.datetime.now())
            output["MMSI"] = MMSI
            output["Heading"] = Heading
            output["Latitude"] = math.degrees(calculatedX + SOG / earthRadius)
            output["Longitude"] = un_project_y(Y)
            output["COG"] = COG
            output["SOG"] = SOG
            output["Length"] = Length
            output["Width"] = Width

    output.to_csv("output.txt", index=False)


if __name__ == "__main__":
    read_file()
