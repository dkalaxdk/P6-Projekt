package Dibbidut.Classes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Obstacle {
    public Point2D position;
    public Velocity velocity;
    public ArrayList<Point> trajectory;

    public Obstacle() {

    }
}
