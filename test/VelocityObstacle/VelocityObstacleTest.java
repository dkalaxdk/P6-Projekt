package VelocityObstacle;

import static org.junit.jupiter.api.Assertions.*;

import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.Velocity;
import Dibbidut.Classes.VelocityObstacle;
import Dibbidut.utils.Vector;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

class TestObstacle extends Obstacle {
    public int Radius;
    public TestObstacle(Point pos, Velocity vel, int radius) {
        position = pos;
        velocity = vel;
        Radius = radius;
    }
}

public class VelocityObstacleTest {

    @Test
    public void ConflictRegion_returnsCircleAtPointWithRadius() {
        Point point = new Point(0, 0);
        int radius = 2;
        VelocityObstacle VO = new VelocityObstacle();

        assertEquals(new Ellipse2D.Double(point.x, point.y, radius, radius), VO.ConflictRegion(point, radius));
    }

/*
    @Test
    public void relativeVO_containsVelocityLeadingToCollision() {
        VelocityObstacle VO = new VelocityObstacle();
        //Velocity relativeVelocity = new Velocity(1, 1);
        Ellipse2D.Double confRegion = new Ellipse2D.Double(1, 1, 1, 1);
        Point pointA = new Point(0, 0);
        Point pointB = new Point(1, 1);
        int radius = 6500000;
        Ellipse2D.Double result = VO.RelativeVO(pointA, pointB, null);

        assertTrue(result.contains(new Point(0, 0)));
    }
*/

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
        Point2D target = new Point2D.Double(5, 5);
        double time = 4;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }

    @Test
    public void willCollide_returnsTrueIfTheVelocityWillCauseCollisionBeforeGivenTime() {
        VelocityObstacle VO = new VelocityObstacle();

        Point2D pointA = new Point2D.Double(1,1);
        Vector vel = new Vector(1, 1);
        Point2D target = new Point2D.Double(5, 5);
        double time = 6;
        assertTrue(VO.WillCollide(pointA, vel, target, time));
    }
    
}
