package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;

import java.util.Hashtable;

public class UpdateShipHandler extends ShipHandler {

//    public UpdateShipHandler(Ship myShip, Ship updatedShip, Hashtable<String, String> warnings) {
//        super(myShip, updatedShip, warnings);
//    }

    public UpdateShipHandler(Ship myShip, AISData updatedData, AISData oldData, Hashtable<String, String> warnings) {
        super(myShip, updatedData, oldData, warnings);
    }

    public int HandleMMSI() {

        if (myShip.mmsi != updatedShip.mmsi) {
            warnings.put("MMSI", "MMSI changed, keeping original MMSI");
        }

        return myShip.mmsi;
    }

    public int HandleHeading() {
        if (updatedShip.heading == 0) {
            warnings.put("Heading", "Ship may have incorrect heading");
        }

        return updatedShip.heading;
    }

    public int HandleLength() {
        if (updatedShip.length == 0) {
            warnings.put("Length", "New length is 0, using old length of " + myShip.length + "m");
            return myShip.length;
        }
        else if (myShip.length != updatedShip.length) {
            warnings.put("Length", "Ship's length has changed, going from " +
                    myShip.length + "m to " + updatedShip.length + "m");
            return updatedShip.length;
        }
        else {
            return myShip.length;
        }
    }

    public int HandleWidth() {
        if (updatedShip.width == 0) {
            warnings.put("Width", "New width is 0, using old width of " + myShip.width + "m");
            return myShip.width;
        }
        else if (myShip.width != updatedShip.width) {
            warnings.put("Width", "Ship's width has changed, going from " +
                    myShip.width + "m to " + updatedShip.width + "m");
            return updatedShip.width;
        }
        else {
            return myShip.width;
        }
    }

    public Vector2D HandleVelocity() {
        Vector2D zero = new Vector2D(0,0);

        // TODO: Håndter det her ved at se på fortiden
        if (updatedShip.velocity.equals(zero)) {
            warnings.put("Velocity", "Ship may still be moving");
        }

        return updatedShip.position;

        // TODO: Måske se på om den nye velocity falder helt uden for skibets path / Er helt urealistisk?
    }

    public Vector2D HandlePosition() {
        Vector2D zero = new Vector2D(0,0);

        if (updatedShip.position.equals(zero)) {
            warnings.put("Position", "Position became zero, keeping old position");
            return myShip.position;
        }

        // TODO: Tjek om det her er helt ved siden af
        return updatedShip.position;
    }

    public Vector2D HandleCentering() {
        Vector2D zero = new Vector2D(0,0);

        if (updatedShip.centeredPosition.equals(zero)) {
            warnings.put("CenteredPosition", "Position became zero, keeping old position");
            return myShip.centeredPosition;
        }
        // TODO: Håndter at vi mistede A, B, C eller D, ved at udregne det igen ud fra de gamle værdier.
        // TODO: Sæt evt. også et flag ovre i AISToShipHandleren, så vi ved hvorfor de er blevet ens
        else if (myShip.position.equals(updatedShip.centeredPosition)) {
            warnings.put("CenteredPosition", "Lost centered position");
            return myShip.position;
        }

        return updatedShip.centeredPosition;
    }

    public double HandleSOG() {
        // TODO: Håndter det her ved at se på fortiden. Fjern placeholder fra AISToShipHandler
        return updatedShip.sog;
    }

    public double HandleCOG() {
        // TODO: Håndter det her ved at se på fortiden
        return updatedShip.cog;
    }

    public double HandleLongitude() {
        // TODO: Tjek om det her er helt skørt
        return updatedShip.longitude;
    }

    public double HandleLatitude() {
        // TODO: Tjek om det her er helt skørt
        return updatedShip.latitude;
    }
}
