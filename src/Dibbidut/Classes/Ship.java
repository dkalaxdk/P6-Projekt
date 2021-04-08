package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class Ship extends Obstacle {
    public ShipDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public Vector2D centeredPosition;
    public int heading;
    public double longitude;
    public double latitude;
    public double cog;
    public double sog;
    public float manoeuvrability;
    public Hashtable<String, String> warnings;

    private AISData oldData;
    private AISData currentData;

    public Ship(int mmsi) {
        super(new Vector2D(0,0), new Vector2D(0,0));

        this.mmsi = mmsi;
    }

    public Ship(Vector2D position, int length, int width, int heading) {
        super(position, new Vector2D(0,0));

        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width);
    }

    public Ship(AISData data, double ownShipLongitude) {
        super(new Vector2D(0,0), new Vector2D(0,0));

        warnings = new Hashtable<>();
        currentData = data;

        IShipDataHandler handler = new AISToShipHandler(this, currentData, ownShipLongitude, warnings);

        handler.Run();

        domain = new ShipDomain(length, width);
    }

    /**
     * Updates the given ship with new data
     * @param data The new data that the ship will be updated with
     * @param ownShipLongitude The longitude of own ship
     */
    public void Update(AISData data, double ownShipLongitude) {

        oldData = currentData;
        currentData = data;

        IShipDataHandler handler = new UpdateShipHandler(this, currentData, oldData, ownShipLongitude, warnings);

        handler.Run();

        domain.Update(sog, cog, latitude, longitude);
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
