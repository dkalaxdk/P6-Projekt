package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import DSDLVO.Classes.Geometry.*;

import DSDLVO.Classes.Geometry.Point;
import DSDLVO.Classes.Geometry.Polygon;
import DSDLVO.Classes.Geometry.Geometry;
import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.VelocityObstacle;
import org.junit.jupiter.api.*;

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

            Geometry relVO = VO.RelativeVO(objPos, obsDomain, obsPos, time);
            double relativeVel = 1;
            // Assert that the returned area contains the future positions of the target ship
            assertTrue(relVO.contains(new HPoint(obsPos)));
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

            assertTrue(relVO.contains(new HPoint(obsPos))); //Center
            // Check that it contains points just inside the edge of the conflictRegion
            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY() + 0.49))); //Top
            assertTrue(relVO.contains(new HPoint(obsPos.getX(), obsPos.getY() - 0.49))); //Bottom
            assertTrue(relVO.contains(new HPoint(obsPos.getX() - 0.49, obsPos.getY()))); //Left
            assertTrue(relVO.contains(new HPoint(obsPos.getX() + 0.49, obsPos.getY()))); //Right
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

            assertTrue(relVO.contains(new HPoint(5, 5)));
            assertFalse(relVO.contains(new HPoint(4, 6.2)));
            assertFalse(relVO.contains(new HPoint(6.2, 4)));
        }

        @Test
        public void RelativeVO_DoesNotContainVelocitiesInDirectionOppositeObstacle() {
            double time = 5;

            HPoint newObjPos = new HPoint(1, 1); // Testing for objPos other than 0
            Geometry relVO = VO.RelativeVO(newObjPos, obsDomain, obsPos, time);

            assertTrue(relVO.contains(new HPoint(obsPos)));
            assertFalse(relVO.contains(new HPoint(0.9, 0.9)));
        }
    }
}

