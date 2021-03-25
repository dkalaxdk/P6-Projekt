
import Dibbidut.Classes.ShipDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipDomainTest {
    private ShipDomain shipDomain;
    private StartingValues startingValues;

    private class StartingValues {
        public double AdvancedDiameter;
        public double TacticalDiameter;
        public double RadiusFore;
        public double RadiusAft;
        public double RadiusStarboard;
        public double RadiusPort;
        public double starboardOffset;
        public double aftOffset;
        public double height;
        public double width;
        public double[] Dimensions;
        public StartingValues(ShipDomain shipDomain) {
            this.AdvancedDiameter = shipDomain.getAdvanceDiameter();
            this.TacticalDiameter = shipDomain.getTacticalDiameter();
            this.RadiusFore = shipDomain.getRadiusFore();
            this.RadiusAft = shipDomain.getRadiusAft();
            this.RadiusStarboard = shipDomain.getRadiusStarboard();
            this.RadiusPort = shipDomain.getRadiusPort();
            this.starboardOffset = shipDomain.getStarboardOffset();
            this.aftOffset = shipDomain.getAftOffset();
            this.height = shipDomain.getHeight();
            this.width = shipDomain.getWidth();
            this.Dimensions = shipDomain.getDimensions();
        }
    }

    @BeforeEach
    public void setUp() {
        shipDomain = new ShipDomain(5,10,"Ellipse");
        startingValues = new StartingValues(shipDomain);
    }

    @Test
    public void testUpdate_ReturnsShipDomain() {
        assertNotNull(shipDomain.Update(20,5));
    }

    @Test
    public void testUpdate_UpdatedTacticalDiameter() {
        //Act
        shipDomain.Update(20,5);

        //Assert
        assertNotEquals(shipDomain.getTacticalDiameter(), startingValues.TacticalDiameter);
    }

    @Test
    public void testUpdate_UpdatedAdvanceDiameter() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getAdvanceDiameter(), startingValues.AdvancedDiameter);
    }
    @Test
    public void testUpdate_UpdatedRadiusFore() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getRadiusFore(), startingValues.RadiusFore);
    }

    @Test
    public void testUpdate_UpdatedRadiusAft() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getRadiusAft(),startingValues.RadiusAft);
    }

    @Test
    public void testUpdate_UpdatedRadiusStarBoard() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getRadiusStarboard(),startingValues.RadiusStarboard);
    }

    @Test
    public void testUpdate_UpdatedPort() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getRadiusPort(), startingValues.RadiusPort);
    }

    @Test
    public void testUpdate_StarboardOffset() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getStarboardOffset(), startingValues.starboardOffset);
    }

    @Test
    public void testUpdate_AftOffset() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getAftOffset(), startingValues.aftOffset);
    }

    @Test
    public void testUpdate_height() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getHeight(), startingValues.height);
    }

    @Test
    public void testUpdate_width() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertNotEquals(shipDomain.getWidth(), startingValues.width);
    }

    @Test
    public void testUpdate_Dimensions() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        boolean equals = true;
        for(double dimension: shipDomain.getDimensions()) {
            for (double StartingValue: startingValues.Dimensions) {
                if (dimension == StartingValue) {
                    equals = false;
                    break;
                }
            }
        }
        assertFalse(equals);
    }

    @Test
    public void testUpdate_UpdatedTacticalDiameter_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);

        //Assert
        assertEquals(shipDomain.getTacticalDiameter(), 4.250014586923374);
    }
    @Test
    public void testUpdate_UpdatedAdvanceDiameter_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getAdvanceDiameter(), 3.65089944412901);
    }

    @Test
    public void testUpdate_UpdatedRadiusFore_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getRadiusFore(), 33.30281972129252);
    }
    @Test
    public void testUpdate_UpdatedRadiusAft_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getRadiusAft(), 19.15140986064626);
    }
    @Test
    public void testUpdate_UpdatedRadiusStarBoard_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getRadiusStarboard(), 22.25007293461687);
    }
    @Test
    public void testUpdate_UpdatedPort_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getRadiusPort(), 16.937554700962654);
    }

    @Test
    public void testUpdate_StarboardOffset_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getStarboardOffset(), 22.25007293461687);
    }

    @Test
    public void testUpdate_aftOffset_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getAftOffset(), 19.15140986064626);
    }

    @Test
    public void testUpdate_height_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getHeight(), 52.45422958193878);
    }

    @Test
    public void testUpdate_width_ToCorrectValue() {
        //Act
        shipDomain.Update(20,5);
        //Assert
        assertEquals(shipDomain.getWidth(), 39.187627635579524);
    }

}
