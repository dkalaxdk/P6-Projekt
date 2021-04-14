package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IDataInput;
import com.opencsv.bean.CsvToBeanBuilder;

import java.lang.System;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FileParser implements IDataInput {

    public Reader reader;
    private ArrayList<AISData> dataList;


    public FileParser(String fileSource) throws IOException {
        reader = new BufferedReader(new FileReader(fileSource));
        dataList = new ArrayList<>();

        SkipHashtagIfPresent();
    }

    public ArrayList<AISData> GetInputList(){
        dataList = (ArrayList<AISData>) new CsvToBeanBuilder(reader).withType(AISData.class).build().parse();

        for (AISData datapoint: dataList) {
            datapoint.AddDateTime();
            datapoint.SetValuesAndBooleans();
        }

        Collections.sort(dataList);

        //todo: slet evt print ting
//        PrintData(dataList.get(0));

        return dataList;
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