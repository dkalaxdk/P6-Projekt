package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;
import org.apache.commons.collections4.Get;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputSimulator extends Thread{

    BlockingQueue<AISData> buffer;
    String inputFile;
    ArrayList<AISData> dataList;
    int listIterator;
    FileParser fileParser;
    public LocalDateTime currentTime;
    public AISData nextInput;

    public InputSimulator(BlockingQueue<AISData> buffer, String inputFile) throws IOException {
        this.buffer = buffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        dataList = fileParser.GetInputList();

        listIterator = 0;
    }

    // todo: lav evt flere tests til run()
    @Override
    public void run() throws NullPointerException{

        nextInput = GetNextInput();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                boolean listNotEmpty = AddListToBuffer();
                if (!listNotEmpty)
                    executorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }


    // todo: gør det muligt at ændre på hvor hurtigt tiden går
    public boolean AddListToBuffer(){
        SetCurrentTime();

        while(nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            buffer.add(nextInput);
            nextInput = GetNextInput();
        }

        boolean listIsEmpty = nextInput == null;
        return !listIsEmpty;
    }

    public void SetCurrentTime(){
        if (currentTime == null)
            currentTime = nextInput.dateTime;
        else
            currentTime = currentTime.plusSeconds(1);
    }

    public AISData GetNextInput() {
        if (listIterator < dataList.size()){
            AISData nextElement = dataList.get(listIterator);
            listIterator++;

            return nextElement;
        }
        else
            return null;
    }
}
