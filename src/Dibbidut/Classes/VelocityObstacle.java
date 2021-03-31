package Dibbidut.Classes;

import Dibbidut.Interfaces.IVelocityObstacle;
import math.geom2d.Vector2D;

import java.awt.*;
import java.awt.geom.*;

public class VelocityObstacle implements IVelocityObstacle {
    public VO velocities;

    @Override
    public Area Calculate(Ship ownShip, Ship other, double timeFrame) {
        AffineTransform translation = new AffineTransform();
        translation.translate(other.velocity.x(), other.velocity.y());
        Area relativeVO = RelativeVO(ownShip, other, timeFrame);

        return relativeVO.createTransformedArea(translation);
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
        Vector2D relativeVel = relativeVelocity(object.velocity, obstacle.velocity);
        // For each timestep
        // Find the area of velocities that lead to collision
        // Need the positions/center of the shapes to find the displacement
        Vector2D displacement = Displacement(object.position, obstacle.position);

        Area combinedRelativeVO = new Area();

        Rectangle2D confBounds = obstacle.conflictRegion.getBounds2D();

        for(int i = 1; i <= timeframe; i++) {
            Vector2D centerCollision = divideVectorByScalar(displacement, i);
            //TODO Create a Velocity Obstacle that ensures that neither ships domain is violated

            //FIXME This approach will likely only work with domains that do not change
            // As it assumes that the point the domain is drawn from keeps the same displacement
            // at all times

            // Calculate the translation that will place the conflictRegion at centerCollision
            Vector2D translationVec = Displacement(obstacle.position, centerCollision);
            AffineTransform translation = new AffineTransform();
            translation.translate(translationVec.x(), translationVec.y());
            Area conflictArea = new Area(obstacle.conflictRegion).createTransformedArea(translation);

            combinedRelativeVO.add(conflictArea);
        }

        return combinedRelativeVO;
    }

    private Vector2D relativeVelocity(Vector2D a, Vector2D b) {
        return new Vector2D(a.x() - b.x(), a.y() - b.y());
    }

    // This finds the velocity needed to place Point A at Point B
    public Vector2D Displacement(Vector2D a, Vector2D b) {
        return new Vector2D(b.x() - a.x(), b.y() - a.y());
    }

    private Vector2D divideVectorByScalar(Vector2D vec, double scalar) {
        return new Vector2D(vec.x() / scalar, vec.y() / scalar);
    }

    // The currentVel should be the relative velocity,
    // then the target can be considered stationary
    public boolean WillCollide(Point2D currentPos, Vector2D currentVel, Shape target, Double timeFrame) {
        Point2D.Double posAtTime = translatePoint(currentPos, currentVel.times(timeFrame));
        Line2D.Double path = new Line2D.Double(currentPos, posAtTime);
        // Is the target placed on the path
        return path.intersects(target.getBounds2D());
    }

    private Point2D.Double translatePoint(Point2D point, Vector2D translation) {
        return new Point2D.Double(point.getX() + translation.x(), point.getY() + translation.y());
    }
}
