package DSDLVO.Interfaces;

import DSDLVO.Classes.Geometry.HPoint;

public interface IShipDataHandler {
    int HandleMMSI();

    int HandleHeading();

    int HandleLength();

    int HandleWidth();

    HPoint HandleVelocity();

    HPoint HandlePosition();

    double HandleSOG();

    double HandleCOG();

    double HandleLongitude();

    double HandleLatitude();

    void Run();
}
