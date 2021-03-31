package SystemTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.CASystem;
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
        system.buffer.add(new AISData(0));

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.get(0).mmsi);
    }

    @Test
    public void emptyListNonEmptyBuffer_twoNewElements_elementsAddedToList() {

        CASystem system = new CASystem();
        system.buffer.add(new AISData(0));
        system.buffer.add(new AISData(1));

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.get(0).mmsi);
        assertEquals(1, system.shipsInRange.get(1).mmsi);
    }

    @Test
    public void nonemptyListNonEmptyBuffer_newElement_elementAddedToList() {

        CASystem system = new CASystem();
        system.buffer.add(new AISData(0));

        system.UpdateShipList();

        system.buffer.add(new AISData(1));

        system.UpdateShipList();

        assertEquals(1, system.shipsInRange.get(1).mmsi);
    }

    @Test
    public void nonemptyListNonEmptyBuffer_existingElement_elementUpdated() {

        CASystem system = new CASystem();
        system.buffer.add(new AISData(0));

        system.UpdateShipList();

        system.buffer.add(new AISData(0));

        system.UpdateShipList();

        assertEquals(1, system.shipsInRange.get(1).mmsi);
    }
}
