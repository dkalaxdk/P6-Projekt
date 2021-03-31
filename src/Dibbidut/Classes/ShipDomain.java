package Dibbidut.Classes;

import Dibbidut.Interfaces.IDomain;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;


public class ShipDomain implements IDomain {
    private double height;
    private double width;
    private final int shipLength;
    private final int shipWidth;
    private double advanceDiameter;
    private double tacticalDiameter;
    private double radiusFore;
    private double radiusAft;
    private double radiusStarboard;
    private double radiusPort;
    private double starboardOffset;
    private double aftOffset;
    private double Lat;
    private double Long;
    private final Ellipse2D.Double ellipseDomain;
    private Shape ellipseDomainAsShape;
    private Path2D.Double pentagonDomain;
    private Shape pentagonDomainAsShape;
    private final DomainDimensions DomainDimensions;
    private boolean domainType;
    private float Heading;


    public ShipDomain(int shipLength, int shipWidth , String domainType ) {
        if (domainType.equals("Pentagon")) {
            this.domainType = true;
        } else if(domainType.equals("Ellipse")) {
            this.domainType = false;
        }
        this.shipLength = shipLength;
        this.shipWidth = shipWidth;
        this.ellipseDomain = new Ellipse2D.Double();
        this.pentagonDomain = new Path2D.Double();
        this.DomainDimensions = new DomainDimensions();

    }

    public static class DomainDimensions {
        public double One;
        public double Two;
        public double Three;
        public double Four;
        public double Five;
    }

    public double getAdvanceDiameter() {
        return advanceDiameter;
    }

    public double getTacticalDiameter() {
        return tacticalDiameter;
    }

    public double getRadiusFore() {
        return radiusFore;
    }

    public double getRadiusAft() {
        return radiusAft;
    }

    public double getRadiusPort() {
        return radiusPort;
    }

    public double getRadiusStarboard() {
        return radiusStarboard;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getStarboardOffset() {
        return starboardOffset;
    }

    public double getAftOffset() {
        return aftOffset;
    }

    public DomainDimensions getDimensions() {
        return DomainDimensions;
    }

    public Shape getDomain() {
        if (domainType) {
            return rotateDomain(this.Heading,pentagonDomainAsShape);
        }
        return rotateDomain(this.Heading,ellipseDomainAsShape);
    }
    @Override
    public ShipDomain Update(float SOG, float Heading, float Lat, float Long) {
        this.Heading = Heading;
        this.Lat = Lat;
        this.Long = Long;
        calculateDiameters(SOG);
        calculateRadii();
        calculateOffsets();
        calculateDimensions();
        if (domainType) {
            updatePentagonDomain();
        } else {
            updateEllipseDomain();
        }
        return this;
    }

    private void calculateOffsets() {
        starboardOffset = radiusStarboard;
        aftOffset = radiusAft;
        width = radiusPort + radiusStarboard;
        height = radiusAft + radiusFore;
    }

    private void calculateDiameters(float SOG) {
        advanceDiameter = Math.pow(10, 0.3591 * Math.log10(SOG) + 0.0952);
        tacticalDiameter = Math.pow(10, 0.5441 * Math.log10(SOG) - 0.0795);
    }

    private void calculateRadii() {
        radiusFore = (float)shipLength/2 + (1 + 1.34 * Math.sqrt(Math.pow(advanceDiameter, 2) + Math.pow(tacticalDiameter / 2, 2))) * shipLength;
        radiusAft = (float)shipLength/2 + (1 + 0.67 * Math.sqrt(Math.pow(advanceDiameter, 2) + Math.pow(tacticalDiameter / 2, 2))) * shipLength;
        radiusStarboard = (float)shipWidth/2 + (0.2 + tacticalDiameter) * shipLength;
        radiusPort = (float)shipWidth/2 + (0.2 + 0.75 * tacticalDiameter) * shipLength;
    }

    private void calculateDimensions() {
        DomainDimensions.One = 0.9 * radiusStarboard;
        DomainDimensions.Two = -0.9 * radiusPort;
        DomainDimensions.Three = -0.9 * radiusAft;
        DomainDimensions.Four = 0.75 * radiusFore - 0.25 * radiusAft;
        DomainDimensions.Five = 1.1 * radiusFore;
    }

    private void updateEllipseDomain() {

        // Updating the ellipseDomain
        this.ellipseDomain.x = (Long - aftOffset);
        this.ellipseDomain.y = (Lat - starboardOffset);
        this.ellipseDomain.width = width;
        this.ellipseDomain.height = height;
    }
    private Ellipse2D.Double scaleEllipseDomain(float scalar) {
        Ellipse2D.Double tempEllipse = new Ellipse2D.Double();
        // Updating the ellipseDomain
        tempEllipse.x = (Long - aftOffset);
        tempEllipse.y = (Lat - starboardOffset);
        tempEllipse.width = width / scalar;
        tempEllipse.height = height / scalar;
        return tempEllipse;
    }

    private void updatePentagonDomain() {
        this.pentagonDomain = new Path2D.Double();

        // P5
        pentagonDomain.moveTo(Long - DomainDimensions.One, Lat + DomainDimensions.Three);
        // P4
        pentagonDomain.lineTo(Long - DomainDimensions.One, Lat + DomainDimensions.Four);
        // P3
        pentagonDomain.lineTo(Long, Lat + DomainDimensions.Five);
        // P2
        pentagonDomain.lineTo(Long - DomainDimensions.Two, Lat + DomainDimensions.Four);
        // P1
        pentagonDomain.lineTo(Long - DomainDimensions.Two, Lat + DomainDimensions.Three);

        // Enclose path to create pentagon
        pentagonDomain.closePath();
    }

    private Shape scalePentagonDomain(float scalar) {
        Path2D.Double tempPath = new Path2D.Double();

        // P5
        tempPath.moveTo(Long - DomainDimensions.One/scalar, Lat + DomainDimensions.Three/scalar);
        // P4
        tempPath.lineTo(Long - DomainDimensions.One/scalar, Lat + DomainDimensions.Four/scalar);
        // P3
        tempPath.lineTo(Long, Lat + DomainDimensions.Five/scalar);
        // P2
        tempPath.lineTo(Long - DomainDimensions.Two/scalar, Lat + DomainDimensions.Four/scalar);
        // P1
        tempPath.lineTo(Long - DomainDimensions.Two/scalar, Lat + DomainDimensions.Three/scalar);

        // Enclose path to create pentagon
        tempPath.closePath();

        return tempPath;
    }


    private Shape rotateDomain(float heading, Shape inputShape) {
        return AffineTransform.getRotateInstance(
                Math.toRadians(heading),
                    Long, Lat)
                    .createTransformedShape(inputShape);
    }

    public Shape getScaledShipDomain(float scalar) {
        if (scalar == 0) scalar = 1;
        if (domainType) {
            return rotateDomain(Heading,scalePentagonDomain(scalar));
        }
        return rotateDomain(Heading,scaleEllipseDomain(scalar));
    }

}
