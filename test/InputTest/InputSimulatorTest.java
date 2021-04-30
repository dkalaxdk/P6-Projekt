package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import Dibbidut.Exceptions.OSNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class InputSimulatorTest {

    private final String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private final String InputThreeElementsSameTime = "test/TestFiles/TestInputThreeElements.csv";
    private final String InputOSDataNotAtStart = "test/TestFiles/TestInputOSDataNotAtStart.csv";


    private InputSimulator createInputSimulator(int osMMSI, BlockingQueue<AISData> osBuffer, BlockingQueue<AISData> tsBuffer, String inputFile) {
        Lock bufferLock = new ReentrantLock(true);
        InputSimulator simulator = null;
        try {
            simulator = new InputSimulator(1f, bufferLock, osMMSI, osBuffer, tsBuffer, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return simulator;
    }

    @Disabled
    @Nested
    @DisplayName("InputSimulator.run")
    class run{

        @Test
        public void run_StopsAtCorrectCurrentTime(){
            int osMMSI = 211235220;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOneElement);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            simulator.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(LocalDateTime.of(2017, 6, 23, 12, 34, 56),
                         simulator.currentTime);
        }

        @Test
        public void run_NextInputIsNullAtEnd() {
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOneElement);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException ignored) {
            }

            simulator.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertNull(simulator.nextInput);
        }

        @Test
        public void run_AddsCorrectNumberOfItemsToTsBuffer() {
            int osMMSI = 265785410;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            simulator.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(11, tsBuffer.size());
        }

        @Test
        public void run_AddsCorrectNumberOfItemsToOsBuffer() {
            int osMMSI = 311000223;

            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            simulator.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(2, osBuffer.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.RunSetUp")
    class RunSetUp{
        @Test
        public void RunSetUp_AddsOneOSDataPointToOsBuffer(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            assertEquals(1, osBuffer.size());
        }

        @Test
        public void RunSetUp_AddsDataToTSBuffer(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            assertEquals(10, tsBuffer.size());
        }

        @Test
        public void RunSetUp_ResetsTsList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            assertEquals(0, simulator.tsList.size());
        }

        @Test
        public void RunSetUp_ThrowsExceptionWhenNoOSIsFound(){
            boolean exceptionThrown = false;

            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                exceptionThrown = true;
            }
            assertTrue(exceptionThrown);
        }

    }

    @Nested
    @DisplayName("InputSimulator.AddNextInputToTSList")
    class AddNextInputToTSList{
        @Test
        public void AddNextInputToTSList_AddsCorrectNumberOfItemsToList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.nextInput = simulator.GetNextInput();
            while (simulator.nextInput != null) {
                simulator.AddNextInputToTSList();
                simulator.nextInput = simulator.GetNextInput();
            }

            assertEquals(12, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddDataToBuffers")
    class AddDataToBuffers{
        @Test
        public void AddDataToBuffers_AddsCorrectNumberOfItemsToTSBuffer(){
            int osMMSI = 219002624;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputThreeElementsSameTime);
            simulator.nextInput = simulator.GetNextInput();
            simulator.currentTime = simulator.nextInput.dateTime;

            simulator.AddDataToBuffers();

            assertEquals(2, tsBuffer.size());
        }

        @Test
        public void AddDataToBuffers_AddsCorrectNumberOfItemsToOSBuffer(){
            int osMMSI = 219002624;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputThreeElementsSameTime);
            simulator.nextInput = simulator.GetNextInput();
            simulator.currentTime = simulator.nextInput.dateTime;

            simulator.AddDataToBuffers();

            assertEquals(1, osBuffer.size());
        }

        @Test
        public void AddDataToBuffers_ResetsTSList(){
            int osMMSI = 219002624;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputThreeElementsSameTime);
            simulator.nextInput = simulator.GetNextInput();
            simulator.currentTime = simulator.nextInput.dateTime;

            simulator.AddDataToBuffers();

            assertEquals(0, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.GetNextInput")
    class GetNextInput {
        @Test
        public void GetNextInput_DoesNotReturnNull() {
            // Arrange
            InputSimulator simulator = createInputSimulator(1, new LinkedBlockingQueue<>(),
                                                            new LinkedBlockingQueue<>(),
                                                            InputOneElement);

            // Assert
            assertNotNull(simulator.GetNextInput());
        }

        @Test
        public void GetNextInput_ReturnsNullAtEndOfList() {
            InputSimulator simulator = createInputSimulator(1, new LinkedBlockingQueue<>(),
                                                            new LinkedBlockingQueue<>(),
                                                            InputOneElement);

            simulator.GetNextInput();

            assert(simulator.GetNextInput() == null);
        }
    }


}
