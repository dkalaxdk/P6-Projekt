package utilities;

import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.PointFactory;
import Dibbidut.Classes.Geometry.Vector;
import Dibbidut.Classes.Geometry.VectorFactory;
import Dibbidut.utilities.ConvexHull;
import Dibbidut.utilities.GrahamScan;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GrahamScanTest {
    protected static ConvexHull<Point> convHull;

    @BeforeAll
    public static void beforeAll() {
        PointFactory factory = new VectorFactory();
        convHull = new GrahamScan(factory);
    }

    @Nested
    @DisplayName("GrahamScan.Calculate")
    class Calculate {

        @Test
        public void Calculate_ThrowsExceptionWhenGivenLessThanThreePoints() {
            ArrayList<Point> points = new ArrayList<>(Arrays.asList(
                    new Vector(1, 1, 1),
                    new Vector(2, 2, 1)
            ));

            assertThrows(IllegalArgumentException.class, () -> convHull.Calculate(points));
        }

        // Trivial case: Given three points, convex hull will always be those points
        @Test
        public void Calculate_GivenThreePointsReturnsGivenPoints() {
            ArrayList<Point> points = new ArrayList<>(Arrays.asList(
                    new Vector(1, 1, 1),
                    new Vector(2, 2, 1),
                    new Vector(1, 2, 1)
            ));

            assertArrayEquals(points.toArray(), convHull.Calculate(points).toArray());
        }

        @Test
        public void Calculate_ReturnsPointsInConvexHull() {
            ArrayList<Point> points = pointList();

            assertArrayEquals(pointsInHull(), convHull.Calculate(points).toArray());
        }

        @Test
        public void Test_doubleCompare() {
            ArrayList<Point> points = pointList();

            assertArrayEquals(pointsInHull(), convHull.Calculate(points).toArray());
        }

        private ArrayList<Point> pointList() {
            return new ArrayList<Point>(Arrays.asList(
                    new Vector(1, 1, 1),
                    new Vector(2.5, 1, 1),
                    new Vector(3.5, 1.5, 1),
                    new Vector(1.5, 2, 1),
                    new Vector(2.5, 2.5, 1),
                    new Vector(4, 2.5, 1),
                    new Vector(1, 3, 1),
                    new Vector(2.5, 3.5, 1)
            ));
        }

        private Point[] pointsInHull() {
            return new Point[] {
                    new Vector(1, 1, 1),
                    new Vector(2.5, 1, 1),
                    new Vector(3.5, 1.5, 1),
                    new Vector(4, 2.5, 1),
                    new Vector(2.5, 3.5, 1),
                    new Vector(1, 3, 1)
            };
        }
    }
}
