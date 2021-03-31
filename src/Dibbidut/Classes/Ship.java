package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import math.geom2d.Vector2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Ship extends Obstacle {
    public ShipDomain domain;
    public int mmsi;
    public int length;
    public int width;
    public int heading;
    public double longitude;
    public double latitude;
    public double cog;
    public double sog;
    public float manoeuvrability;
    public Hashtable<String, String> warnings;

    public Ship(Vector2D position, int length, int width, int heading) {
        super(position, new Vector2D(0,0));

        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width);
    }

    public Ship(Vector2D position, int length, int width, int heading, double sog, double cog) {
        super(position, new Vector2D(0, sog).rotate(Math.toRadians(cog)));

        this.length = length;
        this.width = width;
        this.heading = heading;

        domain = new ShipDomain(length, width);
    }

    public Ship(AISData data, double ownShipLongitude) {
        super(new Vector2D(0,0), new Vector2D(0,0));

        warnings = new Hashtable<>();

        mmsi = HandleMMSI(data.mmsi);
        heading = HandleHeading(data.heading);
        length = HandleLength(data.length);
        width = HandleWidth(data.width);

        velocity = HandleVelocity(data.COG, data.SOG);

        position = HandlePosition(data.longitude, data.latitude, ownShipLongitude);

        position = HandleCentering(position, data.distanceFore,
                data.distanceAft, data.distancePort,
                data.distanceStarboard, length, width);

        domain = new ShipDomain(length, width);

//        longitude = HandleLongitude(data.longitude);
//        latitude = HandleLatitude(data.latitude);

//        sog = HandleSOG(data.SOG);
//        cog = HandleCOG(data.COG);
    }

    public int HandleMMSI(int mmsi) {
        if (mmsi != 0) {
            return mmsi;
        }
        else {
            warnings.put("MMSI", "MMSI is 0");
            return 0;
        }
    }

    public int HandleHeading(int heading) {
        if (heading != 0) {
            return heading;
        }
        else {
            warnings.put("Heading", "Ship may have incorrect heading");
            return 0;
        }
    }

    public int HandleLength(int length) {
        if (length != 0) {
            return length;
        }
        else {
            int l = 10;
            warnings.put("Length", "Ship's length is 0, placeholder is " + l + " meters");
            return l;
        }
    }

    public int HandleWidth(int width) {
        if (width != 0) {
            return width;
        }
        else {
            int w = 5;
            warnings.put("Width", "Ship's width is 0, placeholder is " + w + " meters");
            return w;
        }
    }

    public Vector2D HandleCentering(Vector2D position, int fore, int aft, int port, int starboard, int length, int width) {

        if (starboard + port != width || fore + aft != length) {
            warnings.put("TransceiverAccuracy", "Position of ship may be inaccurate due to miscalibrated AIS transceiver");
        }

        // No need to move if already at center
        if (length / 2 == fore && width / 2 == starboard) {
            return position;
        }
        else if (aft != 0 && port != 0) {
            return MovePositionToCenterAftPort(position, aft, port, length, width);
        }
        else if (fore != 0 && starboard != 0) {
            return MovePositionToCenterForeStarboard(position, fore, starboard, length, width);
        }
        else {
            warnings.put("TransceiverPosition", "Position of AIS transceiver not known");
            return position;
        }
    }

    public Vector2D HandleVelocity(double cog, double sog) {
        if (sog != 0 && cog != 0) {
            return new Vector2D(0, sog).rotate(Math.toRadians(cog));
        }
        else if (sog == 0 && cog != 0) {
            double placeholderSOG = 5;
            warnings.put("Velocity", "Ship's SOG is 0, using " + placeholderSOG + " as placeholder for velocity");
            return new Vector2D(0, placeholderSOG).rotate(Math.toRadians(cog));
        }
        else if (sog != 0 && cog == 0) {
            warnings.put("Velocity", "Ship's COG is 0, the ship might be heading in a different direction");
            return new Vector2D(0, sog).rotate(Math.toRadians(0));
        }
        else {
            warnings.put("Velocity", "Missing information, can not calculate velocity, using (0, 0) as placeholder");
            return new Vector2D(0,0);
        }
    }

    public Vector2D HandlePosition(double longitude, double latitude, double ownShipLongitude) {
        if (longitude != 0 && latitude != 0) {
            return Mercator.projection(longitude, latitude, ownShipLongitude);
        }
        else {
            warnings.put("Position", "Missing information, can not calculate position, using (0, 0) as placeholder");
            return new Vector2D(0,0);
        }
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

    public double HandleSOG(double sog) {
        if (sog != 0) {
            return sog;
        }
        else {
            int s = 5;
            warnings.put("SOG", "Ship's SOG is 0, placeholder is " + s);
            return s;
        }
    }

    public double HandleCOG(double cog) {
        if (cog != 0) {
            return cog;
        }
        else {
            warnings.put("COG", "Ship may have incorrect COG");
            return 0;
        }
    }

    public double HandleLongitude(double longitude) {
        if (longitude != 0) {
            return longitude;
        }
        else {
            warnings.put("Longitude", "Ship's longitude is 0");
            return 0;
        }
    }

    public double HandleLatitude(double latitude) {
        if (latitude != 0) {
            return latitude;
        }
        else {
            warnings.put("Latitude", "Ship's latitude is 0");
            return 0;
        }
    }
}
