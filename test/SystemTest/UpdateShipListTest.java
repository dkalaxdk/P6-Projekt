package SystemTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.CASystem;
import Dibbidut.Classes.InputManagement.AISData;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class UpdateShipListTest {

    @Test
    public void emptyListEmptyBuffer_noChange() {

        CASystem system = new CASystem();

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.size());
    }

    @Test
    public void emptyListNonEmptyBuffer_newElement_elementAddedToList() {

        CASystem system = new CASystem();

        AISData data = new AISData();
        data.mmsi = 0;
        system.buffer.add(data);

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.get(0).mmsi);
    }

    @Test
    public void emptyListNonEmptyBuffer_twoNewElements_elementsAddedToList() {

        CASystem system = new CASystem();
        AISData data = new AISData();
        data.mmsi = 0;
        AISData data1 = new AISData();
        data1.mmsi = 1;
        system.buffer.add(data);
        system.buffer.add(data1);

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.get(0).mmsi);
        assertEquals(1, system.shipsInRange.get(1).mmsi);
    }

    @Test
    public void nonemptyListNonEmptyBuffer_newElement_elementAddedToList() {

        CASystem system = new CASystem();
        AISData data1 = new AISData();
        data1.mmsi = 1;

        system.shipsInRange.add(new Ship(new Vector2D(0,0), 10, 5, 0));

        system.buffer.add(data1);

        system.UpdateShipList();

        assertEquals(1, system.shipsInRange.get(1).mmsi);
    }

    @Test
    public void nonemptyListNonEmptyBuffer_existingElement_elementUpdated() {

        CASystem system = new CASystem();

        AISData data = new AISData();
        data.mmsi = 0;
        data.length = 10;

        system.buffer.add(data);

        system.UpdateShipList();

        AISData data1 = new AISData();
        data.mmsi = 0;
        data.length = 20;

        system.buffer.add(data1);

        system.UpdateShipList();

        assertEquals(20, system.shipsInRange.get(0).length);
    }
}
