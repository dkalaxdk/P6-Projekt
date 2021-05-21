package DSDLVO.Interfaces;

import DSDLVO.Classes.Geometry.Geometry;
import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Geometry.Point;

public interface IVelocityObstacle {

    Geometry Calculate(Point objectPos, Geometry obstacleDomain, Point obstaclePos, HPoint obstacleVel, double timeFrame);
}
