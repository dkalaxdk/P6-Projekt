package SystemTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.CASystem;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class UpdateShipListTest {

    @Test
    @Disabled
    public void emptyListNonEmptyBuffer_shouldAddElementToList() {

        System system = new System();
        AISBuffer buffer = new AISBuffer();
        AISData data = new AISData(0);
        buffer.Push(data);

        system.UpdateShipList();

        assertEquals(system.shipsInRange.get(0).mmsi, data.mmsi);
    }
}
