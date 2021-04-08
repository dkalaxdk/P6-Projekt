import Dibbidut.Classes.*;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.IShipDataHandler;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.security.cert.TrustAnchor;
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

}
