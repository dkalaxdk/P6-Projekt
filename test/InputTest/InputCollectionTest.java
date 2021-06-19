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
            // Also check that it works for the required values
            current.length = 2;
            current.width = 2;
            current.longitude = 2;
            current.latitude = 2;

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
            expected.length = 2;
            expected.width = 2;
            expected.longitude = 2;
            expected.latitude = 2;

            collection.insert(previous);
            collection.insert(current);

            AISData actual = collection.get(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("InputCollection.getOwnShip")
    class getOS {
        @Test
        public void getOwnShip_returns_AISData_of_defined_OwnShip() {
            InputCollection collection = new InputCollection();
            AISData osData = createAISData(248);
            collection.insert(osData);
            collection.insert(createAISData(1));
            collection.insert(createAISData(7892));

            collection.setOwnShipMMSI(248);

            assertEquals(osData, collection.getOwnShip());
        }

        @Test
        public void getOwnShip_returns_null_if_OS_not_set() {
            // If setOwnShipMMSI is not called it defaults to 0.
            // It should simply not attempt to find any data, but return null
            InputCollection collection = new InputCollection();
            collection.insert(createAISData(0));
            collection.insert(createAISData(2));
            collection.insert(createAISData(3));

            assertNull(collection.getOwnShip());
        }
    }

    @Nested
    @DisplayName("InputCollection.getTargetShips")
    class getTargetShips {
        @Test
        public void getTargetShips_returns_all_items_except_os() {
            InputCollection collection = new InputCollection();
            List<AISData> targetShips = Arrays.asList(
                    createAISData(1),
                    createAISData(2),
                    createAISData(3)
            );
            AISData os = createAISData(4);
            collection.insertList(targetShips);
            collection.insert(os);
            collection.setOwnShipMMSI(4);

            assertArrayEquals(targetShips.toArray(), collection.getTargetShips().toArray());
        }
    }
}
