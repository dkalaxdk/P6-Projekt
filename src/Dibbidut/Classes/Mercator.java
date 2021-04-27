package Dibbidut.Classes;

import math.geom2d.Vector2D;

// https://en.wikipedia.org/wiki/Mercator_projection
public class Mercator {
    public final static double earthRadius = 6378137.0;

    public static Vector2D projection(double longitude, double latitude) {

        double radLongitude = Math.toRadians(longitude);
        double radLatitude = Math.toRadians(latitude);

        double x = earthRadius * radLongitude;
        double y = earthRadius * Math.log(Math.tan((Math.PI / 4) + (radLatitude / 2)));

        return new Vector2D(x, y);
    }

    public static double unprojectionX(double x) {
        return Math.toDegrees(x / earthRadius);
    }

    public static double unprojectionY(double y) {

        double n = Math.exp(y / earthRadius);

        double a = (2 * Math.atan(n)) - (Math.PI / 2);

        return Math.toDegrees(a);
    }

    public static double nauticalToMeters(double n) {
        return n * 1852;
    }
}
