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
        super(position, new Vector2D(0,0));

        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width);
    }

    public Ship(Vector2D position, int length, int width, int heading, double sog, double cog) {
        super(position, new Vector2D(0, sog).rotate(cog * (Math.PI / 180)));

        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width);
    }

    public Ship(Vector2D position, Vector2D velocity, Shape conflictRegion) {
        super(position);
        this.velocity = velocity;
        this.conflictRegion = conflictRegion;
    }
}
