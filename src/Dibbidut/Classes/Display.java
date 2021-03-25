package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));

        for (Ship ship : ships) {
            g2.fillOval(ship.position.x + 20, ship.position.y + 45, 10, 10);
            g2.drawOval(ship.position.x, ship.position.y, 50, 100);
        }
    }

    @Override
    public void Update() {
        clearDisplay();
    }
}