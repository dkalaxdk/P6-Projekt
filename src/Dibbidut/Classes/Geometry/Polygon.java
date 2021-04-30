package Dibbidut.Classes.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Polygon extends Geometry{

    public ArrayList<HPoint> coordinates;
    public HPoint center;



    public Polygon(ArrayList<HPoint> coordinates){
        this.coordinates = coordinates;
        calculateCenter();
    }

    @Override
    public void transform(Transformation transformation) {
        ArrayList<HPoint> newCoordinates = new ArrayList<>();

        // Translates the polygon so its center is in 0,0
        Transformation toCenter = new Transformation();
        toCenter.translate(-center.getX(), -center.getY());     // Translation points to center around origin
        Transformation fromCenter = new Transformation();
        fromCenter.translate(center.getX(), center.getY());     // Translation points back

        Transformation t = new Transformation()
                .add(toCenter)
                .add(transformation)
                .add(fromCenter);

        ArrayList<HPoint> copiedCoordinates = new ArrayList<>(copyHPointList(coordinates));

        // Performs the transformation
        for(HPoint point : copiedCoordinates) {
            point.transform(t);
            newCoordinates.add(point);
        }

        coordinates = newCoordinates;

        calculateCenter();
    }

    private List<HPoint> copyHPointList(List<HPoint> list) {
        return list.stream().map(p -> copyHPoint(p)).collect(toList());
    }

    private HPoint copyHPoint(HPoint point) {
        return new HPoint(point.getX(), point.getY(), point.getZ());
    }

    public ArrayList<HPoint> translatePolygon(ArrayList<HPoint> coordinates, double[][] translationMatrix){
        ArrayList<HPoint> newCoordinates = new ArrayList<>();

        for (HPoint point : coordinates){
            newCoordinates.add(matrixHPointProduct(translationMatrix, point));
        }

        return newCoordinates;
    }

    public HPoint matrixHPointProduct(double[][] matrix, HPoint point){
        double x = matrix[0][0] * point.getX() + matrix[1][0] * point.getY() + matrix[2][0] * point.getZ();
        double y = matrix[0][1] * point.getX() + matrix[1][1] * point.getY() + matrix[2][1] * point.getZ();
        double z = matrix[0][2] * point.getX() + matrix[1][2] * point.getY() + matrix[2][2] * point.getZ();

        return new HPoint(x, y, z);
    }
    // Adapted from: https://vlecomte.github.io/cp-geo.pdf (page 61-
    // Checks if the point is contained within the polygon
    @Override
    public boolean contains(HPoint point) {
        int numberOfCrossings = 0;
        HPoint p;
        HPoint q;
        for (int i = 0; i < coordinates.size(); i++){
            p = coordinates.get(i);
            q = coordinates.get((i+1)%coordinates.size());
            if(onSegment(point, p, q))
                return false;

            if (crossesRay(point, p, q))
                numberOfCrossings++;
        }
        return numberOfCrossings % 2 == 1;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 54)
    // Checks if point a is in line with the two end points of the segment
    public boolean onSegment(HPoint a, HPoint p, HPoint q){
        return orientation(a, p, q) == 0 && inDisk(a, p, q);
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 54)
    // Checks if point a is withing the disk with diameter |pq| and which is placed between p and q
    public boolean inDisk(HPoint a, HPoint p, HPoint q){
        return p.subtract(a).dotProduct(q.subtract(a)) <= 0;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 61)
    // Checks if the segment between p and q crosses a line (ray) going out from point a
    public boolean crossesRay(HPoint a, HPoint p, HPoint q){
        int n = q.getY() >= a.getY() ? 1 : 0;
        int m = p.getY() >= a.getY() ? 1 : 0;
        return (n - m) * orientation(a,p,q) > 0;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 40)
    // Returns a value indicating the orientation of the three points compared to each other.
    public double orientation(HPoint a, HPoint b, HPoint c){
        HPoint temp1 = b.subtract(a);
        HPoint temp2 = c.subtract(a);
        return temp1.getX() * temp2.getY() - temp1.getY() * temp2.getX();
    }

    public void calculateCenter(){
        double x = 0;
        double y = 0;
        double z = 0;
        int count = 0;

        if (coordinates == null || coordinates.size() == 0){
            center = null;
        }
        else{
            for (HPoint point : coordinates) {
                x = x + point.getX();
                y = y + point.getY();
                z = z + point.getZ();

                count++;
            }

            //TODO: Should z be 1?
            center = new HPoint(x/count, y/count, z/count);
        }
    }

    public Polygon addPolygon(Polygon polygon){
        ArrayList<HPoint> newCoordinates = new ArrayList<>();

        for (HPoint point : this.coordinates){
            HPoint relativeHPoint = point.subtract(this.center);
            newCoordinates.add(calculateNewHPoint(relativeHPoint, polygon));
        }

        for (HPoint point : polygon.coordinates){
            HPoint relativeHPoint = point.subtract(polygon.center);
            newCoordinates.add(calculateNewHPoint(relativeHPoint, this));
        }

        Collections.sort(newCoordinates);

        return new Polygon (newCoordinates);
    }
// todo: not done here
    public HPoint calculateNewHPoint(HPoint point, Polygon polygon){
        return new HPoint(1, 1, 1);
    }

    public Polygon copy() {
        return new Polygon(coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return coordinates.equals(polygon.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
