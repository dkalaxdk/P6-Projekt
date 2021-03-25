package Dibbidut.Classes;

import java.awt.*;

public class Ship extends Obstacle {
    private ShipDomain domain;
    public int mmsi;
    public int length;
    public float manoeuvrability;

    public Ship(Point point) {
        super(point);
    }

    public Ship(int longitude, int latitude) {
        super(longitude, latitude);
    }
}
