package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.*;
import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Interfaces.IVelocityObstacle;
import Dibbidut.utilities.ConvexHull;
import Dibbidut.utilities.GrahamScan;
import Dibbidut.utilities.ShapeBorder;

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
    public Geometry Calculate(Point objectPos, Geometry obstacleDomain, Point obstaclePos, HPoint obstacleVel, double timeFrame) {
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
        HPoint objPos = new HPoint(objectPos.getX(), objectPos.getY(), 1);
        HPoint obsPos = new HPoint(obstaclePos.getX(), obstaclePos.getY(), 1);
        HPoint centerCollisionAtTZero = displacement(objPos, obsPos); // This is the position it should be translated to, not
        HPoint displacement = displacement(obsPos, centerCollisionAtTZero);
        Transformation transAtTZero = new Transformation()
                .translate(displacement.getX(), displacement.getY());

        Polygon obsDomainOriginal = (Polygon)obstacleDomain; // The actual instance from the ship class. Should not be mutated
        Polygon obsDomainAtTZero = copyPolygon(obsDomainOriginal);   // Not sure how to generalize this approach
        // Copies the points
        obsDomainAtTZero.transform(transAtTZero);

        HPoint centerCollisionAtT = copyHPoint(centerCollisionAtTZero);
        centerCollisionAtT.divide(timeframe);
        HPoint displacement2 = displacement(obsPos, centerCollisionAtT);
        Transformation transAtT = new Transformation()
                .scale(1/timeframe, 1/timeframe)
                .translate(displacement2.getX(), displacement2.getY());

        Polygon obsDomainAtT = copyPolygon(obsDomainOriginal);
        obsDomainAtT.transform(transAtT);

        ArrayList<Point> VOPoints = new ArrayList<>(obsDomainAtTZero.coordinates);
        VOPoints.addAll(obsDomainAtT.coordinates);

        // FIXME: There is a lot being handled here that should be handled at a higher level
        GrahamScan convHull = new GrahamScan(new HPointFactory());
        ArrayList<HPoint> VOPolygonVertices = new ArrayList<>();

        for(Point p : convHull.Calculate(VOPoints))
            VOPolygonVertices.add(new HPoint(p.getX(), p.getY(), 1));

        return new Polygon(VOPolygonVertices);
    }

    private HPoint displacement(HPoint vec1, HPoint vec2) {
        return new HPoint(vec2.getX() - vec1.getX(), vec2.getY() - vec1.getY(), 1);
    }

    private Polygon copyPolygon(Polygon poly) {
        ArrayList<HPoint> copiedPoints = new ArrayList(poly.coordinates.stream().map(p -> copyHPoint(p)).collect(toList()));
        return new Polygon(copiedPoints);
    }

    private HPoint copyHPoint(HPoint vec) {
        return new HPoint(vec.getX(), vec.getY(), vec.getZ());
    }
}
