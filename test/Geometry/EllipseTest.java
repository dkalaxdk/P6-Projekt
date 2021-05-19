package Geometry;

import DSDLVO.Classes.Geometry.Ellipse;
import DSDLVO.Classes.Geometry.Transformation;
import DSDLVO.Classes.Geometry.HPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EllipseTest {

    @Nested
    @DisplayName("Ellipse.contains")
    public class contains{
        @Test
        public void contains_returnsTrueIfPointIsWithinEllipse() {
            Ellipse ellipse = new Ellipse(new HPoint(0, 0, 1), 2, 1);

            assertTrue(ellipse.contains(new HPoint(1, 0.5, 1)));
            assertTrue(ellipse.contains(new HPoint(-1, -0.5, 1)));
            assertTrue(ellipse.contains(new HPoint(1.5, 0.6, 1)));
            assertTrue(ellipse.contains(new HPoint(0, -0.9, 1)));
            assertTrue(ellipse.contains(new HPoint(1.9, 0, 1)));
        }

        @Test
        public void contains_returnsFalseIfPointIsOutsideEllipse() {
            Ellipse ellipse = new Ellipse(new HPoint(0, 0, 1), 2, 1);

            assertFalse(ellipse.contains(new HPoint(2.1, 0, 1)));
            assertFalse(ellipse.contains(new HPoint(0, -1.1, 1)));
            assertFalse(ellipse.contains(new HPoint(2, 2, 1)));
            assertFalse(ellipse.contains(new HPoint(1.5, 1.5, 1)));
            assertFalse(ellipse.contains(new HPoint(1, 1.2, 1)));
        }

        @Test
        public void contains_returnsTrueIfPointIsOnBorderOfEllipse() {
            Ellipse ellipse = new Ellipse(new HPoint(0, 0, 1), 2, 1);

            assertTrue(ellipse.contains(new HPoint(2, 0, 1)));
            assertTrue(ellipse.contains(new HPoint(-2, 0, 1)));
            assertTrue(ellipse.contains(new HPoint(0, 0.5, 1)));
            assertTrue(ellipse.contains(new HPoint(0, -0.5, 1)));
        }
    }

    /*
    @Nested
    @DisplayName("Ellipse.getFoci")
    public class getFoci {
        @Test
        public void getFoci_returnsFociOfHorizontalEllipse() {
            Ellipse ellipse = new Ellipse(new Vector(0,0), 10, 8);

            Vector[] expected = new Vector[] {
                    new Vector(-6, 0),
                    new Vector(6, 0)
            };
            assertArrayEquals(expected, ellipse.getFoci());
        }
    }
    */

    @Nested
    @DisplayName("Ellipse.transform")
    public class transform {
        @Test
        public void transform_transformsEllipseCenter() {
            Ellipse ellipse = new Ellipse(new HPoint(0,0, 1), 10, 8);
            Transformation t = new Transformation();
            t.rotate(90);
            t.scale(0.5, 0.5);
            t.translate(2, 2.5);

            ellipse.transform(t);

            assertEquals(new HPoint(2, 2.5, 1), ellipse.getCenter());
        }

        @Test
        public void transform_transformedEllipseContainsPointsOnAxis() {
            Ellipse ellipse = new Ellipse(new HPoint(0,0, 1), 10, 8);
            Transformation t = new Transformation();
            t.rotate(90);
            t.scale(0.5, 0.5);
            t.translate(2, 2.5);

            ellipse.transform(t);

            assertTrue(ellipse.contains(new HPoint(2, -2.5, 1)));
            assertTrue(ellipse.contains(new HPoint(6, 2.5, 1)));
        }

        @Test
        public void transform_transformedEllipseDoesNotContainPointsOutsideBorder() {
            Ellipse ellipse = new Ellipse(new HPoint(0,0, 1), 10, 8);
            Transformation t = new Transformation();
            t.rotate(90);
            t.scale(0.5, 0.5);
            t.translate(2, 2.5);


            ellipse.transform(t);

            assertFalse(ellipse.contains(new HPoint(2, -2.6, 1)));
            assertFalse(ellipse.contains(new HPoint(6.2, 2.5, 1)));
        }

        /*
        @Test
        public void transform_transformsEllipseFoci() {
            Ellipse ellipse = new Ellipse(new Vector(0,0), 10, 8);
            Vector[] expected = new Vector[] {
                    new Vector(2, -0.5),
                    new Vector(2, 5.5)
            };

            Transformation t = new Transformation();
            t.scale(0.5, 0.5);
            t.rotate(90);
            t.translate(2, 2.5);

            ellipse.transform(t);

            assertArrayEquals(expected, ellipse.getFoci());
        }
        */
    }

    @Nested
    @DisplayName("Ellipse.getCenter")
    public class getCenter {
        @Test
        public void getCenter_returnsCenterOfEllipse() {
            Ellipse ellipse = new Ellipse(new HPoint(5, 7, 1), 1, 1);
            HPoint expected = new HPoint(5, 7, 1);

            assertEquals(expected, ellipse.getCenter());
        }
    }
}
