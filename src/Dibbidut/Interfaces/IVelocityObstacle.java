package Dibbidut.Interfaces;

import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.VO;

public interface IVelocityObstacle {

    public VO Calculate(Obstacle ownShip, Obstacle other);
    public VO Merge(VO to, VO from);
}
