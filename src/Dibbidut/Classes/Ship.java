package Dibbidut.Classes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;

public class Ship extends Obstacle {
    private ShipDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public int heading;
    public float manoeuvrability;

    public Ship(Point2D.Double point) {
        super(point);
    }

    public Ship(double longitude, double latitude) {
        super(longitude, latitude);
    }
}
