package DSDLVO.Interfaces;

import DSDLVO.Classes.Geometry.Geometry;
import DSDLVO.Classes.ShipDomain;


public interface IDomain {

    ShipDomain Update(double SOG, double Heading, double y, double x);
    Geometry getDomain();
    boolean getDomainType();
    double getHeight();
    double getWidth();

}
