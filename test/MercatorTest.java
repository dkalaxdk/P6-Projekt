import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Utilities.Mercator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MercatorTest {



    @Test
    public void projection() {
        double longitude = 8;
        double latitude = 55;
        double referenceLongitude = 0;

        double radLong = Math.toRadians(longitude);
        double radLat = Math.toRadians(latitude);
        double radReference = Math.toRadians(referenceLongitude);

        double longExpected = Mercator.earthRadius * (radLong - radReference);
        double latExpected = Mercator.earthRadius * Math.log(Math.tan((Math.PI / 4) + (radLat / 2)));

        HPoint expected = new HPoint(longExpected, latExpected);

        HPoint actual = Mercator.projection(longitude, latitude);

        assertEquals(expected, actual);
    }

    @Test
    public void unprojectionX() {
        double longitude = 8;
        double latitude = 55;

        HPoint n = Mercator.projection(longitude, latitude);

        double actual = Mercator.unprojectionX(n.getX());

        assertEquals(longitude, actual);
    }

    @Test
    public void unprojectionY() {
        double longitude = 8;
        double latitude = 55;

        HPoint n = Mercator.projection(longitude, latitude);

        double actual = Mercator.unprojectionY(n.getY());

        assertEquals(latitude, Math.round(actual));
    }
}
