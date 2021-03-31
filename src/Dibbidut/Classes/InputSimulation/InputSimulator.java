package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISBuffer;
import Dibbidut.Classes.InputManagement.AISData;

import java.io.IOException;
import java.util.ArrayList;

public class InputSimulator extends Thread{

    AISBuffer buffer;
    String inputFile;
    ArrayList<AISData> dataList;
    FileParser fileParser;

    public InputSimulator(AISBuffer buffer, String inputFile) throws IOException {
        this.buffer = buffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        dataList = fileParser.GetInputList();
    }
    // todo: få den her til at sende AIS data til bufferen

    @Override
    public void run() {
        super.run();
    }
}
