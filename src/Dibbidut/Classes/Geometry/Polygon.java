package Dibbidut.Classes.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Polygon extends Geometry{

    public ArrayList<Vector> coordinates;
    public Vector center;



    public Polygon(ArrayList<Vector> coordinates){
        this.coordinates = coordinates;
        calculateCenter();
    }

    @Override
    public void transform(Transformation transformation) {
        ArrayList<Vector> newCoordinates = new ArrayList<>();

        // Translates the polygon so its center is in 0,0
        Transformation toCenter = new Transformation();
        toCenter.translate(-center.getX(), -center.getY());     // Translation points to center around origin
        Transformation fromCenter = new Transformation();
        fromCenter.translate(center.getX(), center.getY());     // Translation points back

        Transformation t = new Transformation()
                .add(toCenter)
                .add(transformation)
                .add(fromCenter);

        ArrayList<Vector> copiedCoordinates = new ArrayList<>(copyVectorList(coordinates));

        // Performs the transformation
        for(Vector vector : copiedCoordinates) {
            vector.transform(t);
            newCoordinates.add(vector);
        }

        coordinates = newCoordinates;

        calculateCenter();
    }

    private List<Vector> copyVectorList(List<Vector> list) {
        return list.stream().map(p -> copyVector(p)).collect(toList());
    }

    private Vector copyVector(Vector vec) {
        return new Vector(vec.getX(), vec.getY(), vec.getZ());
    }

    public ArrayList<Vector> translatePolygon(ArrayList<Vector> coordinates, double[][] translationMatrix){
        ArrayList<Vector> newCoordinates = new ArrayList<>();

        for (Vector vector : coordinates){
            newCoordinates.add(matrixVectorProduct(translationMatrix, vector));
        }

        return newCoordinates;
    }

    public Vector matrixVectorProduct(double[][] matrix, Vector vector){
        double x = matrix[0][0] * vector.getX() + matrix[1][0] * vector.getY() + matrix[2][0] * vector.getZ();
        double y = matrix[0][1] * vector.getX() + matrix[1][1] * vector.getY() + matrix[2][1] * vector.getZ();
        double z = matrix[0][2] * vector.getX() + matrix[1][2] * vector.getY() + matrix[2][2] * vector.getZ();

        return new Vector(x, y, z);
    }
    // Adapted from: https://vlecomte.github.io/cp-geo.pdf (page 61-
    // Checks if the point is contained within the polygon
    @Override
    public boolean contains(Vector point) {
        int numberOfCrossings = 0;
        Vector p;
        Vector q;
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
    public boolean onSegment(Vector a, Vector p, Vector q){
        return orientation(a, p, q) == 0 && inDisk(a, p, q);
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 54)
    // Checks if point a is withing the disk with diameter |pq| and which is placed between p and q
    public boolean inDisk(Vector a, Vector p, Vector q){
        return p.subtractVector(a).dotProduct(q.subtractVector(a)) <= 0;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 61)
    // Checks if the segment between p and q crosses a line (ray) going out from point a
    public boolean crossesRay(Vector a, Vector p, Vector q){
        int n = q.getY() >= a.getY() ? 1 : 0;
        int m = p.getY() >= a.getY() ? 1 : 0;
        return (n - m) * orientation(a,p,q) > 0;
    }

    // Source: https://vlecomte.github.io/cp-geo.pdf (page 40)
    // Returns a value indicating the orientation of the three vectors compared to each other.
    public double orientation(Vector a, Vector b, Vector c){
        Vector temp1 = b.subtractVector(a);
        Vector temp2 = c.subtractVector(a);
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
            for (Vector vector : coordinates) {
                x = x + vector.getX();
                y = y + vector.getY();
                z = z + vector.getZ();

                count++;
            }

            center = new Vector(x/count, y/count, z/count);
        }
    }

    public Polygon addPolygon(Polygon polygon){
        ArrayList<Vector> newCoordinates = new ArrayList<>();

        for (Vector vector : this.coordinates){
            Vector relativeVector = vector.subtractVector(this.center);
            newCoordinates.add(calculateNewPoint(relativeVector, polygon));
        }

        for (Vector vector : polygon.coordinates){
            Vector relativeVector = vector.subtractVector(polygon.center);
            newCoordinates.add(calculateNewPoint(relativeVector, this));
        }

        Collections.sort(newCoordinates);

        return new Polygon (newCoordinates);
    }

    public Vector calculateNewPoint(Vector vector, Polygon polygon){
        return new Vector(1, 1, 1);
    }
}
