package DSDLVO.Classes.InputSimulation;

import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Exceptions.OSNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class InputSimulator extends Thread {

    public BlockingQueue<AISData> tsBuffer;
    public BlockingQueue<AISData> osBuffer;
    public ArrayList<AISData> tsList;
    private final int osMMSI;
    public InputCollection inputCollection;

    private final FileParser fileParser;
    public LocalDateTime currentTime;
    public AISData nextInput;

    private Lock bufferLock;
    private Float timeFactor;

    public ScheduledExecutorService executorService;

    public InputSimulator(Lock bufferLock, int osMMSI, String inputFile) throws IOException {
        this.timeFactor = 1f;
        this.bufferLock = bufferLock;
        this.osMMSI = osMMSI;
        this.osBuffer = new LinkedBlockingQueue<>();
        this.tsBuffer = new LinkedBlockingQueue<>();

        fileParser = new FileParser(inputFile);
        tsList = new ArrayList<>();
        inputCollection = new InputCollection();
        inputCollection.setOwnShipMMSI(osMMSI);
    }

    @Override
    public void run() throws NullPointerException {
        executorService = Executors.newScheduledThreadPool(1);

        Runnable runnable = () -> {
            if (nextInput != null) {
                if (timeFactor != 0) {
                    long start = System.nanoTime();

                    currentTime = currentTime.plusSeconds(1);
                    AddDataToBuffers();

                    long end = System.nanoTime();
                    long duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);

                    executorService.schedule(this, (1000 / timeFactor.longValue()) - duration, TimeUnit.MILLISECONDS);
                } else {
                    executorService.schedule(this, 500, TimeUnit.MILLISECONDS);
                }
            } else
                executorService.shutdown();
        };

        executorService.schedule(runnable, 0, TimeUnit.MILLISECONDS);
    }

    public void RunSetUp() throws OSNotFoundException {
        AISData os = null;
        nextInput = GetNextInput();

        // Finds the fist occurrence of OS in the file, other ships are added to list of Target Ships
        while (inputIsAvailable() && (currentTime == null || nextInputTimestampIsNotLaterThanCurrentTime())) {
            if (nextInput.mmsi != osMMSI) {
                AddNextInputToTSList();
            } else {
                currentTime = nextInput.dateTime;
                os = nextInput;
            }
            nextInput = GetNextInput();
        }

        // If no OS was found throw exception
        if (currentTime == null) {
            throw new OSNotFoundException();
        }

        addElementsToBuffers(os, tsList);
    }

    public void AddNextInputToTSList() {
        // Remove the old value for the TS
        tsList.removeIf(ts -> ts.mmsi == nextInput.mmsi);
        // Add new value
        tsList.add(nextInput);
    }

    public void AddDataToBuffers() {
        AISData os = null;

        while (inputIsAvailable() && nextInputTimestampIsNotLaterThanCurrentTime()) {
            if (nextInput.mmsi != osMMSI)
                tsList.add(nextInput);
            else
                os = nextInput;

            nextInput = GetNextInput();
        }

        addElementsToBuffers(os, tsList);
    }

    /**
     * @return Whether or not nextInput is null
     */
    private boolean inputIsAvailable() {
        return nextInput != null;
    }

    /**
     * @return True is the datetime property of nextInput is later than currentTime, false otherwise
     */
    private boolean nextInputTimestampIsNotLaterThanCurrentTime() {
        return !nextInput.dateTime.isAfter(currentTime);
    }

    /**
     * This method adds data to buffers and handles the lock
     */
    private void addElementsToBuffers(AISData os, List<AISData> tsList) {
        bufferLock.lock();

        if (os != null)
            //osBuffer.add(os);
        //tsBuffer.addAll(tsList);

        bufferLock.unlock();

        inputCollection.insert(os);
        inputCollection.insertList(tsList);

        tsList.clear();
    }

    /**
     * Returns the next element in the input file. If none is available, returns null
     * @return  Next element in input file
     */
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
