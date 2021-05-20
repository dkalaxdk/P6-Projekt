package DSDLVO.Interfaces;

import DSDLVO.Classes.Ship;
import DSDLVO.Classes.Velocity;
import DSDLVO.Classes.VelocityObstacle;

public interface IRoute {

    Velocity getVelocity();

    Velocity Calculate(VelocityObstacle obstacles, Ship ownShip);
}
