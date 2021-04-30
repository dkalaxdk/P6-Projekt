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

    public Geometry RelativeVO(Point objectPos, Geometry obstacleDomain, Point obstaclePos, double timeframe) {
        HPoint objPos = new HPoint(objectPos);
        HPoint obsPos = new HPoint(obstaclePos);

        Polygon obsDomainAtT1 = (Polygon)createRelativeVOAtTime(objPos, obstacleDomain, obsPos, 1);

        Polygon obsDomainAtEndOfTimeFrame = (Polygon)createRelativeVOAtTime(objPos, obstacleDomain, obsPos, timeframe);

        ArrayList<Point> VOPoints = new ArrayList<>(obsDomainAtT1.coordinates);
        VOPoints.addAll(obsDomainAtEndOfTimeFrame.coordinates);

        // FIXME: There is a lot being handled here that should be handled at a higher level
        GrahamScan convHull = new GrahamScan(new HPointFactory());
        ArrayList<HPoint> VOPolygonVertices = new ArrayList<>();

        for(Point p : convHull.Calculate(VOPoints))
            VOPolygonVertices.add(new HPoint(p.getX(), p.getY(), 1));

        return new Polygon(VOPolygonVertices);
    }

    private Geometry createRelativeVOAtTime(HPoint objPos, Geometry obsDomain, HPoint obsPos, double time) {
        Transformation transToCollisionAtTime1 = calculateTransformationToCollisionAtTime(objPos, obsPos, time);

        Polygon obsDomainOriginal = (Polygon)obsDomain; // The actual instance from the ship class. Should not be mutated
        // Copies the polygon
        Polygon realtiveVO = copyPolygon(obsDomainOriginal);  //TODO: add copy method to polygon
        realtiveVO.transform(transToCollisionAtTime1);

        return realtiveVO;
    }

    private Transformation calculateTransformationToCollisionAtTime(HPoint objPos, HPoint obsPos, double time) {
        HPoint collisionRelativeToObject = obsPos.subtract(objPos);
        HPoint collisionRelativeToOrigin = objPos.add(collisionRelativeToObject);
        HPoint collisionAtTRelativeToOrigin = collisionRelativeToOrigin.copy();
        collisionAtTRelativeToOrigin.divide(time);  // Scale the collision position to the given time
        return new Transformation()
                .scale(1/time, 1/time)                          // Scale domain by time frame
                .translate(collisionAtTRelativeToOrigin.subtract(obsPos));    // Center domain around collision

    }

    private Polygon copyPolygon(Polygon poly) {
        ArrayList<HPoint> copiedPoints = new ArrayList(poly.coordinates.stream().map(p -> copyHPoint(p)).collect(toList()));
        return new Polygon(copiedPoints);
    }

    private HPoint copyHPoint(HPoint vec) {
        return new HPoint(vec.getX(), vec.getY(), vec.getZ());
    }
}
