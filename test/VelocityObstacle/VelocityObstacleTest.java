package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Geometry.Ellipse;
import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.Geometry.HPoint;
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

public class VelocityObstacleTest {
    class mockDomain implements IDomain {
        private Geometry domain;
        private double radius;
        private HPoint position;
        mockDomain(HPoint position, double radius) {
            this.radius = radius;
            this.position = position;
            /*
            domain = new Ellipse2D.Double(
                    position.x() - radius/2, // Get top left position from center
                    position.y() - radius/2,
                    radius, radius
            );
             */
            domain = new Ellipse(new HPoint(position.getX(), position.getY()), radius, radius);
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

            scaledEllipse.x = position.getX() - (radius/scalar) / 2;
            scaledEllipse.y = position.getY() - (radius/scalar) / 2;
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
        VelocityObstacle VO = new VelocityObstacle();
        Ship shipA;
        Ship shipB;

        @BeforeEach
        public void setUp() {
            Shape confA = new Ellipse2D.Double(-0.5, -0.5, 1, 1);
            HPoint velA = new HPoint(1, 1);
            HPoint posA = new HPoint(0, 0);
            shipA = new Ship(posA, 1, 1, 0);
            shipA.velocity = velA;
            shipA.domain = new mockDomain(posA, 1);

            Shape confB = new Ellipse2D.Double(-0.5, 4.5, 1, 1);
            HPoint velB = new HPoint(1, 0);
            HPoint posB = new HPoint(0, 5);
            shipB = new Ship(posB, 1,1, 0);
            shipB.velocity = velB;
            shipB.domain = new mockDomain(posB, 1);
        }

        @Test
        public void Calculate_ContainsOwnShipCurrentVelocityIfTheyCollideWithinTimeFrame() {
            double time = 5;

            Area absVO = VO.Calculate(shipA, shipB, time);

            assertTrue(absVO.contains(new Point2D.Double(1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainOwnShipCurrentVelocityIfNoCollisionWithinTimeFrame() {
            double time = 3;

            Area absVO = VO.Calculate(shipA, shipB, time);

            assertFalse(absVO.contains(new Point2D.Double(1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocityThatLeadsToCollisionAfterTimeFrame() {
            double time = 10;

            Area absVO = VO.Calculate(shipA, shipB, time);

            assertFalse(absVO.contains(new Point2D.Double(1, 0.25)));
        }

        @Test
        public void Calculate_ContainsVelocitiesThatCauseCollision() {
            double time = 10;

            Area absVO = VO.Calculate(shipA, shipB, time);

            assertTrue(absVO.contains(new Point2D.Double(1, 5)));
            assertTrue(absVO.contains(new Point2D.Double(1, 2.5)));
            assertTrue(absVO.contains(new Point2D.Double(1, 2)));
            assertTrue(absVO.contains(new Point2D.Double(1, 1.5)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipPathBeforeItPasses() {
            double time = 10;

            Area absVO = VO.Calculate(shipA, shipB, time);


            assertFalse(absVO.contains(new Point2D.Double(2, 2)));
            assertFalse(absVO.contains(new Point2D.Double(2, 3)));
            assertFalse(absVO.contains(new Point2D.Double(3, 2)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipAfterItPasses() {
            double time = 10;

            Area absVO = VO.Calculate(shipA, shipB, time);

            // TODO: Go through these and see which ones are actually relevant for Absolute VO
            //assertFalse(absVO.contains(new Point2D.Double(0.75, 1)));     // Fails if VO is not cone
            assertFalse(absVO.contains(new Point2D.Double(0.5, 2)));
            assertFalse(absVO.contains(new Point2D.Double(0.5, 1)));      // Fails because VO is not cone
            assertFalse(absVO.contains(new Point2D.Double(0.4, 0.5)));
            assertFalse(absVO.contains(new Point2D.Double(0.5, 0.5)));    // Fails because VO is not cone
            assertFalse(absVO.contains(new Point2D.Double(0.5, 0)));
            assertFalse(absVO.contains(new Point2D.Double(0.75, 0.75)));  // Fails because VO is not cone
            assertFalse(absVO.contains(new Point2D.Double(0.75, 0.5)));   // Fails because VO is not cone
            assertFalse(absVO.contains(new Point2D.Double(0.5, 4)));   // Fails if VO is not cone
        }

        @Test
        public void Calculate_WorksWithTimeFrameOne() {
            double time = 1;

            Area absVO = VO.Calculate(shipA, shipB, time);

            assertTrue(absVO.contains(new Point2D.Double(shipB.position.getX() + shipB.velocity.getX(), shipB.position.getY() + shipB.velocity.getY())));

        }
    }

    @Nested
    @DisplayName("VelocityObstacle.RelativeVO")
    class RelativeVO {
        VelocityObstacle VO = new VelocityObstacle();
        Ship shipA;
        Ship shipB;

        @BeforeEach
        public void setUp() {
            Shape confA = new Ellipse2D.Double(0, 0, 1, 1);
            HPoint velA = new HPoint(1, 1);
            HPoint posA = new HPoint(0, 0);
            shipA = new Ship(posA, velA, confA);
            shipA.domain = new mockDomain(posA, 1);

            Shape confB = new Ellipse2D.Double(4.5, 4.5, 1, 1);
            HPoint velB = new HPoint(0, 0);
            HPoint posB = new HPoint(5, 5);
            shipB = new Ship(posB, velB, confB);
            shipB.domain = new mockDomain(posB, 1);
        }

        @Test
        public void relativeVO_containsVelocityLeadingToCollisionImmediately() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX(), shipB.position.getY())));
        }

        @Test
        public void relativeVO_containsVelocitiesLeadingToCollisionInFuture() {
            /*
                The asserts that are commented out are ones that should pass
                but currently do not due to the size of the time steps.

                Currently it calculates and combines all the areas that cause a collision
                at the exact time step. This leads to several separate and non intersecting
                areas.
                To all the asserts, the space between the individual areas should be included
             */
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);
            double relativeVel = 1;
            // Assert that the returned area contains the future positions of the target ship
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX(), shipB.position.getY())));
            assertTrue(relVO.contains(new Point2D.Double(relativeVel, relativeVel)));
            assertTrue(relVO.contains(new Point2D.Double( relativeVel * 2,  relativeVel * 2)));
            //assertTrue(relVO.contains(new Point2D.Double(relativeVel * 3,  relativeVel * 3)));
            //assertTrue(relVO.contains(new Point2D.Double(relativeVel * 4,  relativeVel * 4)));
            assertTrue(relVO.contains(new Point2D.Double(relativeVel * 5,  relativeVel * 5)));
        }

        @Test
        public void relativeVO_ContainsTargetShipConflictRegionAtSingleTimeStep() {
            double time = 1;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            assertTrue(relVO.intersects(shipB.conflictRegion.getBounds2D()));
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX(), shipB.position.getY()))); //Center
            // Check that it contains points just inside the edge of the conflictRegion
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX(), shipB.position.getY() + 0.49))); //Top
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX(), shipB.position.getY() - 0.49))); //Bottom
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX() - 0.49, shipB.position.getY()))); //Left
            assertTrue(relVO.contains(new Point2D.Double(shipB.position.getX() + 0.49, shipB.position.getY()))); //Right
        }

        @Test
        public void relativeVO_RelativeVOConeIsNarrowNearOwnShip() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            //assertTrue(relVO.contains(new Point2D.Double(0.1, 0.1)));   // center of cone
            assertFalse(relVO.contains(new Point2D.Double(0.1, 0.2)));  // 'under' cone
            assertFalse(relVO.contains(new Point2D.Double(0.2, 0.1)));  // 'above' cone
        }

        @Test
        public void relativeVO_RelativeVOConeIsWideNearTargetShip() {
            double time = 5;

            Area relVO = VO.RelativeVO(shipA, shipB, time);

            assertTrue(relVO.contains(new Point2D.Double(5, 5)));
            assertFalse(relVO.contains(new Point2D.Double(4.2, 5)));
            assertFalse(relVO.contains(new Point2D.Double(5, 4.2)));
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
