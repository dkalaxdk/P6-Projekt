package Geometry;

import Dibbidut.Classes.Geometry.Transformation;
import Dibbidut.Classes.Geometry.HPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HPointTest {
    HPoint HPoint;

    @BeforeEach
    public void setUp() {
        HPoint = new HPoint(5,5,1);
    }

    @Nested
    @DisplayName("HPoint.crossProduct")
    public class crossProduct {

        @Test
        public void crossProduct_returns_correct_Number() {
            HPoint testHPoint = new HPoint(1, 2, 1);

            assertEquals(5, HPoint.crossProduct(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.dotProduct")
    class dotProduct {
        @Test
        public void dotProduct_returns_Number() {
            HPoint testHPoint = new HPoint(3,3,1);
            assertNotEquals(0, HPoint.dotProduct(testHPoint));
        }

        @Test
        public void dotProduct_returns_Number_correct_value() {
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(30, HPoint.dotProduct(testHPoint));
        }
    }

    @Nested
    @DisplayName("HPoint.length")
    class length {
        @Test
        public void length_ReturnsCorrectValue(){
            HPoint HPoint = new HPoint(1, 0);
            assertEquals(1, HPoint.length());
        }
    }

    @Nested
    @DisplayName("HPoint.angle")
    class angle{
        @Test
        public void angle_ReturnsCorrectAngle(){
            HPoint HPoint1 = new HPoint(1, 0);
            HPoint HPoint2 = new HPoint(0,1);

            assertEquals(Math.toRadians(90), HPoint1.angle(HPoint2));
        }

        @Test
        public void angle_returns_correct_number(){
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(0, HPoint.angle(testHPoint));
        }
    }


    @Nested
    @DisplayName("HPoint.scale")
    public class scale {
        @Test
        public void scale_length_is_scaled() {
            double startingValue = HPoint.length();
            // Act
            HPoint.scale(2);
            assertEquals(startingValue*2, HPoint.length());
        }

        @Test
        public void scale_x_is_scaled() {
            double startingValue = HPoint.getX();
            // Act
            HPoint.scale(2);
            assertEquals(startingValue*2, HPoint.getX());
        }

        @Test
        public void scale_y_is_scaled() {
            double startingValue = HPoint.getY();
            // Act
            HPoint.scale(2);
            assertEquals(startingValue*2, HPoint.getY());
        }

        @Test
        public void scale_z_is_scaled() {
            HPoint.scale(2);
            assertEquals(1, HPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.divide")
    public class divide {
        @Test
        public void divide_length_is_divided() {
            double startingValue = HPoint.length();
            // Act
            HPoint.divide(2);
            assertEquals(startingValue/2, HPoint.length());
        }

        @Test
        public void divide_x_is_divided() {
            double startingValue = HPoint.getX();
            // Act
            HPoint.divide(2);
            assertEquals(startingValue/2, HPoint.getX());
        }

        @Test
        public void divide_y_is_divided() {
            double startingValue = HPoint.getY();
            // Act
            HPoint.divide(2);
            assertEquals(startingValue/2, HPoint.getY());
        }

        @Test
        public void divide_z_is_divided() {
            HPoint.divide(2);
            assertEquals(1, HPoint.getZ());
        }
    }

    @Nested
    @DisplayName("HPoint.add")
    class add{
        @Test
        public void add_returns_correct_HPoint_x_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = HPoint.add(testHPoint);

            // Assert
            assertEquals(resultHPoint.getX(),7);
        }

        @Test
        public void add_returns_correct_HPoint_y_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = HPoint.add(testHPoint);

            // Assert
            assertEquals(resultHPoint.getY(),7);
        }

        @Test
        public void add_returns_correct_HPoint_z_correct() {
            HPoint testHPoint = new HPoint(2,2,1);

            //Act
            HPoint resultHPoint = HPoint.add(testHPoint);

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
            HPoint resultHPoint = HPoint.subtract(testHPoint);

            assertEquals(5 - 8, resultHPoint.getX());
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_y_correct() {
            HPoint testHPoint = new HPoint(8, 8, 1);

            // Act
            HPoint resultHPoint = HPoint.subtract(testHPoint);

            assertEquals(5 - 8, resultHPoint.getX());
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_z_correct() {
            HPoint testHPoint = new HPoint(8, 8, 1);

            // Act
            HPoint resultHPoint = HPoint.subtract(testHPoint);

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
            HPoint HPoint1 = new HPoint(-1, 0, 1);

            assertEquals(0, HPoint1.compareTo(HPoint1));
        }

        @Test
        public void compareTo_ReturnsPositiveWhenComparingToHPointCloserToStartingPoint(){
            HPoint HPoint1 = new HPoint(0, 1, 1);
            HPoint HPoint2 = new HPoint(-1, 1, 1);

            assertTrue(HPoint1.compareTo(HPoint2) > 0);
        }

        @Test
        public void compareTo_ReturnsNegativeWhenComparingToHPointFartherFromStartingPoint(){
            HPoint HPoint1 = new HPoint(0, 1, 1);
            HPoint HPoint2 = new HPoint(-1, 1, 1);

            assertTrue(HPoint2.compareTo(HPoint1) < 0);
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
}
