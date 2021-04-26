package Geometry;

import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.Geometry.Transformation;
import Dibbidut.Classes.Geometry.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeometryTest {
    protected class TestGeometry extends Geometry {
        public double[] point;

        public TestGeometry(double x, double y) {
            point = new double[] {x, y, 1};
        }

        @Override
        public void transform(Transformation transformation) {
            double [][] matrix = transformation.get();
            double[] transformedPoint = new double[3];

            for(int col = 0; col < 3; col++) {
                double res = 0;
                for(int row = 0; row < 3; row++)
                    res += matrix[row][col] * point[row];
                transformedPoint[col] = res;
            }
            point = transformedPoint;
        }

        @Override
        public boolean contains(Vector point) {
            return false;
        }
    }

    @Nested
    @DisplayName("Geometry.scale")
    public class scale{
        @Test
        public void scale_ScalesPoint() {
            TestGeometry geom = new TestGeometry(1, 1);
            double[] expected = new double[] {2, 3, 1};

            geom.scale(2, 3);

            assertArrayEquals(expected, geom.point);
        }
    }

    @Nested
    @DisplayName("Geometry.rotate")
    public class rotate {
        @Test
        public void rotate_RotatesPointCounterClockwise() {
            TestGeometry geom = new TestGeometry(4, 4);
            double[] expected = new double[] {1.464, 5.464, 1};

            geom.rotate(-30);

            assertArrayEquals(expected, geom.point);
        }

        @Test
        public void rotate_RotatesPointClockwise() {
            TestGeometry geom = new TestGeometry(4, 4);
            double[] expected = new double[] {4, -4, 1};

            geom.rotate(90);

            assertArrayEquals(expected, geom.point);
        }
    }

    @Nested
    @DisplayName("Geometry.translate")
    public class translate{
        @Test
        public void translate_translatesPoint() {
            TestGeometry geom = new TestGeometry(1, 4);
            double[] expected = new double[] {6, 5, 1};

            geom.translate(5, 1);

            assertArrayEquals(expected, geom.point);
        }
    }

    @Test
    public void contains_returns_boolean_true() {

    }

    @Test
    public void contains_returns_boolean_false() {

    }
}


