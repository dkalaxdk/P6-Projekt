package DSDLVO.Classes.Geometry;

import java.util.Objects;

public class Line {
    private double slope;
    private double yAxisIntersection;

    public Line(double slope, double yAxisIntersection) {
        this.slope = slope;
        this.yAxisIntersection = yAxisIntersection;
    }

    public Line(Point pointOne, Point pointTwo) {
        double changeInY = pointTwo.getY() - pointOne.getY();
        double changeInX = pointTwo.getX() - pointOne.getX();
        // Handle vertical line
        if(isInvalidValue(changeInX)) {
            slope = pointOne.getX();
            yAxisIntersection = Double.NaN;
        }
        else {
            // Handle horizontal line. If changeInY is 0 and changeInX is negative slope will be -0 but should be 0
            slope = changeInY == 0 ? 0 : changeInY/changeInX;
            yAxisIntersection = pointOne.getY() - slope * pointOne.getX();
        }
    }

    private boolean isInvalidValue(double value) {
        return Double.isInfinite(value) || Double.isNaN(value) || value == 0;
    }

    public double getSlope() {
        return slope;
    }

    public double getYAxisIntersection() {
        return yAxisIntersection;
    }

    public Point getIntersection(Line other) {
        double intersectionX = (other.yAxisIntersection - this.yAxisIntersection) / (this.slope - other.slope);
        double intersectionY = intersectionX + this.yAxisIntersection;
        if(isVertical())
            intersectionX = slope;
        if(other.isVertical())
            intersectionX = other.slope;
        if(isHorizontal())
            intersectionY = yAxisIntersection;
        if(other.isHorizontal())
            intersectionY = other.yAxisIntersection;
        return new HPoint(intersectionX, intersectionY);
    }

    public boolean isVertical() {
        return Double.isNaN(yAxisIntersection);
    }

    public boolean isHorizontal() {
        return slope == 0;
    }

    public HPoint getDirectionVector() {
        if(isVertical())
            return new HPoint(0, 1);
        // Find two points on the line
        HPoint p1 = new HPoint(0, yAxisIntersection);               // x = 0
        HPoint p2 = new HPoint(1, slope + yAxisIntersection);    // x = 1

        HPoint directionVector = p2.subtract(p1);
        directionVector.divide(directionVector.length());   // Calculate unit vector
        return directionVector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.slope, slope) == 0 && Double.compare(line.yAxisIntersection, yAxisIntersection) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slope, yAxisIntersection);
    }
}
