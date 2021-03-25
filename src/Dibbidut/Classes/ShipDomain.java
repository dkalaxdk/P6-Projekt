package Dibbidut.Classes;

import Dibbidut.Interfaces.IDomain;

import java.lang.reflect.Array;

public class ShipDomain implements IDomain {
    private double height;
    private double width;
    private final int shipLength;
    private final int shipWidth;
    private double AdvanceDiameter;
    private double TacticalDiameter;
    private double RadiusFore;
    private double RadiusAft;
    private double RadiusStarboard;
    private double RadiusPort;
    private double starboardOffset;
    private double aftOffset;
    private final String Shape;
    private double[] Dimensions;


    public ShipDomain(int shipLength, int shipWidth, String shape) {
        this.shipLength = shipLength;
        this.shipWidth = shipWidth;
        this.Shape = shape;
    }

    public double getAdvanceDiameter() {
        return AdvanceDiameter;
    }

    public double getTacticalDiameter() {
        return TacticalDiameter;
    }

    public double getRadiusFore() {
        return RadiusFore;
    }

    public double getRadiusAft() {
        return RadiusAft;
    }

    public double getRadiusPort() {
        return RadiusPort;
    }

    public double getRadiusStarboard() {
        return RadiusStarboard;
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

    public ShipDomain Update(float SOG, float COG) {
        calculateDiameters(SOG);
        if (this.Shape.equals("Ellipse")) {
            calculateRadii();
            calculateOffsets();

        } else if (this.Shape.equals("Pentagon")) {
            // Implement code here
            calculateRadii();
            calculateDimensions();
        }
        return this;
    }

    private void calculateOffsets() {
        starboardOffset = RadiusStarboard;
        width = RadiusPort + RadiusStarboard;
        height = RadiusAft + RadiusFore;
        aftOffset = RadiusAft;
    }

    private void calculateDiameters(float SOG) {
        this.AdvanceDiameter =  Math.pow(10, 0.3591 * Math.log10(SOG) +0.0952);
        this.TacticalDiameter = Math.pow(10, 0.5441 * Math.log10(SOG) -0.0795);
    }

    private void calculateRadii() {
        this.RadiusFore = (1+1.34 * Math.sqrt(Math.pow(AdvanceDiameter,2) + Math.pow(TacticalDiameter/2,2))) * shipLength;
        this.RadiusAft =  (1+0.67 * Math.sqrt(Math.pow(AdvanceDiameter,2) + Math.pow(TacticalDiameter/2,2))) * shipLength;
        this.RadiusStarboard = (0.2 + TacticalDiameter) * shipLength;
        this.RadiusPort = (0.2 + 0.75 * TacticalDiameter) * shipLength;
    }

    private void calculateDimensions(){
        Dimensions[0] = 0.9 * RadiusStarboard;
        Dimensions[1] = -0.9 ;
    }

}
