package Dibbidut.Classes.Geometry;


import java.util.Objects;

public class HPoint extends Geometry implements Comparable {

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
        return point.getX() * this.x + point.getY() * this.y;
    }

    public double crossProduct(HPoint point) {
        return this.x * point.getY() - this.y * point.getX();
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double angle(HPoint point) {
        return Math.acos(dotProduct(point) / (this.length() * point.length()));
    }

    public double counterClockwiseAngle(HPoint point){
        double angle = this.angle(point);
        double orientation = new HPoint(0,0).orientation(this, point);

        // Source: https://vlecomte.github.io/cp-geo.pdf (page 40)
        // The cross product indicates the orientation of the two points relative to each other,
        // and therefore determines whether the angle is larger or smaller that 180 degrees
        if(orientation >= 0)
            return angle;
        else
            return Math.toRadians(360) - angle;
    }

    public double standardCounterClockwiseAngle(){
        return new HPoint(1, 0).counterClockwiseAngle(this);
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 40)
    // Returns a value indicating the orientation of point p and q relative to the calling HPoint (this)
    public double orientation(HPoint p, HPoint q) {
        HPoint temp1 = p.subtract(this);
        HPoint temp2 = q.subtract(this);
        return temp1.getX() * temp2.getY() - temp1.getY() * temp2.getX();
    }

    public void scale(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
    }

    public void divide(double scalar) {
        this.x = this.x / scalar;
        this.y = this.y / scalar;
    }

    public HPoint add(HPoint point) {
        return new HPoint(point.getX() + this.x, point.getY() + this.y);
    }

    public HPoint subtract(HPoint point) {
        return new HPoint(this.x - point.getX(), this.y - point.getY());
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
        double result = this.standardCounterClockwiseAngle() - ((HPoint)point).standardCounterClockwiseAngle();

        if (result > 0)
            return 1;
        else if (result < 0)
            return -1;
        else
            return 0;
    }

    public PolarPoint toPolarPoint(){
        double length = this.length();
        double angle = this.standardCounterClockwiseAngle();
        return new PolarPoint(length, angle);
    }
}
