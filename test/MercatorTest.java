import Dibbidut.Classes.Mercator;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MercatorTest {



    @Test
    public void projection_() {
        double longitude = 8;
        double latitude = 55;
        double referenceLongitude = 0;

        double radLong = Math.toRadians(longitude);
        double radLat = Math.toRadians(latitude);
        double radReference = Math.toRadians(referenceLongitude);

        double longExpected = Mercator.earthRadius * (radLong - radReference);
        double latExpected = Mercator.earthRadius * Math.log(Math.tan((Math.PI / 4) + (radLat / 2)));

        Vector2D expected = new Vector2D(longExpected, latExpected);

        Vector2D actual = Mercator.projection(longitude, latitude, radReference);

        assertEquals(expected, actual);
    }
}
