package Dibbidut.Classes;

import math.geom2d.Vector2D;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.System;

public class Main {
    public static void main(String[] args) {
//        DisplayTest();

        CASystem caSystem = new CASystem();
        caSystem.Start();
    }

//    public static void DisplayTest() {
//        Ship ownShip = new Ship(new Vector2D(500, 500), 100, 50, 0);
//        ownShip.heading = 0;
//        ownShip.length = 100;
//        ownShip.width = 50;
//        ownShip.domain = new ShipDomain(50, 6,"Ellipse");
//        ArrayList<Ship> s = new ArrayList<>();
//
//        double ownShipLong = 9.87823;
//
//        Vector2D tomasimo = Mercator.projection(9.87823, 57.0565, ownShipLong);
//        Vector2D bijou = Mercator.projection(9.87835, 57.05677, ownShipLong);
//        Vector2D ladyBird = Mercator.projection(9.87856, 57.05646, ownShipLong);
//        Vector2D missJane = Mercator.projection(9.87754, 57.0562, ownShipLong);
//
//
//
//
//        Ship ownShip = new Ship(tomasimo, 9, 3, 37);
//        ownShip.domain = new ShipDomain(ownShip.length, ownShip.width);
//        ArrayList<Ship> s = new ArrayList<>();
//
//        Display display = new Display(ownShip, s);
//
//        SwingUtilities.invokeLater(() -> createAndShowGUI(display));
//
//        s.add(new Ship(bijou, 11, 4, 284));
//        s.add(new Ship(ladyBird, 11, 3, 170));
//        s.add(new Ship(missJane, 15, 5, 0));
//
//            s.get(2).position = new Vector2D((-i % 100) + 300, 600);
//
//            display.Update();
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            i = (i + 1) % 500;
//        }
//    }


//    private static void createAndShowGUI(Display panel) {
//        // Check if we are running on the correct thread (?)
//        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
//
//        // Create a new frame for the graphics to be displayed in
//        JFrame frame = new JFrame("Test Display");
//
//        // The application closes when the window is closed
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Add content to the frame, in the form of a panel
//        frame.add(panel);
//
//        // Make the frame as small as its biggest component
//        frame.pack();
//
//        // Show the frame
//        frame.setVisible(true);
//    }
}
