package Dibbidut.Classes.Geometry;

public class VectorFactory implements PointFactory {
    @Override
    public Point createPoint(double x, double y) {
        return new Vector(x, y, 1);
    }
}
