package Dibbidut.Classes;

import Dibbidut.Interfaces.IVelocityObstacle;
import Dibbidut.utilities.GrahamScan;
import Dibbidut.utilities.ShapeBorder;
import math.geom2d.Vector2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        Vector2D displacement = Displacement(object.centeredPosition, obstacle.centeredPosition);

        Area combinedRelativeVO = new Area();

        Area domainArea = new Area(obstacle.domain.getDomain());

        ArrayList<Point2D> conflictAreaBorder = ShapeBorder.getBorder(domainArea);

        Point2D OSPos = vectorToPoint(object.centeredPosition);
        conflictAreaBorder.add(OSPos);

        Area cone = getCone(conflictAreaBorder, OSPos);
        cone.add(domainArea);


        Vector2D centerCollisionAtTime = divideVectorByScalar(displacement, timeframe);

        // Calculate the translation that will place the conflictRegion at centerCollision
        Vector2D translationVec = Displacement(obstacle.centeredPosition, centerCollisionAtTime);
        AffineTransform translation = new AffineTransform();
        translation.translate(translationVec.x(), translationVec.y());

        Area scaledDomain = new Area(obstacle.domain.getScaledShipDomain((float)timeframe)).createTransformedArea(translation);
        ArrayList<Point2D> scaledDomainBorder = ShapeBorder.getBorder(scaledDomain);
        scaledDomainBorder.add(OSPos);

        Area excludeCone = getCone(scaledDomainBorder, OSPos);
        cone.subtract(excludeCone);
        cone.add(scaledDomain);

        combinedRelativeVO.add(cone);
        return combinedRelativeVO;
    }

    // This finds the velocity needed to place Point A at Point B
    public Vector2D Displacement(Vector2D a, Vector2D b) {
        return new Vector2D(b.x() - a.x(), b.y() - a.y());
    }

    private Area getCone(ArrayList<Point2D> border, Point2D OSPos) {
        GrahamScan grahamScan = new GrahamScan();
        ArrayList<Point2D> convHull = grahamScan.Calculate(border);

        int OSPosIndex = convHull.indexOf(OSPos);
        // Get the previous and next point in the list
        int prevPos = OSPosIndex == 0 ? convHull.size() - 1 : OSPosIndex - 1;
        int nextPos = OSPosIndex == convHull.size() - 1 ? 0 : OSPosIndex + 1;

        Path2D poly = getPathBetweenPoints(new ArrayList<Point2D>(
                Arrays.asList(
                        convHull.get(OSPosIndex),
                        convHull.get(prevPos),
                        convHull.get(nextPos)
                )
        ));

        return new Area(poly);
    }

    private Path2D getPathBetweenPoints(ArrayList<Point2D> points) {
        Path2D path = new Path2D.Double();
        // Move to start point
        path.moveTo(points.get(0).getX(), points.get(0).getY());

        for(int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).getX(),  points.get(i).getY());
        }
        // Draw line back to start point
        path.lineTo(points.get(0).getX(), points.get(0).getY());

        return path;
    }

    private Point2D vectorToPoint(Vector2D vec) {
        return new Point2D.Double(vec.x(), vec.y());
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
