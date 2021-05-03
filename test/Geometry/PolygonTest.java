package Geometry;

import Dibbidut.Classes.Geometry.PolarPoint;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Utility;
import Dibbidut.Exceptions.PolygonNotCenteredOnOrigin;
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

//    @Nested
//    @DisplayName("Polygon.matrixHPointProduct")
//    class matrixHPointProduct{
//        @Test
//        public void matrixHPointProduct_returnsCorrectHPoint(){
//            double [][] matrix = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//            HPoint point = new HPoint(1, 2, 3);
//            Polygon polygon = new Polygon(new ArrayList());
//
//            HPoint result = polygon.matrixHPointProduct(matrix, point);
//
//            assertEquals(30, result.getX());
//            assertEquals(36, result.getY());
//            assertEquals(42, result.getZ());
//
//        }
//    }
//    @Nested
//    @DisplayName("Polygon.translatePolygon")
//    class translatePolygon{
//        @Test
//        public void translatePolygon_translatesCorrectlyToOrigin(){
//            ArrayList<HPoint> points = new ArrayList<>();
//            points.add(new HPoint(1, 1, 1));
//            points.add(new HPoint(1, 3, 1));
//            points.add(new HPoint(3, 3, 1));
//            points.add(new HPoint(3, 1, 1));
//            Polygon polygon = new Polygon(points);
//
//            ArrayList<HPoint> result = polygon.translatePolygon(points, new double[][] {{1, 0, 0}, {0, 1, 0}, {-polygon.center.getX(), -polygon.center.getY(), 1}});
//
//            assertEquals(-1, result.get(0).getX());
//            assertEquals(-1, result.get(0).getY());
//
//            assertEquals(-1, result.get(1).getX());
//            assertEquals(1, result.get(1).getY());
//
//            assertEquals(1, result.get(2).getX());
//            assertEquals(1, result.get(2).getY());
//
//            assertEquals(1, result.get(3).getX());
//            assertEquals(-1, result.get(3).getY());
//
//        }
//    }

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
    @DisplayName("Polygon.addPolygon")
    class addPolygon{
        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingAPolygonToItself(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            Polygon polygon = new Polygon(points);

            Polygon result = null;
            try {
                result = polygon.addPolygon(polygon);
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(4, result.coordinates.size());

            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(0).getX()));
            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(0).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(1).getX()));
            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(1).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(2).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(2).getY()));

            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(3).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(3).getY()));
        }

        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingTwoPolygonsOnTopOfEachOtherWithDifferentOrientations(){
            ArrayList<HPoint> points1 = new ArrayList<>();
            points1.add(new HPoint(1, 1, 1));
            points1.add(new HPoint(1, 3, 1));
            points1.add(new HPoint(3, 3, 1));
            points1.add(new HPoint(3, 1, 1));
            Polygon polygon1 = new Polygon(points1);

            ArrayList<HPoint> points2 = new ArrayList<>();
            points2.add(new HPoint(0, 2, 1));
            points2.add(new HPoint(2, 4, 1));
            points2.add(new HPoint(4, 2, 1));
            points2.add(new HPoint(2, 0, 1));
            Polygon polygon2 = new Polygon(points2);

            Polygon result = null;
            try {
                result = polygon1.addPolygon(polygon2);
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(8, result.coordinates.size());

            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(0).getX()));
            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(0).getY()));

            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(1).getX()));
            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(1).getY()));

            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(2).getX()));
            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(2).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(3).getX()));
            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(3).getY()));

            assertEquals(-1, Utility.roundToFourDecimals(result.coordinates.get(4).getX()));
            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(4).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(5).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(5).getY()));

            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(6).getX()));
            assertEquals(-1, Utility.roundToFourDecimals(result.coordinates.get(6).getY()));

            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(7).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(7).getY()));
        }

        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingTwoPolygonsNotTouchingEachOtherWithDifferentOrientations(){
            ArrayList<HPoint> points1 = new ArrayList<>();
            points1.add(new HPoint(1, 3, 1));
            points1.add(new HPoint(3, 3, 1));
            points1.add(new HPoint(3, 1, 1));
            points1.add(new HPoint(1, 1, 1));
            Polygon polygon1 = new Polygon(points1);

            ArrayList<HPoint> points2 = new ArrayList<>();
            points2.add(new HPoint(4, 4, 1));
            points2.add(new HPoint(5, 5, 1));
            points2.add(new HPoint(6, 4, 1));
            points2.add(new HPoint(5, 3, 1));
            Polygon polygon2 = new Polygon(points2);

            Polygon result = null;
            try {
                result = polygon1.addPolygon(polygon2);
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(8, result.coordinates.size());

            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(0).getX()));
            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(0).getY()));

            assertEquals(3.5, Utility.roundToFourDecimals(result.coordinates.get(1).getX()));
            assertEquals(3.5, Utility.roundToFourDecimals(result.coordinates.get(1).getY()));

            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(2).getX()));
            assertEquals(4, Utility.roundToFourDecimals(result.coordinates.get(2).getY()));

            assertEquals(0.5, Utility.roundToFourDecimals(result.coordinates.get(3).getX()));
            assertEquals(3.5, Utility.roundToFourDecimals(result.coordinates.get(3).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(4).getX()));
            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(4).getY()));

            assertEquals(0.5, Utility.roundToFourDecimals(result.coordinates.get(5).getX()));
            assertEquals(0.5, Utility.roundToFourDecimals(result.coordinates.get(5).getY()));

            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(6).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(6).getY()));

            assertEquals(3.5, Utility.roundToFourDecimals(result.coordinates.get(7).getX()));
            assertEquals(0.5, Utility.roundToFourDecimals(result.coordinates.get(7).getY()));
        }

        @Test
        public void addPolygon_ReturnsCorrectPolygonWhenAddingTwoPolygonsNotTouchingEachOtherWithReferencePointsNotInCenter(){
            ArrayList<HPoint> points1 = new ArrayList<>();
            points1.add(new HPoint(4, 4, 1));
            points1.add(new HPoint(4, 1, 1));
            points1.add(new HPoint(1, 1, 1));
            points1.add(new HPoint(1, 4, 1));
            Polygon polygon1 = new Polygon(points1, new HPoint(3, 3));

            ArrayList<HPoint> points2 = new ArrayList<>();
            points2.add(new HPoint(9, 7, 1));
            points2.add(new HPoint(9, 5, 1));
            points2.add(new HPoint(6, 5, 1));
            points2.add(new HPoint(6, 7, 1));
            Polygon polygon2 = new Polygon(points2, new HPoint(8, 6));

            Polygon result = null;
            try {
                result = polygon1.addPolygon(polygon2);
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(6, result.coordinates.size());

            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(0).getX()));
            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(0).getY()));

            assertEquals(-1, Utility.roundToFourDecimals(result.coordinates.get(1).getX()));
            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(1).getY()));

            assertEquals(-1, Utility.roundToFourDecimals(result.coordinates.get(2).getX()));
            assertEquals(1, Utility.roundToFourDecimals(result.coordinates.get(2).getY()));

            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(3).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(3).getY()));

            assertEquals(4.5, Utility.roundToFourDecimals(result.coordinates.get(4).getX()));
            assertEquals(0, Utility.roundToFourDecimals(result.coordinates.get(4).getY()));

            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(5).getX()));
            assertEquals(1, Utility.roundToFourDecimals(result.coordinates.get(5).getY()));

        }
    }

    @Nested
    @DisplayName("Polygon.makeCopy")
    class makeCopy{
        @Test
        public void makeCopy_ReturnsCorrectCopy(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            Polygon polygon = new Polygon(points);

            Polygon result = polygon.makeCopy();

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
    @DisplayName("Polygon.calculateNewHPoint")
    class calculateNewHPoint{
        @Test
        public void calculateNewHPoint_ReturnsCorrectPoint(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(-1, -1, 1));
            points.add(new HPoint(-1, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, -1, 1));
            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            HPoint point = new HPoint(2, 2);

            HPoint result = null;
            try {
                result = polygon.calculateNewHPoint(point.toPolarPoint(), polygon);
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(3, Utility.roundToFourDecimals(result.getX()));
            assertEquals(3, Utility.roundToFourDecimals(result.getY()));
        }
    }

    @Nested
    @DisplayName("Polygon.lengthToEdgeAtAngle")
    class lengthToEdgeAtAngle{
        @Test
        public void lengthToEdgeAtAngle_ReturnsCorrectLength(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(-1, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, -1, 1));
            points.add(new HPoint(-1, -1, 1));
            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            double angle = Math.toRadians(45);

            try {
                assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)),
                        Utility.roundToFourDecimals(polygon.lengthToEdgeAtAngle(angle)));
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }
        }

        @Test
        public void lengthToEdgeAtAngle_ReturnsCorrectLengthWhenAngleIsLargerThan180Degrees(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(-1, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, -1, 1));
            points.add(new HPoint(-1, -1, 1));
            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            double angle = Math.toRadians(292.5);

            try {
                assertEquals(Utility.roundToFourDecimals(1/Math.sin(Math.toRadians(67.5))),
                        Utility.roundToFourDecimals(polygon.lengthToEdgeAtAngle(angle)));
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }
        }
    }

    @Nested
    @DisplayName("Polygon.findSegmentAtAngle")
    class findSegmentAtAngle {
        @Test
        public void findSegmentAtAngle_ReturnsCorrectSegment() {
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(-1, -1, 1));
            points.add(new HPoint(-1, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, -1, 1));
            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            ArrayList<PolarPoint> segment = new ArrayList<>();

            try {
                segment = polygon.findSegmentAtAngle(Math.toRadians(90));
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(2, segment.size());

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(segment.get(0).length));
            assertEquals(Utility.roundToFourDecimals(Math.toRadians(45)), Utility.roundToFourDecimals(segment.get(0).angle));


            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(segment.get(1).length));
            assertEquals(Utility.roundToFourDecimals(Math.toRadians(135)), Utility.roundToFourDecimals(segment.get(1).angle));
        }

        @Test
        public void findSegmentAtAngle_ReturnsOneHPointIfHPointIsAtAngle() {
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(-1, -1, 1));
            points.add(new HPoint(-1, 1, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(1, -1, 1));
            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            ArrayList<PolarPoint> segment = new ArrayList<>();

            try {
                segment = polygon.findSegmentAtAngle(Math.toRadians(45));
            } catch (PolygonNotCenteredOnOrigin e) {
                e.printStackTrace();
            }

            assertEquals(1, segment.size());

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(segment.get(0).length));
            assertEquals(Utility.roundToFourDecimals(Math.toRadians(45)), Utility.roundToFourDecimals(segment.get(0).angle));

        }

        @Test
        public void findSegmentAtAngle_ThrowsExceptionIfTooManyElementsInSegment() {
            boolean exceptionThrown = false;

            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(2, 4, 1));
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(4, 2, 1));
            points.add(new HPoint(2, 2, 1));

            Polygon polygon = new Polygon(points);
            polygon.convertCoordinatesToPolarCoordinates();

            ArrayList<PolarPoint> segment = new ArrayList<>();
            try {
                segment = polygon.findSegmentAtAngle(Math.toRadians(40));
            } catch (PolygonNotCenteredOnOrigin e) {
                exceptionThrown = true;
            }

            assertTrue(exceptionThrown);
        }
    }

    @Nested
    @DisplayName("Polygon.findIntersection")
    class findIntersection{
        @Test
        public void findIntersection_ReturnsCorrectHPointWhenAngleIs90Degrees(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            segment.add(new PolarPoint(Math.sqrt(2), Math.toRadians(135)));
            segment.add(new PolarPoint(Math.sqrt(2), Math.toRadians(45)));
            double angle = Math.toRadians(90);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(1, Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }

        @Test
        public void findIntersection_ReturnsCorrectHPointWhenAngleIs270Degrees(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            segment.add(new HPoint(-2, -1).toPolarPoint());
            segment.add(new HPoint(2, -1).toPolarPoint());

            double angle = Math.toRadians(270);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(1, Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }

        @Test
        public void findIntersection_ReturnsCorrectHPointWhenAngleIsObtuse(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            double length = Math.sqrt(Math.pow(1, 2) + Math.pow(2, 2));
            segment.add(new HPoint(-2, 1).toPolarPoint());
            segment.add(new HPoint(2, 1).toPolarPoint());

            double angle = Math.toRadians(135);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }

        @Test
        public void findIntersection_ReturnsCorrectHPointWhenAngleIsAcute(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            double length = Math.sqrt(Math.pow(1, 2) + Math.pow(2, 2));
            segment.add(new HPoint(-2, 1).toPolarPoint());
            segment.add(new HPoint(2, 1).toPolarPoint());

            double angle = Math.toRadians(45);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }

        @Test
        public void findIntersection_ReturnsCorrectHPointWhenAngleIsOver180Degrees(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            segment.add(new HPoint(-2, -1).toPolarPoint());
            segment.add(new HPoint(2, -1).toPolarPoint());

            double angle = Math.toRadians(225);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(Utility.roundToFourDecimals(Math.sqrt(2)), Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }

        @Test
        public void findIntersection_ReturnsCorrectHPointWhenSegmentIsAPoint(){
            Polygon polygon = new Polygon(new ArrayList<>());

            ArrayList<PolarPoint> segment = new ArrayList<>();
            segment.add(new PolarPoint(1, Math.toRadians(45)));
            double angle = Math.toRadians(45);

            PolarPoint result = polygon.findIntersection(segment, angle);

            assertEquals(1, Utility.roundToFourDecimals(result.length));
            assertEquals(Utility.roundToFourDecimals(angle), Utility.roundToFourDecimals(result.angle));
        }
    }

//    @Nested
//    @DisplayName("Polygon.findPointAtLengthAndAngle")
//    class findPointAtLengthAndAngle{
//        @Test
//        public void findPointAtLengthAndAngle_ReturnsCorrectPoint(){
//            Polygon polygon = new Polygon(new ArrayList<>());
//            double angle = Math.toRadians(45);
//            double length = Math.sqrt(Math.pow(1, 2) + Math.pow(1, 2));
//
//            HPoint result = polygon.findPointAtLengthAndAngle(length, angle);
//
//            assertEquals(1, Utility.roundToFourDecimals(result.getX()));
//            assertEquals(1, Utility.roundToFourDecimals(result.getY()));
//        }
//    }

    @Nested
    @DisplayName("Polygon.sortCoordinates")
    class sortCoordinates{
        @Test
        public void sortCoordinates_SortsListCorrectly(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(1, 3, 1));
            points.add(new HPoint(3, 3, 1));
            points.add(new HPoint(3, 1, 1));
            points.add(new HPoint(1, 1, 1));
            Polygon polygon = new Polygon(points);

            polygon.sortCoordinates();

            assertEquals(3, polygon.coordinates.get(0).getX());
            assertEquals(3, polygon.coordinates.get(0).getY());

            assertEquals(1, polygon.coordinates.get(1).getX());
            assertEquals(3, polygon.coordinates.get(1).getY());

            assertEquals(1, polygon.coordinates.get(2).getX());
            assertEquals(1, polygon.coordinates.get(2).getY());

            assertEquals(3, polygon.coordinates.get(3).getX());
            assertEquals(1, polygon.coordinates.get(3).getY());
        }
    }

    @Nested
    @DisplayName("Polygon.flipInDirectionOf")
    class flipInDirectionOf{
        @Test
        public void flipInDirectionOf_FlipsPolygonCorrectly(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(1, 4, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(4, 1, 1));
            Polygon polygon = new Polygon(points, new HPoint(3, 3));

            Polygon result = polygon.flipInDirectionOf(new HPoint(6, 6));

            //assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(0).getX()));
            //assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(0).getY()));

            //assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(1).getX()));
            //assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(1).getY()));

            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(2).getX()));
            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(2).getY()));

            assertEquals(5, Utility.roundToFourDecimals(result.coordinates.get(3).getX()));
            assertEquals(2, Utility.roundToFourDecimals(result.coordinates.get(3).getY()));
        }

        @Test
        public void flipInDirectionOf_ReferencePointDoesNotChange(){
            ArrayList<HPoint> points = new ArrayList<>();
            points.add(new HPoint(4, 4, 1));
            points.add(new HPoint(1, 4, 1));
            points.add(new HPoint(1, 1, 1));
            points.add(new HPoint(4, 1, 1));
            Polygon polygon = new Polygon(points, new HPoint(3, 3));

            Polygon result = polygon.flipInDirectionOf(new HPoint(6, 6));

            assertEquals(3, Utility.roundToFourDecimals(result.referencePoint.getX()));
            assertEquals(3, Utility.roundToFourDecimals(result.referencePoint.getY()));
        }
    }

}
