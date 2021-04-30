package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.Ellipse;
import Dibbidut.Classes.Geometry.Geometry;
import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Interfaces.IDomain;

import java.util.ArrayList;


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
    private double y;
    private double x;
    private Dibbidut.Classes.Geometry.Ellipse ellipseDomain;
    private Dibbidut.Classes.Geometry.Polygon pentagonDomain;
    private final DomainDimensions DomainDimensions;
    private boolean domainType;
    private double Heading;


    public ShipDomain(int shipLength, int shipWidth , String domainType ) {
        if (domainType.equals("Pentagon")) {
            this.domainType = true;
        } else if(domainType.equals("Ellipse")) {
            this.domainType = false;
        } else throw new IllegalArgumentException();
        this.shipLength = shipLength;
        this.shipWidth = shipWidth;
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

    public boolean getDomainType() {
        return domainType;
    }

    public Geometry getDomain() {
        if (domainType) {
            updatePentagonDomain();
            return pentagonDomain;
        }
        updateEllipseDomain();
        return ellipseDomain;
    }
    @Override
    public ShipDomain Update(double SOG, double Heading, double y, double x) {
        this.Heading = Heading;
        this.y = y;
        this.x = x;
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
        width = radiusPort + radiusStarboard;
        height = radiusAft + radiusFore;

        starboardOffset = Math.abs(radiusStarboard - (getWidth() / 2));
        aftOffset = Math.abs(radiusAft - (getHeight() / 2));
    }

    private void calculateDiameters(double SOG) {
        advanceDiameter = Math.pow(10, 0.3591 * Math.log10(SOG) + 0.0952);
        tacticalDiameter = Math.pow(10, 0.5441 * Math.log10(SOG) - 0.0795);
    }

    private void calculateRadii() {
        radiusFore = (1 + 1.34 * Math.sqrt(Math.pow(advanceDiameter, 2) + Math.pow(tacticalDiameter / 2, 2))) * shipLength;
        radiusAft = (1 + 0.67 * Math.sqrt(Math.pow(advanceDiameter, 2) + Math.pow(tacticalDiameter / 2, 2))) * shipLength;
        radiusStarboard = (0.2 + tacticalDiameter) * shipLength;
        radiusPort = (0.2 + 0.75 * tacticalDiameter) * shipLength;
    }

    private void calculateDimensions() {
        DomainDimensions.One = 0.9 * radiusStarboard;
        DomainDimensions.Two = -0.9 * radiusPort;
        DomainDimensions.Three = -0.9 * radiusAft;
        DomainDimensions.Four = 0.75 * radiusFore - 0.25 * radiusAft;
        DomainDimensions.Five = 1.1 * radiusFore;
    }

    private void updateEllipseDomain() {
        this.ellipseDomain = new Ellipse(new HPoint(x + starboardOffset,y + aftOffset,1),width,height);
    }

    private void updatePentagonDomain() {


        ArrayList<HPoint> coordinates = new ArrayList<>();
        // P5
        coordinates.add(new HPoint(x - DomainDimensions.Two, y + DomainDimensions.Three));
        // P4
        coordinates.add(new HPoint(x - DomainDimensions.Two, y + DomainDimensions.Four));
        // P3
        coordinates.add(new HPoint(x, y + DomainDimensions.Five));
        // P2
        coordinates.add(new HPoint(x - DomainDimensions.One, y + DomainDimensions.Four));
        // P1
        coordinates.add(new HPoint(x - DomainDimensions.One, y + DomainDimensions.Three));
        pentagonDomain = new Polygon(coordinates);
    }
}
