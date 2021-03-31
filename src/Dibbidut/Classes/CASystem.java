package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.*;
import math.geom2d.Vector2D;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CASystem {

    public ArrayList<Ship> shipsInRange;
    public BlockingQueue<AISData> buffer;

    public IOwnShip OS;
    public Ship ownShip;

    public Display display;
    public IVelocityObstacle MVO;

    public double range;


    public CASystem() {
        shipsInRange = new ArrayList<>();
        buffer = new LinkedBlockingQueue<>();
        ownShip = new Ship(new Vector2D(0,0), 20, 5, 0);
        display = new Display(ownShip, shipsInRange);

        range = 10;
    }

    public void Start() {

        SwingUtilities.invokeLater(() -> createAndShowGUI(display));

        boolean running = true;

        long start;
        long end;
        long duration = 0;

        while(running) {

            if (buffer.size() > 0) {

                start = System.nanoTime();

                UpdateShipList();
                UpdateVelocityObstacles();
                UpdateDisplay();

                end = System.nanoTime();

                duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10000 - (duration));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            duration = 0;
        }
    }

    public void UpdateOwnShip() {

    }

    // Get new ships from buffer, and update exiting ones
    public void UpdateShipList() {

        while (buffer.size() > 0) {
            AISData data = buffer.poll();

            //TODO: Check if the ship is within range. If it isn't, discard

            boolean found = false;
            int i = 0;

            while (!found && i < shipsInRange.size()) {
                Ship ship = shipsInRange.get(i);

                if (ship.mmsi == data.mmsi) {
                    UpdateShip(ship, data);
                    found = true;
                }

                i++;
            }

            if (!found) {
                shipsInRange.add(new Ship(data, this.ownShip.longitude));
            }
        }

        RemoveShipsOutOfRange(ownShip.position, shipsInRange, range);
    }

    public void RemoveShipsOutOfRange(Vector2D ownShipPosition, ArrayList<Ship> ships, double range) {
        for (Ship ship : shipsInRange) {
            Vector2D toTarget = ship.position.minus(ownShip.position);

            double magnitude = Math.sqrt(Math.pow(toTarget.x(), 2) + Math.pow(toTarget.y(), 2));

            if (magnitude > range) {
                shipsInRange.remove(ship);
            }
        }
    }

    public void UpdateShip(Ship ship, AISData data) {
        // TODO: Update ship with new data
    }

    public void UpdateVelocityObstacles() {

    }

    public void UpdateDisplay() {

    }

    private void createAndShowGUI(Display display) {
        // Check if we are running on the correct thread (?)
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());

        // Create a new frame for the graphics to be displayed in
        JFrame frame = new JFrame("Test Display");

        // The application closes when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the frame, in the form of a display
        frame.add(display);

        // Make the frame as small as its biggest component
        frame.pack();

        // Show the frame
        frame.setVisible(true);
    }
}
