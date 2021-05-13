package Geometry;

import Dibbidut.Classes.Geometry.Transformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationTest {

    protected static void assertMatrixEquals(double[][] expected, double [][] actual) {
        assertArrayEquals(expected[0], actual[0], "Column one");
        assertArrayEquals(expected[1], actual[1], "Column two");
        assertArrayEquals(expected[2], actual[2], "Column three");
    }

    @Nested
    @DisplayName("Transformation Constructor")
    public class Constructor {
        @Test
        public void Constructor_defaultCreatesIdentityMatrix() {
            double [][] expected = {
                    {1, 0, 0}, {0, 1, 0}, {0, 0, 1}
            };

            Transformation t = new Transformation();

            assertMatrixEquals(expected, t.get());
        }

        @Test
        public void Constructor_givenTransformationCopiesMatrix() {
            Transformation expected = new Transformation();
            expected.translate(2, 3);
            expected.rotate(90);

            Transformation t = new Transformation(expected);

            assertMatrixEquals(expected.get(), t.get());
        }
    }

    @Nested
    @DisplayName("Transformation.rotate")
    public class Rotate {
        @Test
        public void rotate_createsTransformForClockwiseRotation() {
            Transformation t = new Transformation();
            double angle = 90;
            /*
                 0 1 0
                -1 0 0
                 0 0 1
             */
            double[][] expected = {
                    {0, -1, 0}, {1, 0, 0}, {0, 0, 1}
            };

            t.rotate(angle);

            assertMatrixEquals(expected, t.get());
        }

        @Test
        public void rotate_createsTransformForCounterclockwiseRotation() {
            Transformation t = new Transformation();
            double angle = -90;
            /*
                 0 -1 0
                 1  0 0
                 0  0 1
             */
            double[][] expected = {
                    {0, 1, 0}, {-1, 0, 0}, {0, 0, 1}
            };

            t.rotate(angle);

            assertMatrixEquals(expected, t.get());
        }
    }

    @Nested
    @DisplayName("Transformation.scale")
    public class Scale {
        @Test
        public void scale_createsTransformForScaling() {
            Transformation t = new Transformation();
            double width = 2;
            double height = 3;
            double [][] expected = {
                    {width, 0, 0}, {0, height, 0}, {0, 0, 1}
            };

            t.scale(width, height);

            assertMatrixEquals(expected, t.get());
        }
    }

    @Nested
    @DisplayName("Transformation.translate")
    public class Translate {
        @Test
        public void translate_createsTransformForTranslation() {
            Transformation t = new Transformation();
            double x = 2;
            double y = 3;
            double [][] expected = {
                    {1, 0, 0}, {0, 1, 0}, {x, y, 1}
            };

            t.translate(x, y);

            assertMatrixEquals(expected, t.get());
        }
    }

    @Nested
    @DisplayName("Applying multiple transformations")
    public class multipleTransforms {
        @Test
        public void Transformation_correctlyChainsRotateThenTranslate() {
            double [][] expected = {
                    {0, -1, 0}, {1, 0, 0}, {2, 3, 1}
            };

            Transformation t = new Transformation();
            t.rotate(90);
            t.translate(2, 3);

            assertMatrixEquals(expected, t.get());
        }

        @Test
        public void Transformation_correctlyChainsTranslateThenRotate() {
            double [][] expected = {
                    {0, -1, 0}, {1, 0, 0}, {3, -2, 1}
            };

            Transformation t = new Transformation();
            t.translate(2, 3);
            t.rotate(90);

            assertMatrixEquals(expected, t.get());
        }
    }

    @Nested
    @DisplayName("Transformation.Add")
    public class add {
        @Test
        public void Add_correctlyAddAnotherTransformation() {
            double [][] expected = {
                    {0, -1, 0}, {1, 0, 0}, {2, 3, 1}
            };

            Transformation t = new Transformation();
            t.rotate(90);
            Transformation other = new Transformation();
            other.translate(2, 3);

            t.add(other);

            assertMatrixEquals(expected, t.get());
        }

        @Test
        public void Add_emptyTransformDoesNotChangeMatrix() {
            double [][] expected = {
                    {1, 0, 0}, {0, 1, 0}, {0, 0, 1}
            };

            Transformation t = new Transformation();
            t.add(new Transformation());

            assertMatrixEquals(expected, t.get());
        }
    }
}
