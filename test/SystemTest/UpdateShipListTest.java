package SystemTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.AISData;
import Dibbidut.Classes.AISSource;
import Dibbidut.Classes.AISStream;
import Dibbidut.Classes.System;
import Dibbidut.Interfaces.IDataInput;
import org.junit.jupiter.api.Disabled;
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
