package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.*;
import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Interfaces.IVelocityObstacle;
import Dibbidut.utilities.ConvexHull;
import Dibbidut.utilities.GrahamScan;
import Dibbidut.utilities.ShapeBorder;
import math.geom2d.Vector2D;

import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class VelocityObstacle implements IVelocityObstacle {
    public VO velocities;

    @Override
    public Geometry Calculate(Point objectPos, Geometry obstacleDomain, Point obstaclePos, Vector obstacleVel, double timeFrame) {
        Transformation t = new Transformation().translate(obstacleVel.getX(), obstacleVel.getY());
        Geometry relativeVO = RelativeVO(objectPos, obstacleDomain, obstaclePos, timeFrame);
        relativeVO.transform(t);
        return relativeVO;
    }

    @Override
    public VO Merge(VO to, VO from) {
        return null;
    }

    // Possible this should be moved to obstacle
    // To adapt this to dynamic ship domains perhaps switch Ellipse for the Shape interface
    public Ellipse2D.Double ConflictRegion(Point point, int radius) {
        return new Ellipse2D.Double(point.getX(), point.getY(), radius, radius);
    }

    public Geometry RelativeVO(Point objectPos, Geometry obstacleDomain, Point obstaclePos, double timeframe) {
        Vector objPos = new Vector(objectPos.getX(), objectPos.getY(), 1);
        Vector obsPos = new Vector(obstaclePos.getX(), obstaclePos.getY(), 1);
        Vector centerCollisionAtTZero = displacement(objPos, obsPos); // This is the position it should be translated to, not
        Vector displacement = displacement(obsPos, centerCollisionAtTZero);
        Transformation transAtTZero = new Transformation()
                .translate(displacement.getX(), displacement.getY());

        Polygon obsDomainOriginal = (Polygon)obstacleDomain; // The actual instance from the ship class. Should not be mutated
        Polygon obsDomainAtTZero = copyPolygon(obsDomainOriginal);   // Not sure how to generalize this approach
        // Copies the points
        obsDomainAtTZero.transform(transAtTZero);

        Vector centerCollisionAtT = copyVector(centerCollisionAtTZero);
        centerCollisionAtT.divideProduct(timeframe);
        Vector displacement2 = displacement(obsPos, centerCollisionAtT);
        Transformation transAtT = new Transformation()
                .scale(1/timeframe, 1/timeframe)
                .translate(displacement2.getX(), displacement2.getY());

        Polygon obsDomainAtT = copyPolygon(obsDomainOriginal);
        obsDomainAtT.transform(transAtT);

        ArrayList<Point> VOPoints = new ArrayList<>(obsDomainAtTZero.coordinates);
        VOPoints.addAll(obsDomainAtT.coordinates);

        // FIXME: There is a lot being handled here that should be handled at a higher level
        GrahamScan convHull = new GrahamScan(new VectorFactory());
        ArrayList<Vector> VOPolygonVertices = new ArrayList<>();

        for(Point p : convHull.Calculate(VOPoints))
            VOPolygonVertices.add(new Vector(p.getX(), p.getY(), 1));

        return new Polygon(VOPolygonVertices);
    }

    private Vector displacement(Vector vec1, Vector vec2) {
        return new Vector(vec2.getX() - vec1.getX(), vec2.getY() - vec1.getY(), 1);
    }

    private Polygon copyPolygon(Polygon poly) {
        ArrayList<Vector> copiedPoints = new ArrayList(poly.coordinates.stream().map(p -> copyVector(p)).collect(toList()));
        return new Polygon(copiedPoints);
    }

    private Vector copyVector(Vector vec) {
        return new Vector(vec.getX(), vec.getY(), vec.getZ());
    }

    private AffineTransform getTranslation(Vector2D objectPos, Vector2D obstaclePos, double timeframe) {
        Vector2D displacement = Displacement(objectPos, obstaclePos);
        Vector2D centerCollisionAtTime = divideHPointByScalar(displacement, timeframe);

        // Calculate the translation that will place the conflictRegion at centerCollision
        Vector2D translationVec = Displacement(obstaclePos, centerCollisionAtTime);
        AffineTransform translation = new AffineTransform();
        translation.translate(translationVec.x(), translationVec.y());
        return translation;
    }

    // This finds the velocity needed to place Point A at Point B
    public Vector2D Displacement(Vector2D a, Vector2D b) {
        return new Vector2D(b.x() - a.x(), b.y() - a.y());
    }

    private Vector2D divideVectorByScalar(Vector2D vec, double scalar) {
        return new Vector2D(vec.x() / scalar, vec.y() / scalar);
    }

    // The currentVel should be the relative velocity,
    // then the target can be considered stationary
    public boolean WillCollide(Point2D currentPos, Vector2D currentVel, Shape target, Double timeFrame) {
        Point2D.Double posAtTime = translatePoint(currentPos, currentVel.times(timeFrame));
        Line2D.Double path = new Line2D.Double(currentPos, posAtTime);
        // Is the target placed on the path
        return path.intersects(target.getBounds2D());
    }

    private Point2D.Double translatePoint(Point2D point, Vector2D translation) {
        return new Point2D.Double(point.getX() + translation.x(), point.getY() + translation.y());
    }
}
