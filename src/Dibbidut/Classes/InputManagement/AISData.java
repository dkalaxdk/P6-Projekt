package Dibbidut.Classes.InputManagement;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AISData implements Comparable {

    @CsvBindByName(column = "Timestamp")
    public String timestampString;

    public LocalDateTime dateTime;

    //public String typeOfMobile;

    @CsvBindByName(column = "MMSI")
    public int mmsi;

    @CsvBindByName(column = "Latitude")
    public double latitude;

    @CsvBindByName(column = "Longitude")
    public double longitude;

    //public String navigationalStatus;

    @CsvBindByName(column = "ROT")
    public double ROT;

    @CsvBindByName(column = "SOG")
    public double SOG;

    @CsvBindByName(column = "COG")
    public double COG;

    @CsvBindByName(column = "Heading")
    public int heading;

    @CsvBindByName(column = "A")
    public int distanceFore;

    @CsvBindByName(column = "B")
    public int distanceAft;

    @CsvBindByName(column = "C")
    public int distancePort;

    @CsvBindByName(column = "D")
    public int distanceStarboard;

    //public String IMO;
    //public String callsign;
    //public String name;
    //public String shipType;
    //public String cargoType;

    @CsvBindByName(column = "Width")
    public int width;

    @CsvBindByName(column = "Length")
    public int length;

    //public String typeOfPositionFixingDevice;
    //public String draught;
    //public String destination;
    //public String ETA;
    //public String dateSourceType;

    boolean mmsiIsSet;
    boolean headingIsSet;
    boolean lengthIsSet;
    boolean widthIsSet;
    boolean sogIsSet;
    boolean cogIsSet;
    boolean rotIsSet;
    boolean distanceForeIsSet;
    boolean distanceAftIsSet;
    boolean distancePortIsSet;
    boolean distanceStarboardIsSet;

    public AISData(){ }

    public AISData(String timestampString, int mmsi, float latitude, float longitude, int width, int length) {
        this.timestampString = timestampString;
        this.mmsi = mmsi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.width = width;
        this.length = length;

        AddDateTime();
    }

    public void AddDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dateTime = LocalDateTime.parse(this.timestampString, formatter);
    }

    @Override
    public int compareTo(Object o) {

        if (this.dateTime.isBefore(((AISData)o).dateTime))
            return -1;
        else if (this.dateTime.isAfter(((AISData)o).dateTime))
            return 1;
        else
            return 0;
    }
}
