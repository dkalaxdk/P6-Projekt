package InputTest;

import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Classes.InputSimulation.AISCollection;
import DSDLVO.Classes.InputSimulation.InputCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InputCollectionTest {
    private AISData createAISData(int mmsi) {
        AISData data = new AISData("01/01/2001 11:11:11", mmsi, 1, 1, 1, 1);
        return data;
    }

    @Nested
    @DisplayName("InputCollection.insert")
    class insert {
        @Test
        public void insert_inserts_item() {
            InputCollection collection = new InputCollection();
            AISData item = createAISData(1);
            collection.insert(item);

            assertEquals(item, collection.get(1));
        }

        @Test
        public void insert_missing_data_is_filled_by_previous_entry() {
            InputCollection collection = new InputCollection();
            AISData current = createAISData(1);
            current.timestampString = "01/01/2001 22:11:11";
            current.ROT = 5;
            current.distanceAft = 7;
            AISData previous = createAISData(1);
            previous.SOG = 1;
            previous.COG = 2;
            previous.heading = 3;
            previous.distanceFore = 4;
            previous.distancePort = 6;
            previous.distanceStarboard = 8;

            AISData expected = createAISData(1);
            expected.timestampString = "01/01/2001 22:11:11";
            expected.AddDateTime();
            expected.ROT = 5;
            expected.distanceAft = 7;
            expected.SOG = 1;
            expected.COG = 2;
            expected.heading = 3;
            expected.distanceFore = 4;
            expected.distancePort = 6;
            expected.distanceStarboard = 8;

            collection.insert(previous);
            collection.insert(current);

            AISData actual = collection.get(1);
            assertEquals(expected, actual);
        }
    }
}
