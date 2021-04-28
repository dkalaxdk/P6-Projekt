package Dibbidut.Interfaces;

import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.ShipDomain;

import java.awt.*;


public interface IDomain {

    ShipDomain Update(double SOG, double Heading, double y, double x);
    Geometry getDomain();
    boolean getDomainType();
    double getHeight();
    double getWidth();

}
