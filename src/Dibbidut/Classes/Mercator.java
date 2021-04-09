package Dibbidut.Classes;

import math.geom2d.Vector2D;

public class Mercator {
    public final static double earthRadius = 6378137.0;

    public static Vector2D projection(double longitude, double latitude, double ownShipLongitude) {

        double radLongitude = Math.toRadians(longitude);
        double radLatitude = Math.toRadians(latitude);
        double radReference = Math.toRadians(ownShipLongitude);

        double x = earthRadius * (radLongitude - radReference);
        double y = earthRadius * Math.log(Math.tan((Math.PI / 4) + (radLatitude / 2)));

        return new Vector2D(x, y);
    }
}
