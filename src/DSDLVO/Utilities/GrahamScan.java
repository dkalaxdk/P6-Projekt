package DSDLVO.Utilities;

import DSDLVO.Classes.Geometry.Point;
import DSDLVO.Classes.Geometry.PointFactory;
import DSDLVO.Classes.Geometry.PolarPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

// TODO: Refactor this class to implement Point interface, and adapt usages
class TranslatedPoint {
    public Point originalPoint;
    public Point translatedPoint;
    public PolarPoint polarPoint;

    public TranslatedPoint(PointFactory factory, Point originalPoint, Point translation) {
        this.originalPoint = originalPoint;
        translatedPoint = factory.createPoint(originalPoint.getX() + translation.getX(), originalPoint.getY() + translation.getY());
        polarPoint = translatedPoint.toPolarPoint();
    }

    // Could make getters for X and Y so it is not necessary to access them through the translatedPoint field

}

// Could benefit from ConvexHull being made an abstract class.
// This would remove the meed to have the calculate method and the hull could be produced when an instance is created
// and be made available through a getter. Or it could be calculated in the getter.
public class GrahamScan implements ConvexHull<Point> {
    private final PointFactory factory;

    public GrahamScan(PointFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Point> Calculate(List<Point> points) {
        // Remove duplicate points
        List<Point> uniquePoints = new ArrayList<>(new HashSet<>(points));
        if (uniquePoints.size() < 3) {
            throw new IllegalArgumentException("Arguments must contain collection with at least three points");
        }
        // Find point with lowest y, if multiple then select leftmost
        Point p0 = getStartPoint(uniquePoints);

        Point translation = factory.createPoint(0 - p0.getX(), 0 - p0.getY());

        // Translate points so they are relative to p0 and sort by polar angle
        List<TranslatedPoint> sortedPoints = uniquePoints.stream()
                .filter(p -> p != p0)   // Remove p0 from list
                .map(p -> new TranslatedPoint(factory, p, translation))  // Translate points so they are relative to p0
                .distinct()
                .sorted(Comparator.comparingDouble(p -> p.polarPoint.angle)) // Sort points by polar angle
                .collect(toList());

        sortedPoints = removeDuplicatePolarAngles(sortedPoints);

        ArrayList<TranslatedPoint> lookaheadStack = new ArrayList<>();
        lookaheadStack.add(new TranslatedPoint(factory, p0, translation));
        lookaheadStack.add(sortedPoints.get(0));
        lookaheadStack.add(sortedPoints.get(1));

        for (int i = 3; i <= sortedPoints.size(); i++) {
            while (!leftTurn(nextToTop(lookaheadStack), top(lookaheadStack), getIndex(sortedPoints, i - 1))) {
                lookaheadStack.remove(lookaheadStack.size() - 1);
            }
            lookaheadStack.add(sortedPoints.get(i - 1));
        }

        return lookaheadStack.stream().map(p -> p.originalPoint).collect(toList());
    }

    private Point getStartPoint(List<Point> points) {
        // Is not possible for list to be empty, so there will be a min
        double minY = points.stream().mapToDouble(Point::getY).min().getAsDouble();
        return points.stream()
                .filter(p -> p.getY() == minY)                      // Get all points points with lowest y
                .min(Comparator.comparing(Point::getX)).get();    // Get the one with lowest x
    }

    // If a number of points have the same angle, all but the one with the longest polar length are removed
    private List<TranslatedPoint> removeDuplicatePolarAngles(List<TranslatedPoint> points) {
        ArrayList<TranslatedPoint> tempList = new ArrayList<>();
        List<TranslatedPoint> result = new ArrayList<>();
        double angle = points.get(0).polarPoint.angle;

        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).polarPoint.angle == angle && i + 1 < points.size()) {
                tempList.add(points.get(i));
            } else if (points.get(i).polarPoint.angle == angle && i + 1 == points.size()) {
                tempList.add(points.get(i));

                TranslatedPoint temp = tempList.get(0);
                for (TranslatedPoint p : tempList) {
                    if (Utility.roundToFourDecimals(p.polarPoint.length) > Utility.roundToFourDecimals(temp.polarPoint.length))
                        temp = p;
                }
                result.add(temp);
            } else if (points.get(i).polarPoint.angle != angle && i + 1 < points.size()) {
                TranslatedPoint temp = tempList.get(0);
                for (TranslatedPoint p : tempList) {
                    if (Utility.roundToFourDecimals(p.polarPoint.length) > Utility.roundToFourDecimals(temp.polarPoint.length))
                        temp = p;
                }
                result.add(temp);

                tempList.clear();
                angle = points.get(i).polarPoint.angle;
                tempList.add(points.get(i));
            } else {
                TranslatedPoint temp = tempList.get(0);
                for (TranslatedPoint p : tempList) {
                    if (Utility.roundToFourDecimals(p.polarPoint.length) > Utility.roundToFourDecimals(temp.polarPoint.length))
                        temp = p;
                }
                result.add(temp);

                result.add(points.get(i));
            }
        }

        return result;
    }

    private boolean leftTurn(Point a, Point b, Point c) {
        double crossProduct = (c.getX() - a.getX()) * (b.getY() - a.getY()) - (b.getX() - a.getX()) * (c.getY() - a.getY());
        // Check for negative cross product
        return crossProduct < 0;
    }

    private Point top(List<TranslatedPoint> stack) {
        return stack.get(stack.size() - 1).translatedPoint;
    }

    private Point nextToTop(List<TranslatedPoint> stack) {
        return stack.get(stack.size() - 2).translatedPoint;
    }

    private Point getIndex(List<TranslatedPoint> list, int index) {
        return list.get(index).translatedPoint;
    }

    private List<TranslatedPoint> removeDuplicates(List<TranslatedPoint> points) {
        return new ArrayList<>(new HashSet<>(points));
    }
}
