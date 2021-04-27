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
        public void length_ReturnsCorrestValue(){
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
    }


    @Nested
    public class Transformations {
        @Test
        public void scaleProduct_length_is_scaled() {
            double startingValue = HPoint.length();
            // Act
            HPoint.scaleProduct(2);
            assertEquals(startingValue*2, HPoint.length());
        }

        @Test
        public void scaleProduct_x_is_scaled() {
            double startingValue = HPoint.getX();
            // Act
            HPoint.scaleProduct(2);
            assertEquals(startingValue*2, HPoint.getX());
        }

        @Test
        public void scaleProduct_y_is_scaled() {
            double startingValue = HPoint.getY();
            // Act
            HPoint.scaleProduct(2);
            assertEquals(startingValue*2, HPoint.getY());
        }
    }

    @Nested
    public class Operators {
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
            assertEquals(resultHPoint.getZ(),2);
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_x_correct() {
            HPoint testHPoint = new HPoint(8,8,1);

            // Act
            HPoint resultHPoint = HPoint.subtract(testHPoint);

            assertEquals(resultHPoint.getX(),5-8);
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_y_correct() {
            HPoint testHPoint = new HPoint(8,8,1);

            // Act
            HPoint resultHPoint = HPoint.subtract(testHPoint);

            assertEquals(resultHPoint.getX(),5-8);
        }

        @Test
        public void subtractHPoint_returns_correct_HPoint_z_correct() {
            HPoint testHPoint = new HPoint(8,8,1);

            // Act
            HPoint resultHPoint = HPoint.subtract(testHPoint);

            assertEquals(resultHPoint.getZ(),0);
        }




        @Test
        public void divideScalar_x_correct() {
            double scalar = 5;
            // Act
            HPoint.divideProduct(scalar);

            assertEquals(HPoint.getX(),1);
        }

        @Test
        public void divideScalar_y_correct() {
            double scalar = 5;
            // Act
            HPoint.divideProduct(scalar);

            assertEquals(HPoint.getY(),1);
        }

        @Test
        public void divideScalar_z_correct() {
            double scalar = 5;
            // Act
            HPoint.divideProduct(scalar);

            assertEquals(HPoint.getY(),1);
        }
    }

    @Nested
    public class HPointInformation {
        @Test
        public void getMagnitude_returns_number(){
            assertNotEquals(HPoint.length(),0);
        }

        @Test
        public void getMagnitude_returns_number_is_correct(){
            assertEquals(HPoint.length(),7.14142842854285);
        }

        @Test
        public void angle_returns_correct_number(){
            HPoint testHPoint = new HPoint(3,3,1);
            assertEquals(HPoint.angle(testHPoint),0.09098766221666083);
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
}
