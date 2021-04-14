package Dibbidut.Classes;

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
        super(new Vector2D(0,0), new Vector2D(0,0));

        this.mmsi = mmsi;
    }

    public Ship(Vector2D position, Vector2D velocity, Shape conflictRegion) {
        super(position, velocity);
        this.centeredPosition = position;
        this.velocity = velocity;
        this.conflictRegion = conflictRegion;
    }

    public Ship(Vector2D position, int length, int width, int heading) {
        super(position, new Vector2D(0,0));
        this.centeredPosition = position;
        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width, "Ellipse");
        domain.Update(sog, heading, position.y(), position.x());
    }

    public Ship(AISData data, double ownShipLongitude) {
        super(new Vector2D(0,0), new Vector2D(0,0));

        warnings = new Hashtable<>();
        currentData = data;

        IShipDataHandler handler = new AISToShipHandler(this, currentData, ownShipLongitude, warnings);

        handler.Run();

        domain = new ShipDomain(length, width, "Ellipse");
        domain.Update(sog, heading, position.y(), position.x());
    }

//    @Override
//    public Vector2D GetPosition() {
//        return position;
//    }
//
//    @Override
//    public void SetPosition(Vector2D value) {
//        position = value;
//    }
//
//    @Override
//    public Vector2D GetVelocity() {
//        return velocity;
//    }
//
//    @Override
//    public void SetVelocity(Vector2D value) {
//        velocity = value;
//    }



    /**
     * Updates the given ship with new data
     * @param data The new data that the ship will be updated with
     * @param ownShipLongitude The longitude of own ship
     */
    public void Update(AISData data, double ownShipLongitude) {

        AISData oldData = currentData;
        currentData = data;

        IShipDataHandler handler = new UpdateShipHandler(this, currentData, oldData, ownShipLongitude, warnings);

        handler.Run();

        domain.Update(sog, heading, position.y(), position.x());
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
