package Geometry;

import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Geometry.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonTest {
    @Nested
    @DisplayName("Polygon.transform")
    class transform{
        @Test
        public void transform_rotatesPolygonAroundItsCenter(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.rotate(90);

            assertEquals(1, polygon.coordinates.get(0).getX());
            assertEquals(3, polygon.coordinates.get(0).getY());

            assertEquals(3, polygon.coordinates.get(1).getX());
            assertEquals(3, polygon.coordinates.get(1).getY());

            assertEquals(3, polygon.coordinates.get(2).getX());
            assertEquals(1, polygon.coordinates.get(2).getY());

            assertEquals(1, polygon.coordinates.get(3).getX());
            assertEquals(1, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_CenterTheSameAfterRotation(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.rotate(90);

            assertEquals(2, polygon.center.getX());
            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void transform_scalesPolygonFromItsCenter(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.scale(2, 2);

            assertEquals(0, polygon.coordinates.get(0).getX());
            assertEquals(0, polygon.coordinates.get(0).getY());

            assertEquals(0, polygon.coordinates.get(1).getX());
            assertEquals(4, polygon.coordinates.get(1).getY());

            assertEquals(4, polygon.coordinates.get(2).getX());
            assertEquals(4, polygon.coordinates.get(2).getY());

            assertEquals(4, polygon.coordinates.get(3).getX());
            assertEquals(0, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_CenterTheSameAfterScaling(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.scale(2, 2);

            assertEquals(2, polygon.center.getX());
            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void transform_translatesPolygon(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.translate(1, 1);

            assertEquals(2, polygon.coordinates.get(0).getX());
            assertEquals(2, polygon.coordinates.get(0).getY());

            assertEquals(2, polygon.coordinates.get(1).getX());
            assertEquals(4, polygon.coordinates.get(1).getY());

            assertEquals(4, polygon.coordinates.get(2).getX());
            assertEquals(4, polygon.coordinates.get(2).getY());

            assertEquals(4, polygon.coordinates.get(3).getX());
            assertEquals(2, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_CenterCorrectAfterTranslation(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.translate(1, 1);

            assertEquals(3, polygon.center.getX());
            assertEquals(3, polygon.center.getY());
        }
    }

    @Nested
    @DisplayName("Polygon.setCenter")
    class findCenter{
        @Test
        public void setCenter_SetCenterXCorrectly(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.center.getX());
        }

        @Test
        public void setCenter_SetCenterYCorrectly(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void setCenter_centerSetToNullIfPointsListIsEmpty(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(null, polygon.center);
        }
    }

    @Nested
    @DisplayName("Polygon.matrixVectorProduct")
    class matrixVectorProduct{
        @Test
        public void matrixVectorProduct_returnsCorrectVector(){
            double [][] matrix = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            Vector vector = new Vector(1, 2, 3);
            Polygon polygon = new Polygon(new ArrayList());

            Vector result = polygon.matrixVectorProduct(matrix, vector);

            assertEquals(30, result.getX());
            assertEquals(36, result.getY());
            assertEquals(42, result.getZ());

        }
    }

    @Nested
    @DisplayName("Polygon.translatePolygon")
    class translatePolygon{
        @Test
        public void translatePolygon_translatesCorrectlyToOrigin(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            ArrayList<Vector> result = polygon.translatePolygon(points, new double[][] {{1, 0, 0}, {0, 1, 0}, {-polygon.center.getX(), -polygon.center.getY(), 1}});

            assertEquals(-1, result.get(0).getX());
            assertEquals(-1, result.get(0).getY());

            assertEquals(-1, result.get(1).getX());
            assertEquals(1, result.get(1).getY());

            assertEquals(1, result.get(2).getX());
            assertEquals(1, result.get(2).getY());

            assertEquals(1, result.get(3).getX());
            assertEquals(-1, result.get(3).getY());

        }
    }

    @Nested
    @DisplayName("Polygon.contains")
    class contains{
        @Test
        public void contains_ReturnsTrueWhenPolygonContainsPoint(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            Vector point = new Vector(2, 2, 1);

            assertTrue(polygon.contains(point));
        }

        @Test
        public void contains_ReturnsFalseWhenPolygonDoesNotContainPoint(){
            ArrayList<Vector> points = new ArrayList<Vector>();
            points.add(new Vector(1, 1, 1));
            points.add(new Vector(1, 3, 1));
            points.add(new Vector(3, 3, 1));
            points.add(new Vector(3, 1, 1));
            Polygon polygon = new Polygon(points);

            Vector point = new Vector(4, 4, 1);

            assertTrue(!polygon.contains(point));
        }
    }
}
