package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.Velocity;
import Dibbidut.Classes.VelocityObstacle;
import Dibbidut.utils.Vector;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class VelocityObstacleTest {

    @Test
    public void relativeVO_returnsAreaOfPositionVectorsThatCausesCollision() {
        VelocityObstacle VO = new VelocityObstacle();
        // Check that VO contains points on the edge but not points beyond that
        // Check a few points
    }

    @Test
    public void relativeVO_containsTargetShipPosition() {
        VelocityObstacle VO = new VelocityObstacle();

        Shape confA = new Ellipse2D.Double(0, 0, 1, 1);
        Velocity velA = new Velocity(1, 1);
        Ship shipA = new Ship(new Point2D.Double(0, 0), velA, confA);

        Shape confB = new Ellipse2D.Double(4.5, 4.5, 1, 1);
        Velocity velB = new Velocity(0, 0);
        Ship shipB = new Ship(new Point2D.Double(5, 5), velB, confB);
        double time = 5;

        Area relVO = VO.RelativeVO(shipA, shipB, time);

        assertTrue(relVO.contains(shipB.position));
    }

    @Test
    public void ConflictRegion_returnsCircleAtPointWithRadius() {
        Point point = new Point(0, 0);
        int radius = 2;
        VelocityObstacle VO = new VelocityObstacle();

        assertEquals(new Ellipse2D.Double(point.x, point.y, radius, radius), VO.ConflictRegion(point, radius));
    }

    @Test
    public void displacement_calculatesTheDisplacementFromAToB() {
        VelocityObstacle VO = new VelocityObstacle();

        Point pointA = new Point(0, 0);
        Point pointB = new Point(2, 2);

        assertEquals(VO.Displacement(pointA, pointB), new Vector(2, 2));
    }

    @Test
    public void displacement_calculatesTheDisplacementFromBToA() {
        VelocityObstacle VO = new VelocityObstacle();

        Point pointA = new Point(0, 0);
        Point pointB = new Point(2, 2);

        assertEquals(VO.Displacement(pointB, pointA), new Vector(-2, -2));
    }

    @Test
    public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionAtGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector vel = new Vector(1, 1);
        Shape target = new Ellipse2D.Double(4, 4, 1, 1);
        double time = 4;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionBeforeGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector vel = new Vector(1, 1);
        Shape target = new Ellipse2D.Double(4, 4, 1, 1);
        double time = 6;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsFalseIfVelocityWillNeverCauseCollision() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector vel = new Vector(1, 1);
        Shape target = new Ellipse2D.Double(10, 5, 1, 1);
        double time = 15;
        assertFalse(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsFalseIfVelocityWillNotCauseCollisionBeforeGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector vel = new Vector(1, 1);
        Shape target = new Ellipse2D.Double(10, 10, 1, 1);
        double time = 6;
        assertFalse(VO.WillCollide(pointA, vel, target, time));
    }
}
