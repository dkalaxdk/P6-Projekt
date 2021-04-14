package Dibbidut.Classes.Handlers;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Ship;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;
import java.util.Vector;

public abstract class ShipHandler implements IShipDataHandler {
    public Hashtable<String, String> warnings;

    public final int lengthPlaceHolder = 20;
    public final int widthPlaceHolder = 10;
    public final double sogPlaceHolder = 2;
    public double cogPlaceholder = 0;
    public final int headingPlaceholder = 0;

    private final Ship myShip;

    protected double ownShipLongitude;
    protected AISData data;
    protected AISData oldData;

    public ShipHandler(Ship myShip, AISData data, AISData oldData, double ownShipLongitude, Hashtable<String, String> warnings) {
        this.myShip = myShip;
        this.data = data;
        this.oldData = oldData;
        this.ownShipLongitude = ownShipLongitude;
        this.warnings = warnings;
    }

    public void Run() {
        myShip.mmsi = HandleMMSI();
        myShip.heading = HandleHeading();

        myShip.length = HandleLength();
        myShip.width = HandleWidth();

        myShip.longitude = HandleLongitude();
        myShip.latitude = HandleLatitude();

        // Has to happen before HandleVelocity
        myShip.sog = HandleSOG();
        myShip.cog = HandleCOG();

        // Has to happen after HandleSOG and HandleCOG
        myShip.velocity = HandleVelocity();

        myShip.position = HandlePosition();
    }

    public Vector2D CalculateVelocity(double sog, double cog) {
        return new Vector2D(0, sog).rotate(Math.toRadians(cog));
    }

    public Vector2D MovePositionToCenterAftPort(Vector2D originalPosition,
                                                int aft, int port,
                                                int length, int width) {

        return new Vector2D(MovePositionToCenterPort(originalPosition, port, width).x(),
                MovePositionToCenterAft(originalPosition, aft, length).y());
    }

    public Vector2D MovePositionToCenterAftStarboard(Vector2D originalPosition,
                                                     int aft, int starboard,
                                                     int length, int width) {

        return new Vector2D(MovePositionToCenterStarboard(originalPosition, starboard, width).x(),
                MovePositionToCenterAft(originalPosition, aft, length).y());
    }

    public Vector2D MovePositionToCenterForePort(Vector2D originalPosition,
                                                 int fore, int port,
                                                 int length, int width) {

        return new Vector2D(MovePositionToCenterPort(originalPosition, port, width).x(),
                MovePositionToCenterFore(originalPosition, fore, length).y());
    }

    public Vector2D MovePositionToCenterForeStarboard(Vector2D originalPosition,
                                                      int fore, int starboard,
                                                      int length, int width) {

        return new Vector2D(MovePositionToCenterStarboard(originalPosition, starboard, width).x(),
                MovePositionToCenterFore(originalPosition, fore, length).y());
    }


    public Vector2D MovePositionToCenterFore(Vector2D originalPosition,
                                             int fore, int length) {

        return new Vector2D(originalPosition.x(),
                originalPosition.y() + fore - (((double) length) / 2));
    }

    public Vector2D MovePositionToCenterAft(Vector2D originalPosition,
                                            int aft, int length) {

        return new Vector2D(originalPosition.x(),
                originalPosition.y() - aft + (((double) length / 2)));
    }

    public Vector2D MovePositionToCenterStarboard(Vector2D originalPosition,
                                                  int starboard, int width) {

        return new Vector2D(originalPosition.x() + starboard - (((double) width) / 2),
                originalPosition.y());
    }

    public Vector2D MovePositionToCenterPort(Vector2D originalPosition,
                                             int port, int width) {

        return new Vector2D(originalPosition.x() - port + (((double) width) / 2),
                originalPosition.y());
    }

    public Vector2D GetVelocityPlaceHolder() {
        return CalculateVelocity(sogPlaceHolder, cogPlaceholder);
    }

    public Vector2D GetPositionPlaceHolder() {
        return new Vector2D(0,0);
    }
}
