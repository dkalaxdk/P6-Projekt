package Dibbidut.Classes;

import Dibbidut.Interfaces.IDataInput;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;

public class FileParser implements IDataInput {

    private File input;
    private Reader reader;
    private CSVReader csvReader;


    public FileParser(String fileSource) throws IOException {
        input = new File(fileSource);
        reader = null;
        reader = new FileReader(input);
        csvReader = new CSVReader(reader);
    }

    public AISData getNextInput(){
        return new AISData("n",1234, 123,123,123,123);
    }

    public ArrayList<AISData> getInputList(){
        return new ArrayList<AISData>();
    }

}
