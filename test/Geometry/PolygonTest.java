package Geometry;

import DSDLVO.Classes.Geometry.Line;
import DSDLVO.Classes.Geometry.Polygon;
import DSDLVO.Classes.Geometry.HPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonTest {

    @Nested
    @DisplayName("Polygon.transform")
    class transform{
        @Test
        public void transform_rotatesPolygonAroundItsCenter(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            points.add(new HPoint(1, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.rotate(90);

            assertEquals(3, polygon.coordinates.get(0).getX());
            assertEquals(3, polygon.coordinates.get(0).getY());

            assertEquals(3, polygon.coordinates.get(1).getX());
            assertEquals(1, polygon.coordinates.get(1).getY());

            assertEquals(1, polygon.coordinates.get(2).getX());
            assertEquals(1, polygon.coordinates.get(2).getY());

            assertEquals(1, polygon.coordinates.get(3).getX());
            assertEquals(3, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_CenterTheSameAfterRotation(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.rotate(90);

            assertEquals(2, polygon.referencePoint.getX());
            assertEquals(2, polygon.referencePoint.getY());
        }

        @Test
        public void transform_rotatesPolygonAroundItsReferencePointNotInCenter(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(4, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 4, 1));
            Polygon polygon = new Polygon(points, new HPoint(2, 2));

            polygon.rotate(90);

            assertEquals(4, polygon.coordinates.get(0).getX());
            assertEquals(0, polygon.coordinates.get(0).getY());

            assertEquals(1, polygon.coordinates.get(1).getX());
            assertEquals(0, polygon.coordinates.get(1).getY());

            assertEquals(1, polygon.coordinates.get(2).getX());
            assertEquals(3, polygon.coordinates.get(2).getY());

            assertEquals(4, polygon.coordinates.get(3).getX());
            assertEquals(3, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_scalesPolygonFromItsCenter(){
            ArrayList<HPoint> points = new ArrayList<>();
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
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.scale(2, 2);

            assertEquals(2, polygon.referencePoint.getX());
            assertEquals(2, polygon.referencePoint.getY());
        }

        @Test
        public void transform_scalesPolygonAroundItsReferencePointNotInCenter(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(4, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 4, 1));
            Polygon polygon = new Polygon(points, new HPoint(2, 2));

            polygon.scale(2,2);

            assertEquals(6, polygon.coordinates.get(0).getX());
            assertEquals(6, polygon.coordinates.get(0).getY());

            assertEquals(6, polygon.coordinates.get(1).getX());
            assertEquals(0, polygon.coordinates.get(1).getY());

            assertEquals(0, polygon.coordinates.get(2).getX());
            assertEquals(0, polygon.coordinates.get(2).getY());

            assertEquals(0, polygon.coordinates.get(3).getX());
            assertEquals(6, polygon.coordinates.get(3).getY());
        }

        @Test
        public void transform_translatesPolygon(){
            ArrayList<HPoint> points = new ArrayList<>();
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
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.translate(1, 1);

            assertEquals(3, polygon.referencePoint.getX());
            assertEquals(3, polygon.referencePoint.getY());
        }

        @Test
        public void transform_translatesPolygonAroundItsReferencePointNotInCenter(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(4, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 4, 1));
            Polygon polygon = new Polygon(points, new HPoint(2, 2));

            polygon.translate(1, 1);

            assertEquals(5, polygon.coordinates.get(0).getX());
            assertEquals(5, polygon.coordinates.get(0).getY());

            assertEquals(5, polygon.coordinates.get(1).getX());
            assertEquals(2, polygon.coordinates.get(1).getY());

            assertEquals(2, polygon.coordinates.get(2).getX());
            assertEquals(2, polygon.coordinates.get(2).getY());

            assertEquals(2, polygon.coordinates.get(3).getX());
            assertEquals(5, polygon.coordinates.get(3).getY());
        }
    }

    @Nested
    @DisplayName("Polygon.contains")
    class contains{
        @Test
        public void contains_ReturnsTrueWhenPolygonContainsPoint(){
            ArrayList<HPoint> points = new ArrayList<>();
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
            ArrayList<HPoint> points = new ArrayList<>();
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
            ArrayList<HPoint> points = new ArrayList<>();
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
            ArrayList<HPoint> points = new ArrayList<>();
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
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsTrueWhenRayCrossesSegment_UpperPointInSegmentOnRay(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 1, 1);
            HPoint p2 = new HPoint(2, 0, 1);

            assertTrue(polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsFalseWhenRayDoesNotCrossSegment(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(0, 2, 1);
            HPoint p2 = new HPoint(0, 0, 1);

            assertTrue(!polygon.crossesRay(a, p1, p2));
        }

        @Test
        public void crossesRay_ReturnsFalseWhenRayCrossesSegment_LowerPointInSegmentOnRay(){
            Polygon polygon = new Polygon(new ArrayList<>());
            HPoint a = new HPoint(1, 1, 1);
            HPoint p1 = new HPoint(2, 2, 1);
            HPoint p2 = new HPoint(2, 1, 1);

            assertTrue(!polygon.crossesRay(a, p1, p2));
        }
    }

    @Nested
    @DisplayName("Polygon.CombineWith")
    class combineWith{
        @Test
        public void combineWith_returnsCorrectPolygon(){
            ArrayList<HPoint> points1 = new ArrayList<>();
            points1.add(new HPoint(1, 1, 1));
            points1.add(new HPoint(1, 4, 1));
            points1.add(new HPoint(2, 5, 1));
            points1.add(new HPoint(3, 4, 1));
            points1.add(new HPoint(3, 1, 1));
            Polygon polygon1 = new Polygon(points1, new HPoint(2, 2));

            ArrayList<HPoint> points2 = new ArrayList<>();
            points2.add(new HPoint(5, 6, 1));
            points2.add(new HPoint(6, 7, 1));
            points2.add(new HPoint(9, 7, 1));
            points2.add(new HPoint(9, 5, 1));
            points2.add(new HPoint(6, 5, 1));
            Polygon polygon2 = new Polygon(points2, new HPoint(8, 6));

            Polygon result = polygon1.combineWith(polygon2);

            assertEquals(7, result.coordinates.size());

            assertEquals(0, result.coordinates.get(0).getX());
            assertEquals(0, result.coordinates.get(0).getY());

            assertEquals(5, result.coordinates.get(1).getX());
            assertEquals(0, result.coordinates.get(1).getY());

            assertEquals(6, result.coordinates.get(2).getX());
            assertEquals(1, result.coordinates.get(2).getY());

            assertEquals(6, result.coordinates.get(3).getX());
            assertEquals(4, result.coordinates.get(3).getY());

            assertEquals(4, result.coordinates.get(4).getX());
            assertEquals(6, result.coordinates.get(4).getY());

            assertEquals(1, result.coordinates.get(5).getX());
            assertEquals(6, result.coordinates.get(5).getY());

            assertEquals(0, result.coordinates.get(6).getX());
            assertEquals(5, result.coordinates.get(6).getY());
        }
    }

    @Nested
    @DisplayName("Polygon.calculateCenter")
    class calculateCenter{
        @Test
        public void calculateCenter_SetCenterXCorrectly(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.referencePoint.getX());
        }

        @Test
        public void calculateCenter_SetCenterYCorrectly(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertEquals(2, polygon.referencePoint.getY());
        }

        @Test
        public void calculateCenter_centerSetToNullIfPointsListIsEmpty(){
            ArrayList<HPoint> points = new ArrayList<>();
            Polygon polygon = new Polygon(points);

            polygon.calculateCenter();

            assertNull(polygon.referencePoint);
        }
    }

    @Nested
    @DisplayName("Polygon.copy")
    class copy{
        @Test
        public void copy_ReturnsCorrectCopy(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            Polygon polygon = new Polygon(points);

            Polygon result = polygon.copy();

            assertEquals(3, result.coordinates.size());

            assertEquals(1, result.coordinates.get(0).getX());
            assertEquals(1, result.coordinates.get(0).getY());

            assertEquals(1, result.coordinates.get(1).getX());
            assertEquals(3, result.coordinates.get(1).getY());

            assertEquals(3, result.coordinates.get(2).getX());
            assertEquals(3, result.coordinates.get(2).getY());

        }
    }

    @Nested
    @DisplayName("Polygon.getVertices")
    class getVertices{
        @Test
        public void getVertices_returnsVerticesOfPolygon() {
            HPoint p1 = new HPoint(-3, 0);
            HPoint p2 = new HPoint(3, 0);
            HPoint p3 = new HPoint(0,3);

            ArrayList<Line> expected = new ArrayList<>(Arrays.asList(
                    new Line(p1, p2),
                    new Line(p2, p3),
                    new Line(p3, p1)
            ));

            Polygon poly = new Polygon(new ArrayList<>(Arrays.asList(
                    p1, p2, p3
            )));

            assertEquals(expected, poly.getVertices());
        }
    }
}
