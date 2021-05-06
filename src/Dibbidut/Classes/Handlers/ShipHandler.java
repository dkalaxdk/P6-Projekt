package Dibbidut.Classes.Handlers;

import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Ship;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public abstract class ShipHandler implements IShipDataHandler {
    public Hashtable<String, String> warnings;

    public final int lengthPlaceHolder = 20;
    public final int widthPlaceHolder = 10;
    public final double sogPlaceHolder = 1;
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
        myShip.scaledVelocity = myShip.velocity;

        myShip.position = HandlePosition();
    }

    public HPoint CalculateVelocity(double sog, double cog) {
        HPoint point = new HPoint(0, KnotsToMetersPerSecond(sog));
        point.rotate(cog);

        return point;
    }

    public HPoint GetVelocityPlaceHolder() {
        return CalculateVelocity(sogPlaceHolder, cogPlaceholder);
    }

    public HPoint GetPositionPlaceHolder() {
        return new HPoint(0,0);
    }

    public double KnotsToMetersPerSecond(double kn) {
        return kn / 1.944;
    }

    public static class MovePositionToCenter {
        public static HPoint AftPort(HPoint originalPosition,
                                       int aft, int port,
                                       int length, int width) {

            return new HPoint(Port(originalPosition, port, width).getX(),
                    Aft(originalPosition, aft, length).getY());
        }

        public static HPoint AftStarboard(HPoint originalPosition,
                                            int aft, int starboard,
                                            int length, int width) {

            return new HPoint(Starboard(originalPosition, starboard, width).getX(),
                    Aft(originalPosition, aft, length).getY());
        }

        public static HPoint ForePort(HPoint originalPosition,
                                        int fore, int port,
                                        int length, int width) {

            return new HPoint(Port(originalPosition, port, width).getX(),
                    Fore(originalPosition, fore, length).getY());
        }

        public static HPoint ForeStarboard(HPoint originalPosition,
                                             int fore, int starboard,
                                             int length, int width) {

            return new HPoint(Starboard(originalPosition, starboard, width).getX(),
                    Fore(originalPosition, fore, length).getY());
        }


        public static HPoint Fore(HPoint originalPosition,
                                    int fore, int length) {

            return new HPoint(originalPosition.getX(),
                    originalPosition.getY() + fore - (((double) length) / 2));
        }

        public static HPoint Aft(HPoint originalPosition,
                                   int aft, int length) {

            return new HPoint(originalPosition.getX(),
                    originalPosition.getY() - aft + (((double) length / 2)));
        }

        public static HPoint Starboard(HPoint originalPosition,
                                         int starboard, int width) {

            return new HPoint(originalPosition.getX() + starboard - (((double) width) / 2),
                    originalPosition.getY());
        }

        public static HPoint Port(HPoint originalPosition,
                                    int port, int width) {

            return new HPoint(originalPosition.getX() - port + (((double) width) / 2),
                    originalPosition.getY());
        }
    }
}
