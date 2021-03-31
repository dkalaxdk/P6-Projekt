package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
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

    public Ship(AISData data) {
        super(new Vector2D(0,0), new Vector2D(0,0));

        this.mmsi = data.mmsi;
    }
}
