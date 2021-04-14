package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IDataInput;
import com.opencsv.bean.CsvToBeanBuilder;

import java.lang.System;

import java.io.*;
import java.util.Iterator;

public class FileParser implements IDataInput {

    public Reader reader;
    private final Iterator<AISData> dataList;


    public FileParser(String fileSource) throws IOException {
        reader = new BufferedReader(new FileReader(fileSource));
        SkipHashtagIfPresent();
        dataList = new CsvToBeanBuilder(reader).withType(AISData.class).build().iterator();
    }

    public AISData GetNextInput(){
        AISData data = dataList.next();

        data.AddDateTime();
        data.SetValuesAndBooleans();

        return data;
    }

    public void SkipHashtagIfPresent() throws IOException {
        reader.mark(2);
        if(reader.read() != '#'){
            reader.reset();
        }
    }

    public void PrintData(AISData data){
        System.out.println("TimeStamp: " + data.timestampString);
        System.out.println("DateTime: " + data.dateTime);
        System.out.println("MMSI: " + data.mmsi);
        System.out.println("Latitude: " + data.latitude);
        System.out.println("Longitude: " + data.longitude);
        System.out.println("Width: " + data.width);
        System.out.println("Length: " + data.length);
        System.out.println("-----------------------");
    }

}