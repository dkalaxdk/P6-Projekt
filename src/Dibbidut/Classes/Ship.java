package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Handlers.AISToShipHandler;
import Dibbidut.Classes.Handlers.UpdateShipHandler;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import Dibbidut.Interfaces.IDomain;
import math.geom2d.Vector2D;

import java.awt.*;
import java.util.Hashtable;

public class Ship extends Obstacle {
    public IDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public int heading;
    public double longitude;
    public double latitude;
    public double cog;
    public double sog;
    public double manoeuvrability;
    public Shape conflictRegion;
    public Hashtable<String, String> warnings;

    private AISData currentData;

    public Ship(int mmsi) {
        super(new HPoint(0,0), new HPoint(0,0));

        this.mmsi = mmsi;
    }

    public Ship(HPoint position, HPoint velocity, Shape conflictRegion) {
        super(position, velocity);
        this.position = position;
        this.velocity = velocity;
        this.conflictRegion = conflictRegion;
    }

    public Ship(HPoint position, int length, int width, int heading) {
        super(position, new HPoint(0,0));
        this.position = position;
        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width, "Pentagon");
        domain.Update(sog, heading, position.getY(), position.getX());
    }

    public Ship(AISData data) {
        super(new HPoint(0,0), new HPoint(0,0));

        warnings = new Hashtable<>();
        currentData = data;

        IShipDataHandler handler = new AISToShipHandler(this, currentData, warnings);

        handler.Run();

        domain = new ShipDomain(length, width, "Pentagon");
        domain.Update(sog, heading, position.getY(), position.getX());
    }

    /**
     * Updates the given ship with new data
     * @param data The new data that the ship will be updated with
     */
    public void Update(AISData data) {

        AISData oldData = currentData;
        currentData = data;

        IShipDataHandler handler = new UpdateShipHandler(this, currentData, oldData, warnings);

        handler.Run();

        this.velocity.scale(10);

        domain.Update(sog, heading, position.getY(), position.getX());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Ship.class) {
            return this.mmsi == ((Ship) obj).mmsi;
        }
        else if (obj.getClass() == AISData.class) {
            return this.mmsi == ((AISData) obj).mmsi;
        }
        else {
            return super.equals(obj);
        }
    }
}
