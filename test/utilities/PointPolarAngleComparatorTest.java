package utilities;

import Dibbidut.utilities.PointPolarAngleComparator;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

public class PointPolarAngleComparatorTest {
    @Nested
    @DisplayName("Compare")
    class Compare {
        @Test
        public void Compare_shouldComparePolarAngleOfPoint() {
            Point2D point1 = new Point2D.Double(1, 5);
            Point2D point2 = new Point2D.Double(5, 1);

            double p1Angle = Math.atan2(point1.getY(), point1.getX());
            double p2Angle = Math.atan2(point2.getY(), point2.getX());

            PointPolarAngleComparator comparator = new PointPolarAngleComparator();

            assertEquals(Double.compare(p1Angle, p2Angle), comparator.compare(point1, point2));
            assertEquals(Double.compare(p2Angle, p1Angle), comparator.compare(point2, point1));
        }
    }
}
