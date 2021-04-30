package Dibbidut.Classes.Geometry;


import java.util.Objects;

public class HPoint extends Geometry implements Comparable, Point {

    private double x;
    private double y;
    private double z;

    public HPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public HPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 1;
    }

    public HPoint(Point point) {
        this(point.getX(), point.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double dotProduct(HPoint point) {
        return point.x * this.x + point.y * this.y;
    }

    public double crossProduct(HPoint point) {
        return this.x * point.y - this.y * point.x;
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double angle(HPoint point) {
        return Math.acos(dotProduct(point) / (this.length() * point.length()));
    }

    public void scale(double scalar) {
        x = x * scalar;
        y = y * scalar;
    }

    public void divide(double scalar) {
        x = x / scalar;
        y = y / scalar;
    }

    public HPoint add(HPoint point) {
        return new HPoint(point.getX() + this.x, point.y + this.y);
    }

    public HPoint subtract(HPoint point) {
        return new HPoint(this.x - point.x, this.y - point.y);
    }

    @Override
    public void transform(Transformation transformation) {
        double [][] matrix = transformation.get();
        double[] transformedPoint = new double[3];

        for(int col = 0; col < 3; col++) {
            double res = 0;
            // This is not the most elegant solution,
            // However should anyone ask, it is optimised using loop unrolling
            res += matrix[0][col] * x;
            res += matrix[1][col] * y;
            res += matrix[2][col] * 1; // Faked 3rd dimension of the point

            transformedPoint[col] = res;
        }
        x = transformedPoint[0];
        y = transformedPoint[1];
        z = 1;
    }

    @Override
    public boolean contains(HPoint point) {
        return equals(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HPoint point = (HPoint) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0 &&
                Double.compare(point.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public int compareTo(Object point) {
        HPoint startingPoint = new HPoint(-1, 0, 1);
        return (int)(startingPoint.angle(this) - startingPoint.angle((HPoint)point));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ") ";
    }

    public HPoint copy() {
        return new HPoint(x, y, z);
    }
}
