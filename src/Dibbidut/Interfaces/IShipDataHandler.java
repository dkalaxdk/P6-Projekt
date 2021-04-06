package Dibbidut.Interfaces;

import math.geom2d.Vector2D;

public interface IShipDataHandler {
    int HandleMMSI();
    int HandleHeading();
    int HandleLength();
    int HandleWidth();

    Vector2D HandleVelocity();
    Vector2D HandlePosition();

    Vector2D HandleCentering();

    double HandleSOG();
    double HandleCOG();
    double HandleLongitude();
    double HandleLatitude();

    void Run();
}
