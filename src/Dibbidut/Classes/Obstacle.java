package Dibbidut.Classes;

import java.awt.*;
import java.util.ArrayList;

public abstract class Obstacle {
    public Point position;
    public Velocity velocity;
    public ArrayList<Point> trajectory;

    public Obstacle() {

    }

    public Obstacle(Point point) {
        position = point;
    }

    public Obstacle(int longitude, int latitude) {
        position = new Point(longitude, latitude);
    }
}
