package Dibbidut.Interfaces;

import Dibbidut.Classes.ShipDomain;

import java.awt.*;


public interface IDomain {

    ShipDomain Update(double SOG, double Heading, double Lat, double Long);
    Shape getDomain();
    Shape getScaledShipDomain(double scalar);
    double getHeight();
    double getWidth();

}
