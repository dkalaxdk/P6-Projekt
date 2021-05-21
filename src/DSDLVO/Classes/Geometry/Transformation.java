package DSDLVO.Classes.Geometry;

import DSDLVO.Utilities.Utility;

public class Transformation {
    // 3x3 matrix for homogeneous coordinates
    // Each column is a point
    private double[][] matrix;
    private double rotation = 0;

    public Transformation() {
        matrix = new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
    }

    public Transformation(Transformation t) {
        matrix = t.get();
    }

    public Transformation rotate(double degrees) {
        double radians = Math.toRadians(-degrees);
        rotation += radians;
        setMatrix(new double[][]{
                {Math.cos(radians), Math.sin(radians), 0},
                {-Math.sin(radians), Math.cos(radians), 0},
                {0, 0, 1}
        });
        return this;
    }

    public Transformation scale(double width, double height) {
        setMatrix(new double[][]{
                {width, 0, 0},
                {0, height, 0},
                {0, 0, 1}
        });
        return this;
    }

    public Transformation translate(double x, double y) {
        setMatrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {x, y, 1}
        });
        return this;
    }

    public Transformation translate(HPoint vector) {
        this.translate(vector.getX(), vector.getY());
        return this;
    }

    private void setMatrix(double[][] value) {
        // Possible that the order of the matrices are wrong
        matrix = multiplyMatrix(value, matrix);
    }

    private double[][] multiplyMatrix(double[][] matrixOne, double[][] matrixTwo) {
        double[][] result = new double[3][3];
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                double res = 0;
                for (int i = 0; i < 3; i++)
                    res += matrixOne[i][row] * matrixTwo[col][i];
                result[col][row] = res;
            }
        }
        return result;
    }

    // Rounds the entries to four decimals before returning the matrix
    public double[][] get() {
        double[][] rounded = new double[3][3];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                rounded[i][j] = Utility.roundToFourDecimals(matrix[i][j]);
        return rounded;
    }


    public double getRotation() {
        return rotation;
    }

    public Transformation add(Transformation t) {
        setMatrix(t.get());
        return this;
    }
}
