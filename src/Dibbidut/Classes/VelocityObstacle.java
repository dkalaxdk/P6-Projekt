package Dibbidut.Classes;

import Dibbidut.Interfaces.IVelocityObstacle;
import Dibbidut.utils.Vector;

import java.awt.*;
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

    public Ellipse2D.Double RelativeVO(Point A, Point B, Shape conflicRegion) {
        return null;
    }

    // This finds the velocity needed to place Point A at Point B
    public Vector Displacement(Point2D a, Point2D b) {
        return new Vector(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public boolean WillCollide(Point2D currentPos, Vector currentVel, Point2D target, Double timeFrame) {
        // Need to find out if the current velocity will place currentpos at target at time t
        // so currentpos + (currentvel * t) == target
        // t >= target/currentVel
        Point2D.Double posAtTime = translatePoint(currentPos, scaleVector(currentVel, timeFrame));
        Line2D.Double path = new Line2D.Double(currentPos, posAtTime);
        // Is the target placed on the path
        return path.ptLineDist(target) == 0.0;
    }

    private Point2D.Double translatePoint(Point2D point, Vector translation) {
        return new Point2D.Double(point.getX() + translation.X, point.getY() + translation.Y);
    }

    private Vector scaleVector(Vector vec, double factor) {
        return new Vector(vec.X * factor, vec.Y * factor);
    }
}
