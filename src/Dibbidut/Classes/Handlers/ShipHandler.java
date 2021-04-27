package Dibbidut.Classes.Handlers;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Ship;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public abstract class ShipHandler implements IShipDataHandler {
    public Hashtable<String, String> warnings;

    public final int lengthPlaceHolder = 20;
    public final int widthPlaceHolder = 10;
    public final double sogPlaceHolder = 2;
    public double cogPlaceholder = 0;
    public final int headingPlaceholder = 0;

    private final Ship myShip;

    protected AISData data;
    protected AISData oldData;

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
        return new Vector2D(0, KnotsToMetersPerSecond(sog)).rotate(Math.toRadians(cog));
    }

    public Vector2D GetVelocityPlaceHolder() {
        return CalculateVelocity(sogPlaceHolder, cogPlaceholder);
    }

    public Vector2D GetPositionPlaceHolder() {
        return new Vector2D(0,0);
    }

    public double KnotsToMetersPerSecond(double kn) {
        return kn / 1.944;
    }

    public static class MovePositionToCenter {
        public static Vector2D AftPort(Vector2D originalPosition,
                                       int aft, int port,
                                       int length, int width) {

            return new Vector2D(Port(originalPosition, port, width).x(),
                    Aft(originalPosition, aft, length).y());
        }

        public static Vector2D AftStarboard(Vector2D originalPosition,
                                            int aft, int starboard,
                                            int length, int width) {

            return new Vector2D(Starboard(originalPosition, starboard, width).x(),
                    Aft(originalPosition, aft, length).y());
        }

        public static Vector2D ForePort(Vector2D originalPosition,
                                        int fore, int port,
                                        int length, int width) {

            return new Vector2D(Port(originalPosition, port, width).x(),
                    Fore(originalPosition, fore, length).y());
        }

        public static Vector2D ForeStarboard(Vector2D originalPosition,
                                             int fore, int starboard,
                                             int length, int width) {

            return new Vector2D(Starboard(originalPosition, starboard, width).x(),
                    Fore(originalPosition, fore, length).y());
        }


        public static Vector2D Fore(Vector2D originalPosition,
                                    int fore, int length) {

            return new Vector2D(originalPosition.x(),
                    originalPosition.y() + fore - (((double) length) / 2));
        }

        public static Vector2D Aft(Vector2D originalPosition,
                                   int aft, int length) {

            return new Vector2D(originalPosition.x(),
                    originalPosition.y() - aft + (((double) length / 2)));
        }

        public static Vector2D Starboard(Vector2D originalPosition,
                                         int starboard, int width) {

            return new Vector2D(originalPosition.x() + starboard - (((double) width) / 2),
                    originalPosition.y());
        }

        public static Vector2D Port(Vector2D originalPosition,
                                    int port, int width) {

            return new Vector2D(originalPosition.x() - port + (((double) width) / 2),
                    originalPosition.y());
        }
    }
}
