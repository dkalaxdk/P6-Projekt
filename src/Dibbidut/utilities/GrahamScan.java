package Dibbidut.utilities;

import math.geom2d.Vector2D;

import java.awt.geom.Point2D;
import java.util.*;
import static java.util.stream.Collectors.toList;

class TranslatedPoint {
    public Point2D originalPoint;
    public Point2D translatedPoint;

    public TranslatedPoint(Point2D originalPoint, Vector2D translation) {
        this.originalPoint = originalPoint;
        translatedPoint = new Point2D.Double(originalPoint.getX() + translation.x(), originalPoint.getY() + translation.y());
    }

    // Could make getters for X and Y so it is not necessary to access them through the translatedPoint field

}

// Could benefit from ConvexHull being made an abstract class.
// This would remove the meed to have the calculate method and the hull could be produced when an instance is created
// and be made available through a getter. Or it could be calculated in the getter.
public class GrahamScan implements ConvexHull<Point2D> {
    @Override
    public List<Point2D> Calculate(List<Point2D> points) {
        if(points.size() < 3) {
            throw  new IllegalArgumentException("Arguments must contain collection with at least three points");
        }
        // Find point with lowest y, if multiple then select leftmost
        Point2D p0 = getStartPoint(points);

        Vector2D translation = new Vector2D(0 - p0.getX(), 0 - p0.getY());

        // Translate points so they are relative to p0 and sort by polar angle
        List<TranslatedPoint> sortedPoints = points.stream()
                .filter(p -> p != p0)   // Remove p0 from list
                .map(p -> new TranslatedPoint(p, translation))  // Translate points so they are relative to p0
                .sorted(Comparator.comparingDouble(p -> Math.atan2(p.translatedPoint.getY(), p.translatedPoint.getX()))) // Sort points by polar angle
                .collect(toList());

        ArrayList<TranslatedPoint> lookaheadStack = new ArrayList<>();
        lookaheadStack.add(new TranslatedPoint(p0, translation));
        lookaheadStack.add(sortedPoints.get(0));
        lookaheadStack.add(sortedPoints.get(1));

        for(int i=3; i <= sortedPoints.size(); i++) {
            while(!leftTurn(nextToTop(lookaheadStack), top(lookaheadStack), getIndex(sortedPoints, i -1))) {
                lookaheadStack.remove(lookaheadStack.size() - 1);
            }
            lookaheadStack.add(sortedPoints.get(i - 1));
        }

        return lookaheadStack.stream().map(p -> p.originalPoint).collect(toList());
    }

    private Point2D getStartPoint(List<Point2D> points) {
        // Is not possible for list to be empty, so there will be a min
        double minY = points.stream().mapToDouble(Point2D::getY).min().getAsDouble();
        return points.stream()
                .filter(p -> p.getY() == minY)                      // Get all points points with lowest y
                .min(Comparator.comparing(Point2D::getX)).get();    // Get the one with lowest x
    }

    private boolean leftTurn(Point2D a, Point2D b, Point2D c) {
        double crossProduct = (c.getX() - a.getX())*(b.getY() - a.getY()) - (b.getX() - a.getX())*(c.getY() - a.getY());
        // Check for negative cross product
        return crossProduct < 0;
    }

    private Point2D top(List<TranslatedPoint> stack) {
        return stack.get(stack.size() - 1).translatedPoint;
    }

    private Point2D nextToTop(List<TranslatedPoint> stack) {
        return stack.get(stack.size() - 2).translatedPoint;
    }

    private Point2D getIndex(List<TranslatedPoint> list, int index) {
        return list.get(index).translatedPoint;
    }
}
