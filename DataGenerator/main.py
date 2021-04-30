import math

import pandas as pd
import datetime

earthRadius = 6378137.0


def un_project_y(y):
    n = math.exp(y / earthRadius)
    a = (2 * math.atan(n)) - (math.pi / 2)
    return math.degrees(a)


def unit_vector(deg):
    degrees_as_radians = math.radians(deg - 90)
    out_x = math.cos(degrees_as_radians) * 0 - math.sin(degrees_as_radians) * 1
    out_y = math.sin(degrees_as_radians) * 0 + math.cos(degrees_as_radians) * 1
    return [out_x, out_y]


def un_project_x(x):
    return math.degrees(x / earthRadius)


def read_file():
    time_in_minutes = 10
    output = pd.DataFrame(
        columns=["Timestamp", "Type of mobile", "MMSI", "Latitude", "Longitude", "Navigational status", "ROT", "SOG",
                 "COG", "Heading", "IMO", "Callsign", "Name", "Ship" "type", "Cargo type", "Width", "Length",
                 "Type of position fixing device", "Draught", "Destination", "ETA", "Data source type", "A", "B", "C",
                 "D"])
    inputString = pd.read_csv("input.txt")

    MMSI = 0
    dt = datetime.datetime.now()
    for Heading, X, Y, COG, SOG, Length, Width in zip(inputString["Heading"],
                                                      inputString["X"], inputString["Y"],
                                                      inputString["COG"], inputString["SOG"],
                                                      inputString["Length"], inputString["Width"]):
        MMSI += 1
        calculatedX = X
        calculatedY = Y
        all_lines = []
        uv = unit_vector(COG)
        for i in range(1, time_in_minutes * 60):
            current_line = pd.DataFrame(
                columns=["Timestamp", "Type of mobile", "MMSI", "Latitude", "Longitude", "Navigational status", "ROT",
                         "SOG", "COG", "Heading", "IMO", "Callsign", "Name", "Ship" "type", "Cargo type", "Width",
                         "Length", "Type of position fixing device", "Draught", "Destination", "ETA",
                         "Data source type", "A", "B", "C", "D"])
            # 0.515660452624 is a constant, which makes SOG result in the correct knots.
            calculatedX = calculatedX + uv[0] * (SOG * 0.515660452624)
            calculatedY = calculatedY + uv[1] * (SOG * 0.515660452624)
            current_line["Timestamp"] = [
                pd.to_datetime(dt + datetime.timedelta(seconds=1 * i)).strftime("%d/%m/%Y %H:%M:%S")]
            current_line["MMSI"] = [MMSI]
            current_line["Heading"] = [Heading]
            current_line["Latitude"] = [un_project_x(calculatedX)]
            current_line["Longitude"] = [un_project_y(calculatedY)]
            current_line["COG"] = [COG]
            current_line["SOG"] = [SOG]
            current_line["Length"] = [Length]
            current_line["Width"] = [Width]
            all_lines.append(current_line)
        output = output.append(all_lines)
        output = output.sort_values(by=["Timestamp"], ascending=True)

    output.to_csv("output.csv", index=False)


if __name__ == "__main__":
    read_file()
