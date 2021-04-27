package Dibbidut.Classes.Geometry;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Transformation {
    // 3x3 matrix for homogeneous coordinates
    // Each column is a point
    private double[][] matrix;
    private boolean firstTransform = true;
    private double rotation = 0;

    public Transformation() {
        matrix = new double[3][3];
    }

    public void rotate(double degrees) {
        double radians = degreesToRadians(degrees);
        rotation += radians;
        setMatrix(new double[][] {
                {Math.cos(radians), -Math.sin(radians), 0},
                {Math.sin(radians), Math.cos(radians), 0},
                {0, 0, 1}
        });
    }

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI/180;
    }

    public void scale(double width, double height) {
        setMatrix(new double[][] {
                {width, 0, 0}, {0, height, 0}, {0, 0, 1}
        });
    }

    public void translate(double x, double y) {
        setMatrix(new double[][]{
                {1, 0, 0}, {0, 1, 0}, {x, y, 1}
        });
    }

    private void setMatrix(double[][] value) {
        if(firstTransform) {
            matrix = value;
            firstTransform = false;
        }
        else
            matrix = multiplyMatrix(matrix, value);
    }

    private double[][] multiplyMatrix(double[][] matrixOne, double[][] matrixTwo) {
        double[][] result = new double[3][3];
        for(int col = 0; col < 3; col++) {
            for(int row = 0; row < 3; row++) {
                double res = 0;
                for(int i = 0; i < 3; i++)
                    res += matrixOne[i][row] * matrixTwo[col][i];
                result[col][row] = res;
            }
        }
        return result;
    }

    // Rounds the entries to four decimals before returning the matrix
    public double[][] get() {
        double[][] rounded = new double[3][3];
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                rounded[i][j] = roundToFourDecimals(matrix[i][j]);
        return rounded;
    }

    private double roundToFourDecimals(double number) {
        return (double)Math.round(number * 10000d) / 10000d;
    }

    public double getRotation() {
        return rotation;
    }
}
