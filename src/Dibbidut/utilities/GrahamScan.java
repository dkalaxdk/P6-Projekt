package Dibbidut.utilities;

import math.geom2d.Vector2D;

import java.awt.geom.Point2D;
import java.util.*;

class TranslatedPoint {
    public Point2D originalPoint;
    public Point2D translatedPoint;

    public TranslatedPoint(Point2D originalPoint, Vector2D translation) {
        this.originalPoint = originalPoint;
        translatedPoint = new Point2D.Double(originalPoint.getX() + translation.x(), originalPoint.getY() + translation.y());
    }
}

public class GrahamScan implements ConvexHull<Point2D> {
    @Override
    public ArrayList<Point2D> Calculate(Collection<Point2D> points) {
        if(points.size() < 3) {
            throw  new IllegalArgumentException("Arguments must contain collection with at least three points");
        }
        // Find point with lowest y, if multiple then select leftmost
        Point2D p0 = (Point2D) points.toArray()[0]; // Gross
        for (Point2D point : points) {
            if(point.getY() <= p0.getY()) {
                if(point.getY() < p0.getY()) { // Get point with lowest y
                    p0 = point;
                }
                else if(point.getX() < p0.getX()) { // if y is equal select point with lowest x
                    p0 = point;
                }
            }
        }

        Vector2D translation = new Vector2D(0 - p0.getX(), 0 - p0.getY());

        ArrayList<TranslatedPoint> sortedPoints = new ArrayList<>();
        for(Point2D point : points) {
            if(point != p0)
                sortedPoints.add(new TranslatedPoint(point, translation));
        }
        //Collections.sort(sortedPoints, new PointPolarAngleComparator());
        sortedPoints.sort(Comparator.comparingDouble(p -> Math.atan2(p.translatedPoint.getY(), p.translatedPoint.getX())));

        ArrayList<TranslatedPoint> lookaheadStack = new ArrayList<>();
        lookaheadStack.add(new TranslatedPoint(p0, translation));
        lookaheadStack.add(sortedPoints.get(0));
        lookaheadStack.add(sortedPoints.get(1));

        for(int i=3; i <= sortedPoints.size(); i++) {
            while(!leftTurn(lookaheadStack.get(lookaheadStack.size() - 2).translatedPoint, lookaheadStack.get(lookaheadStack.size() - 1).translatedPoint, sortedPoints.get(i - 1).translatedPoint)) {
                lookaheadStack.remove(lookaheadStack.size() - 1);
            }
            lookaheadStack.add(sortedPoints.get(i - 1));
        }
        ArrayList<Point2D> originalPoints = new ArrayList<>();
        for(TranslatedPoint point : lookaheadStack) {
            originalPoints.add(point.originalPoint);
        }
        return originalPoints;
    }

    private boolean leftTurn(Point2D a, Point2D b, Point2D c) {
        double crossProduct = (c.getX() - a.getX())*(b.getY() - a.getY()) - (b.getX() - a.getX())*(c.getY() - a.getY());
        // Is the cross product negative
        return crossProduct < 0;
    }
}
