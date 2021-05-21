package DSDLVO.Utilities;

import DSDLVO.Classes.Geometry.HPoint;

// https://en.wikipedia.org/wiki/Mercator_projection
public class Mercator {
    public final static double earthRadius = 6378137.0;

    public static HPoint projection(double longitude, double latitude) {

        double radLongitude = Math.toRadians(longitude);
        double radLatitude = Math.toRadians(latitude);

        double x = earthRadius * radLongitude;
        double y = earthRadius * Math.log(Math.tan((Math.PI / 4) + (radLatitude / 2)));

        return new HPoint(x, y);
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
