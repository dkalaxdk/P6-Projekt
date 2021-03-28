package Dibbidut.Classes;

import Dibbidut.Interfaces.IVelocityObstacle;
import Dibbidut.utils.Vector;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class VelocityObstacle implements IVelocityObstacle {
    public VO velocities;

    @Override
    public VO Calculate(Obstacle ownShip, Obstacle other) {
        return null;
    }

    @Override
    public VO Merge(VO to, VO from) {
        return null;
    }

    // Possible this should be moved to obstacle
    // To adapt this to dynamic ship domains perhaps switch Ellipse for the Shape interface
    public Ellipse2D.Double ConflictRegion(Point point, int radius) {
        return new Ellipse2D.Double(point.x, point.y, radius, radius);
    }

    public Area RelativeVO(Ship object, Ship obstacle, double timeframe) {
        // Might have to use Area, as it is possible to add shapes to it.
        Velocity relativeVel = object.velocity.RelativeVelocity(obstacle.velocity);
        // For each timestep
        // Find the area of velocities that lead to collision
        // Need the positions/center of the shapes to find the displacement
        Vector displacement = Displacement(object.position, obstacle.position);

        Area combinedRelativeVO = new Area();
        for(int i = 0; i <= timeframe; i++) {
            Vector centerCollision = displacement.DivideByScalar(i);
            //TODO Add the size of OS ship domain to that of the TS
            //TODO Center this new domain around centerCollision
            //TODO Add this area to the combinedRelativeVO
        }

        // To pass the initial test
        combinedRelativeVO.add(new Area(obstacle.conflictRegion));
        return combinedRelativeVO;
    }

    // This finds the velocity needed to place Point A at Point B
    public Vector Displacement(Point2D a, Point2D b) {
        return new Vector(b.getX() - a.getX(), b.getY() - a.getY());
    }

    // The currentVel should be the relative velocity,
    // then the target can be considered stationary
    public boolean WillCollide(Point2D currentPos, Vector currentVel, Shape target, Double timeFrame) {
        Point2D.Double posAtTime = translatePoint(currentPos, currentVel.Scale(timeFrame));
        Line2D.Double path = new Line2D.Double(currentPos, posAtTime);
        // Is the target placed on the path
        return path.intersects(target.getBounds2D());
    }

    private Point2D.Double translatePoint(Point2D point, Vector translation) {
        return new Point2D.Double(point.getX() + translation.X, point.getY() + translation.Y);
    }
}
