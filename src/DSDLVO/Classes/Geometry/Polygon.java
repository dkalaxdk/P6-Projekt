package DSDLVO.Classes.Geometry;

import DSDLVO.utilities.GrahamScan;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Polygon extends Geometry {

    public ArrayList<HPoint> coordinates;
    public HPoint referencePoint;


    public Polygon(ArrayList<HPoint> coordinates) {
        this.coordinates = coordinates;
        calculateCenter();
    }

    public Polygon(ArrayList<HPoint> coordinates, HPoint referencePoint) {
        this.coordinates = coordinates;
        this.referencePoint = referencePoint;
    }

    @Override
    public void transform(Transformation transformation) {
        ArrayList<HPoint> newCoordinates = new ArrayList<>();

        // Translates the polygon so its center is in 0,0
        Transformation toCenter = new Transformation();
        toCenter.translate(-referencePoint.getX(), -referencePoint.getY());     // Translation points to center around origin
        Transformation fromCenter = new Transformation();
        fromCenter.translate(referencePoint.getX(), referencePoint.getY());     // Translation points back

        Transformation t = new Transformation()
                .add(toCenter)
                .add(transformation)
                .add(fromCenter);

        ArrayList<HPoint> copiedCoordinates = new ArrayList<>(copyHPointList(coordinates));

        // Performs the transformation
        for (HPoint point : copiedCoordinates) {
            point.transform(t);
            newCoordinates.add(point);
        }

        coordinates = newCoordinates;

        referencePoint.transform(t);
    }

    // Adapted from: https://vlecomte.github.io/cp-geo.pdf (page 61-
    // Checks if the point is contained within the polygon
    @Override
    public boolean contains(HPoint point) {
        int numberOfCrossings = 0;
        HPoint p;
        HPoint q;
        for (int i = 0; i < coordinates.size(); i++) {
            p = coordinates.get(i);
            q = coordinates.get((i + 1) % coordinates.size());
            if (onSegment(point, p, q))
                return false;

            if (crossesRay(point, p, q))
                numberOfCrossings++;
        }
        return numberOfCrossings % 2 == 1;
    }

    public boolean inOrOn(HPoint point) {
        int numberOfCrossings = 0;
        HPoint p;
        HPoint q;
        for (int i = 0; i < coordinates.size(); i++) {
            p = coordinates.get(i);
            q = coordinates.get((i + 1) % coordinates.size());
            if (onSegment(point, p, q))
                return true;

            if (crossesRay(point, p, q))
                numberOfCrossings++;
        }
        return numberOfCrossings % 2 == 1;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 54)
    // Checks if point a is in line with the two end points of the segment
    public boolean onSegment(HPoint a, HPoint p, HPoint q) {
        return a.orientation(p, q) == 0 && inDisk(a, p, q);
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 54)
    // Checks if point a is withing the disk with diameter |pq| and which is placed between p and q
    public boolean inDisk(HPoint a, HPoint p, HPoint q) {
        return p.subtract(a).dotProduct(q.subtract(a)) <= 0;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 61)
    // Checks if the segment between p and q crosses a line (ray) going out from point a
    public boolean crossesRay(HPoint a, HPoint p, HPoint q) {
        int n = q.getY() >= a.getY() ? 1 : 0;
        int m = p.getY() >= a.getY() ? 1 : 0;
        return (n - m) * a.orientation(p, q) > 0;
    }

    public Polygon combineWith(Polygon polygon) {
        List<Point> list = new ArrayList<>();
        for (HPoint vertex1 : this.coordinates) {
            for (HPoint vertex2 : polygon.coordinates) {
                list.add(vertex1.add(polygon.referencePoint.subtract(vertex2)));
            }
        }

        GrahamScan convHull = new GrahamScan(new HPointFactory());
        ArrayList<HPoint> newPolygonVertices = new ArrayList<>();

        for (Point p : convHull.Calculate(list)) {
            newPolygonVertices.add(new HPoint(p.getX(), p.getY(), 1));
        }

        return new Polygon(newPolygonVertices, this.referencePoint.copy());
    }

    public void calculateCenter() {
        double x = 0;
        double y = 0;
        int count = 0;

        if (coordinates == null || coordinates.size() == 0) {
            referencePoint = null;
        } else {
            for (HPoint point : coordinates) {
                x = x + point.getX();
                y = y + point.getY();

                count++;
            }

            referencePoint = new HPoint(x / count, y / count, 1);
        }
    }

    public Polygon copy() {
        ArrayList<HPoint> list = new ArrayList<>();
        for (HPoint point : coordinates) {
            list.add(point.copy());
        }
        return new Polygon(list, new HPoint(referencePoint.getX(), referencePoint.getY()));
    }

    private List<HPoint> copyHPointList(List<HPoint> list) {
        return list.stream().map(p -> p.copy()).collect(toList());
    }

    public List<Line> getVertices() {
        ArrayList<Line> vertices = new ArrayList();

        // Create lines between all points
        // Last point is excluded and handled separately
        for (int i = 0; i < coordinates.size() - 1; i++) {
            vertices.add(new Line(coordinates.get(i), coordinates.get(i + 1)));
        }
        HPoint lastPoint = coordinates.get(coordinates.size() - 1);
        HPoint firstPoint = coordinates.get(0);
        vertices.add(new Line(lastPoint, firstPoint));

        return vertices;
    }
}
