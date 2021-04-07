package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public abstract class ShipHandler implements IShipDataHandler {
    public Hashtable<String, String> warnings;

    public Ship myShip;
//    public Ship updatedShip;

    public AISData data;
    public AISData oldData;

//    public ShipHandler(Ship myShip, Ship updatedShip, Hashtable<String, String> warnings) {
//        this.myShip = myShip;
//        this.updatedShip = updatedShip;
//        this.warnings = warnings;
//    }

    public ShipHandler(Ship myShip, AISData data, AISData oldData, Hashtable<String, String> warnings) {
        this.myShip = myShip;
        this.data = data;
        this.oldData = oldData;
        this.warnings = warnings;
    }

    public void Run() {
        myShip.mmsi = HandleMMSI();
        myShip.heading = HandleHeading();

        myShip.length = HandleLength();
        myShip.width = HandleWidth();

        myShip.velocity = HandleVelocity();

        myShip.position = HandlePosition();

        myShip.centeredPosition = HandleCentering();

        myShip.longitude = HandleLongitude();
        myShip.latitude = HandleLatitude();

        myShip.sog = HandleSOG();
        myShip.cog = HandleCOG();
    }

    public Vector2D MovePositionToCenterAftPort(Vector2D originalPosition,
                                                int aft, int port,
                                                int length, int width) {

        return new Vector2D(originalPosition.x() - port + (((double) width) / 2),
                originalPosition.y() - aft + (((double) length / 2)));
    }

    public Vector2D MovePositionToCenterForeStarboard(Vector2D originalPosition,
                                                      int fore, int starboard,
                                                      int length, int width) {

        return new Vector2D(originalPosition.x() + starboard - (((double) width) / 2),
                originalPosition.y() + fore - (((double) length / 2)));
    }
}
