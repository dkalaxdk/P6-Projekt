package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.InputSimulator;
import Dibbidut.Classes.UI.Display;
import Dibbidut.Classes.UI.GUI;
import Dibbidut.Exceptions.OSNotFoundException;
import Dibbidut.Exceptions.PolygonNotCenteredOnOrigin;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
    public GUI gui;
    public VelocityObstacle obstacleCalculator;
    public Hashtable<Ship, Polygon> MVO;

    public final Lock bufferLock;
    public final Lock listLock;

    public double range;
    public double timeFrame;
    public double lookAhead;

    public Float timeFactor;

    public boolean dirty;

    public CASystem() {
        osBuffer = new LinkedBlockingQueue<>();
        tsBuffer = new LinkedBlockingQueue<>();

        bufferLock = new ReentrantLock(true);
        listLock = new ReentrantLock(true);

//        System.out.println("Long: " + Mercator.unprojectionX(Mercator.nauticalToMeters(4)));
//        System.out.println("Lat:  " + Mercator.unprojectionY(Mercator.nauticalToMeters(18)));

        // Set own ship's MMSI here:
//        ownShipMMSI = 219004612;
//        String inputFile = "test/TestFiles/TestInput1.csv";

//        ownShipMMSI = 211235221;
//        String inputFile = "test/TestFiles/TestInput2.csv";

        // Near miss at 13:00 (+-)
        // Ship domain too small at 16:00
        // 8:00 spotty connection to a target ship
//        ownShipMMSI = 377739000;
//        String inputFile = "test/BigTestFiles/aisdk_20210208.csv";

        // Paper with specific Aarhus collisions
        // https://www-sciencedirect-com.zorac.aub.aau.dk/science/article/pii/S0029801818308618

//        ownShipMMSI = 218176000;
//        String inputFile = "InputFiles/AarhusEncounter.csv";

//        ownShipMMSI = 219017081;
//        String inputFile = "InputFiles/aisdk_20190510.csv";
//        String inputFile = "InputFiles/EXPRESS_1_&_BALTIC_CONDOR.csv";

//        ownShipMMSI = 219678000;
//        String inputFile = "InputFiles/SKULD_&_ENSCO_72.csv";

//        ownShipMMSI = 212172000;
//        String inputFile = "InputFiles/NECKAR_HIGHWAY_&_ORION.csv";

//        ownShipMMSI = 305369000;
//        String inputFile = "InputFiles/FRANK_&_LILLY.csv";

        // HELLE
//        ownShipMMSI = 219001359;

        // TØNNE
//        ownShipMMSI = 219798000;
//        String inputFile = "InputFiles/TOENNE_&_HELLE.csv";

        // https://doi.org/10.1016/j.oceaneng.2016.11.044
        // #1
//        ownShipMMSI = 1;
//        String inputFile = "InputFiles/Simulation1.csv";


        ownShipMMSI = 1;
        String inputFile = "InputFiles/generated_file.csv";
//        String inputFile = "InputFiles/generated_file_1.csv";

        timeFactor = 0f;
        range = 20000;
        timeFrame = 1;
        lookAhead = 1;

        try {
            // Set time factor and AIS data input file here:
            inputSimulator = new InputSimulator(timeFactor, bufferLock, ownShipMMSI, osBuffer, tsBuffer, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputSimulator.SetTimeFactor(timeFactor);

        shipsInRange = new ArrayList<>();
        obstacleCalculator = new VelocityObstacle();
        MVO = new Hashtable<>();
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

        while(running) {
            start = System.nanoTime();

            listLock.lock();

            UpdateOwnShip();
            UpdateShipList();

            if (dirty) {
                UpdateLookAhead();
                UpdateVelocityObstacles();
                UpdateDisplay();
                dirty = false;
            }

            listLock.unlock();

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
        bufferLock.lock();

        if (osBuffer.size() > 0) {

            ArrayList<AISData> dataList = new ArrayList<>();

            osBuffer.drainTo(dataList);

            if (ownShip == null) {
                AISData data = dataList.remove(0);
                ownShip = new Ship(data);
            }

            for (AISData data : dataList) {
                ownShip.Update(data);
            }

            dirty = true;
        }
    }

    // Get new ships from buffer, and update exiting ones
    public void UpdateShipList() {

        if (ownShip == null || tsBuffer.size() == 0) {
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
                }
                else {
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

            Polygon domain = ((Polygon) ship.domain.getDomain()).makeCopy();
            domain.referencePoint = ship.position;

            Polygon ownShipDomain = ((Polygon) ownShip.domain.getDomain()).makeCopy();
            ownShipDomain.referencePoint = ownShip.position;

            Polygon combinedDomain = new Polygon(new ArrayList<>());

//            try {
////                combinedDomain = domain.flipAndAddPolygon(ownShipDomain);
////                combinedDomain = domain.rotateAndAddPolygon(ownShipDomain);
//            }
//            catch (PolygonNotCenteredOnOrigin e) {
//                e.printStackTrace();
//            }

            combinedDomain = domain.addPolygon2(ownShipDomain);

//            Polygon vo = (Polygon) obstacleCalculator.RelativeVO(ownShip.position, combinedDomain, ship.position, timeFrame);

            Polygon vo = (Polygon) obstacleCalculator.Calculate(ownShip.position, combinedDomain, ship.position, ship.scaledVelocity, timeFrame);


//            Polygon vo = (Polygon) obstacleCalculator.RelativeVO(ownShip.position, ship.domain.getDomain(), ship.position, timeFrame);

//            Polygon vo = (Polygon) obstacleCalculator.Calculate(ownShip.position, ship.domain.getDomain(), ship.position, ship.scaledVelocity, timeFrame);

//            System.out.println("CA: (");
//
//            System.out.println("Domain");
//            ArrayList<HPoint> coordinates = ((Polygon) ship.domain.getDomain()).coordinates;
//
//            for (HPoint p : coordinates) {
//                System.out.println(p.getX() + "\t" + p.getY() + "\r");
//            }
//
//            System.out.println("VO");
//            for (HPoint p : vo.coordinates) {
//                System.out.println(p.getX() + "\t" + p.getY() + "\r");
//            }
//
//            System.out.println("Position");
//            System.out.println(ship.position.getX() + "\t" + ship.position.getY() + "\r");
//
//            System.out.println(")");

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
     * Creates the window where the simulation is shows
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
