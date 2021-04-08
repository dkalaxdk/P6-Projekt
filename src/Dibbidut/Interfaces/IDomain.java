package Dibbidut.Interfaces;

import Dibbidut.Classes.ShipDomain;

import java.awt.*;


public interface IDomain {

    ShipDomain Update(float SOG, float COG, float Lat, float Long);
    Shape getDomain();
    Shape getScaledShipDomain(float scalar);
    double getHeight();
    double getWidth();

}
