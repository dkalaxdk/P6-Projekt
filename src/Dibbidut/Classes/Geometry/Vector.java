package Dibbidut.Classes.Geometry;


import java.util.Objects;

public class Vector extends Geometry implements Point {

    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double dotProduct(Vector vector) {
        return vector.x * this.x + vector.y * this.y;
    }


    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double angle(Vector vector) {
        return Math.acos(dotProduct(vector) / (this.length() * vector.length()));
    }

    public void scaleProduct(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
    }

    public void divideProduct(double scalar) {
        this.x = this.x / scalar;
        this.y = this.y / scalar;
    }

    public Vector addVector(Vector vector) {
        return new Vector(vector.x + this.x, vector.y + this.y);
    }

    public Vector subtractVector(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y);
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
    }

    @Override
    public boolean contains(Point point) {
        Vector pointAsVector = new Vector(point.getX(), point.getY());
        return equals(pointAsVector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
