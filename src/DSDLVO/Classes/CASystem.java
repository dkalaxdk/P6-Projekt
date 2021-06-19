package DSDLVO.Classes;

import DSDLVO.Classes.Geometry.HPoint;
import DSDLVO.Classes.Geometry.Polygon;
import DSDLVO.Classes.InputManagement.AISData;
import DSDLVO.Classes.InputSimulation.InputSimulator;
import DSDLVO.Classes.UI.Display;
import DSDLVO.Classes.UI.GUI;
import DSDLVO.Exceptions.OSNotFoundException;
import DSDLVO.Utilities.Mercator;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CASystem {

    public InputSimulator inputSimulator;
    public ArrayList<Ship> shipsInRange;

    public Ship ownShip;
    public int ownShipMMSI;

    public Display display;
    public GUI gui;
    public VelocityObstacle obstacleCalculator;
    public Hashtable<Ship, Polygon> MVO;

    public double range;
    public double timeFrame;
    public double lookAhead;

    public Float timeFactor;

    public boolean dirty;
    private String inputFile;

    public CASystem(String inputFile, int ownShipMMSI) {
        timeFactor = 0f;
        range = 20000;
        timeFrame = 1;
        lookAhead = 1;

        try {
            // Set time factor and AIS data input file here:
            inputSimulator = new InputSimulator(ownShipMMSI, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputSimulator.SetTimeFactor(timeFactor);

        shipsInRange = new ArrayList<>();
        obstacleCalculator = new VelocityObstacle();
        MVO = new Hashtable<>();
        this.ownShipMMSI = ownShipMMSI;
    }

    public void Start() {
        try {
            inputSimulator.RunSetUp();
        } catch (OSNotFoundException e) {
            e.printStackTrace();
            return;
        }
        inputSimulator.start();

        boolean running = true;
        dirty = false;

        long start;
        long end;
        long duration = 0;

        while (running) {
            start = System.nanoTime();

            UpdateOwnShip();
            UpdateShipList();

            if (dirty) {
                UpdateLookAhead();
                UpdateVelocityObstacles();
                UpdateDisplay();
                dirty = false;
            }

            end = System.nanoTime();

            duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);

            try {
                TimeUnit.MILLISECONDS.sleep(10 - (duration < 0 ? 0 : duration));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void UpdateOwnShip() {
        AISData osData = inputSimulator.inputCollection.getOwnShip();
        if(osData != null) {
            if(ownShip == null) {
                ownShip = new Ship(osData);
            }
            else {
                ownShip.Update(osData);
            }
            dirty = true;
        }
    }

    // Get new ships from buffer, and update exiting ones
    public void UpdateShipList() {
        ArrayList<AISData> dataList = new ArrayList<>(inputSimulator.inputCollection.getTargetShips());

        for (AISData data : dataList) {

            // If the potential ship is already out of range,
            // check if there is a reference to the ship in the ship list, remove it if there is,
            // and continue to next iteration
            HPoint shipPosition = Mercator.projection(data.longitude, data.latitude);
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
                    ship.Update(data);
                    found = true;
                    dirty = true;
                } else {
                    i++;
                }
            }

            // If it is not in the list, create a new ship, and add it to the list.
            if (!found) {
                shipsInRange.add(new Ship(data));
                dirty = true;
            }
        }

        // Own ship might have moved since the last update,
        // so remove any ships that are now out of range
        RemoveShipsOutOfRange(ownShip.position, shipsInRange, range);
    }

    public void UpdateLookAhead() {
        for (Ship ship : shipsInRange) {
            ship.setScaledVelocity(lookAhead);
        }

        ownShip.setScaledVelocity(lookAhead);
    }

    public boolean isWithinRange(HPoint shipPosition, HPoint ownShipPosition, double range) {
        return GetDistance(ownShipPosition, shipPosition) < range;
    }

    public void RemoveShipsOutOfRange(HPoint ownShipPosition, ArrayList<Ship> ships, double range) {

        ArrayList<Ship> deletionList = new ArrayList<>();

        for (Ship ship : ships) {
            if (!isWithinRange(ownShipPosition, ship.position, range)) {
                deletionList.add(ship);
            }
        }

        ships.removeAll(deletionList);
    }

    public double GetDistance(HPoint from, HPoint to) {

        return to.subtract(from).length();
    }

    public void UpdateVelocityObstacles() {

        MVO.clear();

        for (Ship ship : shipsInRange) {

            Polygon domain = ((Polygon) ship.domain.getDomain()).copy();
            domain.referencePoint = ship.position;

            Polygon ownShipDomain = ((Polygon) ownShip.domain.getDomain()).copy();
            ownShipDomain.referencePoint = ownShip.position;

            Polygon combinedDomain = new Polygon(new ArrayList<>());

            combinedDomain = domain.combineWith(ownShipDomain);

//            Polygon vo = (Polygon) obstacleCalculator.RelativeVO(ownShip.position, combinedDomain, ship.position, timeFrame);

            Polygon vo = (Polygon) obstacleCalculator.Calculate(ownShip.position, combinedDomain, ship.position, ship.scaledVelocity, timeFrame);

//            double collisionTime = TimeOFCollision.calculate(combinedDomain, ownShip.position, ownShip.velocity, ship.velocity);
//            System.out.println("Time to collision: " + collisionTime + " seconds");

            MVO.put(ship, vo);
        }
    }

    public void UpdateDisplay() {

        if (display == null) {
            display = new Display(this);
            gui = new GUI(this);

            SwingUtilities.invokeLater(() -> createAndShowGUI());
        }

        display.Update();
    }

    /**
     * Creates the window where the simulation is shown
     */
    private void createAndShowGUI() {
        // Check if we are running on the correct thread (?)
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());

        // Create a new frame for the graphics to be displayed in
        JFrame frame = new JFrame("Display");

        // The application closes when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        // Add content to the frame
        container.add(display);
        container.add(gui);

        frame.add(container);

        // Make the frame as small as its biggest component
        frame.pack();

        // Show the frame
        frame.setVisible(true);
    }
}
