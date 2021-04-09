package HandlerTest;

import Dibbidut.Classes.*;
import Dibbidut.Classes.Handlers.ShipHandler;
import Dibbidut.Classes.Handlers.UpdateShipHandler;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateShipHandlerTest {

    @Nested
    class HandleHeading {
        @Test
        public void NewSet_OldSet_ReturnNew() {

            AISData startData = new AISData();
            startData.headingIsSet = true;
            startData.heading = 0;

            AISData newData = new AISData();
            newData.headingIsSet = true;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleHeading();

            assertEquals(50, actual);
        }

        @Test
        public void NewSet_OldNotSet_ReturnNew() {

            AISData startData = new AISData();
            startData.headingIsSet = false;
            startData.heading = 0;

            AISData newData = new AISData();
            newData.headingIsSet = true;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleHeading();

            assertEquals(50, actual);
        }

        @Test
        public void NewNotSet_OldSet_ReturnOld() {

            AISData startData = new AISData();
            startData.headingIsSet = true;
            startData.heading = 0;

            AISData newData = new AISData();
            newData.headingIsSet = false;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleHeading();

            assertEquals(0, actual);
        }

        @Test
        public void NewNotSet_OldSet_OldValueSavedInNewData() {

            AISData startData = new AISData();
            startData.headingIsSet = true;
            startData.heading = 0;

            AISData newData = new AISData();
            newData.headingIsSet = false;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            handler.HandleHeading();

            assertEquals(0, newData.heading);
        }

        @Test
        public void NewNotSet_OldSet_NewBecomesSet() {

            AISData startData = new AISData();
            startData.headingIsSet = true;
            startData.heading = 0;

            AISData newData = new AISData();
            newData.headingIsSet = false;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            handler.HandleHeading();

            assertTrue(newData.headingIsSet);
        }

        @Test
        public void NewNotSet_OldNotSet_ReturnZero() {

            AISData startData = new AISData();
            startData.headingIsSet = false;
            startData.heading = 40;

            AISData newData = new AISData();
            newData.headingIsSet = false;
            newData.heading = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleHeading();

            assertEquals(0, actual);
        }
    }

    @Nested
    class HandleLength {
        @Test
        public void NewSet_OldSet_ReturnNew() {

            AISData startData = new AISData();
            startData.lengthIsSet = true;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = true;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleLength();

            assertEquals(50, actual);
        }

        @Test
        public void NewSet_OldNotSet_ReturnNew() {

            AISData startData = new AISData();
            startData.lengthIsSet = false;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = true;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleLength();

            assertEquals(50, actual);
        }

        @Test
        public void NewNotSet_OldNotSet_ReturnPlaceholder() {

            AISData startData = new AISData();
            startData.lengthIsSet = false;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = false;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            ShipHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleLength();

            assertEquals(handler.lengthPlaceHolder, actual);
        }

        @Test
        public void NewNotSet_OldSet_ReturnOld() {

            AISData startData = new AISData();
            startData.lengthIsSet = true;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = false;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            int actual = handler.HandleLength();

            assertEquals(10, actual);
        }

        @Test
        public void NewNotSet_OldSet_OldValueSavedInNewData() {

            AISData startData = new AISData();
            startData.lengthIsSet = true;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = false;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            handler.HandleLength();

            assertEquals(10, newData.length);
        }

        @Test
        public void NewNotSet_OldSet_NewBecomesSet() {

            AISData startData = new AISData();
            startData.lengthIsSet = true;
            startData.length = 10;

            AISData newData = new AISData();
            newData.lengthIsSet = false;
            newData.length = 50;

            Ship myShip = new Ship(startData, 0);

            IShipDataHandler handler = new UpdateShipHandler(myShip, newData, startData, 0, new Hashtable<>());

            handler.HandleLength();

            assertTrue(newData.lengthIsSet);
        }
    }

    @Nested
    class HandleVelocity {

        @Nested
        class New_SOGSet_COGSet {
            @Test
            public void Old_SOGSet_COGSet_UseOnlyNew() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == current);
            }

            @Test
            public void Old_SOGNotSet_COGSet_UseOnlyNew() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == current);
            }

            @Test
            public void Old_SOGSet_COGNotSet_UseOnlyNew() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == current);
            }

            @Test
            public void Old_SOGNotSet_COGNotSet_UseOnlyNew() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == current);
            }
        }

        @Nested
        class New_SOGNotSet_COGSet {

            @Test
            public void Old_SOGSet_COGSet_Use_NewCOG_OldSOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == old && ship.cog == current);
            }

            @Test
            public void Old_SOGNotSet_COGSet_Use_NewCOG_PlaceholderSOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == handler.sogPlaceHolder && ship.cog == current);
            }

            @Test
            public void Old_SOGSet_COGNotSet_Use_NewCOG_OldSOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == old && ship.cog == current);
            }

            @Test
            public void Old_SOGNotSet_COGNotSet_Use_NewCOG_PlaceholderSOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == handler.sogPlaceHolder && ship.cog == current);
            }

        }

        @Nested
        class New_SOGSet_COGNotSet {
            @Test
            public void Old_SOGSet_COGSet_Use_NewSOG_OldCOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == old);
            }

            @Test
            public void Old_SOGNotSet_COGSet_Use_NewSOG_OldCOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == current && ship.cog == old);
            }

            @Test
            public void Old_SOGSet_COGNotSet_Use_NewSOG_PlaceholderCOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == current && ship.cog == handler.cogPlaceholder);
            }

            @Test
            public void Old_SOGNotSet_COGNotSet_Use_NewSOG_PlaceholderCOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = true;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == current && ship.cog == handler.cogPlaceholder);
            }
        }

        @Nested
        class New_SOGNotSet_COGNotSet {
            @Test
            public void Old_SOGSet_COGSet_Use_OnlyOld() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertTrue(ship.sog == old && ship.cog == old);
            }

            @Test
            public void Old_SOGNotSet_COGSet_Use_OldCOG_PlaceholderSOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = true;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == handler.sogPlaceHolder && ship.cog == old);
            }

            @Test
            public void Old_SOGSet_COGNotSet_Use_OldSOG_PlaceholderCOG() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = true;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == old && ship.cog == handler.cogPlaceholder);
            }

            @Test
            public void Old_SOGNotSet_COGNotSet_UseOnlyPlaceholders() {
                int old = 10000;
                int current = 20000;

                AISData oldData = new AISData();
                oldData.SOG = old;
                oldData.sogIsSet = false;

                oldData.COG = old;
                oldData.cogIsSet = false;

                AISData newData = new AISData();
                newData.SOG = current;
                newData.sogIsSet = false;

                newData.COG = current;
                newData.cogIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertTrue(ship.sog == handler.sogPlaceHolder && ship.cog == handler.cogPlaceholder);
            }
        }

        @Nested
        class New_COGSet_HeadingSet {

            @Test
            public void Old_COGSet_HeadingSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGSet_HeadingNotSetSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingNotSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }
        }

        @Nested
        class New_COGNotSet_HeadingSet {
            @Test
            public void Old_COGSet_HeadingSet_UseOldCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(oldCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingSet_UseNewHeading() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentHeading, ship.cog);
            }

            @Test
            public void Old_COGSet_HeadingNotSetSet_UseOldCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(oldCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingNotSet_UseNewHeading() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = true;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentHeading, ship.cog);
            }
        }

        @Nested
        class New_COGSet_HeadingNotSet {
            @Test
            public void Old_COGSet_HeadingSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGSet_HeadingNotSetSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingNotSet_UseNewCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = true;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(currentCOG, ship.cog);
            }
        }

        @Nested
        class New_COGNotSet_HeadingNotSet {
            @Test
            public void Old_COGSet_HeadingSet_UseOldCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(oldCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingSet_UseOldHeading() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = true;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(oldHeading, ship.cog);
            }

            @Test
            public void Old_COGSet_HeadingNotSetSet_UseOldCOG() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = true;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                assertEquals(oldCOG, ship.cog);
            }

            @Test
            public void Old_COGNotSet_HeadingNotSet() {
                int oldCOG = 10000;
                int currentCOG = 20000;

                int oldHeading = 10;
                int currentHeading = 20;

                AISData oldData = new AISData();
                oldData.COG = oldCOG;
                oldData.cogIsSet = false;

                oldData.heading = oldHeading;
                oldData.headingIsSet = false;

                AISData newData = new AISData();
                newData.COG = currentCOG;
                newData.cogIsSet = false;

                newData.heading = currentHeading;
                newData.headingIsSet = false;

                Ship ship = new Ship(oldData, 0);
                ship.Update(newData, 0);

                ShipHandler handler = new UpdateShipHandler();

                assertEquals(handler.cogPlaceholder, ship.cog);
            }
        }
    }
}
