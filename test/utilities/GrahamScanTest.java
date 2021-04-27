package utilities;

import Dibbidut.utilities.GrahamScan;
import org.junit.jupiter.api.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GrahamScanTest {
    @Nested
    @DisplayName("GrahamScan.Calculate")
    class Calculate {

        @Test
        public void Calculate_ThrowsExceptionWhenGivenLessThanThreePoints() {
            GrahamScan convHull = new GrahamScan();
            ArrayList<Point2D> points = new ArrayList<>(Arrays.asList(
                    new Point2D.Double(1, 1),
                    new Point2D.Double(2, 2)
            ));

            assertThrows(IllegalArgumentException.class, () -> convHull.Calculate(points));
        }

        // Trivial case: Given three points, convex hull will always be those points
        @Test
        public void Calculate_GivenThreePointsReturnsGivenPoints() {
            ArrayList<Point2D> points = new ArrayList<>(Arrays.asList(
                    new Point2D.Double(1, 1),
                    new Point2D.Double(2, 2),
                    new Point2D.Double(1, 2)
            ));
            GrahamScan convHull = new GrahamScan();

            assertArrayEquals(points.toArray(), convHull.Calculate(points).toArray());
        }

        @Test
        public void Calculate_ReturnsPointsInConvexHull() {
            ArrayList<Point2D> points = pointList();
            GrahamScan convHull = new GrahamScan();

            assertArrayEquals(pointsInHull(), convHull.Calculate(points).toArray());
        }

        @Test
        public void Test_doubleCompare() {
            ArrayList<Point2D> points = pointList();
            GrahamScan convHull = new GrahamScan();

            assertArrayEquals(pointsInHull(), convHull.Calculate(points).toArray());
        }

        private ArrayList<Point2D> pointList() {
            return new ArrayList<>(Arrays.asList(
                    new Point2D.Double(1, 1),
                    new Point2D.Double(2.5, 1),
                    new Point2D.Double(3.5, 1.5),
                    new Point2D.Double(1.5, 2),
                    new Point2D.Double(2.5, 2.5),
                    new Point2D.Double(4, 2.5),
                    new Point2D.Double(1, 3),
                    new Point2D.Double(2.5, 3.5)
            ));
        }

        private Point2D[] pointsInHull() {
            return new Point2D[] {
                    new Point2D.Double(1, 1),
                    new Point2D.Double(2.5, 1),
                    new Point2D.Double(3.5, 1.5),
                    new Point2D.Double(4, 2.5),
                    new Point2D.Double(2.5, 3.5),
                    new Point2D.Double(1, 3)
            };
        }
    }
}
