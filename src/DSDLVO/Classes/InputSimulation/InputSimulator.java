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

public class InputSimulator {

    public BlockingQueue<AISData> tsBuffer;
    public BlockingQueue<AISData> osBuffer;
    public ArrayList<AISData> tsList;
    public LocalDateTime currentTime;
    public AISData nextInput;

    private final FileParser fileParser;
    private final int osMMSI;
    private Float timeFactor;

    public InputSimulator(int osMMSI, String inputFile) throws IOException {
        this.timeFactor = 1f;
        this.osMMSI = osMMSI;
        this.osBuffer = new LinkedBlockingQueue<>();
        this.tsBuffer = new LinkedBlockingQueue<>();

        fileParser = new FileParser(inputFile);
        tsList = new ArrayList<>();
    }

    public void run() {
        if(inputIsAvailable() && timeFactor != 0) {
            currentTime = currentTime.plusSeconds(1);
            AddDataToBuffers();
        }
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
     * This method adds data to buffers
     */
    private void addElementsToBuffers(AISData os, List<AISData> tsList) {
        if (os != null)
            osBuffer.add(os);
        tsBuffer.addAll(tsList);

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
