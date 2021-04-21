package HandlerTest;

import Dibbidut.Classes.Handlers.AISToShipHandler;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.Ship;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

public class AISToShipHandlerTest {
    @Nested
    class MovePositionToCenter {

        @Nested
        class AftPort {
            @Test
            public void PositionAlreadyCenter_DoNotMove() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,0);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToStarboard_MoveLeft() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,0);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToPort_MoveRight() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,0);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToFore_MoveBack() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,-1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToBack_MoveForward() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndStarboard_MoveBackAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,-1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndPort_MoveBackAndRight() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,-1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndStarboard_MoveForwardAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndPort_MoveForwardAndRight() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,1);

                Vector2D actual = handler.MovePositionToCenterAftPort(ship.position,
                        data.distanceAft,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }
        }

        @Nested
        class AftStarboard {
            @Test
            public void PositionAlreadyCenter_DoNotMove() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,0);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToStarboard_MoveLeft() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,0);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToPort_MoveRight() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,0);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToFore_MoveBack() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,-1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToBack_MoveForward() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndStarboard_MoveBackAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,-1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndPort_MoveBackAndRight() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,-1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndStarboard_MoveForwardAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndPort_MoveForwardAndRight() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,1);

                Vector2D actual = handler.MovePositionToCenterAftStarboard(ship.position,
                        data.distanceAft,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }
        }

        @Nested
        class ForePort {
            @Test
            public void PositionAlreadyCenter_DoNotMove() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,0);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToStarboard_MoveLeft() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,0);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToPort_MoveRight() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,0);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToFore_MoveBack() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,-1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToBack_MoveForward() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndStarboard_MoveBackAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,-1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndPort_MoveBackAndRight() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,-1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndStarboard_MoveForwardAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndPort_MoveForwardAndRight() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,1);

                Vector2D actual = handler.MovePositionToCenterForePort(ship.position,
                        data.distanceFore,
                        data.distancePort,
                        data.length, data.width);

                assertEquals(expected, actual);
            }
        }

        @Nested
        class ForeStarboard {
            @Test
            public void PositionAlreadyCenter_DoNotMove() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,0);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToStarboard_MoveLeft() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,0);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToPort_MoveRight() {
                AISData data = new AISData();
                data.distanceFore = 5;
                data.distanceAft = 5;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,0);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToFore_MoveBack() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,-1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToBack_MoveForward() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 5;
                data.distanceStarboard = 5;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(0,1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndStarboard_MoveBackAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,-1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToForeAndPort_MoveBackAndRight() {
                AISData data = new AISData();
                data.distanceFore = 4;
                data.distanceAft = 6;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,-1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndStarboard_MoveForwardAndLeft() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 6;
                data.distanceStarboard = 4;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(-1,1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }

            @Test
            public void OffByOneToAftAndPort_MoveForwardAndRight() {
                AISData data = new AISData();
                data.distanceFore = 6;
                data.distanceAft = 4;
                data.distancePort = 4;
                data.distanceStarboard = 6;

                data.length = 10;
                data.width = 10;

                Ship ship = new Ship(data);
                ship.position = new Vector2D(0,0);

                AISToShipHandler handler = new AISToShipHandler(ship, data, new Hashtable<>());

                Vector2D expected = new Vector2D(1,1);

                Vector2D actual = handler.MovePositionToCenterForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }
        }
    }


}
