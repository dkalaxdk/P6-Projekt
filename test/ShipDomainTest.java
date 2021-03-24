
import Dibbidut.Classes.ShipDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipDomainTest {
    private ShipDomain shipDomain;
    private StartingValues startingValues;

    private class StartingValues {
        public double AdvancedDiameter = shipDomain.getAdvanceDiameter();
        public double TacticalDiameter = shipDomain.getTacticalDiameter();
        public double RadiusFore = shipDomain.getRadiusFore();
        public double RadiusAft = shipDomain.getRadiusAft();
        public double RadiusStarboard = shipDomain.getRadiusStarboard();
        public double RadiusPort = shipDomain.getRadiusPort();
        public StartingValues(ShipDomain shipDomain) {
            this.AdvancedDiameter = shipDomain.getAdvanceDiameter();
            this.TacticalDiameter = shipDomain.getTacticalDiameter();
            this.RadiusFore = shipDomain.getRadiusFore();
            this.RadiusAft = shipDomain.getRadiusAft();
            this.RadiusStarboard = shipDomain.getRadiusStarboard();
            this.RadiusPort = shipDomain.getRadiusPort();
        }
    }

    @BeforeEach
    public void setUp() {
        shipDomain = new ShipDomain(5,10);
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

}
