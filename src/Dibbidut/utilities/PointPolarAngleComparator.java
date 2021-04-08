package Dibbidut.utilities;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class PointPolarAngleComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D p1, Point2D p2) {
        double p1Angle = Math.atan2(p1.getY(), p1.getX());
        double p2Angle = Math.atan2(p2.getY(), p2.getX());
        return Double.compare(p1Angle, p2Angle);
    }
}
