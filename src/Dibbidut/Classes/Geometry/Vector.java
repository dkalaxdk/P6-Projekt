package Dibbidut.Classes.Geometry;


import java.util.Objects;

public class Vector extends Geometry implements Comparable {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double dotProduct(Vector vector) {
        return vector.getX() * this.x + vector.getY() * this.y + this.z * vector.getZ();
    }

    public Vector crossProduct(Vector vector) {
        return new Vector(this.y * vector.getZ() - this.z * vector.getY(), this.z * vector.getX() - this.x * vector.z, this.x * vector.getY() - this.y * vector.getX());
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double angle(Vector vector) {
        return Math.acos(dotProduct(vector) / (this.length() * vector.length()));
    }

    public void scaleProduct(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        this.z = this.z * scalar;
    }

    public void divideProduct(double scalar) {
        this.x = this.x / scalar;
        this.y = this.y / scalar;
    }

    public Vector addVector(Vector vector) {
        return new Vector(vector.getX() + this.x, vector.getY() + this.y, vector.getZ() + this.z);
    }

    public Vector subtractVector(Vector vector) {
        return new Vector(this.x - vector.getX(), this.y - vector.getY(), this.z - vector.getZ());
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
            res += matrix[2][col] * 1; // Faked 3rd dimension of the vector

            transformedPoint[col] = res;
        }
        x = transformedPoint[0];
        y = transformedPoint[1];
        z = 1;
    }

    @Override
    public boolean contains(Vector point) {
        Vector pointAsVector = new Vector(point.getX(), point.getY(), point.getZ());
        return equals(pointAsVector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0 &&
                Double.compare(vector.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public int compareTo(Object vector) {
        Vector startingPoint = new Vector(-1, 0, 1);
        return (int)(startingPoint.angle(this) - startingPoint.angle((Vector)vector));
    }
}
