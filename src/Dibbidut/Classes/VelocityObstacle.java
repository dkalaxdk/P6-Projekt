package Dibbidut.Classes;

import Dibbidut.Interfaces.IVelocityObstacle;

import java.util.ArrayList;

public class VelocityObstacle implements IVelocityObstacle {
    public VO velocities;

    @Override
    public VO Calculate(Obstacle ownShip, Obstacle other) {
        return null;
    }

    @Override
    public VO Merge(VO to, VO from) {
        return null;
    }
}
