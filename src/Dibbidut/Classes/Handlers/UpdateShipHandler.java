package Dibbidut.Classes.Handlers;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Mercator;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class UpdateShipHandler extends ShipHandler {

    public UpdateShipHandler(Ship myShip, AISData data, AISData oldData, double ownShipLongitude, Hashtable<String, String> warnings) {
        super(myShip, data, oldData, ownShipLongitude, warnings);
    }

    public UpdateShipHandler() {
        super(null, null, null, 0, null);
    }

    public int HandleMMSI() {
        return data.mmsi;
    }

    public int HandleHeading() {

        if (!data.headingIsSet && !oldData.headingIsSet) {
            warnings.put("Heading", "Heading unknown, using 0 degrees as placeholder");
            return 0;
        }
        else if (!data.headingIsSet) {
            warnings.put("Heading", "Heading lost, keeping old heading of " + oldData.heading + " degrees");
            data.heading = oldData.heading;
            data.headingIsSet = true;
            return data.heading;
        }
        else {
            warnings.put("Heading", "");
            return data.heading;
        }
    }

    public int HandleLength() {

        if (!data.lengthIsSet && !oldData.lengthIsSet) {
            warnings.put("Length", "Length is unknown, using " + lengthPlaceHolder + "m as a placeholder");
            return lengthPlaceHolder;
        }
        else if (!data.lengthIsSet) {
            warnings.put("Length", "Length lost, keeping old length of " + oldData.length + "m");
            data.length = oldData.length;
            data.lengthIsSet = true;
            return data.length;
        }
        else if (data.length != oldData.length) {
            warnings.put("Length", "Ship's length has changed, going from " +
                    oldData.length + "m to " + data.length + "m");
            return data.length;
        }
        else {
            warnings.put("Length", "");
            return data.length;
        }
    }

    public int HandleWidth() {

        if (!data.widthIsSet && !oldData.widthIsSet) {
            warnings.put("Width", "Width is unknown, using " + widthPlaceHolder + "m as a placeholder");
            return widthPlaceHolder;
        }
        else if (!data.widthIsSet) {
            warnings.put("Width", "Width lost, keeping old width of " + oldData.width + "m");
            data.width = oldData.width;
            data.lengthIsSet = true;
            return data.width;
        }
        else if (data.width != oldData.width) {
            warnings.put("Width", "Ship's width has changed, going from " +
                    oldData.width + "m to " + data.width + "m");
            return data.width;
        }
        else {
            warnings.put("Width", "");
            return data.width;
        }
    }

    public Vector2D HandleVelocity() {

        // SOG and COG have already been handled at this point, so we don't need to retain values.
        // We can assume that the new data was overridden by the old data, if the new data was lacking

        if (data.cogIsSet && data.sogIsSet) {
            warnings.put("Velocity", "");
            return CalculateVelocity(data.SOG, data.COG);
        }
        else if (!data.cogIsSet && data.sogIsSet) {
            warnings.put("Velocity", "COG unknown, using " + cogPlaceholder + " as a placeholder");
            return CalculateVelocity(data.SOG, cogPlaceholder);
        }
        else if (data.cogIsSet) {
            warnings.put("Velocity", "SOG unknown, using " + sogPlaceHolder + " as a placeholder");
            return CalculateVelocity(sogPlaceHolder, data.COG);
        }
        else {
            warnings.put("Velocity", "Velocity unknown, using " + GetVelocityPlaceHolder() + " as a placeholder");
            return GetVelocityPlaceHolder();
        }

        // TODO: Måske se på om den nye velocity falder helt uden for skibets path / Er helt urealistisk?
    }

    public Vector2D HandlePosition() {

        // TODO: Tjek om det her er helt ved siden af
        return Mercator.projection(data.longitude, data.latitude, ownShipLongitude);
    }

    public Vector2D HandleCentering() {

        int fore = data.distanceFore;
        int aft = data.distanceAft;
        int port = data.distancePort;
        int starboard = data.distanceStarboard;

        int length = data.length;
        int width = data.width;

        if (starboard + port != width || fore + aft != length) {
            warnings.put("TransceiverAccuracy", "Position of ship may be inaccurate due to miscalibrated AIS transceiver");
        }

        Vector2D myShipPosition = Mercator.projection(data.longitude, data.latitude, ownShipLongitude);

        warnings.put("TransceiverPosition", "");

        // Cases where we have both a fore/aft and a starboard/port
        if (data.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, fore, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, fore, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, fore, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, fore, oldData.distancePort, length, width);
            }
        }
        else if (data.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterAftStarboard(myShipPosition, aft, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterAftPort(myShipPosition, aft, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, aft, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, aft, oldData.distancePort, length, width);
            }
        }
        else if (oldData.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, oldData.distanceFore, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, oldData.distanceFore, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, oldData.distanceFore, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, oldData.distanceFore, oldData.distancePort, length, width);
            }
        }
        else if (oldData.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenterAftStarboard(myShipPosition, oldData.distanceAft, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenterAftPort(myShipPosition, oldData.distanceAft, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenterForeStarboard(myShipPosition, oldData.distanceAft, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenterForePort(myShipPosition, oldData.distanceAft, oldData.distancePort, length, width);
            }
        }

        // Cases where we have only fore/aft or only starboard/port
        if (data.distanceForeIsSet && data.distanceAftIsSet) {
            return MovePositionToCenterFore(myShipPosition, fore, length);
        }
        else if (data.distanceStarboardIsSet && data.distancePortIsSet) {
            return MovePositionToCenterStarboard(myShipPosition, starboard, width);
        }
        else if (oldData.distanceForeIsSet && oldData.distanceAftIsSet) {
            return MovePositionToCenterFore(myShipPosition, oldData.distanceFore, length);
        }
        else if (oldData.distanceStarboardIsSet && oldData.distancePortIsSet) {
            return MovePositionToCenterStarboard(myShipPosition, oldData.distanceStarboard, width);
        }

        // Cases where we only have one
        if (data.distanceForeIsSet) {
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
        else if (oldData.distanceForeIsSet) {
            return MovePositionToCenterFore(myShipPosition, oldData.distanceFore, length);
        }
        else if (oldData.distanceAftIsSet) {
            return MovePositionToCenterAft(myShipPosition, oldData.distanceAft, length);
        }
        else if (oldData.distanceStarboardIsSet) {
            return MovePositionToCenterStarboard(myShipPosition, oldData.distanceStarboard, width);
        }
        else if (oldData.distancePortIsSet) {
            return MovePositionToCenterPort(myShipPosition, oldData.distancePort, width);
        }
        else {
            warnings.put("TransceiverPosition", "Position of AIS transceiver not known");
            return myShipPosition;
        }
    }

    public double HandleSOG() {
        if (!data.sogIsSet) {
            if (oldData.sogIsSet) {
                warnings.put("SOG", "Lost SOG, using old data instead");
                data.SOG = oldData.SOG;
                data.sogIsSet = true;
                return data.SOG;
            }
            else {
                warnings.put("SOG", "SOG unknown, using " + sogPlaceHolder + " as a placeholder");
                return sogPlaceHolder;
            }
        }
        else {
            warnings.put("SOG", "");
            return data.SOG;
        }
    }

    public double HandleCOG() {
        if (!data.cogIsSet) {
            if (oldData.cogIsSet) {
                warnings.put("COG", "Lost COG, using old data instead");
                data.COG = oldData.COG;
                data.cogIsSet = true;
                return data.COG;
            }
            else {
                warnings.put("COG", "COG is unknown, using " + cogPlaceholder + " as a placeholder");
                return cogPlaceholder;
            }
        }
        else {
            warnings.put("COG", "");
            return data.COG;
        }
    }

    public double HandleLongitude() {
        // TODO: Tjek om det her er helt skørt
        return data.longitude;
    }

    public double HandleLatitude() {
        // TODO: Tjek om det her er helt skørt
        return data.latitude;
    }
}
