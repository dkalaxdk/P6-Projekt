package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import Dibbidut.Interfaces.*;
import math.geom2d.Vector2D;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CASystem {

    public InputSimulator inputSimulator;
    public ArrayList<Ship> shipsInRange;
    public BlockingQueue<AISData> buffer;

    public IOwnShip OS;
    public Ship ownShip;

    public Display display;
    public IVelocityObstacle MVO;

    public double range;


    public CASystem() {
        buffer = new LinkedBlockingQueue<>();

        try {
            inputSimulator = new InputSimulator(buffer,"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        shipsInRange = new ArrayList<>();
        ownShip = new Ship(new Vector2D(0,0), 20, 5, 0);
        display = new Display(ownShip, shipsInRange);

        range = 10;
    }

    public void Start() {
        inputSimulator.start();
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

            Ship potentialShip = new Ship(data, this.ownShip.longitude);

            // If the potential ship is already out of range,
            // check if there is a reference to the ship in the ship list, remove it if there is,
            // and continue to next iteration
            if (!isWithinRange(potentialShip.position, this.ownShip.position, this.range)) {
                this.shipsInRange.remove(potentialShip);
                continue;
            }

            boolean found = false;
            int i = 0;

            // See if the potential ship is already in the ship list.
            while (!found && i < shipsInRange.size()) {
                Ship ship = shipsInRange.get(i);

                // If the ship is already in the list,
                // update the ship in the list with the data from the potential ship.
                if (ship.mmsi == data.mmsi) {
                    ship.Update(data);
                    found = true;
                }
                else {
                    i++;
                }
            }

            // If it is not in the list, create a new ship, and add it to the list.
            if (!found) {
                shipsInRange.add(potentialShip);
            }
        }

        // Own ship might have moved since the last update,
        // so remove any ships that are now out of range
        RemoveShipsOutOfRange(ownShip.position, shipsInRange, range);
    }

    public boolean isWithinRange(Vector2D shipPosition, Vector2D ownShipPosition, double range) {
        return GetDistance(ownShipPosition, shipPosition) < range;
    }

    public void RemoveShipsOutOfRange(Vector2D ownShipPosition, ArrayList<Ship> ships, double range) {

        ArrayList<Ship> deletionList = new ArrayList<>();

        for (Ship ship : ships) {
            if (!isWithinRange(ownShipPosition, ship.position, range)) {
                deletionList.add(ship);
            }
        }

        ships.removeAll(deletionList);
    }

    public double GetDistance(Vector2D from, Vector2D to) {
        Vector2D difference = to.minus(from);

        return Math.sqrt(Math.pow(difference.x(), 2) + Math.pow(difference.y(), 2));
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
