package Dibbidut.Classes;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ship extends Obstacle {
    private ShipDomain domain;
    public int mmsi;
    public int length;
    public float manoeuvrability;
    public Shape conflictRegion;

    public Ship(Point2D pos, Velocity vel, Shape confR) {
        position = pos;
        velocity = vel;
        conflictRegion = confR;
    }
}
