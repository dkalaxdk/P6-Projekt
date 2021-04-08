package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class UpdateShipHandler extends ShipHandler {

    public UpdateShipHandler(Ship myShip, AISData data, AISData oldData, double ownShipLongitude, Hashtable<String, String> warnings) {
        super(myShip, data, oldData, ownShipLongitude, warnings);
    }

    public int HandleMMSI() {

//        if (data.mmsiIsSet && (myShip.mmsi != data.mmsi)) {
//            warnings.put("MMSI", "MMSI changed, keeping original MMSI");
//        }

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

        // SOG and COG have already been handled at this point

        // If the new data is complete garbage
        if (!data.cogIsSet && !data.sogIsSet) {

            // If the old data was fantastic, use that instead
            if (oldData.cogIsSet && oldData.sogIsSet) {
                warnings.put("Velocity", "Lost SOG and COG, using old data instead");
                return CalculateVelocity(oldData.SOG, oldData.COG);
            }
            // If the old data was somewhat better, use that
            else if (oldData.cogIsSet && !oldData.sogIsSet) {
                warnings.put("Velocity", "Lost COG, using old data. SOG unknown, using " + sogPlaceHolder + " as a placeholder");
                return CalculateVelocity(sogPlaceHolder, oldData.COG);
            }
            // If nothing works, return a placeholder
            else {
                warnings.put("Velocity", "Velocity unknown, using " + GetVelocityPlaceHolder() + " as a placeholder");
                return GetVelocityPlaceHolder();
            }
        }
        // If the new data has COG, but not SOG
        else if (data.cogIsSet && !data.sogIsSet) {

            // If the old data has SOG, use that
            if (oldData.sogIsSet) {
                warnings.put("Velocity", "Lost SOG, using old data instead");
                return CalculateVelocity(oldData.SOG, data.COG);
            }
            // If it doesn't, use the placeholder
            else {
                warnings.put("Velocity", "SOG unknown, using " + sogPlaceHolder + " as a placeholder");
                return CalculateVelocity(sogPlaceHolder, data.COG);
            }
        }
        // If the new data has SOG, but not COG
        else if (!data.cogIsSet && data.sogIsSet) {
            // If the old data has COG, use that
            if (oldData.cogIsSet) {
                warnings.put("Velocity", "Lost COG, using old data instead");
                return CalculateVelocity(data.SOG, oldData.COG);
            }
            // If it doesn't, use the placeholder
            else {
                warnings.put("Velocity", "COG unknown, using " + cogPlaceholder + " as a placeholder");
                return CalculateVelocity(data.SOG, cogPlaceholder);
            }
        }
        // If the new data is fine
        else {
            warnings.put("Velocity", "");
            return CalculateVelocity(data.SOG, data.COG);
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
                warnings.put("COG", "COG is unknown, using " + 0 + " as a placeholder");
                return 0;
            }
        }
        else {
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
