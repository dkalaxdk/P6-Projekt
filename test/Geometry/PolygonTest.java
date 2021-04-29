package Geometry;

import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Geometry.HPoint;
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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.rotate(90);

            assertEquals(2, polygon.center.getX());
            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void transform_scalesPolygonFromItsCenter(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.scale(2, 2);

            assertEquals(2, polygon.center.getX());
            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void transform_translatesPolygon(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.center.getX());
        }

        @Test
        public void setCenter_SetCenterYCorrectly(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.center.getY());
        }

        @Test
        public void setCenter_centerSetToNullIfPointsListIsEmpty(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(null, polygon.center);
        }
    }

    @Nested
    @DisplayName("Polygon.matrixHPointProduct")
    class matrixHPointProduct{
        @Test
        public void matrixHPointProduct_returnsCorrectHPoint(){
            double [][] matrix = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            HPoint point = new HPoint(1, 2, 3);
            Polygon polygon = new Polygon(new ArrayList());

            HPoint result = polygon.matrixHPointProduct(matrix, point);

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
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            ArrayList<HPoint> result = polygon.translatePolygon(points, new double[][] {{1, 0, 0}, {0, 1, 0}, {-polygon.center.getX(), -polygon.center.getY(), 1}});

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
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            HPoint point = new HPoint(2, 2, 1);

            assertTrue(polygon.contains(point));
        }

        @Test
        public void contains_ReturnsFalseWhenPolygonDoesNotContainPoint(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            HPoint point = new HPoint(4, 4, 1);

            assertFalse(polygon.contains(point));
        }

        @Test
        public void contains_ReturnsFalseWhenPointIsOnSegmentOfPolygon(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            HPoint point = new HPoint(3, 2, 1);

            assertFalse(polygon.contains(point));
        }

        @Test
        public void contains_ReturnsFalseWhenPointIsInHPointListForPolygon(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            HPoint point = new HPoint(1, 1, 1);

            assertFalse(polygon.contains(point));
        }
    }

    @Nested
    @DisplayName("Polygon.onSegment")
    class onSegment{
        @Test
        public void onSegment_ReturnsTrueWhenPointIsOnSegment(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint segmentHPoint1 = new HPoint(1, 1, 1);
            HPoint segmentHPoint2 = new HPoint(3, 1, 1);

            HPoint point = new HPoint(2, 1, 1);

            assertTrue(polygon.onSegment(point, segmentHPoint1, segmentHPoint2));
        }

        @Test
        public void onSegment_ReturnsFalseWhenPointIsNotOnSegment(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint segmentHPoint1 = new HPoint(1, 1, 1);
            HPoint segmentHPoint2 = new HPoint(3, 1, 1);

            HPoint point = new HPoint(2, 2, 1);

            assertFalse(polygon.onSegment(point, segmentHPoint1, segmentHPoint2));
        }
    }

    @Nested
    @DisplayName("Polygon.inDisk")
    class inDisk{
        @Test
        public void onSegment_ReturnsTrueWhenPointIsInDisk(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint segmentHPoint1 = new HPoint(1, 1, 1);
            HPoint segmentHPoint2 = new HPoint(3, 1, 1);

            HPoint point = new HPoint(2, 1, 1);

            assertTrue(polygon.inDisk(point, segmentHPoint1, segmentHPoint2));
        }

        @Test
        public void onSegment_ReturnsFalseWhenPointIsNotInDisk(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint segmentHPoint1 = new HPoint(1, 1, 1);
            HPoint segmentHPoint2 = new HPoint(3, 1, 1);

            HPoint point = new HPoint(3, 3, 1);

            assertFalse(polygon.inDisk(point, segmentHPoint1, segmentHPoint2));
        }
    }

    @Nested
    @DisplayName("Polygon.crossesRay")
    class crossesRay{
        @Test
        public void crossesRay_ReturnsTrueWhenRayCrossesSegment(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsTrueWhenRayCrossesSegment_UpperPointInSegmentOnRay(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 1, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsFalseWhenRayDoesNotCrossSegment(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(0, 2, 1);
            HPoint p2 = new HPoint(0, 0, 1);

            assertTrue(!polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsFalseWhenRayCrossesSegment_LowerPointInSegmentOnRay(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 1, 1);

            assertTrue(!polygon.crossesRay(a, p1, p2));
        }
    }

    @Nested
    @DisplayName("Polygon.orientation")
    class orientation{
        @Test
        public void orientation_ReturnsZeroWhenPointsAreOnALine(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(3, 3, 1);

            assertEquals(0, polygon.orientation(a, p1, p2));
        }

        @Test
        public void orientation_ReturnsPositiveValueWhenLeftTurnOrientation(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 0, 1);
            HPoint p2 = new HPoint(2, 2, 1);

            assertTrue(polygon.orientation(a, p1, p2) > 0);
        }

        @Test
        public void orientation_ReturnsNegativeValueWhenRightTurnOrientation(){
            Polygon polygon = new Polygon(new ArrayList<HPoint>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(polygon.orientation(a, p1, p2) < 0);
        }
    }

    @Nested
    @DisplayName("Polygon.addPolygon")
    class addPolygon{
        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingAPolygonToItself(){
            ArrayList<HPoint> points = new ArrayList<HPoint>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            Polygon result = polygon.addPolygon(polygon);

            assertEquals(4, result.coordinates.size());

            assertEquals(0, result.coordinates.get(0).getX());
            assertEquals(0, result.coordinates.get(0).getY());

            assertEquals(0, result.coordinates.get(1).getX());
            assertEquals(4, result.coordinates.get(1).getY());

            assertEquals(4, result.coordinates.get(2).getX());
            assertEquals(4, result.coordinates.get(2).getY());

            assertEquals(4, result.coordinates.get(3).getX());
            assertEquals(0, result.coordinates.get(3).getY());
        }

        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingTwoPolygonsOnTopOfEachOtherWithDifferentOrientations(){
            ArrayList<HPoint> points1 = new ArrayList<HPoint>();
            points1.add(new HPoint(1, 1, 1));
            points1.add(new HPoint(1, 3, 1));
            points1.add(new HPoint(3, 3, 1));
            points1.add(new HPoint(3, 1, 1));
            Polygon polygon1 = new Polygon(points1);

            ArrayList<HPoint> points2 = new ArrayList<HPoint>();
            points2.add(new HPoint(0, 2, 1));
            points2.add(new HPoint(2, 4, 1));
            points2.add(new HPoint(4, 2, 1));
            points2.add(new HPoint(2, 0, 1));
            Polygon polygon2 = new Polygon(points2);

            Polygon result = polygon1.addPolygon(polygon2);

            assertEquals(8, result.coordinates.size());

            assertEquals(0, result.coordinates.get(0).getX());
            assertEquals(0, result.coordinates.get(0).getY());

            assertEquals(-1, result.coordinates.get(1).getX());
            assertEquals(2, result.coordinates.get(1).getY());

            assertEquals(0, result.coordinates.get(2).getX());
            assertEquals(4, result.coordinates.get(2).getY());

            assertEquals(2, result.coordinates.get(3).getX());
            assertEquals(5, result.coordinates.get(3).getY());

            assertEquals(4, result.coordinates.get(4).getX());
            assertEquals(4, result.coordinates.get(4).getY());

            assertEquals(5, result.coordinates.get(5).getX());
            assertEquals(2, result.coordinates.get(5).getY());

            assertEquals(0, result.coordinates.get(6).getX());
            assertEquals(4, result.coordinates.get(6).getY());

            assertEquals(2, result.coordinates.get(7).getX());
            assertEquals(-1, result.coordinates.get(7).getY());
        }

        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingTwoPolygonsNotTouchingEachOtherWithDifferentOrientations(){
            ArrayList<HPoint> points1 = new ArrayList<HPoint>();
            points1.add(new HPoint(1, 1, 1));
            points1.add(new HPoint(1, 3, 1));
            points1.add(new HPoint(3, 3, 1));
            points1.add(new HPoint(3, 1, 1));
            Polygon polygon1 = new Polygon(points1);

            ArrayList<HPoint> points2 = new ArrayList<HPoint>();
            points2.add(new HPoint(4, 4, 1));
            points2.add(new HPoint(5, 5, 1));
            points2.add(new HPoint(6, 4, 1));
            points2.add(new HPoint(5, 3, 1));
            Polygon polygon2 = new Polygon(points2);

            Polygon result = polygon1.addPolygon(polygon2);

            assertEquals(8, result.coordinates.size());

            assertEquals(0.5, result.coordinates.get(0).getX());
            assertEquals(0.5, result.coordinates.get(0).getY());

            assertEquals(0, result.coordinates.get(1).getX());
            assertEquals(2, result.coordinates.get(1).getY());

            assertEquals(0.5, result.coordinates.get(2).getX());
            assertEquals(3.5, result.coordinates.get(2).getY());

            assertEquals(2, result.coordinates.get(3).getX());
            assertEquals(4, result.coordinates.get(3).getY());

            assertEquals(3.5, result.coordinates.get(4).getX());
            assertEquals(3.5, result.coordinates.get(4).getY());

            assertEquals(4, result.coordinates.get(5).getX());
            assertEquals(2, result.coordinates.get(5).getY());

            assertEquals(0.5, result.coordinates.get(6).getX());
            assertEquals(3.5, result.coordinates.get(6).getY());

            assertEquals(2, result.coordinates.get(7).getX());
            assertEquals(0, result.coordinates.get(7).getY());
        }
    }
}
