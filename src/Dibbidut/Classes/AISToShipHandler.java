package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class AISToShipHandler extends ShipHandler {

    public AISToShipHandler(Ship myShip, AISData data, double ownShipLongitude, Hashtable<String, String> warnings) {
        super(myShip, data, null, ownShipLongitude, warnings);
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
        if (!data.headingIsSet) {
            warnings.put("Heading", "Heading unknown, using " + headingPlaceholder + " degrees as a placeholder");
            return headingPlaceholder;
        }

        return data.heading;
    }

    public int HandleLength() {
        if (!data.lengthIsSet) {
            warnings.put("Length", "Ship's length is 0, placeholder is " + lengthPlaceHolder + " meters");
            return lengthPlaceHolder;
        }
        else if (data.length == 0) {
            warnings.put("Length", "Length of ship is 0");
        }

        return data.length;
    }

    public int HandleWidth() {
        if (!data.widthIsSet) {
            warnings.put("Width", "Ship's width is 0, placeholder is " + widthPlaceHolder + " meters");
            return widthPlaceHolder;
        }
        else if (data.width == 0) {
            warnings.put("Width", "Width of ship is 0");
        }

        return data.width;
    }

    public Vector2D HandleVelocity() {

        if (data.sogIsSet && data.cogIsSet) {
            return CalculateVelocity(data.SOG, data.COG);
        }
        else if (!data.sogIsSet && data.cogIsSet) {
            // TODO: Ved ikke om det her er dumt
            warnings.put("Velocity", "Ship's SOG is unknown, using " +
                    sogPlaceHolder + " as placeholder calculating velocity");
            return CalculateVelocity(sogPlaceHolder, data.COG);
        }
        else if (data.sogIsSet && !data.cogIsSet) {
            warnings.put("Velocity", "Ship's COG is unknown, the ship might be heading in a different direction");
            return CalculateVelocity(data.SOG, 0);
        }
        else {
            warnings.put("Velocity", "Missing information, can not calculate velocity, using " +
                    GetVelocityPlaceHolder() + " as placeholder");
            return GetVelocityPlaceHolder();
        }
    }

    public Vector2D HandlePosition() {
        if (data.longitude != 0 && data.latitude != 0) {
            return Mercator.projection(data.longitude, data.latitude, this.ownShipLongitude);
        }
        else {
            warnings.put("Position", "Missing information, can not calculate position, using " +
                    GetPositionPlaceHolder() + " as placeholder");
            return GetPositionPlaceHolder();
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

        Vector2D myShipPosition = Mercator.projection(data.longitude, data.latitude, ownShipLongitude);

        if (starboard + port != width || fore + aft != length) {
            warnings.put("TransceiverAccuracy", "Position of ship may be inaccurate due to miscalibrated AIS transceiver");
        }

        if (data.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, fore, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, fore, port, length, width);
            }
        }
        else if (data.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterAftStarboard(myShipPosition, aft, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterAftPort(myShipPosition, aft, port, length, width);
            }
        }

        if ((data.distanceForeIsSet && data.distanceAftIsSet) && (fore + aft == length)) {
            return MovePositionToCenterFore(myShipPosition, fore, length);
        }
        else if ((data.distanceStarboardIsSet && data.distancePortIsSet) && (starboard + aft == width)) {
            return MovePositionToCenterStarboard(myShipPosition, starboard, width);
        }
        else if (data.distanceForeIsSet) {
            return MovePositionToCenterFore(myShipPosition, fore, length);
        }
        else if (data.distanceAftIsSet) {
            return MovePositionToCenterAft(myShipPosition, aft, length);
        }
        else if (data.distanceStarboardIsSet) {
            return MovePositionToCenterStarboard(myShipPosition, starboard, width);
        }
        else if (data.distancePortIsSet) {
            return MovePositionToCenterPort(myShipPosition, port, width);
        }
        else {
            warnings.put("TransceiverPosition", "Position of AIS transceiver not known");
            return myShipPosition;
        }
    }

    public double HandleSOG() {
        if (!data.sogIsSet) {
            warnings.put("SOG", "SOG is unknown, using " + sogPlaceHolder + " as a placeholder");
            return sogPlaceHolder;
        }

        return data.SOG;
    }

    public double HandleCOG() {
        if (!data.cogIsSet) {
            warnings.put("COG", "COG is unknown, using 0 as a placeholder");
            return 0;
        }

        return data.COG;
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
