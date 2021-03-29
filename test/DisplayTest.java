import Dibbidut.Classes.Display;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayTest {

    private Ship createStandardShip() {
        return new Ship(new Vector2D(0,0), 100, 50, 0);
    }


    @Test
    public void internalListShouldBeSetToGivenList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Ship ownShip = createStandardShip();

        // Act
        Display display = new Display(ownShip, ships);

        // Assert
        assertEquals(ships, display.getShips());
    }

    @Test
    public void internalOwnShipShouldBeSetToGivenOwnShip() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Ship ownShip = createStandardShip();

        // Act
        Display display = new Display(ownShip, ships);

        // Assert
        assertEquals(ownShip, display.getOwnShip());
    }

    @Test
    public void addToExternalShipList_internalShipListShouldUpdateWithExternalShipList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Display display = new Display(createStandardShip(), ships);

        Ship otherShip = createStandardShip();

        // Act
        ships.add(otherShip);

        // Assert
        assertEquals(otherShip, display.getShips().get(1));
    }

    @Test
    public void changeShipInExternalShipList_internalShipListShouldUpdateWithExternalShipList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Display display = new Display(createStandardShip(), ships);

        // Act
        ships.get(0).length = 3;

        // Assert
        assertEquals(3, display.getShips().get(0).length);
    }

    @Test
    public void getCoordinatesToDrawShipFrom_shipPointingNorth() {
        // Arrange
        Display display = new Display(createStandardShip(), new ArrayList<>());

        Vector2D shipPosition = new Vector2D(100, 100);
        int shipLength = 10;
        int shipWidth = 3;
        int shipHeading = 0;

        Ship ship = new Ship(shipPosition, 100, 50, 0);
        ship.length = shipLength;
        ship.width = shipWidth;
        ship.heading = shipHeading;

        double expectedX = shipPosition.x() - (((double) shipWidth) / 2);
        double expectedY = shipPosition.y() - (((double) shipLength) / 2);

        // Act
        Vector2D expected = new Vector2D(expectedX, expectedY);

        Vector2D actual = display.getCoordinatesToDrawShipFrom(ship);

        // Assert
        assertEquals(expected, actual);

    }

    @Test
    public void getCoordinatesToDrawShipFrom_shipPointingNorthEast() {
        // Arrange
        Display display = new Display(createStandardShip(), new ArrayList<>());

        Vector2D shipPosition = new Vector2D(100, 100);
        int shipLength = 10;
        int shipWidth = 3;
        int shipHeading = 45;

        Ship ship = new Ship(shipPosition, 100, 50, 0);
        ship.length = shipLength;
        ship.width = shipWidth;
        ship.heading = shipHeading;

        double expectedX = shipPosition.x() - (((double) shipWidth) / 2);
        double expectedY = shipPosition.y() - (((double) shipLength) / 2);

        // Act
        Vector2D expected = new Vector2D(expectedX, expectedY);

        Vector2D actual = display.getCoordinatesToDrawShipFrom(ship);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void drawHeading_shipPointingNorth() {
        // Arrange
        Display display = new Display(createStandardShip(), new ArrayList<>());



        // Act

        // Assert
    }
}
