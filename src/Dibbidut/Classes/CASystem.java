package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import Dibbidut.Interfaces.*;
import math.geom2d.Vector2D;

import javax.swing.*;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CASystem {

    public InputSimulator inputSimulator;
    public ArrayList<Ship> shipsInRange;
    public BlockingQueue<AISData> tsBuffer;
    public BlockingQueue<AISData> osBuffer;

    public Ship ownShip;
    public int ownShipMMSI;

    public Display display;
    public IVelocityObstacle obstacleCalculator;
    public Area MVO;

    private final Lock bufferLock;

    public double range;
    public double timeFrame;

    public CASystem() {
        osBuffer = new LinkedBlockingQueue<>();
        tsBuffer = new LinkedBlockingQueue<>();

        // Set own ship's MMSI here:
        ownShipMMSI = 211235221;

        bufferLock = new ReentrantLock(true);

        String inputFile = "test/TestFiles/TestInput2.csv";

        try {
            // Set AIS data input file here:
            inputSimulator = new InputSimulator(ownShipMMSI, osBuffer, tsBuffer, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        shipsInRange = new ArrayList<>();
        obstacleCalculator = new VelocityObstacle();
        MVO = new Area();

        range = 100000;
        timeFrame = 200;
    }

    public void Start() {
        inputSimulator.start();

        boolean running = true;

        long start;
        long end;
        long duration = 0;

        while(running) {

            if (tsBuffer.size() > 0) {

                start = System.nanoTime();

                UpdateOwnShip();
                UpdateShipList();
                UpdateVelocityObstacles();
                UpdateDisplay();

                end = System.nanoTime();

                duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(1000 - (duration));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            duration = 0;
        }
    }

    public void UpdateOwnShip() {
        bufferLock.lock();

        if (osBuffer.size() > 0) {

            ArrayList<AISData> dataList = new ArrayList<>();

            osBuffer.drainTo(dataList);

            if (ownShip == null) {
                AISData data = dataList.remove(0);
                ownShip = new Ship(data, data.longitude);
            }

            for (AISData data : dataList) {
                ownShip.Update(data, data.longitude);
            }
        }
    }

    // Get new ships from buffer, and update exiting ones
    public void UpdateShipList() {

        if (ownShip == null) {
            bufferLock.unlock();
            return;
        }

        ArrayList<AISData> dataList = new ArrayList<>();

        tsBuffer.drainTo(dataList);

        bufferLock.unlock();

        for (AISData data : dataList) {

            // If the potential ship is already out of range,
            // check if there is a reference to the ship in the ship list, remove it if there is,
            // and continue to next iteration
            Vector2D shipPosition = Mercator.projection(data.longitude, data.latitude, ownShip.longitude);
            if (!isWithinRange(shipPosition, ownShip.position, range)) {
                this.shipsInRange.remove(new Ship(data.mmsi));
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
                    ship.Update(data, ownShip.longitude);
                    found = true;
                }
                else {
                    i++;
                }
            }

            // If it is not in the list, create a new ship, and add it to the list.
            if (!found) {
                shipsInRange.add(new Ship(data, ownShip.longitude));
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

        MVO.reset();

        for (Ship ship : shipsInRange) {
            Area area = obstacleCalculator.Calculate(this.ownShip, ship, timeFrame);
            MVO.add(area);
        }
    }

    public void UpdateDisplay() {

        if (display == null) {
            display = new Display(ownShip, shipsInRange, MVO);
            SwingUtilities.invokeLater(() -> createAndShowGUI(display));
        }

        display.Update();
    }

    /**
     * Creates the window where the simulation is shows
     * @param display The display that will be shown
     */
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
