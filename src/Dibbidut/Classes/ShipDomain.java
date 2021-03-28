package Dibbidut.Classes;

import Dibbidut.Interfaces.IDomain;

import java.awt.*;
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
    private final double[] dimensions = new double[5];
    private double Lat;
    private double Long;
    private final Ellipse2D.Double ellipseDomain;
    private Path2D.Double pentagonDomain;


    public ShipDomain(int shipLength, int shipWidth) {
        this.shipLength = shipLength;
        this.shipWidth = shipWidth;
        this.ellipseDomain = new Ellipse2D.Double();
        this.pentagonDomain = new Path2D.Double();

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

    public double[] getDimensions() {
        return dimensions;
    }

    public Ellipse2D.Double getDomainAsEllipse() {
        return ellipseDomain;
    }

    public Path2D getDomainAsPolygon() {
        return pentagonDomain;
    }

    @Override
    public ShipDomain Update(float SOG, float COG, float Lat, float Long) {
        this.Lat = Lat;
        this.Long = Long;
        calculateDiameters(SOG);
        calculateRadii();
        calculateOffsets();
        calculateDimensions();
        updateEllipseDomain();
        updatePentagonDomain();
        return this;
    }

    private void calculateOffsets() {
        starboardOffset = radiusStarboard;
        width = radiusPort + radiusStarboard;
        height = radiusAft + radiusFore;
        aftOffset = radiusAft;
    }

    private void calculateDiameters(float SOG) {
        this.advanceDiameter =  Math.pow(10, 0.3591 * Math.log10(SOG) +0.0952);
        this.tacticalDiameter = Math.pow(10, 0.5441 * Math.log10(SOG) -0.0795);
    }

    private void calculateRadii() {
        this.radiusFore = (1 + 1.34 * Math.sqrt(Math.pow(advanceDiameter,2) + Math.pow(tacticalDiameter /2,2))) * shipLength;
        this.radiusAft =  (1 + 0.67 * Math.sqrt(Math.pow(advanceDiameter,2) + Math.pow(tacticalDiameter /2,2))) * shipLength;
        this.radiusStarboard = (0.2 + tacticalDiameter) * shipLength;
        this.radiusPort = (0.2 + 0.75 * tacticalDiameter) * shipLength;
    }

    private void calculateDimensions(){
        dimensions[0] = 0.9 * radiusStarboard;
        dimensions[1] = -0.9 * radiusPort;
        dimensions[2] = -0.9 * radiusAft;
        dimensions[3] = 0.75 * radiusFore - 0.25 * radiusAft;
        dimensions[4] = 1.1 * radiusFore;
    }

    private void updateEllipseDomain() {

        // Updating the ellipseDomain
        this.ellipseDomain.x = Long;
        this.ellipseDomain.y = Lat;
        this.ellipseDomain.width = width;
        this.ellipseDomain.height = height;
    }
    private void updatePentagonDomain() {
        this.pentagonDomain = new Path2D.Double();
        pentagonDomain.moveTo(Lat + dimensions[1],Long - dimensions[4]);
        pentagonDomain.lineTo(Lat + dimensions[1],Long + dimensions[3]);
        pentagonDomain.lineTo(Lat + dimensions[2],Long);
        pentagonDomain.lineTo(Lat - dimensions[0],Long + dimensions[3]);
        pentagonDomain.lineTo(Lat - dimensions[0], Long - dimensions[4]);
        pentagonDomain.closePath();
    }


}
