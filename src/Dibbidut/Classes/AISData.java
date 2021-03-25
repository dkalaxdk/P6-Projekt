package Dibbidut.Classes;

import java.time.*;

public class AISData {
    public String timeStamp;
    //public String typeOfMobile;
    public int mmsi;
    public float latitude;
    public float longitude;
    //public String navigationalStatus;
    //public String ROT;
    //public String SOG;
    //public String COG;
    //public String heading;
    //public String IMO;
    //public String callsign;
    //public String name;
    //public String shipType;
    //public String cargoType;
    public int width;
    public int length;
    //public String typeOfPositionFixingDevice;
    //public String draught;
    //public String destination;
    //public String ETA;
    //public String dateSourceType;

    public AISData(String timeStamp, int mmsi, float latitude, float longitude, int width, int length) {
        this.timeStamp = timeStamp;
        this.mmsi = mmsi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.width = width;
        this.length = length;
    }
}
