package utilities;

import Dibbidut.Classes.Geometry.*;
import Dibbidut.Classes.Geometry.HPoint;
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
        PointFactory factory = new HPointFactory();
        convHull = new GrahamScan(factory);
    }

    @Nested
    @DisplayName("GrahamScan.Calculate")
    class Calculate {

        @Test
        public void Calculate_ThrowsExceptionWhenGivenLessThanThreePoints() {
            ArrayList<Point> points = new ArrayList<>(Arrays.asList(
                    new HPoint(1, 1, 1),
                    new HPoint(2, 2, 1)
            ));

            assertThrows(IllegalArgumentException.class, () -> convHull.Calculate(points));
        }

        // Trivial case: Given three points, convex hull will always be those points
        @Test
        public void Calculate_GivenThreePointsReturnsGivenPoints() {
            ArrayList<Point> points = new ArrayList<>(Arrays.asList(
                    new HPoint(1, 1, 1),
                    new HPoint(2, 2, 1),
                    new HPoint(1, 2, 1)
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
                    new HPoint(1, 1, 1),
                    new HPoint(2.5, 1, 1),
                    new HPoint(3.5, 1.5, 1),
                    new HPoint(1.5, 2, 1),
                    new HPoint(2.5, 2.5, 1),
                    new HPoint(4, 2.5, 1),
                    new HPoint(1, 3, 1),
                    new HPoint(2.5, 3.5, 1)
            ));
        }

        private Point[] pointsInHull() {
            return new Point[] {
                    new HPoint(1, 1, 1),
                    new HPoint(2.5, 1, 1),
                    new HPoint(3.5, 1.5, 1),
                    new HPoint(4, 2.5, 1),
                    new HPoint(2.5, 3.5, 1),
                    new HPoint(1, 3, 1)
            };
        }

        @Test
        public void Calculate_ReturnsPointsInConvexHullWhenSomePointsAreAtSameAngle(){
            ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(
                    new HPoint(1, 1, 1),
                    new HPoint(1, 3, 1),
                    new HPoint(1, 5, 1),
                    new HPoint(3, 6, 1),
                    new HPoint(4, 5, 1),
                    new HPoint(4, 4, 1),
                    new HPoint(3, 3, 1),
                    new HPoint(5, 2, 1),
                    new HPoint(5, 1, 1),
                    new HPoint(3, 1, 1)
            ));

            Point[] expectedResult = new Point[] {
                    new HPoint(1, 1, 1),
                    new HPoint(5, 1, 1),
                    new HPoint(5, 2, 1),
                    new HPoint(4, 5, 1),
                    new HPoint(3, 6, 1),
                    new HPoint(1, 5, 1)

            };

            assertArrayEquals(expectedResult, convHull.Calculate(points).toArray());
        }
    }
}
