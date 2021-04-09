package Dibbidut.Classes;


import Dibbidut.Interfaces.IRoute;

public class Route implements IRoute {
    public Velocity route;

    public Velocity getVelocity() {
        return null;
    }

    public Velocity Calculate(VelocityObstacle obstacles, Ship ownShip) {
        return new Velocity(0, 0);
    }

}
