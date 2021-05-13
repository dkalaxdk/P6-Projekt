package utilities;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.utilities.ShapeBorder;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShapeBorderTest {

    @Nested
    @DisplayName("ShapeBorder.getBorder")
    class GetBorder {
        @Test
        public void getBorder_returnsPointsOnBorder(){
            Ellipse2D circle = new Ellipse2D.Double(4.5, 4.5, 1, 1);

            ArrayList<Point2D> border = ShapeBorder.getBorder(circle);

            assertTrue(border.contains(new Point2D.Double(4.5, 5)));
            assertTrue(border.contains(new Point2D.Double(5, 4.5)));
            assertTrue(border.contains(new Point2D.Double(5, 5.5)));
            assertTrue(border.contains(new Point2D.Double(5.5, 5)));
        }

        @Test
        public void getBorder_doesNotContainDuplicates(){
            Ellipse2D circle = new Ellipse2D.Double(4.5, 4.5, 1, 1);

            ArrayList<Point2D> border = ShapeBorder.getBorder(circle);

            // A set does not contain duplicates
            Set<Point2D> uniqueSet = new HashSet<>(border);

            // If the set and the pointList are the same length then there must not be any duplicates
            assertEquals(uniqueSet.size(), border.size());
        }

        // Points with coordinates (0,0) seem to appear in the list, these should not be returned
        @Test
        public void getBorder_borderDoesNotContainUnwantedZeros(){
            Ellipse2D circle = new Ellipse2D.Double(4.5, 4.5, 1, 1);

            ArrayList<Point2D> border = ShapeBorder.getBorder(circle);

            assertFalse(border.contains(new Point2D.Double(0, 0)));
        }

        @Test
        public void getBorder_borderContainsExpectedZeros(){
            Ellipse2D circle = new Ellipse2D.Double(-0.5, 0, 1, 1);

            ArrayList<Point2D> border = ShapeBorder.getBorder(circle);

            assertTrue(border.contains(new Point2D.Double(0, 0)));
            assertTrue(border.contains(new Point2D.Double(0, 1)));
            assertTrue(border.contains(new Point2D.Double(-0.5, 0.5)));
            assertTrue(border.contains(new Point2D.Double(0.5, 0.5)));
        }
    }

}
