import Dibbidut.Classes.Display;
import Dibbidut.Classes.Ship;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayTest {


    @Test
    public void internalListShouldBeSetToGivenList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Ship(new Point2D.Double(0,0)));

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
        ships.add(new Ship(new Point2D.Double(0,0)));

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
        ships.add(new Ship(new Point2D.Double(0,0)));

        Display display = new Display(new Ship(0,0), ships);

        // Act
        ships.get(0).length = 3;

        // Assert
        assertEquals(3, display.getShips().get(0).length);
    }

    @Test
    public void getCoordinatesToDrawShipFrom_shipPointingNorth() {
        // Arrange
        Display display = new Display(new Ship(0,0), new ArrayList<>());

        Point2D.Double shipPosition = new Point2D.Double(100, 100);
        int shipLength = 10;
        int shipWidth = 3;
        int shipHeading = 0;

        Ship ship = new Ship(shipPosition);
        ship.length = shipLength;
        ship.width = shipWidth;
        ship.heading = shipHeading;

        double expectedX = shipPosition.getX() - (((double) shipWidth) / 2);
        double expectedY = shipPosition.getY() - (((double) shipLength) / 2);

        // Act
        Point2D.Double expected = new Point2D.Double(expectedX, expectedY);

        Point2D.Double actual = display.getCoordinatesToDrawShipFrom(ship);

        // Assert
        assertEquals(expected, actual);

    }
}
