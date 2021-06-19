package DSDLVO.Classes.InputSimulation;

import DSDLVO.Classes.InputManagement.AISData;

/**
 * This class maintains a collection of AISData entries indexed by mmsi.
 * It has methods for getting data about all ships, own ship, and target ships
 * The methods of this class are thread safe
 */
public class InputCollection extends AISCollection{

    @Override
    public void insert(AISData item) {
        if(contains(item)) {
            AISData prevItem = get(item.mmsi);

            AISData newEntry = new AISData(item.timestampString, item.mmsi, item.latitude, item.longitude, item.width, item.length);
            newEntry.ROT = item.ROT == 0 ? prevItem.ROT : item.ROT;
            newEntry.SOG = item.SOG == 0 ? prevItem.SOG : item.SOG;
            newEntry.COG = item.COG == 0 ? prevItem.COG : item.COG;
            newEntry.heading = item.heading == 0 ? prevItem.heading : item.heading;
            newEntry.distanceFore = item.distanceFore == 0 ? prevItem.distanceFore : item.distanceFore;
            newEntry.distanceAft = item.distanceAft == 0 ? prevItem.distanceAft : item.distanceAft;
            newEntry.distancePort = item.distancePort == 0 ? prevItem.distancePort : item.distancePort;
            newEntry.distanceStarboard = item.distanceStarboard == 0 ? prevItem.distanceStarboard : item.distanceStarboard;

            super.insert(newEntry);
        }
        else {
            super.insert(item);
        }
    }
}
