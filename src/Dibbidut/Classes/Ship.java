package Dibbidut.Classes;

import Dibbidut.Interfaces.IDomain;
import math.geom2d.Vector2D;

import java.awt.*;

public class Ship extends Obstacle {
    public IDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public int heading;
    public float manoeuvrability;
    public Shape conflictRegion;
    public Vector2D velocity;

    public Ship(Vector2D position, int length, int width, int heading) {
        super(position);
        this.length = length;
        this.width = width;
        this.heading = heading;
    }

    public Ship(Vector2D position, Vector2D velocity, Shape conflictRegion) {
        super(position);
        this.velocity = velocity;
        this.conflictRegion = conflictRegion;
    }
}
