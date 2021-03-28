package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;
import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.MultiPolygon2D;
import math.geom2d.polygon.Polygons2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class Display extends JPanel implements IDisplay {

    private final ArrayList<Ship> ships;
    private final Ship ownShip;

    Random random;

    public Display(Ship ownShip, ArrayList<Ship> shipsInRange) {

//        Timer timer = new Timer(1, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                clearDisplay();
//            }
//        });
//
//        timer.start();

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

        drawGUIElements(g2);
        drawOwnShip(g2, ownShip);
        drawTargetShips(g2, ships);
    }

    private void drawGUIElements(Graphics2D g) {
        g.setColor(Color.gray);

        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
    }

    private void drawOwnShip(Graphics2D g, Ship ship) {
        g.setColor(Color.blue);

        g.draw(drawShip(ship));
        g.draw(drawShipDomain(ship));
    }

    private void drawTargetShips(Graphics2D g, ArrayList<Ship> targetShips) {
        g.setColor(Color.black);

        for (Ship ship : targetShips) {

            g.draw(drawShip(ship));
            g.draw(drawShipDomain(ship));
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

    private Shape drawShipDomain(Ship ship) {
        Vector2D p = getCoordinatesToDrawDomainFrom(ship);

        return ship.domain.getDomainAsPolygon();
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