package Dibbidut.Classes.Geometry;

import Dibbidut.Exceptions.PolygonNotCenteredOnOrigin;
import Dibbidut.utilities.GrahamScan;
import com.seisw.util.geom.Poly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Polygon extends Geometry {

    public ArrayList<HPoint> coordinates;
    public ArrayList<PolarPoint> PolarCoordinates;
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
        //calculateCenter();
    }

    private List<HPoint> copyHPointList(List<HPoint> list) {
        return list.stream().map(p -> p.copy()).collect(toList());
    }

    private HPoint copyHPoint(HPoint point) {
        return new HPoint(point.getX(), point.getY(), point.getZ());
    }

//    public ArrayList<HPoint> translatePolygon(ArrayList<HPoint> coordinates, double[][] translationMatrix) {
//        ArrayList<HPoint> newCoordinates = new ArrayList<>();
//
//        for (HPoint point : coordinates) {
//            newCoordinates.add(matrixHPointProduct(translationMatrix, point));
//        }
//
//        return newCoordinates;
//    }

//    public HPoint matrixHPointProduct(double[][] matrix, HPoint point) {
//        double x = matrix[0][0] * point.getX() + matrix[1][0] * point.getY() + matrix[2][0] * point.getZ();
//        double y = matrix[0][1] * point.getX() + matrix[1][1] * point.getY() + matrix[2][1] * point.getZ();
//        double z = matrix[0][2] * point.getX() + matrix[1][2] * point.getY() + matrix[2][2] * point.getZ();
//
//        return new HPoint(x, y, z);
//    }

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

    public void calculateCenter() {
        double x = 0;
        double y = 0;
        int count = 0;

        if (coordinates == null || coordinates.size() == 0) {
            referencePoint = null;
        }
        else {
            for (HPoint point : coordinates) {
                x = x + point.getX();
                y = y + point.getY();

                count++;
            }

            referencePoint = new HPoint(x / count, y / count, 1);
        }
    }

    public Polygon addPolygon(Polygon polygon) throws PolygonNotCenteredOnOrigin {
        ArrayList<HPoint> newCoordinates = new ArrayList<>();

        Polygon polygon1 = this.makeCopy();
        Polygon polygon2 = polygon.makeCopy();

        polygon1.translate(-polygon1.referencePoint.getX(), -polygon1.referencePoint.getY());
        polygon2.translate(-polygon2.referencePoint.getX(), -polygon2.referencePoint.getY());

        polygon1.convertCoordinatesToPolarCoordinates();
        polygon2.convertCoordinatesToPolarCoordinates();

        for (PolarPoint point : polygon1.PolarCoordinates) {
            newCoordinates.add(calculateNewHPoint(point, polygon2));
        }

        for (PolarPoint point : polygon2.PolarCoordinates) {
            newCoordinates.add(calculateNewHPoint(point, polygon1));
        }

        newCoordinates = removeDuplicates(newCoordinates);

        Polygon newPolygon = new Polygon(newCoordinates,
                new HPoint(polygon1.referencePoint.getX(), polygon1.referencePoint.getY()));
        newPolygon.sortCoordinates();
        newPolygon.translate(this.referencePoint.getX(), this.referencePoint.getY());

        return newPolygon;
    }

    public Polygon addPolygon2(Polygon polygon) {
        List<Point> list = new ArrayList<>();
        for (HPoint vertex1 : this.coordinates){
            for (HPoint vertex2 : polygon.coordinates){
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

    public Polygon makeCopy() {
        ArrayList<HPoint> list = new ArrayList<>();
        for (HPoint point : coordinates) {
            list.add(new HPoint(point.getX(), point.getY()));
        }
        return new Polygon(list, new HPoint(referencePoint.getX(), referencePoint.getY()));
    }

    public HPoint calculateNewHPoint(PolarPoint point, Polygon polygon) throws PolygonNotCenteredOnOrigin {

        PolarPoint newPoint = new PolarPoint(point.length + polygon.lengthToEdgeAtAngle(point.angle),
                point.angle);

        return newPoint.toHPoint();
    }

    public double lengthToEdgeAtAngle(double angle) throws PolygonNotCenteredOnOrigin {
        ArrayList<PolarPoint> segment = this.findSegmentAtAngle(angle);
        PolarPoint intersection = findIntersection(segment, angle);
        return intersection.length;
    }

    // todo: expand polygons with each other around ship and not center

    public ArrayList<PolarPoint> findSegmentAtAngle(double angle) throws PolygonNotCenteredOnOrigin {


        if(!this.contains(new HPoint(0,0)))
            throw new PolygonNotCenteredOnOrigin();

        Collections.sort(PolarCoordinates);

        ArrayList<PolarPoint> segment = new ArrayList<>();
        for (int i = 0; i < PolarCoordinates.size(); i++) {
            PolarPoint point1 = PolarCoordinates.get(i);
            PolarPoint point2 = PolarCoordinates.get((i + 1) % PolarCoordinates.size());

            if (Math.abs(point1.angle - angle) < 0.000001) {
                segment.add(point1);
                return segment;
            }
            else if ((point1.angle < angle && point2.angle > angle) ||
                    (i == (coordinates.size() - 1) && (point1.angle < angle || point2.angle > angle))) {
                segment.add(point1);
                segment.add(point2);
            }
        }
        return segment;
    }

    public PolarPoint findIntersection(ArrayList<PolarPoint> segment, double angle) {
        if (segment.size() == 1)
            return segment.get(0);

        PolarPoint segPoint1;
        PolarPoint segPoint2;

        if (segment.get(0).angle < segment.get(1).angle){
            segPoint1 = segment.get(0);
            segPoint2 = segment.get(1);
        }
        else {
            segPoint1 = segment.get(1);
            segPoint2 = segment.get(0);
        }

        double C1;
        if(segPoint1.angle - segPoint2.angle <= Math.toRadians(180)){
             C1 = segPoint2.angle - segPoint1.angle;
        }
        else
            C1 = segPoint1.angle + Math.toRadians(360) - segPoint2.angle;

        double a = segPoint1.length;
        double b = segPoint2.length;

        double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - 2 * a * b * Math.cos(C1));

        double B = Math.asin(b * Math.sin(C1) / c);

        double C2 = angle - segPoint1.angle;

        double A = Math.toRadians(180) - B - C2;

        double length = a / Math.sin(A) * Math.sin(B);

        return new PolarPoint(length, angle);
    }

    public void sortCoordinates(){
        HPoint c = copyHPoint(referencePoint);

        this.translate(-c.getX(), -c.getY());
        Collections.sort(coordinates);
        this.translate(c.getX(), c.getY());
    }

    public void convertCoordinatesToPolarCoordinates(){
        PolarCoordinates = new ArrayList<>();
        for(HPoint point : coordinates){
            PolarCoordinates.add(point.toPolarPoint());
        }
    }

    public ArrayList<HPoint> removeDuplicates(ArrayList<HPoint> list){
        ArrayList<HPoint> newList = new ArrayList<>();
        boolean duplicate;
        for (HPoint point : list){
            duplicate = false;
            for (HPoint newPoint : newList){
                if (Math.abs (point.getX() - newPoint.getX()) < 0.00001 &&  Math.abs (point.getY() - newPoint.getY()) < 0.00001){
                    duplicate = true;
                }
            }
            if (!duplicate)
                newList.add(point);
        }
        return newList;
    }

    public Polygon flipInDirectionOf(HPoint point){

        // Translate polygon to origin
        Polygon polygon = this.makeCopy();
        polygon.translate(-polygon.referencePoint.getX(), -polygon.referencePoint.getY());

        // Translate point
        HPoint tempPoint = new HPoint(point.getX(), point.getY());
        tempPoint.translate(-this.referencePoint.getX(), -this.referencePoint.getY());

        // convert HPoints to PolarPoints
        polygon.convertCoordinatesToPolarCoordinates();
        PolarPoint mirrorPoint = tempPoint.toPolarPoint();

        mirrorPoint.angle = (mirrorPoint.angle + Math.toRadians(90)) % Math.toRadians(360);

        ArrayList<HPoint> list = new ArrayList<>();
        for(PolarPoint p : polygon.PolarCoordinates){
            double angleDiff = mirrorPoint.angle - p.angle;
            double newAngle = mirrorPoint.angle + angleDiff;

            if (newAngle > Math.toRadians(360))
                newAngle -= Math.toRadians(360);
            else if (newAngle < 0)
                newAngle += Math.toRadians(360);

            list.add(new PolarPoint(p.length, newAngle).toHPoint());
        }

        Polygon result = new Polygon(list, new HPoint(0, 0));
        result.translate(this.referencePoint.getX(), this.referencePoint.getY());

        return result;
    }

    public Polygon flipAndAddPolygon(Polygon polygon) throws PolygonNotCenteredOnOrigin {

        return this.addPolygon(polygon.flipInDirectionOf(this.referencePoint));

//        return this.flipInDirectionOf(polygon.referencePoint).addPolygon(polygon);
    }

    public Polygon rotateAndAddPolygon(Polygon polygon) throws PolygonNotCenteredOnOrigin {
        Polygon p = polygon.makeCopy();

        p.rotate(180);

        return this.addPolygon(p);
    }
}
