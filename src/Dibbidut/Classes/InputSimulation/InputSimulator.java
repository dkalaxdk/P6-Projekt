package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class InputSimulator extends Thread{

    BlockingQueue<AISData> buffer;
    String inputFile;
    ArrayList<AISData> dataList;
    int listIterator;
    FileParser fileParser;

    public InputSimulator(BlockingQueue<AISData> buffer, String inputFile) throws IOException {
        this.buffer = buffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        dataList = fileParser.GetInputList();
        listIterator = 0;
    }
    // todo: få den her til at sende AIS data til bufferen

    @Override
    public void run() throws NullPointerException{
        buffer.add(dataList.get(listIterator));
        listIterator++;
    }

    public AISData GetNextInput(){
        if (listIterator < dataList.size()){
            AISData nextElement = dataList.get(listIterator);
            listIterator++;

            return nextElement;
        }
        else
            return null;

    }
}
