import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Mercator;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {
    @Test
    public void MovePositionToCenter_PositionAlreadyCenter_DoNotMove() {
        AISData data = new AISData();
        data.distanceFore = 5;
        data.distanceAft = 5;
        data.distancePort = 5;
        data.distanceStarboard = 5;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(0,0);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToStarboard_MoveLeft() {
        AISData data = new AISData();
        data.distanceFore = 5;
        data.distanceAft = 5;
        data.distancePort = 6;
        data.distanceStarboard = 4;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(-1,0);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToPort_MoveRight() {
        AISData data = new AISData();
        data.distanceFore = 5;
        data.distanceAft = 5;
        data.distancePort = 4;
        data.distanceStarboard = 6;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(1,0);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToFore_MoveBack() {
        AISData data = new AISData();
        data.distanceFore = 4;
        data.distanceAft = 6;
        data.distancePort = 5;
        data.distanceStarboard = 5;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(0,-1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToBack_MoveForward() {
        AISData data = new AISData();
        data.distanceFore = 6;
        data.distanceAft = 4;
        data.distancePort = 5;
        data.distanceStarboard = 5;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(0,1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToForeAndStarboard_MoveBackAndLeft() {
        AISData data = new AISData();
        data.distanceFore = 4;
        data.distanceAft = 6;
        data.distancePort = 6;
        data.distanceStarboard = 4;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(-1,-1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToForeAndPort_MoveBackAndRight() {
        AISData data = new AISData();
        data.distanceFore = 4;
        data.distanceAft = 6;
        data.distancePort = 4;
        data.distanceStarboard = 6;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(1,-1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToAftAndStarboard_MoveForwardAndLeft() {
        AISData data = new AISData();
        data.distanceFore = 6;
        data.distanceAft = 4;
        data.distancePort = 6;
        data.distanceStarboard = 4;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(-1,1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }

    @Test
    public void MovePositionToCenter_OffByOneToAftAndPort_MoveForwardAndRight() {
        AISData data = new AISData();
        data.distanceFore = 6;
        data.distanceAft = 4;
        data.distancePort = 4;
        data.distanceStarboard = 6;

        data.length = 10;
        data.width = 10;

        Ship ship = new Ship(data, 0);
        ship.position = new Vector2D(0,0);

        Vector2D expected = new Vector2D(1,1);

        Vector2D actual = ship.MovePositionToCenterAftPort(ship.position,
                data.distanceAft,
                data.distancePort,
                data.length, data.width);

        assertEquals(expected, actual);
    }
}
