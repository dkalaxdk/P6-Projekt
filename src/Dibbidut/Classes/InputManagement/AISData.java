package Dibbidut.Classes.InputManagement;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.apache.commons.lang3.StringUtils;

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
    public double ROT;
    public boolean rotIsSet;

    @CsvBindByName(column = "SOG")
    public String sogString;
    public double SOG;
    public boolean sogIsSet;

    @CsvBindByName(column = "COG")
    public String cogString;
    public double COG;
    public boolean cogIsSet;

    @CsvBindByName(column = "Heading")
    public String headingString;
    public int heading;
    public boolean headingIsSet;


    @CsvBindByName(column = "A")
    public String distanceForeString;
    public int distanceFore;
    public boolean distanceForeIsSet;

    @CsvBindByName(column = "B")
    public String distanceAftString;
    public int distanceAft;
    public boolean distanceAftIsSet;

    @CsvBindByName(column = "C")
    public String distancePortString;
    public int distancePort;
    public boolean distancePortIsSet;

    @CsvBindByName(column = "D")
    public String distanceStarboardString;
    public int distanceStarboard;
    public boolean distanceStarboardIsSet;

    //public String IMO;
    //public String callsign;
    //public String name;
    //public String shipType;
    //public String cargoType;

    @CsvBindByName(column = "Width")
    public String widthString;
    public int width;
    public boolean widthIsSet;

    @CsvBindByName(column = "Length")
    public String lengthString;
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

    public void SetValuesAndBooleans(){
        rotIsSet = !StringUtils.isEmpty(rotString);
        ROT = rotIsSet ? Double.parseDouble(rotString) : 0;

        sogIsSet = !StringUtils.isEmpty(sogString);
        SOG = sogIsSet ? Double.parseDouble(sogString) : 0;

        cogIsSet = !StringUtils.isEmpty(cogString);
        COG = cogIsSet ? Double.parseDouble(cogString) : 0;

        headingIsSet = !StringUtils.isEmpty(headingString);
        heading = headingIsSet ? Integer.parseInt(headingString) : 0;

        distanceForeIsSet = !StringUtils.isEmpty(distanceForeString);
        distanceFore = distanceForeIsSet ? Integer.parseInt(distanceForeString) : 0;

        distanceAftIsSet = !StringUtils.isEmpty(distanceAftString);
        distanceAft = distanceAftIsSet ? Integer.parseInt(distanceAftString) : 0;

        distancePortIsSet = !StringUtils.isEmpty(distancePortString);
        distancePort = distancePortIsSet ? Integer.parseInt(distancePortString) : 0;

        distanceStarboardIsSet = !StringUtils.isEmpty(distanceStarboardString);
        distanceStarboard = distanceStarboardIsSet ? Integer.parseInt(distanceStarboardString) : 0;

        widthIsSet = !StringUtils.isEmpty(widthString);
        width = widthIsSet ? Integer.parseInt(widthString) : 0;

        lengthIsSet = !StringUtils.isEmpty(lengthString);
        length = lengthIsSet ? Integer.parseInt(lengthString) : 0;
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
