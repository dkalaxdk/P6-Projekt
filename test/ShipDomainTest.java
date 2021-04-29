
import Dibbidut.Classes.ShipDomain;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShipDomainTest {
    private ShipDomain shipDomain;
    private StartingValues startingValues;

    private static class StartingValues {
        public double advancedDiameter;
        public double tacticalDiameter;
        public double radiusFore;
        public double radiusAft;
        public double radiusStarboard;
        public double radiusPort;
        public double starboardOffset;
        public double aftOffset;
        public double height;
        public double width;
        public ShipDomain.DomainDimensions Dimensions;
        public StartingValues(ShipDomain shipDomain) {
            this.advancedDiameter = shipDomain.getAdvanceDiameter();
            this.tacticalDiameter = shipDomain.getTacticalDiameter();
            this.radiusFore = shipDomain.getRadiusFore();
            this.radiusAft = shipDomain.getRadiusAft();
            this.radiusStarboard = shipDomain.getRadiusStarboard();
            this.radiusPort = shipDomain.getRadiusPort();
            this.starboardOffset = shipDomain.getStarboardOffset();
            this.aftOffset = shipDomain.getAftOffset();
            this.height = shipDomain.getHeight();
            this.width = shipDomain.getWidth();
            this.Dimensions = new ShipDomain.DomainDimensions();
        }
    }

    @BeforeEach
    public void setUp() {
        shipDomain = new ShipDomain(5,10,"Pentagon");
        startingValues = new StartingValues(shipDomain);
        shipDomain.Update(20,5,100,100);
    }

    @Nested
    @DisplayName("ValuesUpdated")
    class update_ValuesUpdated {
        @Test
        public void testUpdate_ReturnsShipDomain() {
            assertNotNull(shipDomain.Update(20,5,100,100));
        }

        @Test
        public void testUpdate_UpdatedTacticalDiameter() {

            //Assert
            assertNotEquals(shipDomain.getTacticalDiameter(), startingValues.tacticalDiameter);
        }

        @Test
        public void testUpdate_UpdatedAdvanceDiameter() {
            //Assert
            assertNotEquals(shipDomain.getAdvanceDiameter(), startingValues.advancedDiameter);
        }
        @Test
        public void testUpdate_UpdatedRadiusFore() {
            //Assert
            assertNotEquals(shipDomain.getRadiusFore(), startingValues.radiusFore);
        }

        @Test
        public void testUpdate_UpdatedRadiusAft() {

            //Assert
            assertNotEquals(shipDomain.getRadiusAft(),startingValues.radiusAft);
        }

        @Test
        public void testUpdate_UpdatedRadiusStarBoard() {

            //Assert
            assertNotEquals(shipDomain.getRadiusStarboard(),startingValues.radiusStarboard);
        }

        @Test
        public void testUpdate_UpdatedPort() {

            //Assert
            assertNotEquals(shipDomain.getRadiusPort(), startingValues.radiusPort);
        }

        @Test
        public void testUpdate_StarboardOffset() {

            //Assert
            assertNotEquals(shipDomain.getStarboardOffset(), startingValues.starboardOffset);
        }

        @Test
        public void testUpdate_AftOffset() {

            //Assert
            assertNotEquals(shipDomain.getAftOffset(), startingValues.aftOffset);
        }

        @Test
        public void testUpdate_height() {

            //Assert
            assertNotEquals(shipDomain.getHeight(), startingValues.height);
        }

        @Test
        public void testUpdate_width() {

            //Assert
            assertNotEquals(shipDomain.getWidth(), startingValues.width);
        }

        @Test
        public void testUpdate_Dimensions() {

           //Assert
           assertNotEquals(shipDomain.getDimensions().One,startingValues.Dimensions.One);
        }
    }

    @Nested
    @DisplayName("Values updated to Specific result")
    class update_ValuesUpdatedToSpecificResult {
        @Test
        public void testUpdate_UpdatedTacticalDiameter_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getTacticalDiameter(), 4.250014586923374);
        }
        @Test
        public void testUpdate_UpdatedAdvanceDiameter_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getAdvanceDiameter(), 3.65089944412901);
        }

        @Test
        public void testUpdate_UpdatedRadiusFore_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getRadiusFore(), 33.30281972129252);
        }
        @Test
        public void testUpdate_UpdatedRadiusAft_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getRadiusAft(), 19.15140986064626);
        }
        @Test
        public void testUpdate_UpdatedRadiusStarBoard_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getRadiusStarboard(), 22.25007293461687);
        }
        @Test
        public void testUpdate_UpdatedPort_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getRadiusPort(), 16.937554700962654);
        }

        @Test
        public void testUpdate_StarboardOffset_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getStarboardOffset(), 2.6562591168271084);
        }

        @Test
        public void testUpdate_aftOffset_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getAftOffset(), 7.0757049303231305);
        }

        @Test
        public void testUpdate_height_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getHeight(), 52.45422958193878);
        }

        @Test
        public void testUpdate_width_ToCorrectValue() {
            //Assert
            assertEquals(shipDomain.getWidth(), 39.187627635579524);
        }

        @Test
        public void getDomain_Returns_Shape() {
            assertNotNull(shipDomain.getDomain());
        }

    }

}
