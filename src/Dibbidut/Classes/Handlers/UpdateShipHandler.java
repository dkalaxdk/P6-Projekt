package Dibbidut.Classes.Handlers;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Mercator;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class UpdateShipHandler extends ShipHandler {

    public UpdateShipHandler(Ship myShip, AISData data, AISData oldData, Hashtable<String, String> warnings) {
        super(myShip, data, oldData, warnings);
    }

    public UpdateShipHandler() {
        super(null, null, null, null);
    }

    public int HandleMMSI() {
        return data.mmsi;
    }

    public int HandleHeading() {

        if (!data.headingIsSet && !oldData.headingIsSet) {
            if (data.cogIsSet) {
                warnings.put("Heading", "Heading unknown, using COG as a placeholder");
                return (int) Math.round(data.COG);
            }
            else if (oldData.cogIsSet) {
                warnings.put("Heading", "Heading unknown, using COG as a placeholder");
                return (int) Math.round(oldData.COG);
            }
            else {
                warnings.put("Heading", "Heading unknown, using " + headingPlaceholder + " degrees as placeholder");
                return headingPlaceholder;
            }
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

        int fore = data.distanceFore;
        int aft = data.distanceAft;
        int port = data.distancePort;
        int starboard = data.distanceStarboard;

        int length = data.length;
        int width = data.width;

        if (starboard + port != width || fore + aft != length) {
            warnings.put("TransceiverAccuracy", "Position of ship may be inaccurate due to miscalibrated AIS transceiver");
        }

        Vector2D myShipPosition = Mercator.projection(data.longitude, data.latitude);

        warnings.put("TransceiverPosition", "");

        // Cases where we have both a fore/aft and a starboard/port
        if (data.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, fore, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, fore, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, fore, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, fore, oldData.distancePort, length, width);
            }
        }
        else if (data.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.AftStarboard(myShipPosition, aft, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenter.AftPort(myShipPosition, aft, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, aft, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, aft, oldData.distancePort, length, width);
            }
        }
        else if (oldData.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, oldData.distanceFore, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, oldData.distanceFore, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, oldData.distanceFore, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, oldData.distanceFore, oldData.distancePort, length, width);
            }
        }
        else if (oldData.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.AftStarboard(myShipPosition, oldData.distanceAft, starboard, length, width);
            }
            else if (data.distancePortIsSet) {
                return MovePositionToCenter.AftPort(myShipPosition, oldData.distanceAft, port, length, width);
            }
            else if (oldData.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, oldData.distanceAft, oldData.distanceStarboard, length, width);
            }
            else if (oldData.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, oldData.distanceAft, oldData.distancePort, length, width);
            }
        }

        // Cases where we have only fore/aft or only starboard/port
        if (data.distanceForeIsSet && data.distanceAftIsSet) {
            return MovePositionToCenter.Fore(myShipPosition, fore, length);
        }
        else if (data.distanceStarboardIsSet && data.distancePortIsSet) {
            return MovePositionToCenter.Starboard(myShipPosition, starboard, width);
        }
        else if (oldData.distanceForeIsSet && oldData.distanceAftIsSet) {
            return MovePositionToCenter.Fore(myShipPosition, oldData.distanceFore, length);
        }
        else if (oldData.distanceStarboardIsSet && oldData.distancePortIsSet) {
            return MovePositionToCenter.Starboard(myShipPosition, oldData.distanceStarboard, width);
        }

        // Cases where we only have one
        if (data.distanceForeIsSet) {
            return MovePositionToCenter.Fore(myShipPosition, fore, length);
        }
        else if (data.distanceAftIsSet) {
            return MovePositionToCenter.Aft(myShipPosition, aft, length);
        }
        else if (data.distanceStarboardIsSet) {
            return MovePositionToCenter.Starboard(myShipPosition, starboard, width);
        }
        else if (data.distancePortIsSet) {
            return MovePositionToCenter.Port(myShipPosition, port, width);
        }
        else if (oldData.distanceForeIsSet) {
            return MovePositionToCenter.Fore(myShipPosition, oldData.distanceFore, length);
        }
        else if (oldData.distanceAftIsSet) {
            return MovePositionToCenter.Aft(myShipPosition, oldData.distanceAft, length);
        }
        else if (oldData.distanceStarboardIsSet) {
            return MovePositionToCenter.Starboard(myShipPosition, oldData.distanceStarboard, width);
        }
        else if (oldData.distancePortIsSet) {
            return MovePositionToCenter.Port(myShipPosition, oldData.distancePort, width);
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
            //TODO: Calculate SOG based on previous position
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
            //TODO: Calculate COG based on previous position
            else if (data.headingIsSet) {
                warnings.put("COG", "COG unknown, using heading instead");
                cogPlaceholder = data.heading;
                return data.heading;
            }
            else if (oldData.headingIsSet) {
                warnings.put("COG", "Lost COG and heading, using old data heading");
                cogPlaceholder = oldData.heading;
                return oldData.heading;

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

        if (data.longitude == 181) {
            warnings.put("Longitude", "Longitude is unrealistic");
        }

        // TODO: Tjek om det her er helt skørt
        return data.longitude;
    }

    public double HandleLatitude() {

        if (data.latitude == 91) {
            warnings.put("Latitude", "Latitude is unrealistic");
        }

        // TODO: Tjek om det her er helt skørt
        return data.latitude;
    }
}
