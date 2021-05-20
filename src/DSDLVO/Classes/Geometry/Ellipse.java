package DSDLVO.Classes.Geometry;

public class Ellipse extends Geometry {
    private HPoint center;
    private double semiMajorAxis;
    private double semiMinorAxis;
    private HPoint minorAxisReferencePoint;
    private HPoint majorAxisReferencePoint;
    private double rotation = 0;

    // It is assumed that the ellipse is always horizontal
    public Ellipse(HPoint center, double semiMajorAxis, double semiMinorAxis) {
        this.center = center;
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
        minorAxisReferencePoint = new HPoint(center.getX(), center.getY() + semiMinorAxis, 1);
        majorAxisReferencePoint = new HPoint(center.getX() + semiMajorAxis, center.getY(), 1);
    }

    @Override
    public void transform(Transformation transformation) {
        // Create ellipse data with center (0,0)
        HPoint originEllipseCenter = new HPoint(0, 0, 1);
        HPoint originEllipseMinAxisRefPoint = new HPoint(0, semiMinorAxis, 1);
        HPoint originEllipseMajAxisRefPoint = new HPoint(semiMajorAxis, 0, 1);

        // Find the translation that will translate originEllipse to actual ellipse
        HPoint centerDisplacement = displacement(center, originEllipseCenter);
        HPoint minAxisDisplacement = displacement(minorAxisReferencePoint, originEllipseMinAxisRefPoint);
        HPoint majAxisDisplacement = displacement(majorAxisReferencePoint, originEllipseMajAxisRefPoint);

        // Transform each of the points using the given transform
        originEllipseCenter.transform(transformation);
        originEllipseMinAxisRefPoint.transform(transformation);
        originEllipseMajAxisRefPoint.transform(transformation);

        // Set value of semi major and semi minor axis
        semiMinorAxis = HPointDistance(originEllipseCenter, originEllipseMinAxisRefPoint);
        semiMajorAxis = HPointDistance(originEllipseCenter, originEllipseMajAxisRefPoint);

        // Translate back to get the transformed actual ellipse
        // This is a bit of a hack to deal with 3d vectors
        originEllipseCenter.translate(centerDisplacement.getX(), centerDisplacement.getY());
        center = originEllipseCenter;
        minorAxisReferencePoint = originEllipseMinAxisRefPoint.add(minAxisDisplacement);
        majorAxisReferencePoint = originEllipseMajAxisRefPoint.add(majAxisDisplacement);

        // set the rotation
        rotation = transformation.getRotation();
    }

    private HPoint displacement(HPoint a, HPoint b) {
        return new HPoint(b.getX() - a.getX(), b.getY() - a.getY(), 1);
    }

    private double HPointDistance(HPoint a, HPoint b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    @Override
    public boolean contains(HPoint point) {
        // Long bit of math, read more at: https://stackoverflow.com/questions/7946187/point-and-ellipse-rotated-position-test-algorithm
        return Math.pow(Math.cos(rotation) * (point.getX() - center.getX()) + Math.sin(rotation) * (point.getY() - center.getY()), 2) / Math.pow(semiMajorAxis, 2) + Math.pow(Math.sin(rotation) * (point.getX() - center.getX()) - Math.cos(rotation) * (point.getY() - center.getY()), 2) / Math.pow(semiMinorAxis, 2) <= 1;
    }

    public HPoint getCenter() {
        return center;
    }
}
