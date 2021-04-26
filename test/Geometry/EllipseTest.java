package Geometry;

import Dibbidut.Classes.Geometry.Ellipse;
import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Transformation;
import Dibbidut.Classes.Geometry.Vector;
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
            Ellipse ellipse = new Ellipse(new Vector(0, 0), 2, 1);

            assertTrue(ellipse.contains(new Vector(1, 0.5)));
            assertTrue(ellipse.contains(new Vector(-1, -0.5)));
            assertTrue(ellipse.contains(new Vector(1.5, 0.6)));
            assertTrue(ellipse.contains(new Vector(0, -0.9)));
            assertTrue(ellipse.contains(new Vector(1.9, 0)));
        }

        @Test
        public void contains_returnsFalseIfPointIsOutsideEllipse() {
            Ellipse ellipse = new Ellipse(new Vector(0, 0), 2, 1);

            assertFalse(ellipse.contains(new Vector(2.1, 0)));
            assertFalse(ellipse.contains(new Vector(0, -1.1)));
            assertFalse(ellipse.contains(new Vector(2, 2)));
            assertFalse(ellipse.contains(new Vector(1.5, 1.5)));
            assertFalse(ellipse.contains(new Vector(1, 1.2)));
        }

        @Test
        public void contains_returnsTrueIfPointIsOnBorderOfEllipse() {
            Ellipse ellipse = new Ellipse(new Vector(0, 0), 2, 1);

            assertTrue(ellipse.contains(new Vector(2, 0)));
            assertTrue(ellipse.contains(new Vector(-2, 0)));
            assertTrue(ellipse.contains(new Vector(0, 0.5)));
            assertTrue(ellipse.contains(new Vector(0, -0.5)));
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
            Ellipse ellipse = new Ellipse(new Vector(0,0), 10, 8);
            Transformation t = new Transformation();
            t.translate(2, 2.5);
            t.scale(0.5, 0.5);
            t.rotate(90);

            ellipse.transform(t);

            assertEquals(new Vector(2, 2.5), ellipse.getCenter());
        }

        @Test
        public void transform_transformedEllipseContainsPointsOnAxis() {
            Ellipse ellipse = new Ellipse(new Vector(0,0), 10, 8);
            Transformation t = new Transformation();
            t.translate(2, 2.5);
            t.scale(0.5, 0.5);
            t.rotate(90);

            ellipse.transform(t);

            assertTrue(ellipse.contains(new Vector(2, -2.5)));
            assertTrue(ellipse.contains(new Vector(6, 2.5)));
        }

        @Test
        public void transform_transformedEllipseDoesNotContainPointsOutsideBorder() {
            Ellipse ellipse = new Ellipse(new Vector(0,0), 10, 8);
            Transformation t = new Transformation();
            t.translate(2, 2.5);
            t.scale(0.5, 0.5);
            t.rotate(90);

            ellipse.transform(t);

            assertFalse(ellipse.contains(new Vector(2, -2.6)));
            assertFalse(ellipse.contains(new Vector(6.2, 2.5)));
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
            Ellipse ellipse = new Ellipse(new Vector(5, 7), 1, 1);
            Vector expected = new Vector(5, 7);

            assertEquals(expected, ellipse.getCenter());
        }
    }

    @Test
    public void getWidth() {

    }

    @Test
    public void getHeight() {

    }

}
