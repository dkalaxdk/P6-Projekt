package Dibbidut.Interfaces;

import Dibbidut.Classes.Ship;
import Dibbidut.Classes.Velocity;
import Dibbidut.Classes.VelocityObstacle;

public interface IRoute {

    public Velocity getVelocity();
    public Velocity Calculate(VelocityObstacle obstacles, Ship ownShip);
}
