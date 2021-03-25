package Dibbidut.Classes;

import com.opencsv.bean.CsvBindByName;

import java.time.*;

public class AISData {

    @CsvBindByName(column = "Timestamp", required = true)
    public String timeStamp;

    //public String typeOfMobile;

    @CsvBindByName(column = "MMSI", required = true)
    public int mmsi;

    @CsvBindByName(column = "Latitude", required = true)
    public float latitude;

    @CsvBindByName(column = "Longitude", required = true)
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

    @CsvBindByName(column = "Width", required = true)
    public int width;

    @CsvBindByName(column = "Length", required = true)
    public int length;

    //public String typeOfPositionFixingDevice;
    //public String draught;
    //public String destination;
    //public String ETA;
    //public String dateSourceType;

    public AISData(){}

    public AISData(String timeStamp, int mmsi, float latitude, float longitude, int width, int length) {
        this.timeStamp = timeStamp;
        this.mmsi = mmsi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.width = width;
        this.length = length;
    }
}
