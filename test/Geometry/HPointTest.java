package Geometry;

import DSDLVO.Classes.Geometry.Transformation;
import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Utilities.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class HPointTest {
    HPoint hPoint;

    @BeforeEach
    public void setUp() {
        hPoint = new HPoint(5,5,1);
    }

    @Nested
    @DisplayName("HPoint.crossProduct")
    public class crossProduct {

        @Test
        public void crossProduct_returns_correct_Number() {
            HPoint testHPoint = new HPoint(1, 2, 1);

            assertEquals(5, hPoint.crossProduct(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.dotProduct")
    class dotProduct {
        @Test
        public void dotProduct_returns_Number() {
            HPoint testHPoint = new HPoint(3,3,1);
            assertNotEquals(0, hPoint.dotProduct(testHPoint));
        }

        @Test
        public void dotProduct_returns_Number_correct_value() {
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(30, hPoint.dotProduct(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.length")
    class length {
        @Test
        public void length_ReturnsCorrectValue(){
            HPoint point = new HPoint(1, 0);
            assertEquals(1, point.length());
        }
    }

    @Nested
    @DisplayName("HPoint.angle")
    class angle{
        @Test
        public void angle_ReturnsCorrectAngle(){
            HPoint point1 = new HPoint(1, 0);
            HPoint point2 = new HPoint(0,1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(90)),
                    Utility.roundToFourDecimals(point1.angle(point2)));
        }

        @Test
        public void angle_ReturnsCorrectAngle_ReverseOrder(){
            HPoint point1 = new HPoint(1, 0);
            HPoint point2 = new HPoint(0,1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(90)),
                    Utility.roundToFourDecimals(point2.angle(point1)));
        }

        @Test
        public void angle_returns_correct_number(){
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(0, hPoint.angle(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.clockwiseAngle")
    class clockwiseAngle{
        @Test
        public void clockwiseAngle_ReturnsCorrectAngle_clockwise(){
            HPoint point1 = new HPoint(0, 1);
            HPoint point2 = new HPoint(1,0);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(270)),
                    Utility.roundToFourDecimals(point1.counterClockwiseAngle(point2)));
        }

        @Test
        public void clockwiseAngle_ReturnsCorrectAngle_counterclockwise(){
            HPoint point1 = new HPoint(1, 0);
            HPoint point2 = new HPoint(0,1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(90)),
                    Utility.roundToFourDecimals(point1.counterClockwiseAngle(point2)));
        }

        @Test
        public void clockwiseAngle_ReturnsCorrectAngle_180Degrees(){
            HPoint point1 = new HPoint(0, -1);
            HPoint point2 = new HPoint(0,1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(180)),
                    Utility.roundToFourDecimals(point1.counterClockwiseAngle(point2)));
        }

        @Test
        public void clockwiseAngle_returns_correct_number(){
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(0, hPoint.counterClockwiseAngle(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.standardClockwiseAngle")
    class standardClockwiseAngle{
        @Test
        public void standardClockwiseAngle_ReturnsCorrectAngle_90Degrees(){
            HPoint point = new HPoint(0, 1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(90)),
                    Utility.roundToFourDecimals(point.standardCounterClockwiseAngle()));
        }

        @Test
        public void standardClockwiseAngle_ReturnsCorrectAngle_270Degrees(){
            HPoint point = new HPoint(0,-1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(270)),
                    Utility.roundToFourDecimals(point.standardCounterClockwiseAngle()));
        }

        @Test
        public void standardClockwiseAngle_ReturnsCorrectAngle_45Degrees(){
            HPoint point = new HPoint(1, 1);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(45)),
                    Utility.roundToFourDecimals(point.standardCounterClockwiseAngle()));
        }

        @Test
        public void standardClockwiseAngle_ReturnsCorrectAngle_180Degrees(){
            HPoint point = new HPoint(-1,0);

            assertEquals(Utility.roundToFourDecimals(Math.toRadians(180)),
                    Utility.roundToFourDecimals(point.standardCounterClockwiseAngle()));
        }
    }

    @Nested
    @DisplayName("HPoint.orientation")
    class orientation{
        @Test
        public void orientation_ReturnsZeroWhenPointsAreOnALine(){
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(3, 3, 1);

            assertEquals(0, a.orientation(p1, p2));
        }

        @Test
        public void orientation_ReturnsPositiveValueWhenLeftTurnOrientation(){
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 0, 1);
            HPoint p2 = new HPoint(2, 2, 1);

            assertTrue(a.orientation(p1, p2) > 0);
        }

        @Test
        public void orientation_ReturnsNegativeValueWhenRightTurnOrientation(){
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(a.orientation(p1, p2) < 0);
        }
    }


    @Nested
    @DisplayName("HPoint.scale")
    public class scale {
        @Test
        public void scale_length_is_scaled() {
            double startingValue = hPoint.length();
            // Act
            hPoint.scale(2);
            assertEquals(startingValue*2, hPoint.length());
        }

        @Test
        public void scale_x_is_scaled() {
            double startingValue = hPoint.getX();
            // Act
            hPoint.scale(2);
            assertEquals(startingValue*2, hPoint.getX());
        }

        @Test
        public void scale_y_is_scaled() {
            double startingValue = hPoint.getY();
            // Act
            hPoint.scale(2);
            assertEquals(startingValue*2, hPoint.getY());
        }

        @Test
        public void scale_z_is_scaled() {
            hPoint.scale(2);
            assertEquals(1, hPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.divide")
    public class divide {
        @Test
        public void divide_length_is_divided() {
            double startingValue = hPoint.length();
            // Act
            hPoint.divide(2);
            assertEquals(startingValue/2, hPoint.length());
        }

        @Test
        public void divide_x_is_divided() {
            double startingValue = hPoint.getX();
            // Act
            hPoint.divide(2);
            assertEquals(startingValue/2, hPoint.getX());
        }

        @Test
        public void divide_y_is_divided() {
            double startingValue = hPoint.getY();
            // Act
            hPoint.divide(2);
            assertEquals(startingValue/2, hPoint.getY());
        }

        @Test
        public void divide_z_is_divided() {
            hPoint.divide(2);
            assertEquals(1, hPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.add")
    class add{
        @Test
        public void add_returns_correct_HPoint_x_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = hPoint.add(testHPoint);

            // Assert
            assertEquals(resultHPoint.getX(),7);
        }

        @Test
        public void add_returns_correct_HPoint_y_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = hPoint.add(testHPoint);

            // Assert
            assertEquals(resultHPoint.getY(),7);
        }

        @Test
        public void add_returns_correct_HPoint_z_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = hPoint.add(testHPoint);

            // Assert
            assertEquals(1, resultHPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.subtract")
    public class subtract {
        @Test
        public void subtractHPoint_returns_correct_HPoint_x_correct() {
            HPoint testHPoint = new HPoint(8, 8, 1);

            // Act
            HPoint resultHPoint = hPoint.subtract(testHPoint);

            assertEquals(5 - 8, resultHPoint.getX());
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_y_correct() {
            HPoint testHPoint = new HPoint(8, 8, 1);

            // Act
            HPoint resultHPoint = hPoint.subtract(testHPoint);

            assertEquals(5 - 8, resultHPoint.getX());
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_z_correct() {
            HPoint testHPoint = new HPoint(8, 8, 1);

            // Act
            HPoint resultHPoint = hPoint.subtract(testHPoint);

            assertEquals(1, resultHPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.transform")
    public class Transform {
        @Test
        public void transform_ScalesHPoint() {
            HPoint expected = new HPoint(2, 3, 1);
            HPoint testHPoint = new HPoint(1, 1, 1);
            Transformation t = new Transformation();

            t.scale(2, 3);
            testHPoint.transform(t);

            assertEquals(expected, testHPoint);
        }

        @Test
        public void transform_RotatesHPoint() {
            HPoint expected = new HPoint(4, -4, 1);
            HPoint testHPoint = new HPoint(4, 4, 1);
            Transformation t = new Transformation();

            t.rotate(90);
            testHPoint.transform(t);

            assertEquals(expected, testHPoint);
        }

        @Test
        public void transform_TranslatesHPoint() {
            HPoint expected = new HPoint(3, 4, 1);
            HPoint testHPoint = new HPoint(1, 1, 1);
            Transformation t = new Transformation();

            t.translate(2, 3);
            testHPoint.transform(t);

            assertEquals(expected, testHPoint);
        }
    }

    @Nested
    @DisplayName("HPoint.compareTo")
    class compareTo{
        @Test
        public void compareTo_ReturnsZeroWhenHPointComparedToItSelf(){
            HPoint point1 = new HPoint(-1, 0, 1);

            assertEquals(0, point1.compareTo(point1));
        }

        @Test
        public void compareTo_ReturnsPositiveWhenComparingToHPointCloserToStartingPoint(){
            HPoint point1 = new HPoint(-1, 1);
            HPoint point2 = new HPoint(0, 1);

            assertTrue(point1.compareTo(point2) > 0);
        }

        @Test
        public void compareTo_ReturnsNegativeWhenComparingToHPointFartherFromStartingPoint(){
            HPoint point1 = new HPoint(-1, 1);
            HPoint point2 = new HPoint(0, 1);

            assertTrue(point2.compareTo(point1) < 0);
        }

        @Test
        public void compareTo_SortsListCorrectly(){
            ArrayList<HPoint> list = new ArrayList<>();
            list.add(new HPoint(2, 2));
            list.add(new HPoint(3, -3));
            list.add(new HPoint(-1, 1));

            Collections.sort(list);

            assertEquals(2, list.get(0).getX());
            assertEquals(-1, list.get(1).getX());
            assertEquals(3, list.get(2).getX());
        }
    }

    @Nested
    @DisplayName("HPoint.copy")
    class copy{
        @Test
        public void copy_ReturnsNewInstanceWithSameValues(){
            HPoint original = new HPoint(-83, 237, 903.66);
            HPoint copy = original.copy();

            assertEquals(original, copy);
            assertNotSame(original, copy);
        }
    }

    @Nested
    @DisplayName("HPoint.distance")
    class distance{
        @Test
        public void distance_returnsZeroWhenGivenPointInSamePlace(){
            HPoint p1 = new HPoint(5.5, 3.3);
            HPoint p2 = new HPoint(5.5, 3.3);

            assertEquals(0, p1.distance(p2));
        }

        @Test
        public void distance_returnsCorrectDistanceWhenPointsHaveSameXValue(){
            HPoint p1 = new HPoint(5.5, 3);
            HPoint p2 = new HPoint(5.5, 6);

            assertEquals(3, p1.distance(p2));
        }

        @Test
        public void distance_returnsCorrectDistanceWhenPointsHaveSameYValue(){
            HPoint p1 = new HPoint(2.7, 3.3);
            HPoint p2 = new HPoint(5.5, 3.3);

            assertEquals(2.8, p1.distance(p2));
        }

        @Test
        public void distance_returnsCorrectDistanceWhenPointsHaveNoValuesInCommon(){
            HPoint p1 = new HPoint(8.9, 55);
            HPoint p2 = new HPoint(55, 3.3);

            assertEquals(69.26831887666974, p1.distance(p2));
        }
    }

    @Nested
    @DisplayName("HPoint.getUnitVector")
    public class getUnitVector {
        @Test
        public void getUnitVector_returnsVectorWithLengthOne() {
            double expected = 1;
            HPoint vec = new HPoint(8, -3.4);
            double actual = vec.getUnitVector().length();
            assertEquals(expected, actual);
        }

        @Test
        public void getUnitVector_returnsUnitVectorForYVector() {
            HPoint expected = new HPoint(0, 1);
            HPoint actual = new HPoint(0,987.2).getUnitVector();
            assertEquals(expected, actual);
        }

        @Test
        public void getUnitVector_returnsUnitVectorForXVector() {
            HPoint expected = new HPoint(-1, 0);
            HPoint actual = new HPoint(-3.41,0).getUnitVector();
            assertEquals(expected, actual);
        }

        @Test
        public void getUnitVector_returnsUnitVectorForXYVector() {
            HPoint expected = new HPoint(0.7071067811865475, 0.7071067811865475);
            HPoint actual = new HPoint(10,10).getUnitVector();
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("HPoint.roundElements")
    public class roundElements {
        @Test
        public void roundElements_roundsElementsToFourDecimals() {
            HPoint expected = new HPoint(0.7071, -0.7071);
            HPoint vec = new HPoint(0.7071067811865475, -0.7071067811865475);

            HPoint actual = vec.roundElements();

            assertEquals(expected, actual);
        }
    }
}
