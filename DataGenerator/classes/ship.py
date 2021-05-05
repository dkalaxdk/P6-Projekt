import math


class Ship:
    MMSI = 0
    Heading = 0.0
    COG = 0.0
    SOG = 0.0
    Length = 0.0
    Width = 0.0
    UnitVector = []

    def __init__(self, MMSI, heading, x, y, COG, SOG, length, width):
        self.MMSI = MMSI
        self.Heading = heading
        self.X = x
        self.Y = y
        self.COG = COG
        self.SOG = SOG
        self.Length = length
        self.Width = width

    def get_unit_vector(self):
        if len(self.UnitVector) == 0:
            self.unit_vector()
        return self.UnitVector

    def unit_vector(self):
        degrees_as_radians = math.radians(-self.COG)
        out_x = math.cos(degrees_as_radians) * 0 - math.sin(degrees_as_radians) * 1
        out_y = math.sin(degrees_as_radians) * 0 + math.cos(degrees_as_radians) * 1
        self.UnitVector = [out_x, out_y]
