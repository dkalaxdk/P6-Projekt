package DSDLVO.Classes.Handlers;

import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Utilities.Mercator;
import DSDLVO.Classes.Ship;

import java.util.Hashtable;

public class AISToShipHandler extends ShipHandler {

    public AISToShipHandler(Ship myShip, AISData data, Hashtable<String, String> warnings) {
        super(myShip, data, null, warnings);
    }

    public int HandleMMSI() {
        if (data.mmsi != 0) {
            return data.mmsi;
        } else {
            warnings.put("MMSI", "MMSI is 0");
            return 0;
        }
    }

    public int HandleHeading() {
        if (!data.headingIsSet) {
            if (data.cogIsSet) {
                warnings.put("Heading", "Heading unknown, using COG as a placeholder");
                return (int) Math.round(data.COG);
            }

            warnings.put("Heading", "Heading unknown, using " + headingPlaceholder + " degrees as a placeholder");
            return headingPlaceholder;
        }

        return data.heading;
    }

    public int HandleLength() {
        if (!data.lengthIsSet) {
            warnings.put("Length", "Ship's length is 0, placeholder is " + lengthPlaceHolder + " meters");
            return lengthPlaceHolder;
        } else if (data.length == 0) {
            warnings.put("Length", "Length of ship is 0");
        }

        return data.length;
    }

    public int HandleWidth() {
        if (!data.widthIsSet) {
            warnings.put("Width", "Ship's width is 0, placeholder is " + widthPlaceHolder + " meters");
            return widthPlaceHolder;
        } else if (data.width == 0) {
            warnings.put("Width", "Width of ship is 0");
        }

        return data.width;
    }

    public HPoint HandleVelocity() {

        if (data.sogIsSet && data.cogIsSet) {
            return CalculateVelocity(data.SOG, data.COG);
        } else if (!data.sogIsSet && data.cogIsSet) {
            warnings.put("Velocity", "Ship's SOG is unknown, using " +
                    sogPlaceHolder + " as placeholder");
            return CalculateVelocity(sogPlaceHolder, data.COG);
        } else if (data.sogIsSet && !data.cogIsSet) {
            warnings.put("Velocity", "Ship's COG is unknown, the ship might be moving in a different direction");
            return CalculateVelocity(data.SOG, cogPlaceholder);
        } else {
            warnings.put("Velocity", "Missing information, can not calculate velocity, using " +
                    GetVelocityPlaceHolder() + " as placeholder");
            return GetVelocityPlaceHolder();
        }
    }

    public HPoint HandlePosition() {
        HPoint myShipPosition;

        if (data.longitude != 0 && data.latitude != 0) {
            myShipPosition = Mercator.projection(data.longitude, data.latitude);
        } else {
            warnings.put("Position", "Missing information, can not calculate position, using " +
                    GetPositionPlaceHolder() + " as placeholder");
            myShipPosition = GetPositionPlaceHolder();
        }

        return HandleCentering(myShipPosition);
    }

    private HPoint HandleCentering(HPoint myShipPosition) {

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

        if (data.distanceForeIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.ForeStarboard(myShipPosition, fore, starboard, length, width);
            } else if (data.distancePortIsSet) {
                return MovePositionToCenter.ForePort(myShipPosition, fore, port, length, width);
            }
        } else if (data.distanceAftIsSet) {
            if (data.distanceStarboardIsSet) {
                return MovePositionToCenter.AftStarboard(myShipPosition, aft, starboard, length, width);
            } else if (data.distancePortIsSet) {
                return MovePositionToCenter.AftPort(myShipPosition, aft, port, length, width);
            }
        }

        if ((data.distanceForeIsSet && data.distanceAftIsSet) && (fore + aft == length)) {
            return MovePositionToCenter.Fore(myShipPosition, fore, length);
        } else if ((data.distanceStarboardIsSet && data.distancePortIsSet) && (starboard + aft == width)) {
            return MovePositionToCenter.Starboard(myShipPosition, starboard, width);
        } else if (data.distanceForeIsSet) {
            return MovePositionToCenter.Fore(myShipPosition, fore, length);
        } else if (data.distanceAftIsSet) {
            return MovePositionToCenter.Aft(myShipPosition, aft, length);
        } else if (data.distanceStarboardIsSet) {
            return MovePositionToCenter.Starboard(myShipPosition, starboard, width);
        } else if (data.distancePortIsSet) {
            return MovePositionToCenter.Port(myShipPosition, port, width);
        } else {
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
            if (data.headingIsSet) {
                warnings.put("COG", "COG is unknown, using heading as a placeholder");
                cogPlaceholder = data.heading;
                return cogPlaceholder;
            }

            warnings.put("COG", "COG is unknown, using " + cogPlaceholder + " as a placeholder");
            return cogPlaceholder;
        }

        return data.COG;
    }

    public double HandleLongitude() {
        if (data.longitude != 0) {
            if (data.longitude == 181) {
                warnings.put("Longitude", "Longitude is unrealistic");
            }
            return data.longitude;
        } else {
            warnings.put("Longitude", "Ship's longitude is 0");
            return 0;
        }
    }

    public double HandleLatitude() {
        if (data.latitude != 0) {
            if (data.latitude == 91) {
                warnings.put("Latitude", "Latitude is unrealistic");
            }
            return data.latitude;
        } else {
            warnings.put("Latitude", "Ship's latitude is 0");
            return 0;
        }
    }
}
