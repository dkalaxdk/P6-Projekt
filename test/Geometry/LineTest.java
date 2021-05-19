package Geometry;

import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Geometry.Line;
import DSDLVO.Classes.Geometry.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    protected static void assertLineEquals(Line expected, Line actual) {
        assertEquals(expected.getSlope(), actual.getSlope());
        assertEquals(expected.getYAxisIntersection(), actual.getYAxisIntersection());
    }

    @Nested
    @DisplayName("Constructor")
    public class Constructor {
        @Test
        public void constructor_returnsLineDescribedByGivenVariables() {
            double expectedX = 3;
            double expectedY = 2;

            Line actual = new Line(3,2);

            assertEquals(expectedX, actual.getSlope());
            assertEquals(expectedY, actual.getYAxisIntersection());
        }

        @Test
        public void constructor_returnsLineDescribedByTwoPoints() {
            double expectedX = -4;
            double expectedY = 1;

            HPoint pointOne = new HPoint(3, -11);
            HPoint pointTwo = new HPoint(-1, 5);

            Line actual = new Line(pointOne, pointTwo);

            assertEquals(expectedX, actual.getSlope());
            assertEquals(expectedY, actual.getYAxisIntersection());
        }

        @Test
        public void constructor_yIsNanWhenLineIsVertical() {
            double expectedX = 0;
            double expectedY = Double.NaN;

            HPoint pointOne = new HPoint(0, 0);
            HPoint pointTwo = new HPoint(0, 5);

            Line actual = new Line(pointOne, pointTwo);

            assertEquals(expectedX, actual.getSlope());
            assertEquals(expectedY, actual.getYAxisIntersection());
        }

        @Test
        public void constructor_xIsZeroWhenLineIsHorizontal() {
            double expectedX = 0;
            double expectedY = -0.8;

            HPoint pointOne = new HPoint(5, -0.8);
            HPoint pointTwo = new HPoint(-5, -0.8);

            Line actual = new Line(pointOne, pointTwo);

            assertEquals(expectedX, actual.getSlope());
            assertEquals(expectedY, actual.getYAxisIntersection());
        }
    }

    @Nested
    @DisplayName("Line.getIntersection")
    public class Intersection {
        @Test
        public void intersection_returnsPointOfIntersection() {
            HPoint expected = new HPoint(2, 2);

            Line line1 = new Line(1, 0);
            Line line2 = new Line(-3, 8);

            Point actual = line1.getIntersection(line2);

            // Could replace with a comparison of the points
            // But that would make the test rely on the equals method of the point
            assertEquals(expected.getX(), actual.getX());
            assertEquals(expected.getY(), actual.getY());
        }

        @Test
        public void intersection_returnsIntersectionBetweenVerticalAndHorizontalLine() {
            HPoint expected = new HPoint(6, 9);

            Line horizontal = new Line(0, 9);
            Line vertical = new Line(6, Double.NaN);

            Point actual = horizontal.getIntersection(vertical);

            // Could replace with a comparison of the points
            // But that would make the test rely on the equals method of the point
            assertEquals(expected.getX(), actual.getX());
            assertEquals(expected.getY(), actual.getY());
        }
    }

    @Nested
    @DisplayName("Line.equals")
    public class Equals {
        @Test
        public void equals_returnsTrueWhenSlopeAndIntersectionAreEqual() {
            Line line1 = new Line(34, -0.8);
            Line line2 = new Line(34, -0.8);

            assertTrue(line1.equals(line2));
        }

        @Test
        public void equals_returnsFalseWhenSlopeDiffer() {
            Line line1 = new Line(8.3, -0.8);
            Line line2 = new Line(34, -0.8);

            assertFalse(line1.equals(line2));
        }

        @Test
        public void equals_returnsFalseWhenIntersectionDiffer() {
            Line line1 = new Line(34, 0.8);
            Line line2 = new Line(34, -0.8);

            assertFalse(line1.equals(line2));
        }

        @Test
        public void equals_returnsFalseWhenSlopeAndIntersectionDiffer() {
            Line line1 = new Line(34, -0.8);
            Line line2 = new Line(9.2, 12);

            assertFalse(line1.equals(line2));
        }
    }

    @Nested
    @DisplayName("Line.isVertical")
    public class isVertical {
        @Test
        public void isVertical_returnsTrueWhenLineIsVertical() {
            Line line = new Line(6, Double.NaN);
            assertTrue(line.isVertical());
        }

        @Test
        public void isVertical_returnsFalseWhenLineIsNotVertical() {
            Line line = new Line(6, 1);
            assertFalse(line.isVertical());
        }
    }

    @Nested
    @DisplayName("Line.isHorizontal")
    public class isHorizontal {
        @Test
        public void isHorizontal_returnsTrueWhenLineIsHorizontal() {
            Line line = new Line(0, 9);
            assertTrue(line.isHorizontal());
        }

        @Test
        public void isHorizontal_returnsFalseWhenLineIsNotHorizontal() {
            Line line = new Line(2, 9);
            assertFalse(line.isHorizontal());
        }
    }

    @Nested
    @DisplayName("Line.getDirectionVector")
    public class getDirectionVector {
        @Test
        public void getDirectionVector_worksForPositiveSlope() {
            HPoint expected = new HPoint(0.7071067811865475, 0.7071067811865475);
            Line line = new Line(1, 0);
            assertEquals(expected, line.getDirectionVector());
        }

        @Test
        public void getDirectionVector_worksForNegativeSlope() {
            HPoint expected = new HPoint(0.7071067811865475, -0.7071067811865475);
            Line line = new Line(-1, 0);
            assertEquals(expected, line.getDirectionVector());
        }

        @Test
        public void getDirectionVector_worksForHorizontalLine() {
            HPoint expected = new HPoint(1, 0);
            Line line = new Line(0, 9);
            assertEquals(expected, line.getDirectionVector());
        }

        @Test
        public void getDirectionVector_worksForVerticalLine() {
            HPoint expected = new HPoint(0, 1);
            Line line = new Line(6, Double.NaN);
            assertEquals(expected, line.getDirectionVector());
        }
    }
}
