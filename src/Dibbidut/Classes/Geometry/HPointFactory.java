package Dibbidut.Classes.Geometry;

public class HPointFactory implements PointFactory {
    @Override
    public Point createPoint(double x, double y) {
        return new HPoint(x, y);
    }
}
