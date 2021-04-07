package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class AISToShipHandler extends ShipHandler {
    private final double ownShipLongitude;

    private final int lengthPlaceHolder = 0;
    private final int widthPlaceHolder = 0;
    private final double sogPlaceHolder = 0;
    private final Vector2D positionPlaceHolder = new Vector2D(0,0);
    private final Vector2D velocityPlaceHolder = new Vector2D(0,0);

    public AISToShipHandler(Ship myShip, AISData data, double ownShipLongitude, Hashtable<String, String> warnings) {
        super(myShip, data, null, warnings);
        this.ownShipLongitude = ownShipLongitude;
    }

    public int HandleMMSI() {
        if (data.mmsi != 0) {
            return data.mmsi;
        }
        else {
            warnings.put("MMSI", "MMSI is 0");
            return 0;
        }
    }

    public int HandleHeading() {
        if (data.heading != 0) {
            return data.heading;
        }
        else {
            warnings.put("Heading", "Ship may have incorrect heading");
            return 0;
        }
    }

    public int HandleLength() {
        if (data.length != 0) {
            return data.length;
        }
        else {
            warnings.put("Length", "Ship's length is 0, placeholder is " + lengthPlaceHolder + " meters");
            return lengthPlaceHolder;
        }
    }

    public int HandleWidth() {
        if (data.width != 0) {
            return data.width;
        }
        else {
            warnings.put("Width", "Ship's width is 0, placeholder is " + widthPlaceHolder + " meters");
            return widthPlaceHolder;
        }
    }

    public Vector2D HandleVelocity() {
        if (data.SOG != 0 && data.COG != 0) {
            return new Vector2D(0, data.SOG).rotate(Math.toRadians(data.COG));
        }
        else if (data.SOG == 0 && data.COG != 0) {
            // TODO: Ved ikke om det her er dumt
            warnings.put("Velocity", "Ship's SOG is 0, using " +
                    sogPlaceHolder + " as placeholder calculating velocity");
            return new Vector2D(0, sogPlaceHolder).rotate(Math.toRadians(data.COG));
        }
        else if (data.SOG != 0) {
            warnings.put("Velocity", "Ship's COG is 0, the ship might be heading in a different direction");
            return new Vector2D(0, data.SOG).rotate(Math.toRadians(0));
        }
        else {
            warnings.put("Velocity", "Missing information, can not calculate velocity, using " +
                    velocityPlaceHolder + " as placeholder");
            return new Vector2D(0,0);
        }
    }

    public Vector2D HandlePosition() {
        if (data.longitude != 0 && data.latitude != 0) {
            return Mercator.projection(data.longitude, data.latitude, this.ownShipLongitude);
        }
        else {
            warnings.put("Position", "Missing information, can not calculate position, using " +
                    positionPlaceHolder + " as placeholder");
            return new Vector2D(0,0);
        }
    }

    public Vector2D HandleCentering() {

        // Renaming for shortness sake
        int fore = data.distanceFore;
        int aft = data.distanceAft;
        int port = data.distancePort;
        int starboard = data.distanceStarboard;

        int length = data.length;
        int width = data.width;

        if (starboard + port != width || fore + aft != length) {
            warnings.put("TransceiverAccuracy", "Position of ship may be inaccurate due to miscalibrated AIS transceiver");
        }

        // No need to move if already at center
        if (length / 2 == fore && width / 2 == starboard) {
            return myShip.position;
        }
        else if (aft != 0 && port != 0) {
            return MovePositionToCenterAftPort(myShip.position, aft, port, length, width);
        }
        else if (fore != 0 && starboard != 0) {
            return MovePositionToCenterForeStarboard(myShip.position, fore, starboard, length, width);
        }
        else {
            warnings.put("TransceiverPosition", "Position of AIS transceiver not known");
            return myShip.position;
        }
    }



    public double HandleSOG() {
        if (data.SOG != 0) {
            return data.SOG;
        }
        else {
            // TODO: Udregn SOG baseret på tidligere positions punkter
            warnings.put("SOG", "Ship's SOG is 0, placeholder is " + sogPlaceHolder);
            return sogPlaceHolder;
        }
    }

    public double HandleCOG() {
        if (data.COG != 0) {
            return data.COG;
        }
        else {
            warnings.put("COG", "Ship may have incorrect COG");
            return 0;
        }
    }

    public double HandleLongitude() {
        if (data.longitude != 0) {
            return data.longitude;
        }
        else {
            warnings.put("Longitude", "Ship's longitude is 0");
            return 0;
        }
    }

    public double HandleLatitude() {
        if (data.latitude != 0) {
            return data.latitude;
        }
        else {
            warnings.put("Latitude", "Ship's latitude is 0");
            return 0;
        }
    }
}
