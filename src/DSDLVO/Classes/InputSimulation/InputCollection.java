package DSDLVO.Classes.InputSimulation;

import DSDLVO.Classes.InputManagement.AISData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Make sure it does not cause problems that the elements themselves are not thread safe
/**
 * This class maintains a collection of AISData entries indexed by mmsi.
 * It has methods for getting data about all ships, own ship, and target ships
 * The methods of this class are thread safe
 */
public class InputCollection extends AISCollection {
    private int osMMSI;
    private boolean isOsMMSIDefined = false;

    @Override
    public void insert(AISData item) {
        if(contains(item)) {
            AISData prevItem = get(item.mmsi);

            // This is a lot of effort because AISData is very much intended to only ever be created from csv files.
            // Setting the values properties and the isSet properties this way should not be necessary
            AISData newEntry = new AISData(item.timestampString, item.mmsi, item.latitude, item.longitude, item.width, item.length);
            newEntry.widthIsSet = true;
            newEntry.lengthIsSet = true;
            newEntry.ROT = item.ROT == 0 ? prevItem.ROT : item.ROT;
            newEntry.rotIsSet = true;
            newEntry.SOG = item.SOG == 0 ? prevItem.SOG : item.SOG;
            newEntry.sogIsSet = true;
            newEntry.COG = item.COG == 0 ? prevItem.COG : item.COG;
            newEntry.cogIsSet = true;
            newEntry.heading = item.heading == 0 ? prevItem.heading : item.heading;
            newEntry.headingIsSet = true;
            newEntry.distanceFore = item.distanceFore == 0 ? prevItem.distanceFore : item.distanceFore;
            newEntry.distanceForeIsSet = true;
            newEntry.distanceAft = item.distanceAft == 0 ? prevItem.distanceAft : item.distanceAft;
            newEntry.distanceAftIsSet = true;
            newEntry.distancePort = item.distancePort == 0 ? prevItem.distancePort : item.distancePort;
            newEntry.distancePortIsSet = true;
            newEntry.distanceStarboard = item.distanceStarboard == 0 ? prevItem.distanceStarboard : item.distanceStarboard;
            newEntry.distanceStarboardIsSet = true;

            super.insert(newEntry);
        }
        else {
            super.insert(item);
        }
    }

    public synchronized void setOwnShipMMSI(int mmsi) {
        osMMSI = mmsi;
        isOsMMSIDefined = true;
    }

    public AISData getOwnShip() {
        if(isOsMMSIDefined)
            return super.get(osMMSI);
        else
            return null;
    }

    public List<AISData> getTargetShips() {
        return super.getAll().stream().filter(item -> item.mmsi != osMMSI).collect(Collectors.toList());
    }
}
