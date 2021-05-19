package HandlerTest;

import DSDLVO.Classes.Handlers.ShipHandler;
import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Classes.Ship;
import DSDLVO.Classes.Geometry.HPoint;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AISToShipHandlerTest {
    @Nested
    class MovePositionToCenterToCenter {

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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftPort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.AftStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForePort(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,0);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(0,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,-1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(-1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
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
                ship.position = new HPoint(0,0);

                HPoint expected = new HPoint(1,1);

                HPoint actual = ShipHandler.MovePositionToCenter.ForeStarboard(ship.position,
                        data.distanceFore,
                        data.distanceStarboard,
                        data.length, data.width);

                assertEquals(expected, actual);
            }
        }
    }


}
