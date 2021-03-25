import Dibbidut.Classes.Display;
import Dibbidut.Classes.Ship;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayTest {


    @Test
    public void internalListShouldBeSetToGivenList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Ship(new Point(0,0)));

        Ship ownShip = new Ship(0,0);

        // Act
        Display display = new Display(ownShip, ships);

        // Assert
        assertEquals(ships, display.getShips());
    }

    @Test
    public void internalOwnShipShouldBeSetToGivenOwnShip() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Ship(0,0));

        Ship ownShip = new Ship(0,0);

        // Act
        Display display = new Display(ownShip, ships);

        // Assert
        assertEquals(ownShip, display.getOwnShip());
    }

    @Test
    public void addToExternalShipList_internalShipListShouldUpdateWithExternalShipList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Ship(new Point(0,0)));

        Display display = new Display(new Ship(0,0), ships);

        Ship otherShip = new Ship(0,0);

        // Act
        ships.add(otherShip);

        // Assert
        assertEquals(otherShip, display.getShips().get(1));
    }

    @Test
    public void changeShipInExternalShipList_internalShipListShouldUpdateWithExternalShipList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Ship(new Point(0,0)));

        Display display = new Display(new Ship(0,0), ships);

        // Act
        ships.get(0).length = 3;

        // Assert
        assertEquals(3, display.getShips().get(0).length);
    }
}
