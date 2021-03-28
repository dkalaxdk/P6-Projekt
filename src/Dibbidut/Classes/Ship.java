package Dibbidut.Classes;

import math.geom2d.Vector2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;

public class Ship extends Obstacle {
    public ShipDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public int heading;
    public float manoeuvrability;
    public Shape conflictRegion;

    public Ship(Vector2D position, int length, int width, int heading) {
        super(position);
        this.length = length;
        this.width = width;
        this.heading = heading;
    }
}
