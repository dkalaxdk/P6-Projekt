package Dibbidut.utilities;

import java.awt.geom.Point2D;
import java.util.*;

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

        ArrayList<Point2D> sortedPoints = new ArrayList<>(points);
        sortedPoints.remove(p0);
        Collections.sort(sortedPoints, new PointPolarAngleComparator());

        ArrayList<Point2D> lookaheadStack = new ArrayList<>();
        lookaheadStack.add(p0);
        lookaheadStack.add(sortedPoints.get(0));
        lookaheadStack.add(sortedPoints.get(1));

        for(int i=3; i <= sortedPoints.size(); i++) {
            while(!leftTurn(lookaheadStack.get(lookaheadStack.size() - 2), lookaheadStack.get(lookaheadStack.size() - 1), sortedPoints.get(i - 1))) {
                lookaheadStack.remove(lookaheadStack.size() - 1);
            }
            lookaheadStack.add(sortedPoints.get(i - 1));
        }
        return lookaheadStack;
    }

    private boolean leftTurn(Point2D a, Point2D b, Point2D c) {
        double crossProduct = (c.getX() - a.getX())*(b.getY() - a.getY()) - (b.getX() - a.getX())*(c.getY() - a.getY());
        // Is the cross product negative
        return crossProduct < 0;
    }
}
