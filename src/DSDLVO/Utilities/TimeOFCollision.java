package DSDLVO.Utilities;

import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Geometry.Line;
import DSDLVO.Classes.Geometry.Polygon;

import java.util.List;

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
