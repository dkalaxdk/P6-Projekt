package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InputSimulatorTest {

    private String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private String InputWithWrongOrder = "test/TestFiles/TestInputTwoElementsWrongOrder.csv";

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
    @DisplayName("InputSimulator.Run")
    class Run{
        @Test
        public void Run_AddsElementToBuffer(){
            BlockingQueue<AISData> buffer = new LinkedBlockingQueue<>();
            InputSimulator simulator = createInputSimulator(buffer, InputOneElement);

            simulator.run();

            assertEquals(buffer.size(), 1);
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
