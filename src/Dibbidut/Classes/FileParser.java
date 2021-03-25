package Dibbidut.Classes;

import Dibbidut.Interfaces.IDataInput;
import com.opencsv.bean.CsvToBeanBuilder;

import java.lang.System;

import java.io.*;
import java.util.ArrayList;

public class FileParser implements IDataInput {

    public Reader reader;
    private ArrayList<AISData> data;


    public FileParser(String fileSource) throws IOException {
        reader = new BufferedReader(new FileReader(fileSource));
        data = new ArrayList<>();

        CheckFileFormat();
    }

    public AISData GetNextInput(){
        return new AISData();
    }

    public ArrayList<AISData> GetInputList(){
        data = (ArrayList<AISData>) new CsvToBeanBuilder(reader).withType(AISData.class).build().parse();

        for (AISData datapoint: data) {
            datapoint.AddDateTime();
        }

        PrintData(data.get(0));

        return data;
    }

    public void CheckFileFormat() throws IOException {
        reader.mark(2);
        if(reader.read() != '#'){
            reader.reset();
        }
    }

    public void PrintData(AISData data){
        System.out.println("TimeStamp: " + data.timestampString);
        System.out.println("MMSI: " + data.mmsi);
        System.out.println("Latitude: " + data.latitude);
        System.out.println("Longitude: " + data.longitude);
        System.out.println("Width: " + data.width);
        System.out.println("Length: " + data.length);
        System.out.println("-----------------------");
    }
}
