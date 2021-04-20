package Dibbidut.Classes.Geometry;

public class Vector {

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
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    public double angle(Vector vector) {
        return Math.acos(dotProduct(vector)/(this.length() * vector.length()));
    }

    public void scaleProduct(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
    }

    public void divideProduct(double scalar) {
        this.x = this.x/ scalar;
        this.y = this.y/ scalar;
    }

    public Vector addVector(Vector vector) {
        return new Vector(vector.x + this.x, vector.y+ this.y);
    }

    public Vector subtractVector(Vector vector) {return new Vector(this.x - vector.x, this.y - vector.y);}


}
