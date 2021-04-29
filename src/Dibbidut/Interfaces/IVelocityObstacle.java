package Dibbidut.Interfaces;

import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.Geometry.Point;
import Dibbidut.Classes.Geometry.Vector;
import Dibbidut.Classes.Obstacle;
import Dibbidut.Classes.Ship;
import Dibbidut.Classes.VO;

import java.awt.geom.Area;

public interface IVelocityObstacle {

    public Geometry Calculate(Point objectPos, Geometry obstacleDomain, Point obstaclePos, Vector obstacleVel, double timeFrame);
    public VO Merge(VO to, VO from);
}
