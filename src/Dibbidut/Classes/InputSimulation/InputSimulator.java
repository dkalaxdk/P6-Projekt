package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputSimulator extends Thread{

    BlockingQueue<AISData> tsBuffer;
    BlockingQueue<AISData> osBuffer;
    String inputFile;
    ArrayList<AISData> dataList;
    int dataListIterator;
    public ArrayList<AISData> tsList;
    int osMMSI;

    FileParser fileParser;
    public LocalDateTime currentTime;
    public AISData nextInput;

    public InputSimulator(int osMMSI, BlockingQueue<AISData> osBuffer, BlockingQueue<AISData> tsBuffer, String inputFile) throws IOException {
        this.osMMSI = osMMSI;
        this.osBuffer = osBuffer;
        this.tsBuffer = tsBuffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        dataList = fileParser.GetInputList();
        tsList = new ArrayList<>();

        dataListIterator = 0;
    }

    // todo: lav evt flere tests til run()
    @Override
    public void run() throws NullPointerException{

        // tag lock her
        RunSetUp();
        // slip lock her

        // todo: gør det muligt at ændre på hvor hurtigt tiden går
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (nextInput != null){
                    currentTime = currentTime.plusSeconds(1);
                    AddDataToBuffers();
                }
                else
                    executorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    // todo: lav tests af RunSetUp() (hvad hvis os ikke er i input?) og AddDataToBuffers()
    public void RunSetUp(){
        nextInput = GetNextInput();

        while(nextInput != null && (currentTime == null || !nextInput.dateTime.isAfter(currentTime))){
            if (nextInput.mmsi != osMMSI){
                AddNextInputToTSList();
            }
            else {
                currentTime = nextInput.dateTime;
                osBuffer.add(nextInput);
            }
            nextInput = GetNextInput();
        }
        tsBuffer.addAll(tsList);
        tsList.clear();
    }

    public void AddNextInputToTSList(){
        int i = 0;

        if (tsList.size() > 0) {
            while (tsList.size() > i) {
                if (nextInput.mmsi == tsList.get(i).mmsi)
                    tsList.remove(i);
                i++;
            }
        }

        tsList.add(nextInput);
    }

    public void AddDataToBuffers(){
        while (nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            if (nextInput.mmsi != osMMSI)
                tsList.add(nextInput);
            else
                osBuffer.add(nextInput);
            nextInput = GetNextInput();
        }
        tsBuffer.addAll(tsList);
        tsList.clear();
    }


    public AISData GetNextInput() {
        if (dataListIterator < dataList.size()){
            AISData nextElement = dataList.get(dataListIterator);
            dataListIterator++;

            return nextElement;
        }
        else
            return null;
    }
}
