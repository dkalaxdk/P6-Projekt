package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));

        for (Ship ship : ships) {

            Shape shape = new Ellipse2D.Double(ship.position.getX() + 20, ship.position.getY() + 45, 10.0, 10.0);
            g2.draw(shape);

            shape = new Ellipse2D.Double(ship.position.getX(), ship.position.getY(), 50, 100);
            g2.draw(shape);
        }
    }

    public Point2D.Double getCoordinatesToDrawShipFrom(Ship ship) {

        if (ship.heading % 90 == 0) {

        }



        return new Point2D.Double(0,0);
    }

    public Point2D.Double getCoordinatesToDrawDomainFrom(Ship ship) {
        return new Point2D.Double(0,0);
    }

    @Override
    public void Update() {
        clearDisplay();
    }
}