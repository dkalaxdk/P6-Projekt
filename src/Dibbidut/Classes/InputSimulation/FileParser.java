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
    int listIterator;


    public FileParser(String fileSource) throws IOException {
        reader = new BufferedReader(new FileReader(fileSource));
        dataList = new ArrayList<>();
        listIterator = 0;

        CheckFileFormat();
    }

    public AISData GetNextInput(){
        AISData nextElement = dataList.get(listIterator);
        listIterator++;
        return nextElement;
    }

    public ArrayList<AISData> GetInputList(){
        dataList = (ArrayList<AISData>) new CsvToBeanBuilder(reader).withType(AISData.class).build().parse();

        for (AISData datapoint: dataList) {
            datapoint.AddDateTime();
        }

        Collections.sort(dataList);

        PrintData(dataList.get(0));

        return dataList;
    }

    public void CheckFileFormat() throws IOException {
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


// todo: her er oversigten over hvad der skal ske
// next step: put data from list into buffer (how do we handle the timing??)

// sorter efter timestamp

//start timer ved ældste datapunkt

// trhreads: blocking queue i java

// frasorter skibe der er for langt væk

//midlertidig sluk tråd: sleep
//    tag tid på udregning og træk det fra tiden der skal gå inden næste gang vi kører den


