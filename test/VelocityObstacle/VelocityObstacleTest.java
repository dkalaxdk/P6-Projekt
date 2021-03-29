package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.Velocity;
import Dibbidut.Classes.VelocityObstacle;
import Dibbidut.utils.Vector;
import math.geom2d.Vector2D;
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
        Vector2D velA = new Vector2D(1, 1);
        Vector2D posA = new Vector2D(0, 0);
        Ship shipA = new Ship(posA, velA, confA);

        Shape confB = new Ellipse2D.Double(4.5, 4.5, 1, 1);
        Vector2D velB = new Vector2D(0, 0);
        Vector2D posB = new Vector2D(5, 5);
        Ship shipB = new Ship(posB, velB, confB);
        double time = 5;

        Area relVO = VO.RelativeVO(shipA, shipB, time);

        assertTrue(relVO.contains(new Point2D.Double(shipB.position.x(), shipB.position.y())));
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

        Vector2D pointA = new Vector2D(0, 0);
        Vector2D pointB = new Vector2D(2, 2);

        assertEquals(VO.Displacement(pointA, pointB), new Vector(2, 2));
    }

    @Test
    public void displacement_calculatesTheDisplacementFromBToA() {
        VelocityObstacle VO = new VelocityObstacle();

        Vector2D pointA = new Vector2D(0, 0);
        Vector2D pointB = new Vector2D(2, 2);

        assertEquals(VO.Displacement(pointB, pointA), new Vector(-2, -2));
    }

    @Test
    public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionAtGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector2D vel = new Vector2D(1, 1);
        Shape target = new Ellipse2D.Double(4, 4, 1, 1);
        double time = 4;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionBeforeGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector2D vel = new Vector2D(1, 1);
        Shape target = new Ellipse2D.Double(4, 4, 1, 1);
        double time = 6;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsFalseIfVelocityWillNeverCauseCollision() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector2D vel = new Vector2D(1, 1);
        Shape target = new Ellipse2D.Double(10, 5, 1, 1);
        double time = 15;
        assertFalse(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsFalseIfVelocityWillNotCauseCollisionBeforeGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector2D vel = new Vector2D(1, 1);
        Shape target = new Ellipse2D.Double(10, 10, 1, 1);
        double time = 6;
        assertFalse(VO.WillCollide(pointA, vel, target, time));
    }
}
