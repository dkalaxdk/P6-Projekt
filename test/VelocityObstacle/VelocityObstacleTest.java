package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Geometry.*;

import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Geometry.Ellipse;
import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.ShipDomain;
import Dibbidut.Classes.VelocityObstacle;
import Dibbidut.Interfaces.IDomain;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class VelocityObstacleTest {
    @Nested
    @DisplayName("VelocityObstacle.CalculateVO")
    class CalculateVO {
        PointFactory factory = new HPointFactory();
        VelocityObstacle VO;
        Geometry obsDomain;
        Point objPos;
        Point obsPos;
        HPoint obsVel;

        @BeforeEach
        public void setUp() {
            VO = new VelocityObstacle();
            obsDomain = new Polygon(new ArrayList<HPoint>(Arrays.asList(
                    new HPoint(-1,4,1),
                    new HPoint(-1, 6, 1),
                    new HPoint(1, 4, 1),
                    new HPoint(1, 6, 1)
            )));
            obsPos = factory.createPoint(0,5);
            objPos = factory.createPoint(0,0);
            obsVel = new HPoint(1, 0, 1);

        }

        @Test
        public void Calculate_ContainsOwnShipCurrentVelocityIfTheyCollideWithinTimeFrame() {
            double time = 5;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new HPoint(1, 1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainOwnShipCurrentVelocityIfNoCollisionWithinTimeFrame() {
            double time = 3;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertFalse(absVO.contains(new HPoint(1, 1, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocityThatLeadsToCollisionAfterTimeFrame() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertFalse(absVO.contains(new HPoint(1, 0.25, 1)));
        }

        @Test
        public void Calculate_ContainsVelocitiesThatCauseCollision() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new HPoint(1, 5, 1)));
            assertTrue(absVO.contains(new HPoint(1, 2.5, 1)));
            assertTrue(absVO.contains(new HPoint(1, 2, 1)));
            assertTrue(absVO.contains(new HPoint(1, 1.5, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipPathBeforeItPasses() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);


            assertFalse(absVO.contains(new HPoint(2, 2, 1)));
            assertFalse(absVO.contains(new HPoint(2, 3, 1)));
            assertFalse(absVO.contains(new HPoint(3, 2, 1)));
        }

        @Test
        public void Calculate_DoesNotContainVelocitiesWhereOwnShipReachesTargetShipAfterItPasses() {
            double time = 10;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            // TODO: Go through these and see which ones are actually relevant for Absolute VO
            //assertFalse(absVO.contains(new Point2D.Double(0.75, 1)));     // Fails if VO is not cone
            assertFalse(absVO.contains(new HPoint(0.5, 2, 1)));
            assertFalse(absVO.contains(new HPoint(0.5, 1, 1)));      // Fails because VO is not cone
            assertFalse(absVO.contains(new HPoint(0.4, 0, 1.5)));
            assertFalse(absVO.contains(new HPoint(0.5, 0, 1.5)));    // Fails because VO is not cone
            assertFalse(absVO.contains(new HPoint(0.5, 0, 1)));
            assertFalse(absVO.contains(new HPoint(0.75,0.75, 1)));  // Fails because VO is not cone
            assertFalse(absVO.contains(new HPoint(0.75,0.5, 1)));   // Fails because VO is not cone
        }

        @Test
        public void Calculate_WorksWithTimeFrameOne() {
            double time = 1;

            Geometry absVO = VO.Calculate(objPos, obsDomain, obsPos, obsVel, time);

            assertTrue(absVO.contains(new HPoint(obsPos.getX() + obsVel.getX(), obsPos.getY() + obsVel.getY(), 1)));

        }
    }

    @Nested
    @DisplayName("VelocityObstacle.RelativeVO")
    class RelativeVO {
        PointFactory factory = new HPointFactory();
        VelocityObstacle VO;
        Geometry obsDomain;
        Point objPos;
        Point obsPos;

        @BeforeEach
        public void setUp() {
            VO = new VelocityObstacle();
            obsDomain = new Polygon(new ArrayList<HPoint>(Arrays.asList(
                    new HPoint(4,4,1),
                    new HPoint(6, 4, 1),
                    new HPoint(6, 6, 1),
                    new HPoint(4, 6, 1)
            )));
            obsPos = factory.createPoint(5,5);
            objPos = factory.createPoint(0,0);

        }

        @Test
        public void RelativeVO_containsObstaclePosition() {
            double time = 5;

            HPoint newObjPos = new HPoint(2, 2); // Testing for objPos other than 0
            Geometry relVO = VO.RelativeVO(newObjPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY(), 1)));
        }

        @Test
        public void RelativeVO_containsVelocitiesLeadingToCollisionInFuture() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);
            double relativeVel = 1;
            // Assert that the returned area contains the future positions of the target ship
            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY(), 1)));
            assertTrue(relVO.contains(new HPoint(relativeVel, relativeVel, 1)));
            assertTrue(relVO.contains(new HPoint( relativeVel * 2,  relativeVel * 2, 1)));
            assertTrue(relVO.contains(new HPoint(relativeVel * 3,  relativeVel * 3, 1)));
            assertTrue(relVO.contains(new HPoint(relativeVel * 4,  relativeVel * 4, 1)));
            assertTrue(relVO.contains(new HPoint(relativeVel * 5,  relativeVel * 5, 1)));
        }

        @Test
        public void RelativeVO_ContainsTargetShipConflictRegionAtSingleTimeStep() {
            double time = 1;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY(), 1))); //Center
            // Check that it contains points just inside the edge of the conflictRegion
            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY() + 0.49, 1))); //Top
            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY() - 0.49, 1))); //Bottom
            assertTrue(relVO.contains(new HPoint(obsPos.getX() - 0.49, obsPos.getY(), 1))); //Left
            assertTrue(relVO.contains(new HPoint(obsPos.getX() + 0.49, obsPos.getY(), 1))); //Right
        }

        @Test
        public void RelativeVO_RelativeVOConeIsNarrowNearOwnShip() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            //assertTrue(relVO.contains(new Point2D.Double(0.1, 0.1)));   // center of cone
            assertFalse(relVO.contains(new HPoint(0.1, 0.2, 1)));  // 'under' cone
            assertFalse(relVO.contains(new HPoint(0.2, 0.1, 1)));  // 'above' cone
        }

        @Test
        public void relativeVO_RelativeVOConeIsWideNearTargetShip() {
            double time = 5;

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new HPoint(5, 5, 1)));
            assertFalse(relVO.contains(new HPoint(4, 6.2, 1)));
            assertFalse(relVO.contains(new HPoint(6.2, 4, 1)));
        }
    }
}

