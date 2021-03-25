package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;
import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.MultiPolygon2D;
import math.geom2d.polygon.Polygons2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


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
        repaint(0,0,1000, 1000);
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

        g2.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        g2.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);

        // Draw OS
        g2.setColor(Color.blue);

        Vector2D drawOwnShipFrom = getCoordinatesToDrawShipFrom(ownShip);

        Shape shape = new Ellipse2D.Double(
                drawOwnShipFrom.x(),
                drawOwnShipFrom.y(),
                this.ownShip.width,
                this.ownShip.length);

        shape = AffineTransform.getRotateInstance(
                degreesToRadians(ownShip.heading),
                ownShip.position.x(),
                ownShip.position.y())
                .createTransformedShape(shape);

        g2.draw(shape);

        Point2D.Double point = new Point2D.Double((this.getWidth() / 2) + 30, (this.getHeight() / 2) + 30);

        Shape shape1 = new Ellipse2D.Double(point.getX() - 3, point.getY() - 3, 6, 6);

        if (shape.contains(point)) {
            g2.setColor(Color.red);
        }

        g2.draw(shape1);

        // Draw TS'sS
        g2.setColor(Color.black);

        for (Ship ship : ships) {

            shape = new Ellipse2D.Double(ship.position.x() + 20, ship.position.y() + 45, 10.0, 10.0);
            g2.draw(shape);

            shape = new Ellipse2D.Double(ship.position.x(), ship.position.y(), 50, 100);
            g2.draw(shape);
        }
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