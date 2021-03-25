package Dibbidut.Classes;

import math.geom2d.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.System;

public class Main {
    public static void main(String[] args) {
        DisplayTest();
    }

    public static void DisplayTest() {
        Ship ownShip = new Ship(500,500);
        ownShip.heading = 0;
        ownShip.length = 100;
        ownShip.width = 50;
        ArrayList<Ship> s = new ArrayList<>();

        Display display = new Display(ownShip, s);

        SwingUtilities.invokeLater(() -> createAndShowGUI(display));

        s.add(new Ship(new Vector2D(0,0)));
        s.add(new Ship(new Vector2D(0,0)));
        s.add(new Ship(new Vector2D(0,0)));

        Random rand = new Random();

        int i = 0;
        while (true) {
            ownShip.heading = (ownShip.heading + 1) % 360;
            ownShip.position = ownShip.position.plus(new Vector2D(1, 0));

            s.get(0).position = new Vector2D(100, (i % 60) + 30);

            s.get(1).position = new Vector2D((i % 30) + 700, 300);

            s.get(2).position = new Vector2D((-i % 100) + 300, 600);

            display.Update();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i = (i + 1) % 500;
        }
    }


    private static void createAndShowGUI(Display panel) {
        // Check if we are running on the correct thread (?)
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());

        // Create a new frame for the graphics to be displayed in
        JFrame frame = new JFrame("Test Display");

        // The application closes when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the frame, in the form of a panel
        frame.add(panel);

        // Make the frame as small as its biggest component
        frame.pack();

        // Show the frame
        frame.setVisible(true);
    }
}
