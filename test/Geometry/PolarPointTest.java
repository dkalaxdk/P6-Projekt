package Geometry;

import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Geometry.PolarPoint;
import DSDLVO.Utilities.Utility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PolarPointTest {

    @Nested
    @DisplayName("PolarPoint.transform")
    class transform{
        @Test
        public void transform_RotatesPointCorrectly(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));

            point.rotate(-90);

            assertEquals(1, point.length);
            assertEquals(Utility.roundToFourDecimals(Math.toRadians(180)), Utility.roundToFourDecimals(point.angle));
        }

        @Test
        public void transform_scalePointCorrectly(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));

            point.scale(2,2);

            assertEquals(2, point.length);
            assertEquals(Utility.roundToFourDecimals(Math.toRadians(90)), Utility.roundToFourDecimals(point.angle));
        }

        @Test
        public void transform_TranslatePointCorrectly_LengthIsChanged(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));

            point.translate(0,1);

            assertEquals(2, point.length);
            assertEquals(Math.toRadians(90), point.angle);
        }

        @Test
        public void transform_TranslatePointCorrectly_LengthAndAngleAreChanged(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));

            point.translate(1,1);

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(Math.pow(1, 2) + Math.pow(2, 2))),
                    Utility.roundToFourDecimals(point.length));
            assertEquals(Utility.roundToFourDecimals(Math.atan(2/1)), Utility.roundToFourDecimals(point.angle));
        }
    }

    @Nested
    @DisplayName("PolarPoint.contains")
    class contains{
        @Test
        public void contains_ReturnsTrueWhenSamePoint(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));

            assertTrue(point.contains(point.toHPoint()));
        }

        @Test
        public void contains_ReturnsFalseWhenNotSameLength(){
            PolarPoint point1 = new PolarPoint(1, Math.toRadians(90));
            PolarPoint point2 = new PolarPoint(2, Math.toRadians(90));

            assertFalse(point1.contains(point2.toHPoint()));
        }

        @Test
        public void contains_ReturnsFalseWhenNotSameAngle(){
            PolarPoint point1 = new PolarPoint(1, Math.toRadians(90));
            PolarPoint point2 = new PolarPoint(1, Math.toRadians(45));

            assertFalse(point1.contains(point2.toHPoint()));
        }
    }

    @Nested
    @DisplayName("PolarPoint.compareTo")
    class compareTo{
        @Test
        public void compareTo_ReturnsPositiveWhenBiggerThanComparedTo(){
            PolarPoint point1 = new PolarPoint(1, Math.toRadians(90));
            PolarPoint point2 = new PolarPoint(1, Math.toRadians(45));

            assertTrue(point1.compareTo(point2) > 0);
        }

        @Test
        public void compareTo_ReturnsNegativeWhenSmallerThanComparedTo(){
            PolarPoint point1 = new PolarPoint(1, Math.toRadians(90));
            PolarPoint point2 = new PolarPoint(1, Math.toRadians(45));

            assertTrue(point2.compareTo(point1) < 0);
        }

        @Test
        public void compareTo_ReturnsZeroWhenSameAngle(){
            PolarPoint point1 = new PolarPoint(1, Math.toRadians(90));
            PolarPoint point2 = new PolarPoint(1, Math.toRadians(90));

            assertTrue(point2.compareTo(point1) == 0);
        }
    }

    @Nested
    @DisplayName("PolarPoint.toHPoint")
    class toHPoint{
        @Test
        public void toHPoint_ReturnsCorrectHPoint(){
            PolarPoint point = new PolarPoint(1, Math.toRadians(90));
            HPoint hPoint = point.toHPoint();

            assertEquals( Utility.roundToFourDecimals(0), Utility.roundToFourDecimals(hPoint.getX()));
            assertEquals(Utility.roundToFourDecimals(1), Utility.roundToFourDecimals(hPoint.getY()));
        }
    }
}
