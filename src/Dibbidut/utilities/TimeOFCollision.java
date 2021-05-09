package Dibbidut.utilities;

import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Geometry.Line;
import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeOFCollision {

    public static double calculate(Polygon combinedDomain, HPoint objPos, HPoint objVel, HPoint obsVel) {
        // Find all the lines that make up the polygon
        List<Line> vertices = combinedDomain.getVertices();

        HPoint futureObjPos = objPos.add(objVel);

        Line objTrajectory = new Line(objPos, futureObjPos);

        double shortestDistance = vertices.stream()
                .filter(l -> l.getDirectionVector().equals(obsVel.getUnitVector()))
                .map(v -> new HPoint(objTrajectory.getIntersection(v)))
                .mapToDouble(p -> p.distance(objPos))
                .min()
                .orElse(0);

        // Can find the direction of the line by finding the vector equation, and the displacement between two points
        return shortestDistance / objVel.length();
    }
}
