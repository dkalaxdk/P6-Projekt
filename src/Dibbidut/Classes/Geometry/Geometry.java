package Dibbidut.Classes.Geometry;

public abstract class Geometry {
    public abstract void transform(Transformation transformation);

    public abstract boolean contains(Point point);

    public void rotate(double degrees) {
        Transformation t = new Transformation();
        t.rotate(degrees);
        transform(t);
    }

    public void scale(double width, double height) {
        Transformation t = new Transformation();
        t.scale(width, height);
        transform(t);
    }

    public void translate(double x, double y) {
        Transformation t = new Transformation();
        t.translate(x, y);
        transform(t);
    }
}
