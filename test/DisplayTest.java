import Dibbidut.Classes.Display;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayTest {

    private Ship createStandardShip() {
        Ship ship = new Ship(new Vector2D(0,0), 100, 50, 0);
        ship.position = new Vector2D(0,0);
        return ship;
    }


    @Test
    public void internalListShouldBeSetToGivenList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Ship ownShip = createStandardShip();

        // Act
        Display display = new Display(ownShip, ships, new Area());

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
        Display display = new Display(ownShip, ships, new Area());

        // Assert
        assertEquals(ownShip, display.getOwnShip());
    }

    @Test
    public void addToExternalShipList_internalShipListShouldUpdateWithExternalShipList() {
        // Arrange
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(createStandardShip());

        Display display = new Display(createStandardShip(), ships, new Area());

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

        Display display = new Display(createStandardShip(), ships, new Area());

        // Act
        ships.get(0).length = 3;

        // Assert
        assertEquals(3, display.getShips().get(0).length);
    }

    @Test
    public void getCoordinatesToDrawShipFrom_shipPointingNorth() {
        // Arrange
        Display display = new Display(createStandardShip(), new ArrayList<>(), new Area());

        Vector2D shipPosition = new Vector2D(100, 100);
        int shipLength = 10;
        int shipWidth = 3;
        int shipHeading = 0;

        Ship ship = new Ship(shipPosition, shipLength, shipWidth, shipHeading);
        ship.position = shipPosition;

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
        Display display = new Display(createStandardShip(), new ArrayList<>(), new Area());

        Vector2D shipPosition = new Vector2D(100, 100);
        int shipLength = 10;
        int shipWidth = 3;
        int shipHeading = 45;

        Ship ship = new Ship(shipPosition, shipLength, shipWidth, shipHeading);
        ship.position = shipPosition;

        double expectedX = shipPosition.x() - (((double) shipWidth) / 2);
        double expectedY = shipPosition.y() - (((double) shipLength) / 2);

        // Act
        Vector2D expected = new Vector2D(expectedX, expectedY);

        Vector2D actual = display.getCoordinatesToDrawShipFrom(ship);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getZoomedPosition_bothShipsOrigo_zoom1_noChange() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        double zoom = 1;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(0,0);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_bothShipsOrigo_zoom2_noChange() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(0,0);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetPosPos_zoom1_noChange() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        double zoom = 1;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(1,1);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetPosPos_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(2,2);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetPosPos_zoom1Point5_Change() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        double zoom = 1.5;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(1.5,1.5);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetPosPos_zoom1_noChange() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(2,2), 10, 10, 0);
        double zoom = 1;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(2,2);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetPosPos_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(2,2), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(3,3);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetPosPos_zoom1Point5_Change() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(2,2), 10, 10, 0);
        double zoom = 1.5;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(2.5,2.5);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetPosNeg_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(1,-1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(2,-2);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetNegPos_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(-1,1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(-2,2);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownShipOrigo_targetNegNeg_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(-1,-1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(-2,-2);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetPosNeg_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(1,-1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(1,-3);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetNegNeg_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(-1,-1), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(-3,-3);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }

    @Test
    public void getZoomedPosition_ownPosPos_targetOrigo_zoom2_Change() {
        Ship ownShip = new Ship(new Vector2D(1,1), 10, 10, 0);
        Ship targetShip = new Ship(new Vector2D(0,0), 10, 10, 0);
        double zoom = 2;

        Display display = new Display(ownShip, new ArrayList<>(), new Area());

        Vector2D expectedTarget = new Vector2D(-1,-1);

        Vector2D actualTarget = display.getZoomedPosition(ownShip.position, targetShip.position, zoom);

        assertEquals(expectedTarget, actualTarget);
    }
}
