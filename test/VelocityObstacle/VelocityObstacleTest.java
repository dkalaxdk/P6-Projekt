package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Geometry.*;

import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.ShipDomain;
import Dibbidut.Classes.VelocityObstacle;
import Dibbidut.Interfaces.IDomain;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class VelocityObstacleTest {
    class mockDomain implements IDomain {
        private Geometry domain;
        private double radius;
        private Vector2D position;
        mockDomain(Vector2D position, double radius) {
            this.radius = radius;
            this.position = position;
            /*
            domain = new Ellipse2D.Double(
                    position.x() - radius/2, // Get top left position from center
                    position.y() - radius/2,
                    radius, radius
            );
             */
            domain = new Ellipse(new Vector(position.x(), position.y(), 1), radius, radius);
        }
        // Not needed for these tests
        @Override
        public ShipDomain Update(double SOG, double Heading, double y, double x) {
            return null;
        }

        @Override
        public Geometry getDomain() {
            return domain;
        }

        @Override
        public boolean getDomainType() {
            return true;
        }

        public Shape getScaledShipDomain(double scalar) {
            Ellipse2D.Double scaledEllipse = new Ellipse2D.Double();
            //scaledEllipse.x = domain.getBounds2D().getX();
            //scaledEllipse.y = domain.getBounds2D().getY();

            scaledEllipse.x = position.x() - (radius/scalar) / 2;
            scaledEllipse.y = position.y() - (radius/scalar) / 2;
            scaledEllipse.width = radius/scalar;
            scaledEllipse.height = radius/scalar;

            return scaledEllipse;
        }

        @Override
        public double getHeight() {
            return 0;
        }

        @Override
        public double getWidth() {
            return 0;
        }
    }
    @Nested
    @DisplayName("VelocityObstacle.CalculateVO")
    class CalculateVO {
        PointFactory factory = new VectorFactory();
        VelocityObstacle VO;
        Geometry obsDomain;
        Point objPos;
        Point obsPos;
        Vector obsVel;

        @BeforeEach
        public void setUp() {
            VO = new VelocityObstacle();
            obsDomain = new Polygon(new ArrayList<Vector>(Arrays.asList(
                    new Vector(-1,4,1),
                    new Vector(-1, 6, 1),
                    new Vector(1, 4, 1),
                    new Vector(1, 6, 1)
            )));
            obsPos = factory.createPoint(0,5);
            objPos = factory.createPoint(0,0);
            obsVel = new Vector(1, 0, 1);

        }

        @Test
        public void Calculate_ContainsOwnShipCurrentVelocityIfTheyCollideWithinTimeFrame() {
            double time = 5;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new Vector(1, 1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainOwnShipCurrentVelocityIfNoCollisionWithinTimeFrame() {
            double time = 3;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertFalse(absVO.contains(new Vector(1, 1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocityThatLeadsToCollisionAfterTimeFrame() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertFalse(absVO.contains(new Vector(1, 0.25, 1)));
        }

        @Test
        public void Calculate_ContainsVelocitiesThatCauseCollision() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new Vector(1, 5, 1)));
            assertTrue(absVO.contains(new Vector(1, 2.5, 1)));
            assertTrue(absVO.contains(new Vector(1, 2, 1)));
            assertTrue(absVO.contains(new Vector(1, 1.5, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipPathBeforeItPasses() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);


            assertFalse(absVO.contains(new Vector(2, 2, 1)));
            assertFalse(absVO.contains(new Vector(2, 3, 1)));
            assertFalse(absVO.contains(new Vector(3, 2, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipAfterItPasses() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            // TODO: Go through these and see which ones are actually relevant for Absolute VO
            //assertFalse(absVO.contains(new Point2D.Double(0.75, 1)));     // Fails if VO is not cone
            assertFalse(absVO.contains(new Vector(0.5, 2, 1)));
            assertFalse(absVO.contains(new Vector(0.5, 1, 1)));      // Fails because VO is not cone
            assertFalse(absVO.contains(new Vector(0.4, 0, 1.5)));
            assertFalse(absVO.contains(new Vector(0.5, 0, 1.5)));    // Fails because VO is not cone
            assertFalse(absVO.contains(new Vector(0.5, 0, 1)));
            assertFalse(absVO.contains(new Vector(0.75,0.75, 1)));  // Fails because VO is not cone
            assertFalse(absVO.contains(new Vector(0.75,0.5, 1)));   // Fails because VO is not cone
        }

        @Test
        public void Calculate_WorksWithTimeFrameOne() {
            double time = 1;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new Vector(obsPos.getX() + obsVel.getX(), obsPos.getY() + obsVel.getY(), 1)));

        }
    }

    @Nested
    @DisplayName("VelocityObstacle.RelativeVO")
    class RelativeVO {
        PointFactory factory = new VectorFactory();
        VelocityObstacle VO;
        Geometry obsDomain;
        Point objPos;
        Point obsPos;

        @BeforeEach
        public void setUp() {
            VO = new VelocityObstacle();
            obsDomain = new Polygon(new ArrayList<Vector>(Arrays.asList(
                    new Vector(4,4,1),
                    new Vector(6, 4, 1),
                    new Vector(6, 6, 1),
                    new Vector(4, 6, 1)
            )));
            obsPos = factory.createPoint(5,5);
            objPos = factory.createPoint(0,0);

        }

        @Test
        public void getRelativeVO_containsObstaclePosition() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new Vector(obsPos.getX(), obsPos.getY(), 1)));
        }

        @Test
        public void getRelativeVO_containsVelocitiesLeadingToCollisionInFuture() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);
            double relativeVel = 1;
            // Assert that the returned area contains the future positions of the target ship
            assertTrue(relVO.contains(new Vector(obsPos.getX(), obsPos.getY(), 1)));
            assertTrue(relVO.contains(new Vector(relativeVel, relativeVel, 1)));
            assertTrue(relVO.contains(new Vector( relativeVel * 2,  relativeVel * 2, 1)));
            assertTrue(relVO.contains(new Vector(relativeVel * 3,  relativeVel * 3, 1)));
            assertTrue(relVO.contains(new Vector(relativeVel * 4,  relativeVel * 4, 1)));
            assertTrue(relVO.contains(new Vector(relativeVel * 5,  relativeVel * 5, 1)));
        }

        @Test
        public void getRelativeVO_ContainsTargetShipConflictRegionAtSingleTimeStep() {
            double time = 1;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new Vector(obsPos.getX(), obsPos.getY(), 1))); //Center
            // Check that it contains points just inside the edge of the conflictRegion
            assertTrue(relVO.contains(new Vector(obsPos.getX(), obsPos.getY() + 0.49, 1))); //Top
            assertTrue(relVO.contains(new Vector(obsPos.getX(), obsPos.getY() - 0.49, 1))); //Bottom
            assertTrue(relVO.contains(new Vector(obsPos.getX() - 0.49, obsPos.getY(), 1))); //Left
            assertTrue(relVO.contains(new Vector(obsPos.getX() + 0.49, obsPos.getY(), 1))); //Right
        }

        @Test
        public void relativeVO_RelativeVOConeIsNarrowNearOwnShip() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            //assertTrue(relVO.contains(new Point2D.Double(0.1, 0.1)));   // center of cone
            assertFalse(relVO.contains(new Vector(0.1, 0.2, 1)));  // 'under' cone
            assertFalse(relVO.contains(new Vector(0.2, 0.1, 1)));  // 'above' cone
        }

        @Test
        public void relativeVO_RelativeVOConeIsWideNearTargetShip() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new Vector(5, 5, 1)));
            assertFalse(relVO.contains(new Vector(4, 6.2, 1)));
            assertFalse(relVO.contains(new Vector(6.2, 4, 1)));
        }
    }

    @Nested
    @DisplayName("VelocityObstacle.ConflictRegion")
    class ConflictRegion {
        @Test
        public void ConflictRegion_returnsCircleAtPointWithRadius() {
            //Point point = new Point(0, 0);
            Vector point = new Vector(0, 0, 1);
            int radius = 2;
            VelocityObstacle VO = new VelocityObstacle();

            assertEquals(new Ellipse2D.Double(point.getX(), point.getY(), radius, radius), VO.ConflictRegion(point, radius));
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
        public void setUp() {
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
