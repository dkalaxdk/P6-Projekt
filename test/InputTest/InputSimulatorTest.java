package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InputSimulatorTest {

    private String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private String InputWithWrongOrder = "test/TestFiles/TestInputTwoElementsWrongOrder.csv";
    private String InputThreeElementsSameTime = "test/TestFiles/TestInputThreeElements.csv";

    private InputSimulator createInputSimulator(BlockingQueue<AISData> buffer, String inputFile) {
        InputSimulator simulator = null;
        try {
            simulator = new InputSimulator(buffer, inputFile);
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
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputOneElement);

            simulator.run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(simulator.currentTime,
                         LocalDateTime.of(2017, 6, 23, 12, 34, 56));
        }

        @Test
        public void run_NextInputIsNullAtEnd() {
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputOneElement);

            simulator.run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertEquals(simulator.nextInput,null);
        }
    }

    @Nested
    @DisplayName("InputSimulator.AddListToBuffer")
    class AddListToBuffer{

        @Test
        public void AddListToBuffer_AddsListOfOneToBuffer(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputOneElement);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(buffer.size(), 1);
        }

        @Test
        public void AddListToBuffer_AddsListOfThreeOfSameTimeToBuffer(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputThreeElementsSameTime);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(buffer.size(), 3);
        }

        @Test
        public void AddListToBuffer_AddsOnlyRelevantItemsFromList(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputWithWrongOrder);

            simulator.nextInput = simulator.GetNextInput();
            simulator.AddListToBuffer();

            assertEquals(buffer.size(), 1);
        }

        @Test
        public void AddListToBuffer_ReturnsNullWhenListIsNotEmpty(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputWithWrongOrder);

            simulator.nextInput = simulator.GetNextInput();

            assert(simulator.AddListToBuffer());
        }

        @Test
        public void AddListToBuffer_ReturnsNullAtEndOfEmpty(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputThreeElementsSameTime);

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
            InputSimulator simulator = createInputSimulator(new LinkedBlockingQueue<>(), InputOneElement);

            // Assert
            assertNotNull(simulator.GetNextInput());
        }

        @Test
        public void GetNextInput_ReturnsCorrectAISData() {
            InputSimulator simulator = createInputSimulator(new LinkedBlockingQueue<>(), InputWithWrongOrder);

            assertEquals(simulator.GetNextInput().mmsi, 265781000);
        }

        @Test
        public void GetNextInput_ReturnsNullAtEndOfList() {
            InputSimulator simulator = createInputSimulator(new LinkedBlockingQueue<>(), InputOneElement);

            simulator.GetNextInput();

            assert(simulator.GetNextInput() == null);
        }
    }


}
