package Dibbidut.Classes.Geometry;

public class Vector {

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
        this.z = this.z / scalar;
    }

    public Vector addVector(Vector vector) {
        return new Vector(vector.getX() + this.x, vector.getY() + this.y, vector.getZ() + this.z);
    }

    public Vector subtractVector(Vector vector) {
        return new Vector(this.x - vector.getX(), this.y - vector.getY(), this.z - vector.getZ());
    }


}
