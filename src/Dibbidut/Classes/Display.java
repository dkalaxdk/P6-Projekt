package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;
import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.MultiPolygon2D;
import math.geom2d.polygon.Polygons2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class Display extends JPanel implements IDisplay {

    private final ArrayList<Ship> ships;
    private final Ship ownShip;

    Random random;

    public Display(Ship ownShip, ArrayList<Ship> shipsInRange) {

        ships = shipsInRange;
        this.ownShip = ownShip;
        random = new Random();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public Ship getOwnShip() {
        return ownShip;
    }

    private void clearDisplay() {
        repaint(0,0,this.getWidth(), this.getHeight());
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }

    private double degreesToRadians(int degrees) {
        return degrees * (Math.PI / 180);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));

        // Translate so that own ship is in the center
        g2.translate(((double) this.getWidth() / 2) - ownShip.position.x(),
                ((double) this.getHeight() / 2) - ownShip.position.y());

        drawGUIElements(g2, ownShip, ships);

        // Rotate so that north is at the top of the screen
        g2.rotate(Math.PI, ownShip.position.x(), ownShip.position.y());

        // Rotate so that own ship is pointing at the top of the screen
//        g2.rotate(Math.PI - degreesToRadians(ownShip.heading), ownShip.position.x(), ownShip.position.y());

        drawOwnShip(g2, ownShip);
        drawTargetShips(g2, ships);
    }

    private void drawGUIElements(Graphics2D g, Ship ownShip, ArrayList<Ship> ships) {
        g.setColor(Color.gray);

        // Horizontal
        g.draw(new Line2D.Double(ownShip.position.x() - this.getWidth(),
                ownShip.position.y(),
                ownShip.position.x() + this.getWidth(),
                ownShip.position.y()));

        // Vertical
        g.draw(new Line2D.Double(ownShip.position.x(),
                ownShip.position.y() - this.getHeight(),
                ownShip.position.x(),
                ownShip.position.y() + this.getHeight()));
    }

    private void drawOwnShip(Graphics2D g, Ship ship) {
        g.setColor(Color.blue);

        g.draw(drawShip(ship));
        g.draw(drawShipDomain(ship));
        g.draw(drawHeading(ship));
    }

    private void drawTargetShips(Graphics2D g, ArrayList<Ship> targetShips) {
        g.setColor(Color.black);

        for (Ship ship : targetShips) {

            g.draw(drawShip(ship));
            g.draw(drawShipDomain(ship));
            g.draw(drawHeading(ship));
        }
    }

    private Shape drawShip(Ship ship) {
        Vector2D p = getCoordinatesToDrawShipFrom(ship);

        Shape shape = new Rectangle2D.Double(p.x(), p.y(), ship.width, ship.length);

        return AffineTransform.getRotateInstance(
                degreesToRadians(ship.heading),
                ship.position.x(),
                ship.position.y())
                .createTransformedShape(shape);
    }

    private Shape drawHeading(Ship ship) {
        Vector2D heading = new Vector2D(0, (double) ship.length / 2);
        heading = heading.rotate(degreesToRadians(ship.heading));
        Vector2D point = ship.position.plus(heading);

        return new Line2D.Double(ship.position.x(), ship.position.y(), point.x(), point.y());
    }

    private Shape drawShipDomain(Ship ship) {
        Vector2D p = getCoordinatesToDrawDomainFrom(ship);

        return ship.domain.getDomainAsEllipse();
    }

    public Vector2D getCoordinatesToDrawShipFrom(Ship ship) {

        double x = ship.position.x() - (((double) ship.width) / 2);
        double y = ship.position.y() - (((double) ship.length) / 2);

        return new Vector2D(x, y);
    }

    public Vector2D getCoordinatesToDrawDomainFrom(Ship ship) {

        double x = ship.position.x() - (ship.domain.getWidth() / 2);
        double y = ship.position.y() - (ship.domain.getHeight() / 2);

        return new Vector2D(x,y);
    }

    @Override
    public void Update() {
        clearDisplay();
    }
}