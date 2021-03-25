package Dibbidut.Classes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;
import java.util.ArrayList;

public abstract class Obstacle {
    public Point2D.Double position;
    public Velocity velocity;
    public ArrayList<Point2D.Double> trajectory;

    public Obstacle() {

    }

    public Obstacle(Point2D.Double point) {
        position = point;
    }

    public Obstacle(double longitude, double latitude) {
        position = new Point2D.Double(longitude, latitude);
    }
}
