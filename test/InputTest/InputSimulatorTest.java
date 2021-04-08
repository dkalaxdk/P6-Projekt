package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InputSimulatorTest {

    private String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private String InputWithWrongOrder = "test/TestFiles/TestInputTwoElementsWrongOrder.csv";
    private String InputThreeElementsSameTime = "test/TestFiles/TestInputThreeElements.csv";
    private String InputOSDataNotAtStart = "test/TestFiles/TestInputOSDataNotAtStart.csv";


    private InputSimulator createInputSimulator(int osMMSI, BlockingQueue<AISData> osBuffer, BlockingQueue<AISData> tsBuffer, String inputFile) {
        InputSimulator simulator = null;
        try {
            simulator = new InputSimulator(osMMSI, osBuffer, tsBuffer, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return simulator;
    }

    @Nested
    @DisplayName("InputSimulator.run")
    class run{

        @Test
        public void run_StopsAtCorrectTime(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOneElement);

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

            simulator.run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(null, simulator.nextInput);
        }
    }

    @Nested
    @DisplayName("InputSimulator.RunSetUp")
    class RunSetUp{
        @Test
        public void RunSetUp_AddsOneOSDataPointToBuffer(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.RunSetUp();

            assertEquals(1, osBuffer.size());
        }

        @Test
        public void RunSetUp_AddsDataToTSList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.RunSetUp();

            assertEquals(10, tsBuffer.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddDataBeforeOSToTSList")
    class AddDataBeforeOSToTSList{
        @Test
        public void AddDataBeforeOSToTSList_AddsDataToList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.nextInput = simulator.GetNextInput();

            simulator.AddDataBeforeOSToTSList();

            assertEquals(8, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddDataAfterOSToTSList")
    class AddDataAfterOSToTSList{
        @Test
        public void AddDataAfterOSToTSList_AddsDataToList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddDataBeforeOSToTSList();
            simulator.nextInput = simulator.GetNextInput();

            simulator.AddDataAfterOSToTSList();

            assertEquals(10, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddListToTSBuffer")
    class AddListToTSBuffer{
        @Test
        public void AddListToTSBuffer_AddsListToBuffer(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.tsList = new ArrayList<>();
            simulator.tsList.add(new AISData());
            simulator.tsList.add(new AISData());

            simulator.AddTSListToTSBuffer();

            assertEquals(2, tsBuffer.size());
        }

        @Test
        public void AddListToTSBuffer_ResetsList(){
            int osMMSI = 219007034;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOSDataNotAtStart);

            simulator.tsList = new ArrayList<>();
            simulator.tsList.add(new AISData());
            simulator.tsList.add(new AISData());

            simulator.AddTSListToTSBuffer();

            assertEquals(0, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddListToBuffer")
    class AddListToBuffer{

        @Test
        public void AddListToBuffer_AddsListOfOneToBuffer(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputOneElement);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(1, tsBuffer.size());
        }

        @Test
        public void AddListToBuffer_AddsListOfThreeOfSameTimeToBuffer(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputThreeElementsSameTime);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(3, tsBuffer.size());
        }

        @Test
        public void AddListToBuffer_AddsOnlyRelevantItemsFromList(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputWithWrongOrder);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(1, tsBuffer.size());
        }

        @Test
        public void AddListToBuffer_ReturnsNullWhenListIsNotEmpty(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputWithWrongOrder);

            simulator.nextInput = simulator.GetNextInput();

            assert(simulator.AddListToBuffer());
        }

        @Test
        public void AddListToBuffer_ReturnsNullAtEndOfEmpty(){
            int osMMSI = 1;
            BlockingQueue<AISData> osBuffer = new LinkedBlockingQueue<>();
            BlockingQueue<AISData> tsBuffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(osMMSI, osBuffer, tsBuffer, InputThreeElementsSameTime);

            simulator.nextInput = simulator.GetNextInput();

            assert(!simulator.AddListToBuffer());
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
        public void GetNextInput_ReturnsCorrectAISData() {
            InputSimulator simulator = createInputSimulator(1, new LinkedBlockingQueue<>(),
                                                            new LinkedBlockingQueue<>(),
                                                            InputWithWrongOrder);

            assertEquals(265781000, simulator.GetNextInput().mmsi);
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
