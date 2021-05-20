package SystemTest;

import DSDLVO.Classes.*;
import DSDLVO.Classes.CASystem;
import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Handlers.ShipHandler;
import DSDLVO.Classes.Handlers.UpdateShipHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateVelocityObstacle {

    @Test
    public void TwoShips_ShouldWorkForAllTimeFrames() {
        ShipHandler handler = new UpdateShipHandler();

        double ownShipHeading = 45;
        double targetShipHeading = 270;

        Ship ownShip = new Ship(new HPoint(0,0), 50, 20, (int) ownShipHeading);
        ownShip.sog = 20;
        ownShip.cog = ownShipHeading;
        ownShip.mmsi = 1;
        ownShip.velocity = handler.CalculateVelocity(ownShip.sog, ownShipHeading);
        ownShip.domain.Update(ownShip.sog, ownShipHeading, ownShip.position.getY(), ownShip.position.getX());

        Ship targetShip = new Ship(new HPoint(600, 100), 50, 20, (int) targetShipHeading);
        targetShip.sog = 20;
        targetShip.cog = targetShipHeading;
        targetShip.mmsi = 2;
        targetShip.velocity = handler.CalculateVelocity(targetShip.sog, targetShipHeading);
        targetShip.domain.Update(targetShip.sog, targetShipHeading, targetShip.position.getY(), targetShip.position.getX());


        CASystem caSystem = new CASystem("test/TestFiles/TestInput1.csv",2194005);
        caSystem.ownShipMMSI = 1;
        caSystem.ownShip = ownShip;
        caSystem.shipsInRange.add(targetShip);

        int i = 1;
        try {
            while (i <= 200) {
                caSystem.timeFrame = i;
                caSystem.UpdateVelocityObstacles();
                i++;
            }
        }
        catch (Exception e) {
            System.out.println("Timeframe was: " + i);
            e.printStackTrace();
            fail();
        }
    }
}
