package Dibbidut.Classes.Geometry;

import math.geom2d.Vector2D;

import java.util.Arrays;

public class Ellipse extends Geometry {
    private Vector center;
    private double semiMajorAxis;
    private double semiMinorAxis;
    private Vector minorAxisReferencePoint;
    private Vector majorAxisReferencePoint;
    private double rotation = 0;

    // It is assumed that the ellipse is always horizontal
    public Ellipse(Vector center, double semiMajorAxis, double semiMinorAxis) {
        this.center = center;
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
        minorAxisReferencePoint = new Vector(center.getX(), center.getY() + semiMinorAxis, 1);
        majorAxisReferencePoint = new Vector(center.getX() + semiMajorAxis, center.getY(), 1);
    }

    @Override
    public void transform(Transformation transformation) {
        // Create ellipse data with center (0,0)
        Vector originEllipseCenter = new Vector(0, 0, 1);
        Vector originEllipseMinAxisRefPoint = new Vector(0, semiMinorAxis, 1);
        Vector originEllipseMajAxisRefPoint = new Vector(semiMajorAxis, 0, 1);

        // Find the translation that will translate originEllipse to actual ellipse
        Vector centerDisplacement = displacement(center, originEllipseCenter);
        Vector minAxisDisplacement = displacement(minorAxisReferencePoint, originEllipseMinAxisRefPoint);
        Vector majAxisDisplacement = displacement(majorAxisReferencePoint, originEllipseMajAxisRefPoint);

        // Transform each of the points using the given transform
        originEllipseCenter.transform(transformation);
        originEllipseMinAxisRefPoint.transform(transformation);
        originEllipseMajAxisRefPoint.transform(transformation);

        // Set value of semi major and semi minor axis
        semiMinorAxis = vectorDistance(originEllipseCenter, originEllipseMinAxisRefPoint);
        semiMajorAxis = vectorDistance(originEllipseCenter, originEllipseMajAxisRefPoint);

        // Translate back to get the transformed actual ellipse
        // This is a bit of a hack to deal with 3d vectors
        originEllipseCenter.translate(centerDisplacement.getX(), centerDisplacement.getY());
        center = originEllipseCenter;
        minorAxisReferencePoint = originEllipseMinAxisRefPoint.addVector(minAxisDisplacement);
        majorAxisReferencePoint = originEllipseMajAxisRefPoint.addVector(majAxisDisplacement);

        // set the rotation
        rotation = transformation.getRotation();
    }

    private Vector displacement(Vector a, Vector b) {
        return new Vector(b.getX() - a.getX(), b.getY() - a.getY(), 1);
    }

    private double vectorDistance(Vector a, Vector b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public double x;
    public double y;
    public double width;
    public double height;

    @Override
    public boolean contains(Vector point) {
        // Long bit of math, read more at: https://stackoverflow.com/questions/7946187/point-and-ellipse-rotated-position-test-algorithm
        return Math.pow(Math.cos(rotation)*(point.getX() - center.getX()) + Math.sin(rotation)*(point.getY() - center.getY()),2) / Math.pow(semiMajorAxis, 2) + Math.pow(Math.sin(rotation)*(point.getX() - center.getX()) - Math.cos(rotation)*(point.getY()-center.getY()), 2) / Math.pow(semiMinorAxis, 2) <= 1;
    }

    public Vector getCenter() {
        return center;
    }
}
