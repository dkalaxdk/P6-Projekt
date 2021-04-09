package Dibbidut.Interfaces;

import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.VO;

import java.awt.geom.Area;

public interface IVelocityObstacle {

    public Area Calculate(Ship ownShip, Ship other, double timeFrame);
    public VO Merge(VO to, VO from);
}
