package Dibbidut.Classes.Geometry;

import java.util.ArrayList;

public class Polygon extends Geometry{

    public ArrayList<Vector> coordinates;
    public Vector center;



    public Polygon(ArrayList<Vector> coordinates){
        this.coordinates = coordinates;
        calculateCenter();
    }

    @Override
    public void transform(Transformation transformation) {
        ArrayList<Vector> tempCoordinates1;
        ArrayList<Vector> tempCoordinates2 = new ArrayList<>();

        // Translates the polygon so its center is in 0,0
        tempCoordinates1 = translatePolygon(coordinates, new double[][] {{1, 0, 0}, {0, 1, 0}, {-center.getX(), -center.getY(), 1}});

        // Performs the transformation
        for(Vector vector : tempCoordinates1) {
            tempCoordinates2.add(matrixVectorProduct(transformation.get(), vector));
        }

        // Reverses the previous translation to 0,0
        coordinates = translatePolygon(tempCoordinates2, new double[][] {{1, 0, 0}, {0, 1, 0}, {center.getX(), center.getY(), 1}});

        calculateCenter();
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

    @Override
    public boolean contains(Vector point) {
        return false;
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
}
