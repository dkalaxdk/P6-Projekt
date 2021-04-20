package Geometry;

import Dibbidut.Classes.Geometry.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VectorTest {
    Vector vector;

    @BeforeEach
    public void setUp() {
        vector = new Vector(5,5);
    }

    @Nested
    public class Products {
        //TODO Evt slet det udkommenterede.
        /*        @Test
        public void crossProduct_returns_Number() {
            Vector testVector = new Vector(2,2);
            assertNotEquals(vector.crossProduct(testVector),0);
        }

        @Test
        public void crossProduct_returns_correct_Number() {
            Vector testVector = new Vector(2,2);
            assertEquals(vector.crossProduct(testVector),18.258905014552557);
        }*/

        @Test
        public void dotProduct_returns_Number() {
            Vector testVector = new Vector(3,3);
            assertNotEquals(vector.dotProduct(testVector),0);
        }

        @Test
        public void dotProduct_returns_Number_correct_value() {
            Vector testVector = new Vector(3,3);
            assertEquals(vector.dotProduct(testVector),30);
        }
    }


    @Nested
    public class Transformations {
        @Test
        public void scaleProduct_length_is_scaled() {
            double startingValue = vector.length();
            // Act
            vector.scaleProduct(2);
            assertEquals(startingValue*2,vector.length());
        }

        @Test
        public void scaleProduct_x_is_scaled() {
            double startingValue = vector.getX();
            // Act
            vector.scaleProduct(2);
            assertEquals(startingValue*2,vector.getX());
        }

        @Test
        public void scaleProduct_y_is_scaled() {
            double startingValue = vector.getY();
            // Act
            vector.scaleProduct(2);
            assertEquals(startingValue*2,vector.getY());
        }
    }

    @Nested
    public class Operators {
        @Test
        public void addVector_returns_correct_vector_x_correct() {
            Vector testVector = new Vector(2,2);

            //Act
            Vector resultVector = vector.addVector(testVector);

            // Assert
            assertEquals(resultVector.getX(),7);
        }

        @Test
        public void addVector_returns_correct_vector_y_correct() {
            Vector testVector = new Vector(2,2);

            //Act
            Vector resultVector = vector.addVector(testVector);

            // Assert
            assertEquals(resultVector.getY(),7);
        }

        @Test
        public void subtractVector_returns_correct_vector_y_correct() {
            Vector testVector = new Vector(8,8);

            // Act
            Vector resultVector = vector.subtractVector(testVector);

            assertEquals(resultVector.getX(),5-8);
        }

        @Test
        public void subtractVector_returns_correct_vector_x_correct() {
            Vector testVector = new Vector(8,8);

            // Act
            Vector resultVector = vector.subtractVector(testVector);

            assertEquals(resultVector.getX(),5-8);
        }


        @Test
        public void divideScalar_x_correct() {
            double scalar = 5;
            // Act
            vector.divideProduct(scalar);

            assertEquals(vector.getX(),1);
        }

        @Test
        public void divideScalar_y_correct() {
            double scalar = 5;
            // Act
            vector.divideProduct(scalar);

            assertEquals(vector.getY(),1);
        }
    }

    @Nested
    public class VectorInformation {
        @Test
        public void getMagnitude_returns_number(){
            assertNotEquals(vector.length(),0);
        }

        @Test
        public void getMagnitude_returns_number_is_correct(){
            assertEquals(vector.length(),7.0710678118654755);
        }

        @Test
        public void angle_returns_correct_number(){
            Vector testVector = new Vector(3,3);
            assertEquals(vector.angle(testVector),0);
        }
    }
}
