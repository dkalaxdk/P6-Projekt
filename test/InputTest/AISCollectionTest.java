package InputTest;

import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Classes.InputSimulation.AISCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AISCollectionTest {

    private AISData createAISData(int mmsi) {
        return new AISData("01/01/2001 11:11:11", mmsi, 1, 1, 1, 1);
    }

    @Nested
    @DisplayName("AISCollection.insert")
    class insertItem {
        @Test
        public void insert_inserts_item() {
            AISCollection collection = new AISCollection();
            AISData data = createAISData(1);

            collection.insert(data);

            assertEquals(data, collection.get(1));
        }
    }

    @Nested
    @DisplayName("AISCollection.insertList")
    class insertList {
        @Test
        public void insertList_inserts_all_items() {
            AISCollection collection= new AISCollection();
            List<AISData> dataList = Arrays.asList(
                    createAISData(1),
                    createAISData(2),
                    createAISData(3)
            );
            collection.insertList(dataList);

            assertArrayEquals(dataList.toArray(), collection.getAll().toArray());
        }
    }

    @Nested
    @DisplayName("AISCollection.get")
    class getItem {
        @Test
        public void get_returns_item() {
            AISCollection collection = new AISCollection();
            AISData data = createAISData(1);
            collection.insert(data);

            assertEquals(data, collection.get(1));
        }
    }

    @Nested
    @DisplayName("AISCollection.getAll")
    class getAllItems {
        @Test
        public void getAll_returns_all_items() {
            AISCollection collection= new AISCollection();
            List<AISData> dataList = Arrays.asList(
                    createAISData(1),
                    createAISData(2),
                    createAISData(3)
            );
            dataList.forEach(collection::insert);

            assertArrayEquals(dataList.toArray(), collection.getAll().toArray());
        }
    }

    @Nested
    @DisplayName("AISCollection.contains")
    class contains {
        @Test
        public void contains_returns_false_if_item_not_in_collection() {
            AISCollection collection = new AISCollection();

            assertFalse(collection.contains(createAISData(1)));
        }

        @Test
        public void contains_returns_true_if_item_in_collection() {
            AISCollection collection = new AISCollection();
            AISData item = createAISData(1);
            collection.insert(item);

            assertTrue(collection.contains(item));
        }
    }
}
