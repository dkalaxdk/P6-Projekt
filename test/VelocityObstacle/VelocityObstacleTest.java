package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Ship;
import Dibbidut.Classes.VelocityObstacle;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class VelocityObstacleTest {

    @Nested
    @DisplayName("VelocityObstacle.RelativeVO")
    class RelativeVO {
        VelocityObstacle VO = new VelocityObstacle();
        Ship shipA;
        Ship shipB;
        @BeforeEach
        public void setUp() {
            Shape confA = new Ellipse2D.Double(0, 0, 1, 1);
            Vector2D velA = new Vector2D(1, 1);
            Vector2D posA = new Vector2D(0, 0);
            shipA = new Ship(posA, velA, confA);

            Shape confB = new Ellipse2D.Double(4.5, 4.5, 1, 1);
            Vector2D velB = new Vector2D(0, 0);
            Vector2D posB = new Vector2D(5, 5);
            shipB = new Ship(posB, velB, confB);
        }

        @Test
        public void relativeVO_containsTargetShipPosition() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x(), shipB.position.y())));
        }

        @Test
        public void relativeVO_containsTargetShipPositionAtAllTimeSteps() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);
            double relativeVel = 1;
            // Assert that the returned area contains the future positions of the target ship
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x(), shipB.position.y())));
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x() + relativeVel, shipB.position.y() + relativeVel)));
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x() + relativeVel * 2, shipB.position.y() + relativeVel * 2)));
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x() + relativeVel * 3, shipB.position.y() + relativeVel * 3)));
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.x() + relativeVel * 4, shipB.position.y() + relativeVel * 4)));
        }

        @Test
        public void relativeVO_ContainsTargetShipConflictRegionAtSingleTimeStep() {
            double time = 1;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            assertTrue(relVO.contains(shipB.conflictRegion.getBounds2D()));
        }

        @Test
        public void relativeVO_ContainsTargetShipConflictRegionAtEveryTimeStep() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            Double boundX = shipB.conflictRegion.getBounds2D().getX();
            Double boundY = shipB.conflictRegion.getBounds2D().getY();
            Double width = shipB.conflictRegion.getBounds2D().getWidth();
            Double height = shipB.conflictRegion.getBounds2D().getHeight();

            assertTrue(relVO.contains(shipB.conflictRegion.getBounds2D()));
            assertTrue(relVO.contains(new Rectangle2D.Double(boundX + 1, boundY + 1, width, height)));
            assertTrue(relVO.contains(new Rectangle2D.Double(boundX + 2, boundY + 2, width, height)));
            assertTrue(relVO.contains(new Rectangle2D.Double(boundX + 3, boundY + 3, width, height)));
            assertTrue(relVO.contains(new Rectangle2D.Double(boundX + 4, boundY + 4, width, height)));
        }
    }

    @Nested
    @DisplayName("VelocityObstacle.ConflictRegion")
    class ConflictRegion {
        @Test
        public void ConflictRegion_returnsCircleAtPointWithRadius() {
            Point point = new Point(0, 0);
            int radius = 2;
            VelocityObstacle VO = new VelocityObstacle();

            assertEquals(new Ellipse2D.Double(point.x, point.y, radius, radius), VO.ConflictRegion(point, radius));
        }
    }

    @Nested
    @DisplayName("VelocityObstacle.Displacement")
    class Displacement {
        VelocityObstacle VO;
        Vector2D pointA;
        Vector2D pointB;
        @BeforeEach
        public void setUp() {
            VO = new VelocityObstacle();

            pointA = new Vector2D(0, 0);
            pointB = new Vector2D(2, 2);
        }
        @Test
        public void displacement_calculatesTheDisplacementFromAToB() {

            assertEquals(VO.Displacement(pointA, pointB), new Vector2D(2, 2));
        }

        @Test
        public void displacement_calculatesTheDisplacementFromBToA() {

            assertEquals(VO.Displacement(pointB, pointA), new Vector2D(-2, -2));
        }
    }

    @Nested
    @DisplayName("VelocityObstacle.WillCollide")
    class WillCollide {
        VelocityObstacle VO;
        Point2D pointA;
        Vector2D vel;
        @BeforeEach
        public void setUP() {
            VO = new VelocityObstacle();
            pointA = new Point2D.Double(1,1);
            vel = new Vector2D(1, 1);
        }
        @Test
        public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionAtGivenTime() {
            Shape target = new Ellipse2D.Double(4, 4, 1, 1);
            double time = 4;
            assertTrue(VO.WillCollide(pointA, vel, target, time));
        }

        @Test
        public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionBeforeGivenTime() {
            Shape target = new Ellipse2D.Double(4, 4, 1, 1);
            double time = 6;
            assertTrue(VO.WillCollide(pointA, vel, target, time));
        }

        @Test
        public void willCollide_returnsFalseIfVelocityWillNeverCauseCollision() {
            Shape target = new Ellipse2D.Double(10, 5, 1, 1);
            double time = 15;
            assertFalse(VO.WillCollide(pointA, vel, target, time));
        }

        @Test
        public void willCollide_returnsFalseIfVelocityWillNotCauseCollisionBeforeGivenTime() {
            Shape target = new Ellipse2D.Double(10, 10, 1, 1);
            double time = 6;
            assertFalse(VO.WillCollide(pointA, vel, target, time));
        }
    }
}
