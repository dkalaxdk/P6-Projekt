package Dibbidut.Interfaces;

import Dibbidut.Classes.ShipDomain;

import java.awt.*;


public interface IDomain {

    ShipDomain Update(double SOG, double Heading, double y, double x);
    Shape getDomain();
    Shape getScaledShipDomain(double scalar);
    double getHeight();
    double getWidth();

}
