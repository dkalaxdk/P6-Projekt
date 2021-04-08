package SystemTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.CASystem;
import Dibbidut.Classes.InputManagement.AISData;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Nested;
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

        AISData data1 = new AISData();
        data1.mmsi = 10;
        data1.length = 10;
        data1.lengthIsSet = true;

        system.buffer.add(data1);

        system.UpdateShipList();

        AISData data2 = new AISData();
        data2.mmsi = 10;
        data2.length = 20;
        data2.lengthIsSet = true;

        system.buffer.add(data2);

        system.UpdateShipList();

        assertEquals(20, system.shipsInRange.get(0).length);
    }

    @Test
    public void emptyListNonEmptyBuffer_elementOutOfRange_listRemainsEmpty() {
        CASystem system = new CASystem();

        system.ownShip = new Ship(new Vector2D(0,0), 50, 10, 0);
        system.ownShip.longitude = 0;
        system.range = 1;

        AISData data = new AISData();
        data.mmsi = 1;
        data.longitude = 10;
        data.latitude = 10;

        system.buffer.add(data);

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.size());
    }

    @Test
    public void nonEmptyListNonEmptyBuffer_elementOutOfRange_removeElementFromList() {
        CASystem system = new CASystem();

        system.ownShip = new Ship(new Vector2D(0,0), 50, 10, 0);
        system.ownShip.longitude = 0;
        system.range = 1;

        AISData data1 = new AISData();
        data1.mmsi = 1;
        data1.longitude = 0;
        data1.latitude = 0;

        system.buffer.add(data1);

        AISData data2 = new AISData();
        data2.mmsi = 1;
        data2.longitude = 10;
        data2.latitude = 10;

        system.buffer.add(data2);

        system.UpdateShipList();

        assertEquals(0, system.shipsInRange.size());
    }

    @Nested
    class RemoveShipsOutOfRange {
        @Test
        public void ShipsAreWithinRange_NoChange() {
            CASystem system = new CASystem();

            Ship target1 = new Ship(new Vector2D(1,0), 10, 10, 0);
            Ship target2 = new Ship(new Vector2D(2,0), 10, 10, 0);

            target1.mmsi = 0;
            target2.mmsi = 1;

            system.shipsInRange.add(target1);
            system.shipsInRange.add(target2);

            system.RemoveShipsOutOfRange(new Vector2D(0,0), system.shipsInRange, 10);

            assertEquals(system.shipsInRange.size(), 2);
        }

        @Test
        public void OneShipOutOfRange_RemoveOneShip() {
            CASystem system = new CASystem();

            Ship target1 = new Ship(new Vector2D(11,0), 10, 10, 0);
            Ship target2 = new Ship(new Vector2D(1,0), 10, 10, 0);

            target1.mmsi = 0;
            target2.mmsi = 1;

            system.shipsInRange.add(target1);
            system.shipsInRange.add(target2);

            system.RemoveShipsOutOfRange(new Vector2D(0,0), system.shipsInRange, 10);

            assertEquals(system.shipsInRange.get(0).position, target2.position);
        }

        @Test
        public void MultipleShipsOutOfRange_RemoveShips() {
            CASystem system = new CASystem();

            Ship target1 = new Ship(new Vector2D(11,0), 10, 10, 0);
            Ship target2 = new Ship(new Vector2D(12,0), 10, 10, 0);
            Ship target3 = new Ship(new Vector2D(1,0), 10, 10, 0);

            target1.mmsi = 0;
            target2.mmsi = 1;
            target3.mmsi = 2;

            system.shipsInRange.add(target1);
            system.shipsInRange.add(target2);
            system.shipsInRange.add(target3);

            system.RemoveShipsOutOfRange(new Vector2D(0,0), system.shipsInRange, 10);

            assertEquals(system.shipsInRange.get(0).position, target3.position);
        }

        @Test
        public void AllShipOutOfRange_EmptyList() {
            CASystem system = new CASystem();

            Ship target1 = new Ship(new Vector2D(11,0), 10, 10, 0);
            Ship target2 = new Ship(new Vector2D(12,0), 10, 10, 0);
            Ship target3 = new Ship(new Vector2D(13,0), 10, 10, 0);

            target1.mmsi = 0;
            target2.mmsi = 1;
            target3.mmsi = 2;

            system.shipsInRange.add(target1);
            system.shipsInRange.add(target2);
            system.shipsInRange.add(target3);

            system.RemoveShipsOutOfRange(new Vector2D(0,0), system.shipsInRange, 10);

            assertEquals(system.shipsInRange.size(), 0);
        }
    }
}
