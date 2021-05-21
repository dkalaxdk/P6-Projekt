package DSDLVO.Utilities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

public class ShapeBorder {

    public static ArrayList<Point2D> getBorder(Shape shape) {
        PathIterator path = shape.getPathIterator(new AffineTransform());
        ArrayList<Point2D> points = new ArrayList<>();
        while (!path.isDone()) {
            points.addAll(getSegmentCoordinates(path));
            path.next();
        }

        // Creating a set with the elements ensures there are no duplicate elements
        HashSet<Point2D> pointList = new HashSet<>(points);

        return new ArrayList<Point2D>(pointList);
    }

    private static ArrayList<Point2D> getSegmentCoordinates(PathIterator path) {
        double[] segment = new double[6];
        int type = path.currentSegment(segment);

        // An array able to contain 3 points, how many points are actually provided depends on the type of the segment
        int numberOfElements = getNumberOfCoordinates(type);

        ArrayList<Point2D> points = new ArrayList<>();
        for (int i = 0; i < numberOfElements; i += 2) {
            points.add(new Point2D.Double(segment[i], segment[i + 1]));
        }
        return points;
    }

    private static int getNumberOfCoordinates(int type) {
        int number = 0;
        switch (type) {
            case PathIterator.SEG_CLOSE:
                number = 0;
                break;
            case PathIterator.SEG_LINETO:
            case PathIterator.SEG_MOVETO:
                number = 2;
                break;
            case PathIterator.SEG_QUADTO:
                number = 4;
                break;
            case PathIterator.SEG_CUBICTO:
                number = 6;
                break;
        }
        return number;
    }

}
