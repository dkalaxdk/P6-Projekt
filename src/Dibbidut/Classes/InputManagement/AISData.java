package Dibbidut.Classes.InputManagement;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.security.interfaces.DSAPublicKey;
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
    public String rotString;
    @CsvBindByName(column = "ROT")
    public double ROT;
    public boolean rotIsSet;

    @CsvBindByName(column = "SOG")
    public String sogString;
    @CsvBindByName(column = "SOG")
    public double SOG;
    public boolean sogIsSet;

    @CsvBindByName(column = "COG")
    public String cogString;
    @CsvBindByName(column = "COG")
    public double COG;
    public boolean cogIsSet;

    @CsvBindByName(column = "Heading")
    public String headingString;
    @CsvBindByName(column = "Heading")
    public int heading;
    public boolean headingIsSet;


    @CsvBindByName(column = "A")
    public String distanceForeString;
    @CsvBindByName(column = "A")
    public int distanceFore;
    public boolean distanceForeIsSet;

    @CsvBindByName(column = "B")
    public String distanceAftString;
    @CsvBindByName(column = "B")
    public int distanceAft;
    public boolean distanceAftIsSet;

    @CsvBindByName(column = "C")
    public String distancePortString;
    @CsvBindByName(column = "C")
    public int distancePort;
    public boolean distancePortIsSet;

    @CsvBindByName(column = "D")
    public String distanceStarboardString;
    @CsvBindByName(column = "D")
    public int distanceStarboard;
    public boolean distanceStarboardIsSet;

    //public String IMO;
    //public String callsign;
    //public String name;
    //public String shipType;
    //public String cargoType;

    @CsvBindByName(column = "Width")
    public String widthString;
    @CsvBindByName(column = "Width")
    public int width;
    public boolean widthIsSet;

    @CsvBindByName(column = "Length")
    public String lengthString;
    @CsvBindByName(column = "Length")
    public int length;
    public boolean lengthIsSet;


    //public String typeOfPositionFixingDevice;
    //public String draught;
    //public String destination;
    //public String ETA;
    //public String dateSourceType;

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

    public void SetBooleans(){
        rotIsSet = rotString != null;

        sogIsSet = sogString != null;

        cogIsSet = cogString != null;

        headingIsSet = headingString != null;

        distanceForeIsSet = distanceForeString != null;

        distanceAftIsSet = distanceAftString != null;

        distancePortIsSet = distancePortString != null;

        distanceStarboardIsSet = distanceStarboardString != null;

        widthIsSet = widthString != null;

        lengthIsSet = lengthString != null;
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
