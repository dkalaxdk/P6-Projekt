package Dibbidut.Classes;

import Dibbidut.Interfaces.IDataInput;
import com.opencsv.*;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.lang.System;

import java.io.*;
import java.util.ArrayList;

public class FileParser implements IDataInput {

    private Reader reader;
    private ArrayList<AISData> data;


    public FileParser(String fileSource) throws IOException {
        reader = new FileReader(new File(fileSource));
        data = new ArrayList<AISData>();
    }

    public AISData getNextInput(){
        return new AISData("n",1234, 123,123,123,123);
    }

    public ArrayList<AISData> getInputList(){
        data = (ArrayList<AISData>) new CsvToBeanBuilder(reader).withType(AISData.class).build().parse();
        printData(data.get(0));
        return data;
    }

    public void printData(AISData data){
        System.out.println("TimeStamp:" + data.timeStamp);
        System.out.println("MMSI" + data.mmsi);
        System.out.println("Latitude" + data.latitude);
        System.out.println("Longitude" + data.longitude);
        System.out.println("Width" + data.width);
        System.out.println("Length" + data.length);
    }

}
