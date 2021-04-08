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

    boolean dataListEmpty;

    public InputSimulator(int osMMSI, BlockingQueue<AISData> osBuffer, BlockingQueue<AISData> tsBuffer, String inputFile) throws IOException {
        this.osMMSI = osMMSI;
        this.osBuffer = osBuffer;
        this.tsBuffer = tsBuffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        dataList = fileParser.GetInputList();
        tsList = new ArrayList<>();

        dataListIterator = 0;
        dataListEmpty = false;
    }

    // todo: lav evt flere tests til run()
    @Override
    public void run() throws NullPointerException{

        RunSetUp();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                AddDataToBuffers();
                if (dataListEmpty)
                    executorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void RunSetUp(){
        nextInput = GetNextInput();

        AddDataBeforeOSToTSList();

        currentTime = nextInput.dateTime;
        osBuffer.add(nextInput);
        nextInput = GetNextInput();

        while(nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            tsList.add(nextInput);
            nextInput = GetNextInput();
        }

        AddTSListToTSBuffer();
    }

    public void AddDataBeforeOSToTSList(){
        while (nextInput != null && nextInput.mmsi != osMMSI){
            tsList.add(nextInput);
            nextInput = GetNextInput();
        }
    }

    public void AddDataAfterOSToTSList(){
        int i = 0;
        while (nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            while (tsList.size() > i){
                if (tsList.get(i).mmsi == nextInput.mmsi){
                    
                }
                i++;
            }
            tsList.add(nextInput);
            nextInput = GetNextInput();
        }
    }

    public void AddTSListToTSBuffer(){
        int i = 0;
        while (tsList.size() > i){
            tsBuffer.add(tsList.get(i));
            i++;
        }
        tsList.clear();
    }

    public void AddDataToBuffers(){
        // set listEmpty boolean
    }


    // todo: gør det muligt at ændre på hvor hurtigt tiden går
    public boolean AddListToBuffer(){
        SetCurrentTime();

        while(nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            tsBuffer.add(nextInput);
            nextInput = GetNextInput();
        }
        // todo: start simulation fra own ship (lad ikke programmet køre i tid før OS)
        //  lav starup metode der henter ting fra listen indtil vi starter

        boolean listIsEmpty = nextInput == null;
        return !listIsEmpty;

        // todo: lav og implementer own ship buffer
    }

    public void SetCurrentTime(){
        if (currentTime == null)
            currentTime = nextInput.dateTime;
        else
            currentTime = currentTime.plusSeconds(1);
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
