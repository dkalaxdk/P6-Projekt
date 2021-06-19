package InputTest;

import DSDLVO.Classes.InputSimulation.InputSimulator;
import DSDLVO.Exceptions.OSNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class InputSimulatorTest {

    private final String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private final String InputThreeElementsSameTime = "test/TestFiles/TestInputThreeElements.csv";
    private final String InputOSDataNotAtStart = "test/TestFiles/TestInputOSDataNotAtStart.csv";


    private InputSimulator createInputSimulator(int osMMSI, String inputFile) {
        InputSimulator simulator = null;
        try {
            simulator = new InputSimulator(osMMSI, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return simulator;
    }
    
    @Nested
    @DisplayName("InputSimulator.run")
    class run{

        @Test
        public void run_StopsAtCorrectCurrentTime(){
            int osMMSI = 211235220;
            InputSimulator simulator = createInputSimulator(osMMSI, InputOneElement);

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
            InputSimulator simulator = createInputSimulator(osMMSI, InputOneElement);

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
    }

    @Nested
    @DisplayName("InputSimulator.RunSetUp")
    class RunSetUp{
        @Test
        public void RunSetUp_AddsDataToInputCollection(){
            int osMMSI = 219007034;
            InputSimulator simulator = createInputSimulator(osMMSI, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            assertEquals(11, simulator.inputCollection.getAll().size());
        }

        @Test
        public void RunSetUp_ResetsTsList(){
            int osMMSI = 219007034;
            InputSimulator simulator = createInputSimulator(osMMSI, InputOSDataNotAtStart);

            try {
                simulator.RunSetUp();
            } catch (OSNotFoundException e) {
                e.printStackTrace();
            }

            assertEquals(0, simulator.tsList.size());
        }

        @Test
        public void RunSetUp_ThrowsExceptionWhenNoOSIsFound(){
            int osMMSI = 1;
            InputSimulator simulator = createInputSimulator(osMMSI, InputOSDataNotAtStart);

            assertThrows(OSNotFoundException.class, simulator::RunSetUp);
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddNextInputToTSList")
    class AddNextInputToTSList{
        @Test
        public void AddNextInputToTSList_AddsCorrectNumberOfItemsToList(){
            int osMMSI = 219007034;
            InputSimulator simulator = createInputSimulator(osMMSI, InputOSDataNotAtStart);

            simulator.nextInput = simulator.GetNextInput();
            while (simulator.nextInput != null) {
                simulator.AddNextInputToTSList();
                simulator.nextInput = simulator.GetNextInput();
            }

            assertEquals(12, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddDataToInputCollection")
    class AddDataToBuffers{
        @Test
        public void AddDataToInputCollection_AddsCorrectNumberOfItemsToInputCollection(){
            int osMMSI = 219002624;
            InputSimulator simulator = createInputSimulator(osMMSI, InputThreeElementsSameTime);
            simulator.nextInput = simulator.GetNextInput();
            simulator.currentTime = simulator.nextInput.dateTime;

            simulator.AddDataToInputCollection();

            assertEquals(3, simulator.inputCollection.getAll().size());
        }

        @Test
        public void AddDataToInputCollection_ResetsTSList(){
            int osMMSI = 219002624;
            InputSimulator simulator = createInputSimulator(osMMSI, InputThreeElementsSameTime);
            simulator.nextInput = simulator.GetNextInput();
            simulator.currentTime = simulator.nextInput.dateTime;

            simulator.AddDataToInputCollection();

            assertEquals(0, simulator.tsList.size());
        }
    }

    @Nested
    @DisplayName("InputSimulator.GetNextInput")
    class GetNextInput {
        @Test
        public void GetNextInput_DoesNotReturnNull() {
            // Arrange
            InputSimulator simulator = createInputSimulator(1, InputOneElement);

            // Assert
            assertNotNull(simulator.GetNextInput());
        }

        @Test
        public void GetNextInput_ReturnsNullAtEndOfList() {
            InputSimulator simulator = createInputSimulator(1, InputOneElement);

            simulator.GetNextInput();

            assert(simulator.GetNextInput() == null);
        }
    }
}
