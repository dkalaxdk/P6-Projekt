package Dibbidut.Classes.InputSimulation;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Exceptions.OSNotFoundException;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class InputSimulator extends Thread{

    BlockingQueue<AISData> tsBuffer;
    BlockingQueue<AISData> osBuffer;
    String inputFile;
    int dataListIterator;
    public ArrayList<AISData> tsList;
    int osMMSI;

    FileParser fileParser;
    public LocalDateTime currentTime;
    public AISData nextInput;

    Lock bufferLock;
    Float timeFactor;

    public ScheduledExecutorService executorService;

    public InputSimulator(Float timeFactor, Lock bufferLock, int osMMSI, BlockingQueue<AISData> osBuffer, BlockingQueue<AISData> tsBuffer, String inputFile) throws IOException {
        this.timeFactor = timeFactor > 0 ? timeFactor : 1f;

        this.bufferLock = bufferLock;

        this.osMMSI = osMMSI;
        this.osBuffer = osBuffer;
        this.tsBuffer = tsBuffer;
        this.inputFile = inputFile;

        fileParser = new FileParser(inputFile);
        tsList = new ArrayList<>();

        dataListIterator = 0;
    }

    // todo: lav evt flere tests til run()
    @Override
    public void run() throws NullPointerException{

        executorService = Executors.newScheduledThreadPool(1);

        // Adds time to the current time, and updates the buffers every 1000/timeFactor milliseconds, i.e. once a second if timeFactor = 1

//        executorService.scheduleAtFixedRate(() -> {
//            if (nextInput != null){
//                currentTime = currentTime.plusSeconds(1);
//                AddDataToBuffers();
//            }
//            else
//                executorService.shutdown();
//        }, 0, (long)(1000 / timeFactor), TimeUnit.MILLISECONDS);

        Runnable runnable = () -> {
            if (nextInput != null){

                if (timeFactor != 0) {
                    long start = System.nanoTime();

                    currentTime = currentTime.plusSeconds(1);
                    AddDataToBuffers();

                    long end = System.nanoTime();

                    long duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);

                    executorService.schedule(this, (1000 / timeFactor.longValue()) - duration, TimeUnit.MILLISECONDS);
                }
                else {
                    executorService.schedule(this, 500, TimeUnit.MILLISECONDS);
                }

            }
            else
                executorService.shutdown();
        };

        executorService.schedule(runnable, 0, TimeUnit.MILLISECONDS);
    }

    public void RunSetUp() throws OSNotFoundException {
        AISData os = null;
        nextInput = GetNextInput();

        while(nextInput != null && (currentTime == null || !nextInput.dateTime.isAfter(currentTime))){
            if (nextInput.mmsi != osMMSI){
                AddNextInputToTSList();
            }
            else {
                currentTime = nextInput.dateTime;
                os = nextInput;
            }
            nextInput = GetNextInput();
        }

        if (currentTime == null) {
            throw new OSNotFoundException();
        }

        bufferLock.lock();

        if (os != null)
            osBuffer.add(os);
        tsBuffer.addAll(tsList);

        bufferLock.unlock();

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
        AISData os = null;

        while (nextInput != null && !nextInput.dateTime.isAfter(currentTime)){
            if (nextInput.mmsi != osMMSI)
                tsList.add(nextInput);
            else
                os = nextInput;

            nextInput = GetNextInput();
        }

        bufferLock.lock();

        if (os != null)
            osBuffer.add(os);
        tsBuffer.addAll(tsList);

        bufferLock.unlock();

        tsList.clear();
    }

    public AISData GetNextInput() {

        try {
            return fileParser.GetNextInput();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void SetTimeFactor(float value) {
        timeFactor = value;
    }

    public float GetTimeFactor() {
        return timeFactor;
    }
}
